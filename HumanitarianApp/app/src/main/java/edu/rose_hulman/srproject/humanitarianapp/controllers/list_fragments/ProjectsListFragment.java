package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link Backable}
 * interface.
 */
public class ProjectsListFragment extends AbstractListFragment<Project>{
    protected ProjectsListListener mListener;
    private ArrayList<Project> projects=new ArrayList<>();
    public ProjectsListFragment(){
        Project project=new Project(1);
        project.setName("Project 1");
        Project project2=new Project(2);
        project2.setName("Project 2");
        projects.add(project);
        projects.add(project2);
    }


    @Override
    public ListArrayAdapter<Project> getAdapter() {
        ListArrayAdapter<Project> adapter=new ListArrayAdapter<Project>(getActivity(),
                android.R.layout.simple_list_item_1, getItems()){

            @Override
            public View customiseView(View v, Project project) {
                TextView line1=(TextView) v.findViewById(android.R.id.text1);
                line1.setText(project.getName());
                return v;
            }
        };
        return adapter;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ProjectsListListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
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
    public void onItemSelected(Project project) {
        Log.wtf("s40", "Project Selected");
        mListener.onItemSelected(project);
    }
    public List<Project> getItems(){

        return projects;
    }
    public interface ProjectsListListener{
        void onItemSelected(Project t);
    }

}