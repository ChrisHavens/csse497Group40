package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


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
    ListArrayAdapter<Project> adapter;
    public ProjectsListFragment(){
//        Project project=new Project(1);
//        project.setName("Project 1");
//        Project project2=new Project(2);
//        project2.setName("Project 2");
//        projects.add(project);
//        projects.add(project2);
    }


    @Override
    public ListArrayAdapter<Project> getAdapter() {
        adapter=new ListArrayAdapter<Project>(getActivity(),
                android.R.layout.simple_list_item_1, projects){

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
        NonLocalDataService service=new NonLocalDataService();
        service.getAllProjects(new ProjectListCallback());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.projects);
    }

    @Override
    public void onItemSelected(Project project) {
        Log.wtf("s40", "Project Selected");
        mListener.onItemSelected(project);
    }
    public void checkForArgs(){

    }
    public List<Project> getItems(){

        return projects;
    }
    public interface ProjectsListListener{
        void onItemSelected(Project t);
    }
    public class ProjectListCallback implements Callback<Response>{

        @Override
        public void success(Response response, Response response2) {
            Log.wtf("SUCCESS", "PRJListCallbacks");
            ObjectMapper mapper=new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference=
                    new TypeReference<HashMap<String, Object>>() {
            };
            try {
                HashMap<String, Object> o=mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list=(ArrayList)((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map: list){
                    Log.w("Found a project", map.toString());
                    HashMap<String, Object> source=(HashMap)map.get("_source");

                    Project p=new Project(Integer.parseInt(((String)map.get("_id"))));
                    p.setName((String)source.get("name"));
                   projects.add(p);
                    adapter.notifyDataSetChanged();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitError", error.getMessage());
        }
    }

}