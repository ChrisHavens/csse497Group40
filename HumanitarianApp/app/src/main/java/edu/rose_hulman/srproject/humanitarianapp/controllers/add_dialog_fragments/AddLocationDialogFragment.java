package edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddLocationDialogFragment.AddLocationListener} interface
 * to handle interaction events.

 */
public class AddLocationDialogFragment extends DialogFragment {

    private AddLocationListener mListener;
    private EditText nameField;
    private EditText latField;
    private EditText lngField;
    private long projectID;



    public AddLocationDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments()!=null){
            projectID=getArguments().getLong("projectID");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View v=onCreateView(inflater);
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        mListener.addNewLocation(createLocation());
                        AddLocationDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddLocationDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_location_dialog, null);
        nameField=(EditText) view.findViewById(R.id.nameField);
        latField=(EditText) view.findViewById(R.id.latField);
        lngField=(EditText)view.findViewById(R.id.lngField);
        return view;
    }
    private Location createLocation(){
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=400000;
        Location l= new Location(i);
        l.addNewProjectID(projectID);
        String name=nameField.getText().toString();
        float lat=Float.parseFloat(latField.getText().toString());
        float lng=Float.parseFloat(lngField.getText().toString());
        l.setName(name);
        l.setLat(lat);
        l.setLng(lng);
        return l;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddLocationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddLocationListener");
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
    public interface AddLocationListener {
        void addNewLocation(Location l);
    }
    //


}
