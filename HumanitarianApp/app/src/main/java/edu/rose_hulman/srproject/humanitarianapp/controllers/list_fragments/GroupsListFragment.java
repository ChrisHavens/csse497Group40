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

import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
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
public class GroupsListFragment extends AbstractListFragment<Group>{
    protected GroupsListListener mListener;
    ArrayList<Group> groups=new ArrayList<>();
    ListArrayAdapter<Group> adapter;
    public GroupsListFragment(){

    }


    @Override
    public ListArrayAdapter<Group> getAdapter() {
        adapter=new ListArrayAdapter<Group>(getActivity(),
                android.R.layout.simple_list_item_1, groups){

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
        NonLocalDataService service=new NonLocalDataService();
        service.getAllGroups(mListener.getSelectedProject(), new GroupListCallback());
//        Group a=new Group("Group 40", mListener.getSelectedProject());
//        Group b=new Group("Group 41", mListener.getSelectedProject());
//        groups.add(a);
//        groups.add(b);

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
    public class GroupListCallback implements Callback<Response> {

        @Override
        public void success(Response response, Response response2) {
            Log.e("here", "success");
            ObjectMapper mapper=new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference=
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o=mapper.readValue(response.getBody().in(), typeReference);

                ArrayList<HashMap<String, Object>> list=(ArrayList)((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map: list){

                    HashMap<String, Object> source=(HashMap)map.get("_source");
                    for (String s: source.keySet()){
                        Log.e("Result", s);
                    }
                    Group p=new Group(Integer.parseInt(((String)map.get("_id")).substring(3)));
                    p.setName((String) source.get("name"));
                    groups.add(p);
                    adapter.notifyDataSetChanged();
                    //adapter.add(p);

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
    public interface GroupsListListener{
        void onItemSelected(Group t);
        Project getSelectedProject();
    }


}