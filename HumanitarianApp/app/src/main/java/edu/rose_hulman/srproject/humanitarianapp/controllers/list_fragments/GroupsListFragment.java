package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link Backable}
 * interface.
 */
public class GroupsListFragment extends AbstractListFragment<Group>{
    protected GroupsListListener mListener;
    ArrayList<Group> groups=new ArrayList<>();
    public GroupsListFragment(){

    }


    @Override
    public ListArrayAdapter<Group> getAdapter() {
      ListArrayAdapter<Group> adapter=new ListArrayAdapter<Group>(getActivity(),
        android.R.layout.simple_list_item_1, getItems()){

            @Override
            public View customiseView(View v, Group group) {
                TextView line1=(TextView) v.findViewById(android.R.id.text1);
                line1.setText(group.getName());
                return v;
            }
        };
        return adapter;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (GroupsListListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
        Group a=new Group("Group 40", mListener.getSelectedProject());
        Group b=new Group("Group 41", mListener.getSelectedProject());
        groups.add(a);
        groups.add(b);

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
    public void onItemSelected(Group group) {
        mListener.onItemSelected(group);
    }

    public List<Group> getItems(){

        return groups;
    }
    public interface GroupsListListener{
        void onItemSelected(Group t);
        Project getSelectedProject();
    }


}