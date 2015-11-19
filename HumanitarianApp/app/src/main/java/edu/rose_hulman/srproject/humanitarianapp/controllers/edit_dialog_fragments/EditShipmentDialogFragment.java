package edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.widgets.TextPicker;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by daveyle on 11/3/2015.
 */
public class EditShipmentDialogFragment extends DialogFragment
{

    private EditShipmentListener mListener;
    private EditText nameField;
    private EditText contentsField;
    private TextView toField;
    private TextView fromField;
    private TextView dateField;
    private TextView timeField;

    private List<Location> locations=new ArrayList<>();
    private Spinner toSpinner;
    private LocationSpinnerAdapter toSpinnerAdapter;
    private Spinner fromSpinner;
    private LocationSpinnerAdapter fromSpinnerAdapter;

    private Calendar date=Calendar.getInstance();

    private FrameLayout layout;
    private long projectID;
    private long groupID;

    private Shipment shipment;



    public EditShipmentDialogFragment() {

        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments()!=null){
            projectID=getArguments().getLong("projectID");
            groupID=getArguments().getLong("groupID");
        }
        shipment=mListener.getSelectedShipment().clone();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        toSpinnerAdapter=new LocationSpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1,locations);
        fromSpinnerAdapter=new LocationSpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1,locations);

        NonLocalDataService service=new NonLocalDataService();
        service.getAllLocations(projectID,false, false, new LocationListCallback());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View v=onCreateView(inflater);
        builder.setView(v)
                // Edit action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        mListener.addNewShipment(createShipment());
                        EditShipmentDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditShipmentDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_add_shipment_dialog, null);
        nameField=(EditText) view.findViewById(R.id.titleField);
        nameField.setText(shipment.getName());
        contentsField=(EditText)view.findViewById(R.id.contentsField);
        contentsField.setText(shipment.getContents());
        toField=(TextView)view.findViewById(R.id.toLabel);
        toField.setText(shipment.getToName());
        fromField=(TextView)view.findViewById(R.id.fromLabel);
        fromField.setText(shipment.getFromName());
        dateField=(TextView)view.findViewById(R.id.dateLabel);
        dateField.setText(shipment.getDate());
        timeField=(TextView)view.findViewById(R.id.timeLabel);
        timeField.setText(shipment.getTime());
        layout=(FrameLayout)view.findViewById(R.id.tabLayout);
        layout.addView(createLocationChooser());


        return view;
    }
    private Shipment createShipment(){
        shipment.setName(nameField.getText().toString());
        shipment.setContents(contentsField.getText().toString());


        shipment.setParentID(groupID);




//        l.addNewProjectID(projectID);
//        String name=nameField.getText().toString();
//        float lat=Float.parseFloat(latField.getText().toString());
//        float lng=Float.parseFloat(lngField.getText().toString());
//        l.setName(name);
//        l.setLat(lat);
//        l.setLng(lng);
        return shipment;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (EditShipmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement EditShipmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    private View createLocationChooser(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_add_shipment_location_chooser, null);
        toSpinner=(Spinner)view.findViewById(R.id.toSpinner);
        fromSpinner=(Spinner)view.findViewById(R.id.fromSpinner);

        toSpinner.setAdapter(toSpinnerAdapter);
        fromSpinner.setAdapter(fromSpinnerAdapter);
        if (shipment!=null && shipment.getTo()!=null && !shipment.getTo().equals("")){
            Log.wtf("s40","Setting selection TO: "+shipment.getTo());

            toSpinner.setSelection(toSpinnerAdapter.getPositionByID(shipment.getTo()));
        }
        if (shipment!=null && shipment.getFrom()!=null && !shipment.getFrom().equals("")){
            Log.wtf("s40","Setting selection FROM: "+shipment.getFrom());
            Log.wtf("s40", fromSpinnerAdapter.getPositionByID(shipment.getFrom())+"");
            fromSpinner.setSelection(fromSpinnerAdapter.getPositionByID(shipment.getFrom()));
        }

        Button dateButton=(Button)view.findViewById(R.id.dateButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location to=(Location)toSpinner.getSelectedItem();
                Location from=(Location)fromSpinner.getSelectedItem();
                shipment.setTo(to.getID() + "");
                shipment.setToName(to.getName());
                shipment.setFrom(from.getID() + "");
                shipment.setFromName(from.getName());
                toField.setText(to.getName());
                fromField.setText(from.getName());
                layout.removeAllViews();
                layout.addView(createDateChooser());
            }
        });


        return view;
    }
    private View createDateChooser(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_add_shipment_date_chooser, null);
        final NumberPicker yearPicker=(NumberPicker)view.findViewById(R.id.yearPicker);
        yearPicker.setMinValue(Calendar.getInstance().get(Calendar.YEAR));
        yearPicker.setMaxValue(Calendar.getInstance().get(Calendar.YEAR) + 10);
        yearPicker.setValue(Integer.parseInt(shipment.getDate().split("-")[0]));
        final NumberPicker monthPicker=(NumberPicker)view.findViewById(R.id.monthPicker);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        monthPicker.setValue(Integer.parseInt(shipment.getDate().split("-")[1]));
        final NumberPicker dayPicker=(NumberPicker)view.findViewById(R.id.dayPicker);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        dayPicker.setValue(Integer.parseInt(shipment.getDate().split("-")[2]));

        Button timeButton=(Button)view.findViewById(R.id.timeButton);

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateSet(yearPicker.getValue(), monthPicker.getValue(), dayPicker.getValue());
                layout.removeAllViews();
                layout.addView(createTimeChooser());
            }
        });
        return view;


    }
    private View createTimeChooser(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_add_shipment_time_chooser, null);
        final NumberPicker hourPicker=(NumberPicker)view.findViewById(R.id.hourPicker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setValue(Integer.parseInt(shipment.getTime().split(":")[0]));
        final NumberPicker minPicker=(NumberPicker)view.findViewById(R.id.minutePicker);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(59);
        minPicker.setValue(Integer.parseInt(shipment.getTime().split(":")[1]));
        Button ok=(Button)view.findViewById(R.id.okButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeSet(hourPicker.getValue(), minPicker.getValue());
                layout.removeAllViews();
                Button b=new Button(getActivity());
                b.setText(getResources().getString(R.string.chooseLocations));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.removeAllViews();
                        layout.addView(createLocationChooser());
                    }
                });
                layout.addView(b);

            }
        });



        return view;
    }



    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {

        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateField.setText(date.get(Calendar.YEAR) + "-" + String.format("%02d", (date.get(Calendar.MONTH) + 1)) + "-" + String.format("%02d", date.get(Calendar.DAY_OF_MONTH)));
        shipment.setDate(date.get(Calendar.YEAR) + "-" +
                String.format("%02d", (date.get(Calendar.MONTH) + 1)) + "-" +
                String.format("%02d", date.get(Calendar.DAY_OF_MONTH)));
    }


    public void onTimeSet(int hourOfDay, int minute) {
        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
        date.set(Calendar.MINUTE, minute);
        timeField.setText(String.format("%02d", date.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", date.get(Calendar.MINUTE)));
        shipment.setTime(String.format("%02d", date.get(Calendar.HOUR_OF_DAY)) +
                ":" + String.format("%02d", date.get(Calendar.MINUTE)));
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
    public interface EditShipmentListener {
        public void addNewShipment(Shipment l);
        public Shipment getSelectedShipment();
    }
    private class LocationSpinnerAdapter extends ArrayAdapter<Location> {
        private final List<Location> objects;
        private final int layout;


        public LocationSpinnerAdapter(Context context, int resource, List<Location> objects) {
            super(context, resource, objects);
            this.objects=objects;
            this.layout=resource;

        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layout, parent, false);
            TextView line1=(TextView) view.findViewById(android.R.id.text1);
            line1.setText(objects.get(position).getName());
            return view;

        }
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layout, parent, false);
            TextView line1 = (TextView) view.findViewById(android.R.id.text1);
            line1.setText(objects.get(position).getName());

            return view;
        }
        public int getPositionByID(String id){
            for (int i=0; i<objects.size(); i++){
                Location l= objects.get(i);
                Log.wtf("s40", l.getID()+"");
                if ((l.getID()+"").equals(id)){
                    return i;
                }
            }
            return -1;
        }


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
                    toSpinnerAdapter.notifyDataSetChanged();
                    fromSpinnerAdapter.notifyDataSetChanged();
                    if (shipment!=null &&(l.getID()+"").equals(shipment.getTo())){
                        toSpinner.setSelection(toSpinnerAdapter.getPositionByID(l.getID() + ""));
                    }
                    if (shipment!=null &&(l.getID()+"").equals(shipment.getFrom())){
                        fromSpinner.setSelection(fromSpinnerAdapter.getPositionByID(l.getID()+""));
                    }

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

}
