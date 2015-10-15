package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPersonDialogFragment.AddPersonListener} interface
 * to handle interaction events.

 */
public class AddPersonDialogFragment extends DialogFragment {
    
    private AddPersonListener mListener;
    private EditText nameField;
    private EditText phoneField;
    private EditText emailField;
    private Spinner roleSpinner;


    
    public AddPersonDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
                        String name=nameField.getText().toString();
                        String phone=phoneField.getText().toString();
                        String email=emailField.getText().toString();
                        Roles.PersonRoles role= Roles.PersonRoles.valueOf(((String)roleSpinner.getSelectedItem()).toUpperCase());
                        mListener.addPerson(name, phone, email, role);
                        AddPersonDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddPersonDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();
        
    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_person_dialog, null);
        nameField=(EditText) view.findViewById(R.id.nameField);
        phoneField=(EditText) view.findViewById(R.id.phoneField);
        emailField=(EditText)view.findViewById(R.id.emailField);
        roleSpinner=(Spinner)view.findViewById(R.id.roleSpinner);
        roleSpinner.setAdapter(new RoleSpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1,Roles.roles));
        return view;
    }

    

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddPersonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddPersonListener");
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
    public interface AddPersonListener {
        // TODO: Update argument type and name
        public void addPerson(String name, String phone, String email, Roles.PersonRoles role);
    }
    //
    private class RoleSpinnerAdapter extends ArrayAdapter<String>{
        private final String[] objects;
        private final int layout;


        public RoleSpinnerAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            this.objects=objects;
            this.layout=resource;

        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layout, parent, false);
            TextView line1=(TextView) view.findViewById(android.R.id.text1);
            line1.setText(objects[position]);
            return view;

        }
    }

}
