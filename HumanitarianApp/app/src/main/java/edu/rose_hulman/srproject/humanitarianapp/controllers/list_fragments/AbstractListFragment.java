package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.ListSelectable;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class AbstractListFragment<T> extends Fragment implements AbsListView.OnItemClickListener{
    private ListSelectable<T> mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public AbstractListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Change Adapter to display your content
        mAdapter = getAdapter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        TextView title=(TextView) view.findViewById(R.id.title);
        title.setText(getTitle());
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.listView);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ListSelectable<T>) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.selectItem(position);
    }
    public abstract ListAdapter getAdapter();

    public abstract String getTitle();

    public abstract void selectItem(int position);

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
