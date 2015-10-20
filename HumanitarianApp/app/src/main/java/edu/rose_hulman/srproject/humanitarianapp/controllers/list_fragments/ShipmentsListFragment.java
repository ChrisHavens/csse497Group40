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
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
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
public class ShipmentsListFragment extends AbstractListFragment<Shipment> {
    protected ShipmentsListListener mListener;
    ArrayList<Shipment> shipments=new ArrayList<>();
    ListArrayAdapter<Shipment> adapter;
    //public Shipment(String contents, String from, String to, String time, String date) {


    public ShipmentsListFragment(){
        shipments.add(new Shipment("Potatoes", "HQ", "Little Village", "14:30", "2016/11/15"));
        shipments.add(new Shipment("Lumber", "Big Village", "Small Village", "17:15", "2016/11/21"));
    }


    @Override
    public ListArrayAdapter<Shipment> getAdapter() {
        adapter=new ListArrayAdapter<Shipment>(getActivity(),
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
        NonLocalDataService service=new NonLocalDataService();
        service.getAllLocations(mListener.getSelectedGroup(), new LocationListCallback());
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
    public void checkForArgs(){

    }

    public List<Shipment> getItems(){

        return shipments;
    }
    public class LocationListCallback implements Callback<Response> {

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
                    Shipment s=new Shipment(Integer.parseInt(((String)map.get("_id")).substring(3)));
                    s.setContents((String) source.get("contents"));
                    s.setFrom((String) source.get("fromLocationID"));
                    s.setTo((String) source.get("toLocationID"));
                    //s.setLast((String) source.get("fromLocationID"));
                    s.setName((String)source.get("name"));
                    s.setDate((String) source.get("pickupTime"));
                    s.setStatus((String) source.get("status"));
                    shipments.add(s);
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
    public interface ShipmentsListListener{
        void onItemSelected(Shipment t);
        Group getSelectedGroup();
    }
}