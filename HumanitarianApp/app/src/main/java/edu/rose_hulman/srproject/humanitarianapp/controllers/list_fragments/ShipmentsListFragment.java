package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link Backable}
 * interface.
 */
public class ShipmentsListFragment extends AbstractListFragment<Shipment> {
    protected ShipmentsListListener mListener;
    ArrayList<Shipment> shipments=new ArrayList<>();
    //public Shipment(String contents, String from, String to, String time, String date) {


    public ShipmentsListFragment(){
        shipments.add(new Shipment("Potatoes", "HQ", "Little Village", "14:30", "2016/11/15"));
        shipments.add(new Shipment("Lumber", "Big Village", "Small Village", "17:15", "2016/11/21"));
    }


    @Override
    public ListArrayAdapter<Shipment> getAdapter() {
        ListArrayAdapter<Shipment> adapter=new ListArrayAdapter<Shipment>(getActivity(),
                android.R.layout.simple_list_item_2, getItems()){

            @Override
            public View customiseView(View v, Shipment shipment) {
                TextView line1=(TextView) v.findViewById(android.R.id.text1);
                TextView line2=(TextView) v.findViewById(android.R.id.text2);
                line1.setText(shipment.getContents());
                line2.setText(shipment.getFrom()+"->"+shipment.getTo()+" ("+shipment.getDate()+" "+shipment.getTime()+")");
                return v;
            }
        };
        return adapter;

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ShipmentsListListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ShipmentsListListener");
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

    @Override
    public String getTitle() {
        return getResources().getString(R.string.shipments);
    }

    @Override
    public void onItemSelected(Shipment shipment) {
        mListener.onItemSelected(shipment);
    }

    public List<Shipment> getItems(){

        return shipments;
    }
    public interface ShipmentsListListener{
        void onItemSelected(Shipment t);
    }
}