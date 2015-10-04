package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

import android.app.Activity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.controllers.ListSelectable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Worker;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link ListSelectable}
 * interface.
 */
public class ChecklistsListFragment extends AbstractListFragment<Checklist> {
    protected ChecklistsListListener mListener;
    ArrayList<Checklist> checklists=new ArrayList<>();
    public ChecklistsListFragment(){
        Worker aWorker=new Worker("Alice Jones", "555-555-5555");
        Worker bWorker=new Worker("Bob Smith", "555-555-5556");
        Checklist a=new Checklist("To-do for 11/15");

        a.addItem(new Checklist.ChecklistItem("Pick up Potatoes", aWorker));
        a.addItem(new Checklist.ChecklistItem("Build a house",true, bWorker));
        Checklist b=new Checklist("To-do for 11/16");
        b.addItem(new Checklist.ChecklistItem("Cook dinner"));
        checklists.add(a);
        checklists.add(b);

    }


    @Override
    public ListArrayAdapter<Checklist> getAdapter() {
        ListArrayAdapter<Checklist> adapter=new ListArrayAdapter<Checklist>(getActivity(),
            android.R.layout.simple_list_item_1, getItems()){

        @Override
        public View customiseView(View v, Checklist checklist) {
            TextView line1=(TextView) v.findViewById(android.R.id.text1);
            line1.setText(checklist.getTitle());
            return v;
        }
    };
        return adapter;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ChecklistsListListener) getParentFragment();
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
    public String getTitle() {
        return null;
    }

    @Override
    public void onItemSelected(Checklist checklist) {
        mListener.onItemSelected(checklist);
    }

    public List<Checklist> getItems(){

        return checklists;
    }
    public interface ChecklistsListListener{
        void onItemSelected(Checklist t);
    }


}