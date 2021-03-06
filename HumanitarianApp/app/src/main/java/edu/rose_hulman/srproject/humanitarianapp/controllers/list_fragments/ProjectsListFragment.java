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
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
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
    private HashMap<Long, Project> projects = new HashMap<>();
    ListArrayAdapter<Project> adapter;
    private boolean showHidden=false;
    public ProjectsListFragment(){
    }


    @Override
    public ListArrayAdapter<Project> getAdapter() {
        adapter = new ListArrayAdapter<Project>(getActivity(),
                android.R.layout.simple_list_item_1, getItems()) {

            @Override
            public View customiseView(View v, Project project) {
                TextView line1 = (TextView) v.findViewById(android.R.id.text1);
                line1.setText(project.getName()+" ("+project.getID()+")");
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
        updateItems();

    }

    @Override
    public void updateItems() {
        List<Project> overallProjects = ApplicationWideData.getAllProjects();
        //Yes the big theta is horrid and makes CSs cry but getting it coded
        // fast is more important than getting it coded right.
        for(Project existingProject: overallProjects){
            if(existingProject == null){
                continue;
            }
            projects.put(existingProject.getID(), existingProject);
        }


        if (!ApplicationWideData.getManualSync()){
            NonLocalDataService service = new NonLocalDataService();
            showHidden = mListener.getShowHidden();
//            Log.wtf(mListener.getUserID(), "USER ID");
            service.service.getProjectList(mListener.getUserID(), showHidden, new ProjectListCallback());
        } else{
            loadList();
        }
    }

    public void loadList(){
        if(adapter == null) {
            return;
        }
        adapter.clear();
        for(Long l: projects.keySet()){
            adapter.add(projects.get(l));
        }
        adapter.notifyDataSetChanged();
        ApplicationWideData.addProjectHashMap(projects);
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
//        Log.wtf("s40", "Project Selected");
        mListener.onItemSelected(project);
    }

    public void checkForArgs() {

    }

    public List<Project> getItems() {
        List<Project> l=new ArrayList<>();
        l.addAll(projects.values());
        return l;
    }

    public interface ProjectsListListener extends Interfaces.UserIDGetter{
        void onItemSelected(Project t);
        boolean getShowHidden();

    }

    public class ProjectListCallback implements Callback<Response> {

        @Override
        public void success(Response response, Response response2) {
//            Log.wtf("URL", response.getUrl());
//            Log.wtf("SUCCESS", "PRJListCallbacks");
            ObjectMapper mapper = new ObjectMapper();
            List<Project> projectList;

            if (adapter != null) {
                projectList = adapter.getList();
            } else {
                projectList = getItems();
            }
            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
//                    Log.w("Found a project", map.toString());
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Project p=Project.parseJSON(Long.parseLong(((String) map.get("_id"))), source);

                    ApplicationWideData.addExistingProject(p);
                    LocalDataSaver.saveProject(p);
                    projects.put(p.getID(),p);

                }
                adapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadList();
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitErrorPLF", error.getMessage());
            loadList();
        }
    }

}