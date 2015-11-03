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
import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LocationsListFragment extends AbstractListFragment<Location>{
    protected LocationsListListener mListener;
    ArrayList<Location> locations=new ArrayList<>();
    ListArrayAdapter<Location> adapter;
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
            mListener = (LocationsListListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
        NonLocalDataService service=new NonLocalDataService();
        service.getAllLocations(mListener.getSelectedProject(), new LocationListCallback());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public List<Location> getItems(){

        return locations;
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
                    for (String s: source.keySet()){
                        Log.e("Result", s);
                    }
                    Location l=new Location(Integer.parseInt(((String)map.get("_id"))));
                    l.setName((String) source.get("name"));
                    l.setLat(Float.parseFloat((String) source.get("lat")));
                    l.setLng(Float.parseFloat((String) source.get("lng")));
                    locations.add(l);
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

    public interface LocationsListListener{
        void onItemSelected(Location t);
        Project getSelectedProject();
    }
}
