package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link Backable}
 * interface.
 */
public class PeopleListFragment extends AbstractListFragment<Person>{
    protected PeopleListListener mListener;
    ArrayList<Person> persons =new ArrayList<>();
    public PeopleListFragment(){
        Person a=new Person("Alice Jones", "555-555-5555");
        a.setLastCheckin(new Location("Little Village"));
        persons.add(a);
        Person b=new Person("Bob Smith", "555-555-5556");
        b.setLastCheckin(new Location("HQ"));
        persons.add(b);
    }


    @Override
    public ListArrayAdapter<Person> getAdapter() {
        ListArrayAdapter<Person> adapter=new ListArrayAdapter<Person>(getActivity(),
                android.R.layout.simple_list_item_2, getItems()){

            @Override
            public View customiseView(View v, Person worker) {
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
    public void onItemSelected(Person person) {
        mListener.onItemSelected(person);
    }

    public List<Person> getItems(){

        return persons;
    }
    public interface PeopleListListener{
        void onItemSelected(Person t);
    }


}