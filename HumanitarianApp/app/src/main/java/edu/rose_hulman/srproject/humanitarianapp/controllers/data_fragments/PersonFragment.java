package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.MainActivity;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonFragment.WorkerFragmentListener} interface
 * to handle interaction events.
 *
 */
public class PersonFragment extends Fragment {

    private WorkerFragmentListener mListener;
    private GoogleMap map;
    private  AppCompatButton showMap;



    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Person p=mListener.getSelectedPerson();
        Log.d("ED",p.getID() + "");
        Log.d("ED", p.toJSON());
        View v=inflater.inflate(R.layout.fragment_worker, container, false);
        TextView name=(TextView) v.findViewById(R.id.nameField);
        final TextView phone=(TextView) v.findViewById(R.id.phoneNumberField);
        TextView email=(TextView)v.findViewById(R.id.emailField);
        TextView lastLoc=(TextView) v.findViewById(R.id.lastLocField);
        showMap = (AppCompatButton) v.findViewById(R.id.showMapButton);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeMap();

            }
        });
        //this.initializeMap();
        name.setText(p.getName());
        phone.setText(p.getPhoneNumber());
        email.setText(p.getEmail());
        if (p.getLastCheckin()!=null){
            lastLoc.setText("Last Loc: " + p.getLastCheckin().getName()+" "+p.getLastCheckin().getTime());
        }
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(getActivity(), phone);
                menu.getMenuInflater().inflate(R.menu.menu_call, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.action_call) {
                            ((MainActivity) getActivity()).makePhoneCall(mListener.getSelectedPerson().getPhoneNumber());
                        }
                        else{
                            ((MainActivity) getActivity()).makeText(mListener.getSelectedPerson().getPhoneNumber());
                        }
                        return true;
                    }
                });
                menu.show();
            }
        });
        return v;
    }

   
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (WorkerFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement WorkerFragmentListener");
        }
    }

    public void initializeMap()
    {
        final Person p=mListener.getSelectedPerson();
        final List<LatLng> recentLocs = new ArrayList<>();

              //  new LatLng(p.getLastCheckin().getLat(), p.getLastCheckin().getLng());

        final LatLng currentLoc = new LatLng(p.getLastCheckin().getLat(), p.getLastCheckin().getLng());
        FragmentManager f = getFragmentManager();
        FragmentTransaction fragmentTransaction = f.beginTransaction();
        SupportMapFragment mapFragment = new SupportMapFragment();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                Marker marker =  map.addMarker(new MarkerOptions().position(currentLoc).title(p.getName()));
                for (Person.PersonLocation pLoc: p.getLocations()){
                    map.addMarker(new MarkerOptions().position(new LatLng(pLoc.getLat(), pLoc.getLng())).title(pLoc.getName()));
                }
                //Marker markers= map.addMarker()
                marker.showInfoWindow();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 7));
            }
        });
        fragmentTransaction.add(R.id.fragment_container, mapFragment, "map");
        fragmentTransaction.commit();
        showMap.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface WorkerFragmentListener {

        public Person getSelectedPerson();
    }

}
