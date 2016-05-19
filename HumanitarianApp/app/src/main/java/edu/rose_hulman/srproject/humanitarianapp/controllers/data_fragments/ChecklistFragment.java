package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChecklistFragment.ChecklistFragmentListener} interface
 * to handle interaction events.

 */
public class ChecklistFragment extends Fragment implements AbsListView.OnItemClickListener{

    private ChecklistFragmentListener mListener;
    private Checklist checklist;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListArrayAdapter<Checklist.ChecklistItem> mAdapter;

    public ChecklistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checklist=mListener.getSelectedChecklist();

        mAdapter=new ListArrayAdapter<Checklist.ChecklistItem>(getActivity(),
            R.layout.list_checklist, checklist.getItemList()){

                @Override
                public View customiseView(View v, final Checklist.ChecklistItem item) {
                    CheckBox box=(CheckBox)v.findViewById(R.id.checkBox);
                    box.setText(item.getCheckBoxInfoString());
                    box.setChecked(item.isDone());
                    box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            item.setDone(isChecked);
                        }
                    });
                    LinearLayout layout = (LinearLayout)v.findViewById(R.id.sublistView);
                    for (final Checklist.SublistItem sublistItem: item.getSublistItems()) {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.list_subchecklist, null);
                        CheckBox box2 = (CheckBox) child.findViewById(R.id.checkBox);
                            box2.setText(sublistItem.getCheckBoxInfoString());
                            box2.setChecked(sublistItem.isDone());
                            box2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    sublistItem.setDone(isChecked);
                                }
                            });

                        layout.addView(child);
                    }
//
//                            AbsListView listView = (AbsListView) v.findViewById(R.id.sublistView);
//                    ListArrayAdapter<Checklist.SublistItem> adapter=
//                            new ListArrayAdapter<Checklist.SublistItem>(getActivity(),
//                                    R.layout.list_subchecklist, item.getSublistItems()) {
//                        @Override
//                        public View customiseView(View v, final Checklist.SublistItem object) {
//                            CheckBox box=(CheckBox)v.findViewById(R.id.checkBox);
//                            box.setText(object.getCheckBoxInfoString());
//                            box.setChecked(object.isDone());
//                            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                @Override
//                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                    object.setDone(isChecked);
//                                }
//                            });
//                            return v;
//                        }
//                    };
//                    Log.wtf("count", adapter.getCount()+"");
//                    listView.setAdapter(adapter);
                    return v;
                }
            };


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checklist, container, false);
        TextView title=(TextView) view.findViewById(R.id.title);
        Button b= (Button) view.findViewById(R.id.okButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editChecklist(checklist);
            }
        });
        title.setText(checklist.getTitle());
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ChecklistFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ChecklistFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

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
    public interface ChecklistFragmentListener {

       Checklist getSelectedChecklist();
        void editChecklist(Checklist c);
    }

}
