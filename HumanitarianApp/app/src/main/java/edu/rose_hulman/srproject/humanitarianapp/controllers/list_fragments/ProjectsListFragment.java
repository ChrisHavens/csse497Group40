package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;

import edu.rose_hulman.srproject.humanitarianapp.controllers.Interfaces;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataLoader;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataRetriver;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class ProjectsListFragment extends AbstractListFragment<Project> {
    protected ProjectsListListener mListener;
    private List<Project> projects = new ArrayList<>();
    ListArrayAdapter<Project> adapter;
    private boolean showHidden=false;
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
        adapter = new ListArrayAdapter<Project>(getActivity(),
                android.R.layout.simple_list_item_1, projects) {

            @Override
            public View customiseView(View v, Project project) {
                TextView line1 = (TextView) v.findViewById(android.R.id.text1);
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
            mListener = (ProjectsListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener == null) {
            throw new NullPointerException("Parent fragment is null");
        }

            for (Project project : projects) {
                Log.wtf("s40 List fragment", "Found this many things " + Integer.toString(project.getGroups().size()));
            }
            if (this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }
         if (!ApplicationWideData.getManualSync()){
            NonLocalDataService service = new NonLocalDataService();
            showHidden = mListener.getShowHidden();
            Toast.makeText(this.getActivity(), mListener.getUserID(), Toast.LENGTH_LONG).show();
            //if (mListener.getUserID().equals("-1")){
            service.service.getProjectList(mListener.getUserID(), showHidden, new ProjectListCallback());
            //}
            //else {
            //    service.service.getProjectList(mListener.getUserID(), showHidden, new ProjectListCallback());
            //}
        }
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

    public void checkForArgs() {

    }

    public List<Project> getItems() {

        return projects;
    }

    public interface ProjectsListListener extends Interfaces.UserIDGetter{
        void onItemSelected(Project t);
        boolean getShowHidden();

    }

    public class ProjectListCallback implements Callback<Response> {

        @Override
        public void success(Response response, Response response2) {
            Log.wtf("URL", response.getUrl());
            Log.wtf("SUCCESS", "PRJListCallbacks");
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    Log.w("Found a project", map.toString());
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Project p = new Project(Integer.parseInt(((String) map.get("_id"))));
                    p.setName((String) source.get("name"));
                    if(source.get("dateArchived") == null)
                        p.setHidden(false);
                    else
                        p.setHidden(true);
                    ApplicationWideData.addExistingProject(p);
                    LocalDataSaver.addProject(p);
                    projects.add(p);

                }
//                if (list.size() > 0) {
                    projects = ApplicationWideData.getAllProjects();
//                }
                adapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitErrorPLF", error.getMessage());
        }
    }

}