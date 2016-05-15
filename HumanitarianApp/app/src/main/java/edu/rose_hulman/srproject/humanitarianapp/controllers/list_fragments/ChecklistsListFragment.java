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
import edu.rose_hulman.srproject.humanitarianapp.controllers.Interfaces;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
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
    ArrayList<Checklist> checklists=new ArrayList<>();
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
        NonLocalDataService service=new NonLocalDataService();
        showHidden=mListener.getShowHidden();
        service.service.getChecklistList(showHidden, mListener.getSelectedGroup().getID()+"", new ChecklistListCallback());
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

        return checklists;
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
                for (HashMap<String, Object> map: list){

                    HashMap<String, Object> source=(HashMap)map.get("_source");

                    Checklist l=new Checklist(Integer.parseInt(((String)map.get("_id"))));
                    l.setTitle((String)source.get("name"));
                    if(source.get("dateArchived") == null)
                        l.setHidden(false);
                    else
                        l.setHidden(true);
                    l.setParentID(Long.parseLong((String)source.get("parentID")));

                    ArrayList<HashMap<String, Object>> items=(ArrayList)source.get("checklistItems");
                    for (HashMap item: items) {
                        Checklist.ChecklistItem checklistItem = new Checklist.ChecklistItem((String) item.get("task"));
                        ArrayList<HashMap<String, Object>> subitems = (ArrayList) item.get("sublistItems");

                        for (HashMap subitem : subitems) {
                            Checklist.SublistItem sublistItem = new Checklist.SublistItem((String) subitem.get("task"));
                            //TODO:
                            //sublistItem.setAssigned();
                            if (subitem.containsKey("sublistItemID")&&!((String)subitem.get("sublistItemID")).equals("null")) {
                                sublistItem.setItemID(Long.parseLong((String) subitem.get("sublistItemID")));
                            }
                            if (subitem.containsKey("isDone")) {
                                sublistItem.setDone((boolean) subitem.get("isDone"));
                            }
                            checklistItem.addNewSublistItem(sublistItem);

                        }
                        if (item.containsKey("checklistItemID")&&!((String)item.get("checklistItemID")).equals("null")) {
                            checklistItem.setItemID(Long.parseLong((String) item.get("checklistItemID")));
                        }
                        if (item.containsKey("isDone")) {
                            checklistItem.setDone((boolean) item.get("isDone"));
                        }
                        l.addItem(checklistItem);
                    }
                    checklists.add(l);
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
    public interface ChecklistsListListener extends Interfaces.UserIDGetter{
        void onItemSelected(Checklist t);
        boolean getShowHidden();
        Group getSelectedGroup();
    }



}