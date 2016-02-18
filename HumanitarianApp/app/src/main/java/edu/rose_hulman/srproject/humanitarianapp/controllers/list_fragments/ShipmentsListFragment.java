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
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
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
public class ShipmentsListFragment extends AbstractListFragment<Shipment> {
    protected ShipmentsListListener mListener;
    ArrayList<Shipment> shipments=new ArrayList<>();
    NonLocalDataService service;
    ListArrayAdapter<Shipment> adapter;
    private boolean showHidden=false;
    //public Shipment(String contents, String from, String to, String time, String date) {


    public ShipmentsListFragment(){
    }


    @Override
    public ListArrayAdapter<Shipment> getAdapter() {
        adapter=new ListArrayAdapter<Shipment>(getActivity(),
                android.R.layout.simple_list_item_2, getItems()){

            @Override
            public View customiseView(View v, Shipment shipment) {
                TextView line1=(TextView) v.findViewById(android.R.id.text1);
                TextView line2=(TextView) v.findViewById(android.R.id.text2);
                line1.setText(shipment.getName()+" ("+shipment.getContents()+")");
                line2.setText(shipment.getFromName()+"->"+shipment.getToName()+" ("+shipment.getDate()+" "+shipment.getTime()+")");
                return v;
            }
        };
        return adapter;

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ShipmentsListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ShipmentsListListener");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
        service=new NonLocalDataService();
        showHidden=mListener.getShowHidden();
        service.service.getShipmentList(showHidden, mListener.getSelectedGroup().getId()+"", new ShipmentListCallback());
//        service.getAllShipments(mListener.getSelectedGroup(), showHidden, new ShipmentListCallback());
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
    public class ShipmentListCallback implements Callback<Response> {

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
                    Shipment s=new Shipment(Integer.parseInt(((String)map.get("_id"))));
                    s.setContents((String) source.get("contents"));
                    s.setFrom((String) source.get("fromLocationID"));
                    s.setTo((String) source.get("toLocationID"));
                    service.service.getLocation(s.getTo(), new ShipmentToLocationCallbacks(s));
                    service.service.getLocation(s.getFrom(), new ShipmentFromLocationCallbacks(s));
                    //s.setLast((String) source.get("fromLocationID"));
                    s.setName((String) source.get("name"));
                    String[] split=((String) source.get("pickupTime")).split(" ");
                    if (split.length==2) {
                        s.setDate(split[0]);
                        s.setTime(split[1]);
                    }
                    s.setStatus((String) source.get("status"));
                    shipments.add(s);
                    //LocalDataSaver.addShipment(s);
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
    public class ShipmentToLocationCallbacks implements Callback<Response>{
        private Shipment s;

        public ShipmentToLocationCallbacks(Shipment s){
            this.s=s;
        }

        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper=new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference=
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try{
                HashMap<String, Object> map=mapper.readValue(response.getBody().in(), typeReference);
                HashMap<String, Object> source=(HashMap)map.get("_source");
                for (String s: source.keySet()){
                    Log.e("Result", s);
                }
//                Location l=new Location(Integer.parseInt(((String)map.get("_id")).substring(3)));
//                l.setName((String) source.get("name"));
//                l.setLat(Float.parseFloat((String) source.get("lat")));
//                l.setLng(Float.parseFloat((String) source.get("lng")));
                s.setToName((String) source.get("name"));
                adapter.notifyDataSetChanged();
            }catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitError", error.getMessage());
        }
    }
    public class ShipmentFromLocationCallbacks implements Callback<Response>{
        private Shipment s;

        public ShipmentFromLocationCallbacks(Shipment s){
            this.s=s;
        }

        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper=new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference=
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try{
                HashMap<String, Object> map=mapper.readValue(response.getBody().in(), typeReference);
                HashMap<String, Object> source=(HashMap)map.get("_source");
                for (String s: source.keySet()){
                    Log.e("Result", s);
                }
//                Location l=new Location(Integer.parseInt(((String)map.get("_id")).substring(3)));
//                l.setName((String) source.get("name"));
//                l.setLat(Float.parseFloat((String) source.get("lat")));
//                l.setLng(Float.parseFloat((String) source.get("lng")));
                s.setFromName((String) source.get("name"));
                adapter.notifyDataSetChanged();
            }catch (IOException e) {
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
        boolean getShowHidden();
        Group getSelectedGroup();
    }
}