package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.app.Activity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.controllers.ListSelectable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Worker;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link ListSelectable}
 * interface.
 */
public class PeopleListFragment extends AbstractListFragment<Worker>{
    protected PeopleListListener mListener;
    ArrayList<Worker> workers=new ArrayList<>();
    public PeopleListFragment(){
        Worker a=new Worker("Alice Jones", "555-555-5555");
        a.setLastCheckin(new Location("Little Village"));
        workers.add(a);
        Worker b=new Worker("Bob Smith", "555-555-5556");
        b.setLastCheckin(new Location("HQ"));
        workers.add(b);
    }


    @Override
    public ListArrayAdapter<Worker> getAdapter() {
        ListArrayAdapter<Worker> adapter=new ListArrayAdapter<Worker>(getActivity(),
                android.R.layout.simple_list_item_2, getItems()){

            @Override
            public View customiseView(View v, Worker worker) {
                TextView line1=(TextView) v.findViewById(android.R.id.text1);
                TextView line2=(TextView) v.findViewById(android.R.id.text2);
                line1.setText(worker.getName());
                line2.setText(worker.getLastCheckin().getName());
                return v;
            }
        };
        return adapter;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PeopleListListener) getParentFragment();
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
    public void onItemSelected(Worker worker) {
        mListener.onItemSelected(worker);
    }

    public List<Worker> getItems(){

        return workers;
    }
    public interface PeopleListListener{
        void onItemSelected(Worker t);
    }


}