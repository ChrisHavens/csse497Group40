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
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
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
public class ChecklistsListFragment extends AbstractListFragment<Checklist> {
    protected ChecklistsListListener mListener;
    ListArrayAdapter<Checklist> adapter;
    ArrayList<Checklist> checklists=new ArrayList<>();
    public ChecklistsListFragment(){
//        Person aPerson =new Person("Alice Jones", "555-555-5555");
//        Person bPerson =new Person("Bob Smith", "555-555-5556");
//        Checklist a=new Checklist("To-do for 11/15");
//
//        a.addItem(new Checklist.ChecklistItem("Pick up Potatoes", aPerson));
//        a.addItem(new Checklist.ChecklistItem("Build a house",true, bPerson));
//        Checklist b=new Checklist("To-do for 11/16");
//        b.addItem(new Checklist.ChecklistItem("Cook dinner"));
//        checklists.add(a);
//        checklists.add(b);

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
            mListener = (ChecklistsListListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
        NonLocalDataService service=new NonLocalDataService();
        service.getAllChecklists(mListener.getSelectedGroup(), new ChecklistListCallback());
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
                    Checklist l=new Checklist(Integer.parseInt(((String)map.get("_id"))));
                    l.setTitle((String)source.get("name"));
                    ArrayList<HashMap<String, Object>> items=(ArrayList)source.get("checklistItems");
                    for (HashMap item: items) {
                        Checklist.ChecklistItem checklistItem = new Checklist.ChecklistItem((String) item.get("task"));
                        ArrayList<HashMap<String, Object>> subitems = (ArrayList) item.get("sublistItems");
                        Log.wtf("s40", subitems.toString());
                        for (HashMap subitem : subitems) {
                            Checklist.SublistItem sublistItem = new Checklist.SublistItem((String) subitem.get("task"));
                            //TODO:
                            //sublistItem.setAssigned();
                            sublistItem.setDone((boolean) subitem.get("isDone"));
                            checklistItem.addNewSublistItem(sublistItem);
                            Log.wtf("hi!", "hi!");
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
    public interface ChecklistsListListener{
        void onItemSelected(Checklist t);
        Group getSelectedGroup();
    }



}