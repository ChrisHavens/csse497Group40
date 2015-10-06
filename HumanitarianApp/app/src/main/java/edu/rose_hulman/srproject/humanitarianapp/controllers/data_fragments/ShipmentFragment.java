package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShipmentFragment.ShipmentFragmentListener} interface
 * to handle interaction events.
 *
 */
public class ShipmentFragment extends Fragment implements OnMapReadyCallback {


    private ShipmentFragmentListener mListener;

    private Shipment shipment;



    public ShipmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachMapFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shipment, container, false);
        shipment=mListener.getSelectedShipment();
        if (shipment!=null){
            TextView contentsLabel=(TextView)view.findViewById(R.id.contentsLabel);
            TextView toLabel=(TextView) view.findViewById(R.id.toLabel);
            TextView fromLabel=(TextView) view.findViewById(R.id.fromLabel);
            TextView dateLabel=(TextView) view.findViewById(R.id.dateLabel);
            TextView timeLabel=(TextView) view.findViewById(R.id.timeLabel);
            contentsLabel.setText(shipment.getContents());
            toLabel.setText(shipment.getTo());
            fromLabel.setText(shipment.getFrom());
            dateLabel.setText(shipment.getDate());
            timeLabel.setText(shipment.getTime());
            MapFragment mapFragment = (MapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map_container);
            mapFragment.getMapAsync(this);
        }
        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ShipmentFragmentListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ShipmentFragmentListener");
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
    private void attachMapFragment(){
        MapFragment myMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map_container, myMapFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(shipment.getLastLocation().getLat(), shipment.getLastLocation().getLng()))
                .title("Shipment"));
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
   public interface ShipmentFragmentListener{
        Shipment getSelectedShipment();
    }

}
