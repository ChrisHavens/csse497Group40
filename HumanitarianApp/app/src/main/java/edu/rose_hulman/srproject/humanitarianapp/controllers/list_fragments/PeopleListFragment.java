package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;


import android.app.Activity;
import android.os.Bundle;
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

import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
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
public class PeopleListFragment extends AbstractListFragment<Person>{
    protected PeopleListListener mListener;
    private boolean isFromProject;
    ListArrayAdapter<Person> adapter;
    ArrayList<Person> persons =new ArrayList<>();
    private boolean showHidden=false;
    public PeopleListFragment(){
    }


    @Override
    public ListArrayAdapter<Person> getAdapter() {
        adapter=new ListArrayAdapter<Person>(getActivity(),
                android.R.layout.simple_list_item_2, getItems()){

            @Override
            public View customiseView(View v, Person worker) {
                TextView line1=(TextView) v.findViewById(android.R.id.text1);
                TextView line2=(TextView) v.findViewById(android.R.id.text2);
                line1.setText(worker.getName());
                if (worker.getLastCheckin()!=null) {
                    line2.setText(worker.getLastCheckin().getName());
                }
                else{
                    line2.setText("");
                }
                return v;
            }
        };
        return adapter;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PeopleListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
        NonLocalDataService service=new NonLocalDataService();

        showHidden=mListener.getShowHidden();
        if (mListener.isFromProject()) {
            service.service.getPersonListByProjectID(showHidden, mListener.getSelectedProject().getId() + "", new PeopleListCallback());
//            service.getAllPeople(mListener.getSelectedProject(), showHidden,new PeopleListCallback());
        }
        else{
            service.service.getPersonListByGroupID(showHidden, mListener.getSelectedGroup().getId()+"", new PeopleListCallback());
//            service.getAllPeople(mListener.getSelectedGroup(), showHidden, new PeopleListCallback());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.people);
    }

    @Override
    public void onItemSelected(Person person) {
        mListener.onItemSelected(person);
    }
    public void checkForArgs(){
//        Bundle b=getArguments();
//        this.isFromProject=b.getBoolean("isFromProject");
    }

    public List<Person> getItems(){

        return persons;
    }
    public class PeopleListCallback implements Callback<Response> {

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
                    Person p=new Person(Integer.parseInt(((String)map.get("_id"))));
                    p.setName((String) source.get("name"));
                    p.setEmail((String) source.get("email"));
                    p.setPhoneNumber((String) source.get("phone"));
                    if(source.get("dateArchived") == null)
                        p.setHidden(false);
                    else
                        p.setHidden(true);
                    //Log.w("Type of lastLocation", .get("lastLocation"))
                    HashMap<String, Object> lastLoc=(HashMap)source.get("lastLocation");
                    Person.PersonLocation personLoc=new Person.PersonLocation();
                    personLoc.setLat(Float.parseFloat((String)lastLoc.get("lat")));
                    personLoc.setLng(Float.parseFloat((String) lastLoc.get("lng")));
                    personLoc.setName((String) lastLoc.get("name"));
                    personLoc.setTime((String) lastLoc.get("time"));
                    p.setLastCheckin(personLoc);
                    Log.d("ED", p.toJSON());
                    persons.add(p);
                    LocalDataSaver.addPerson(p);
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
    public interface PeopleListListener{
        void onItemSelected(Person t);
        boolean getShowHidden();
        boolean isFromProject();
        Project getSelectedProject();
        Group getSelectedGroup();
    }


}