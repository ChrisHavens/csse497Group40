package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

import android.app.Activity;
import android.app.Application;
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
import edu.rose_hulman.srproject.humanitarianapp.controllers.Interfaces;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
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
public class ChecklistsListFragment extends AbstractListFragment<Checklist> {
    protected ChecklistsListListener mListener;
    ListArrayAdapter<Checklist> adapter;
    HashMap<Long, Checklist> checklists=new HashMap<>();
    private boolean showHidden=false;
    public ChecklistsListFragment(){
    }


    @Override
    public ListArrayAdapter<Checklist> getAdapter() {
        adapter=new ListArrayAdapter<Checklist>(getActivity(),
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
            mListener = (ChecklistsListListener) activity;
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
    public void updateItems(){
        Group g= mListener.getSelectedGroup();
        long gId=g.getID();
        List<Checklist> allChecklists=ApplicationWideData.getAllChecklists();
        for (Checklist c: allChecklists){
            Log.wtf("Found a checklist", c.toJSON());
            if (c.getParentID()==gId){
                checklists.put(c.getID(), c);
            }
        }
        if (!ApplicationWideData.manualSnyc) {
            NonLocalDataService service = new NonLocalDataService();
            showHidden = mListener.getShowHidden();
            service.service.getChecklistList(showHidden, mListener.getSelectedGroup().getID() + "", new ChecklistListCallback());
        }else {
            loadList();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.checklists);
    }

    @Override
    public void onItemSelected(Checklist checklist) {
        mListener.onItemSelected(checklist);
    }
    public void checkForArgs(){

    }


    public List<Checklist> getItems(){
        List<Checklist> l=new ArrayList<>();
        l.addAll(checklists.values());
        return l;
    }
    public class ChecklistListCallback implements Callback<Response> {

        @Override
        public void success(Response response, Response response2) {

            ObjectMapper mapper=new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference=
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o=mapper.readValue(response.getBody().in(), typeReference);

                ArrayList<HashMap<String, Object>> list=(ArrayList)((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map: list) {

                    HashMap<String, Object> source = (HashMap) map.get("_source");
                    Checklist l= Checklist.parseJSON(Long.parseLong((String)map.get("_id")), source);


                    checklists.put(l.getID(), l);
                    adapter.notifyDataSetChanged();
                    //adapter.add(p);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadList();
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitError", "ChecklistsListCallback: "+error.getMessage());
            loadList();
        }
    }
    public void loadList(){
        if(adapter == null) {
            ApplicationWideData.hesNotTheMessiahHesAVeryNaughtyBoy(this);
            return;
        }
        adapter.clear();
        for(long l: checklists.keySet()){
            adapter.add(checklists.get(l));
        }
        ApplicationWideData.addChecklistHashMap(checklists);
        adapter.notifyDataSetChanged();
    }
    public interface ChecklistsListListener extends Interfaces.UserIDGetter{
        void onItemSelected(Checklist t);
        boolean getShowHidden();
        Group getSelectedGroup();
    }



}