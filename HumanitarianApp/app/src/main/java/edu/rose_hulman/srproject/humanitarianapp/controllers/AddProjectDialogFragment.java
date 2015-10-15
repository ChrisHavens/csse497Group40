package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link edu.rose_hulman.srproject.humanitarianapp.controllers.AddProjectDialogFragment.AddProjectListener} interface
 * to handle interaction events.

 */
public class AddProjectDialogFragment extends DialogFragment{

    private AddProjectListener mListener;
    private EditText nameField;


    public AddProjectDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

                        mListener.addProject(name);
                        AddProjectDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddProjectDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_project_dialog, null);
        nameField=(EditText) view.findViewById(R.id.nameField);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddProjectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface AddProjectListener {
        // TODO: Update argument type and name
        public void addProject(String name);
    }

}
