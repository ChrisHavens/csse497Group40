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
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.ResponseCallback;
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
    HashMap<Long, Person> persons =new HashMap<>();
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
        Toast.makeText(getActivity(), "Somebody???", Toast.LENGTH_SHORT);
        try {
            mListener = (PeopleListListener) activity;
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

        Toast.makeText(getActivity(), "We are doing something, right?", Toast.LENGTH_SHORT);
        List<Person> allPersons=ApplicationWideData.getAllPersons();
        Toast.makeText(getActivity(), "FPeople in local: " + allPersons.size(), Toast.LENGTH_SHORT);
        if (mListener.isFromProject()){
            long projectID=mListener.getSelectedProject().getID();
            for (Person p: allPersons){
                if (p.isInProject(projectID)){
                    persons.put(p.getID(), p);
                }
            }
        }
        else{
            long groupID= mListener.getSelectedGroup().getID();
            for (Person p: allPersons){
                if (p.isInGroup(groupID)){
                    persons.put(p.getID(), p);
                }
            }
        }
        if (!ApplicationWideData.manualSnyc) {
            NonLocalDataService service = new NonLocalDataService();

            showHidden = mListener.getShowHidden();
            if (mListener.isFromProject()) {
                service.service.getPersonListByProjectID(showHidden, mListener.getSelectedProject().getID() + "", new PeopleListCallback());
            } else {
                service.service.getPersonListByGroupID(showHidden, mListener.getSelectedGroup().getID() + "", new PeopleListCallback());
            }
        } else{
            adapter.clear();
            for(long l: persons.keySet()){
                adapter.add(persons.get(l));
            }
            Toast.makeText(getActivity(), "From local", Toast.LENGTH_SHORT);
            adapter.notifyDataSetChanged();
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
        List<Person> l=new ArrayList<>();
        l.addAll(persons.values());
        return l;
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
                NonLocalDataService service=new NonLocalDataService();
                HashMap<String, Object> o=mapper.readValue(response.getBody().in(), typeReference);

                ArrayList<HashMap<String, Object>> list=(ArrayList)((HashMap) o.get("hits")).get("hits");

                Toast.makeText(getActivity(), "Person count: " + list.size(), Toast.LENGTH_SHORT);
                for (HashMap<String, Object> map: list){

                    HashMap<String, Object> source=(HashMap)map.get("_source");

                    final Person p= Person.parseJSON(Long.parseLong((String)map.get("_id")),source);
                    Callback<Response> subCallback=new ResponseCallback() {
                        @Override
                        public void success(Response response) {
                            ObjectMapper mapper = new ObjectMapper();
                            TypeReference<HashMap<String, Object>> typeReference =
                                    new TypeReference<HashMap<String, Object>>() {
                                    };
                            try {
                                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);

                                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                                for (HashMap<String, Object> map : list) {

                                    HashMap<String, Object> source = (HashMap) map.get("_source");
                                    for (String s : source.keySet()) {
                                        Log.e("Result", s);
                                    }
                                    Person.PersonLocation personLoc = new Person.PersonLocation();
                                    personLoc.setLat(Float.parseFloat((String) source.get("lat")));
                                    personLoc.setLng(Float.parseFloat((String) source.get("lng")));
                                    personLoc.setName((String) source.get("name"));
                                    personLoc.setTime((String) source.get("time"));
                                    p.addLocation(personLoc);
                                }
                            } catch (Exception e) {

                            }
                            adapter.clear();
                            for(long l: persons.keySet()){
                                adapter.add(persons.get(l));
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    };
                    service.getPersonLocationsListSize(p.getID()+"", 10, subCallback);
                    //Log.w("Type of lastLocation", .get("lastLocation"))
//                    HashMap<String, Object> lastLoc=(HashMap)source.get("lastLocation");
//

                    Log.d("ED", p.toJSON());
                    persons.put(p.getID(), p);
                    //LocalDataSaver.addPerson(p);
                    //adapter.add(p);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(long l: persons.keySet()){
                Person person = persons.get(l);
                adapter.add(person);
                Toast.makeText(getActivity(), "Person with name: " + person.getName(), Toast.LENGTH_SHORT);
            }
            adapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "From callback", Toast.LENGTH_SHORT);
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitError", error.getMessage());
        }
    }
    public interface PeopleListListener extends Interfaces.UserIDGetter{
        void onItemSelected(Person t);
        boolean getShowHidden();
        boolean isFromProject();
        Project getSelectedProject();
        Group getSelectedGroup();
    }


}