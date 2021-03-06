package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

/**
 * Created by daveyle on 10/6/2015.
 */
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
import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LocationsListFragment extends AbstractListFragment<Location>{
    protected LocationsListListener mListener;
    HashMap<Long, Location> locations=new HashMap<>();
    ListArrayAdapter<Location> adapter;
    private boolean showHidden=false;
    public LocationsListFragment(){
//        Location a=new Location("Little Village");
//        Location b=new Location("HQ");
//        a.setLat(32.11f);
//        a.setLng(57.6f);
//        b.setLat(29.56f);
//        b.setLng(45.32f);
//        locations.add(a);
//        locations.add(b);
    }
    @Override
    public ListArrayAdapter<Location> getAdapter() {
        adapter=new ListArrayAdapter<Location>(getActivity(),
                android.R.layout.simple_list_item_1, getItems()){

            @Override
            public View customiseView(View v, Location location) {
                TextView line1=(TextView) v.findViewById(android.R.id.text1);
                line1.setText(location.getName());
                return v;
            }
        };
        return adapter;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.locations);
    }

    @Override
    public void onItemSelected(Location location) {
        mListener.onItemSelected(location);
    }

    public void checkForArgs(){

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (LocationsListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
        updateItems();

     //   service.getAllLocations(mListener.getSelectedProject(), showHidden, new LocationListCallback());
    }

    @Override
    public void updateItems() {
        Project p= mListener.getSelectedProject();
        long gId=p.getID();
        List<Location> allLocs=ApplicationWideData.getAllLocations();
        for (Location c: allLocs){
            if (c.getProjectIDs().contains(gId)){
                locations.put(c.getID(), c);
            }
        }
        if (!ApplicationWideData.manualSnyc) {
            NonLocalDataService service = new NonLocalDataService();
            showHidden = mListener.getShowHidden();
            service.service.getLocationListByProjectID(showHidden, mListener.getSelectedProject().getID() + "", new LocationListCallback());
        } else {

            loadList();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public List<Location> getItems(){
        List<Location> l=new ArrayList<>();
        l.addAll(locations.values());
        return l;
    }
    public class LocationListCallback implements Callback<Response> {

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

                    long id = Long.parseLong(((String)map.get("_id")));
                    Location l= Location.parseJSON(id, source);
                    locations.put(l.getID(), l);
                    //LocalDataSaver.addLocation(l);
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
            Log.e("RetrofitError", "LocationsListCallback: "+error.getMessage());
            loadList();
        }
    }
    public void loadList(){
            if(adapter == null) {
            return;
        }
        adapter.clear();
        for(long l: locations.keySet()){
            adapter.add(locations.get(l));
        }
        ApplicationWideData.addLocationHashMap(locations);
        adapter.notifyDataSetChanged();
    }

    public interface LocationsListListener extends Interfaces.UserIDGetter{
        void onItemSelected(Location t);
        boolean getShowHidden();
        Project getSelectedProject();
    }
}
