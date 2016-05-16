package edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments;

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
public class AddShipmentDialogFragment extends DialogFragment
       {

    private AddShipmentListener mListener;
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
           private Location to;
           private Location from;
    private Calendar date=Calendar.getInstance();

           private FrameLayout layout;
    private long projectID;
           private long groupID;

           private Shipment shipment;



    public AddShipmentDialogFragment() {
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=600000;
        shipment= new Shipment(i);
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments()!=null){
            projectID=getArguments().getLong("projectID");
            groupID=getArguments().getLong("groupID");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        toSpinnerAdapter=new LocationSpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1,locations);
        fromSpinnerAdapter=new LocationSpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1,locations);

        NonLocalDataService service=new NonLocalDataService();
        service.service.getLocationListByProjectID(false, projectID+"", new LocationListCallback());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View v=onCreateView(inflater);
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        mListener.addNewShipment(createShipment());
                        AddShipmentDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddShipmentDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_add_shipment_dialog, null);
        nameField=(EditText) view.findViewById(R.id.titleField);
        contentsField=(EditText)view.findViewById(R.id.contentsField);
        toField=(TextView)view.findViewById(R.id.toLabel);
        fromField=(TextView)view.findViewById(R.id.fromLabel);
        dateField=(TextView)view.findViewById(R.id.dateLabel);
        timeField=(TextView)view.findViewById(R.id.timeLabel);
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
            mListener = (AddShipmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddShipmentListener");
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
               if (to!=null){
                   toSpinner.setSelection(toSpinnerAdapter.getPosition(to));
               }
               if (from!=null){
                   fromSpinner.setSelection(fromSpinnerAdapter.getPosition(from));
               }

               Button dateButton=(Button)view.findViewById(R.id.dateButton);

               dateButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       to=(Location)toSpinner.getSelectedItem();
                       from=(Location)fromSpinner.getSelectedItem();
                       shipment.setTo(to.getID() + "");
                       shipment.setToName(to.getName());
                       shipment.setFrom(from.getID() + "");
                       shipment.setFromName(from.getName());
                       shipment.setLastLocation(from);
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
                yearPicker.setValue(date.get(Calendar.YEAR));
               final NumberPicker monthPicker=(NumberPicker)view.findViewById(R.id.monthPicker);
               monthPicker.setMinValue(1);
               monthPicker.setMaxValue(12);

               monthPicker.setValue(date.get(Calendar.MONTH));
               final NumberPicker dayPicker=(NumberPicker)view.findViewById(R.id.dayPicker);
               dayPicker.setMinValue(1);
               dayPicker.setMaxValue(31);
               dayPicker.setValue(date.get(Calendar.DAY_OF_MONTH));

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
               hourPicker.setValue(date.get(Calendar.HOUR_OF_DAY));
               final NumberPicker minPicker=(NumberPicker)view.findViewById(R.id.minutePicker);
               minPicker.setMinValue(0);
               minPicker.setMaxValue(59);
               minPicker.setValue(date.get(Calendar.MINUTE));
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
    public interface AddShipmentListener {
        public void addNewShipment(Shipment l);
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
                    long val= Long.parseLong(((String)map.get("_id")));
                    Location l=new Location(val);
                    l.setName((String) source.get("name"));
                    l.setLat(Float.parseFloat((String) source.get("lat")));
                    l.setLng(Float.parseFloat((String) source.get("lng")));
                    locations.add(l);
                    toSpinnerAdapter.notifyDataSetChanged();
                    fromSpinnerAdapter.notifyDataSetChanged();
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
