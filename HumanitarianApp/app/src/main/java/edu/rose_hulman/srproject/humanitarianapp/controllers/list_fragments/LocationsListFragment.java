package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

/**
 * Created by daveyle on 10/6/2015.
 */
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;

public class LocationsListFragment extends AbstractListFragment<Location>{
    protected LocationsListListener mListener;
    ArrayList<Location> locations=new ArrayList<>();
    public LocationsListFragment(){
        Location a=new Location("Little Village");
        Location b=new Location("HQ");
        a.setLat(32.11f);
        a.setLng(57.6f);
        b.setLat(29.56f);
        b.setLng(45.32f);
        locations.add(a);
        locations.add(b);
    }
    @Override
    public ListArrayAdapter<Location> getAdapter() {
        ListArrayAdapter<Location> adapter=new ListArrayAdapter<Location>(getActivity(),
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public List<Location> getItems(){

        return locations;
    }
    public interface LocationsListListener{
        void onItemSelected(Location t);
    }
}
