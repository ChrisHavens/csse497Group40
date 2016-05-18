package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.app.Activity;
import android.app.Application;
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
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
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
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class GroupsListFragment extends AbstractListFragment<Group>{
    protected GroupsListListener mListener;
    HashMap<Long, Group> groups=new HashMap<>();
    ListArrayAdapter<Group> adapter;
    private boolean showHidden=false;
    public GroupsListFragment(){

    }


    @Override
    public ListArrayAdapter<Group> getAdapter() {
        adapter=new ListArrayAdapter<Group>(getActivity(),
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
            mListener = (GroupsListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
        updateItems();

    }

    @Override
    public void updateItems() {

        Project p= mListener.getSelectedProject();
        List<Long> longs=p.getGroupIDs();
        Toast.makeText(getActivity(), "Size: "+longs.size(), Toast.LENGTH_LONG).show();
        for (Long l: longs){
            Group g=ApplicationWideData.getGroupByID(l);
            if (g!=null) {
                groups.put(l, g);
            }
        }
        if (!ApplicationWideData.manualSnyc) {
            NonLocalDataService service = new NonLocalDataService();
            showHidden = mListener.getShowHidden();
            //if (mListener.getUserID().equals("-1")){
            service.service.getGroupList(mListener.getUserID(), showHidden, mListener.getSelectedProject().getID() + "", new GroupListCallback());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.groups);
    }

    @Override
    public void onItemSelected(Group group) {
        mListener.onItemSelected(group);
    }
    public void checkForArgs(){

    }

    public List<Group> getItems(){
        List<Group> l=new ArrayList<>();
        l.addAll(groups.values());
        return l;
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
                    Log.wtf("Found a group", map.toString());

                    HashMap<String, Object> source=(HashMap)map.get("_source");

                    long id = Long.parseLong((String)map.get("_id"));
                    Group g= Group.parseJSON(id, source);
                    groups.put(g.getID(), g);
                    //LocalDataSaver.addGroup(p);
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
    public interface GroupsListListener extends Interfaces.UserIDGetter{
        void onItemSelected(Group t);
        boolean getShowHidden();
        Project getSelectedProject();

    }


}