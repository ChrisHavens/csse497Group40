package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class AbstractListFragment<T> extends Fragment implements AbsListView.OnItemClickListener{


    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListArrayAdapter<T> mAdapter;

    public AbstractListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkForArgs();


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
        mListView.setAdapter(mAdapter);
        View emptyView = inflater.inflate(R.layout.list_empty, container, false);
        TextView emptyText=(TextView) emptyView.findViewById(R.id.emptyViewText);
        String format1=getResources().getString(R.string.noneFound);

        emptyText.setText(String.format(format1, getItemName()));
        mListView.setEmptyView(emptyView);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.wtf("s40", "Selected item");
        onItemSelected(mAdapter.getItem(position));
    }
    public abstract ListArrayAdapter<T> getAdapter();

    public abstract String getTitle();


    public abstract void onItemSelected(T t);

    public abstract void checkForArgs();

    private String getItemName(){
        return getTitle().toLowerCase();
    }
    public abstract List<T> getItems();

    public void refresh(){
        updateItems();
        mAdapter.clear();
        mAdapter.addAll(getItems());
    }
    public abstract void updateItems();

    public abstract void loadList();


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
