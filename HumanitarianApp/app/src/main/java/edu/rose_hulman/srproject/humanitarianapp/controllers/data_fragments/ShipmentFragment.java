package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShipmentFragment.ShipmentFragmentListener} interface
 * to handle interaction events.
 *
 */
public class ShipmentFragment extends Fragment {


    private ShipmentFragmentListener mListener;

    private Shipment shipment;



    public ShipmentFragment() {
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
