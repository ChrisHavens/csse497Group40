package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;


public class ChangeURLDialogFragment extends DialogFragment {

    private ChangeURLListener mListener;
    private EditText URLField;
    private AlertDialog alert;
    private boolean result=false;





    public ChangeURLDialogFragment() {
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
                        URLCheckReturns returns = checkURL();
                        if (returns == URLCheckReturns.NOT_HTTPS) {
                            alert = new AlertDialog.Builder(getActivity()).setMessage(R.string.badURL)
                                    .setPositiveButton(R.string.acceptBadURL, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // FIRE ZE MISSILES!
                                            mListener.setURL(URLField.getText().toString());
                                            alert.dismiss();
                                            ChangeURLDialogFragment.this.getDialog().dismiss();

                                        }
                                    })
                                    .setNegativeButton(R.string.rejectBadURL, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                            alert.dismiss();
                                            ChangeURLDialogFragment.this.getDialog().dismiss();
                                        }
                                    }).create();


                            alert.show();

                        } else if (returns == URLCheckReturns.GOOD) {
                            mListener.setURL(URLField.getText().toString());
                            ChangeURLDialogFragment.this.getDialog().dismiss();
                        }

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeURLDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_change_url_dialog, null);
        URLField=(EditText) view.findViewById(R.id.urlField);



//        roleSpinner=(Spinner)view.findViewById(R.id.roleSpinner);
//        roleSpinner.setAdapter(new RoleSpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1,Roles.roles));
        return view;
    }

    private URLCheckReturns checkURL() {
        if (this.URLField == null) {
            return URLCheckReturns.INVALID;
        } else if (this.URLField.getText().toString().isEmpty()) {
            this.URLField.setError("URL cannot be empty!");
            return URLCheckReturns.INVALID;
        }
        String url = this.URLField.getText().toString();
        if (url.startsWith("http://")) {
            this.URLField.setError("The URL should be an https connection, if possible.");
            return URLCheckReturns.NOT_HTTPS;
        }
        else if (url.startsWith("https://")){
            return URLCheckReturns.GOOD;
        }
        return URLCheckReturns.INVALID;
    }
    private enum URLCheckReturns{
        INVALID,
        NOT_HTTPS,
        GOOD
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ChangeURLListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ChangeURLListener");
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
    public interface ChangeURLListener {
        // TODO: Update argument type and name

        public void setURL(String url);

    }
    public static class BadURLDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.badURL)
                    .setPositiveButton(R.string.acceptBadURL, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton(R.string.rejectBadURL, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }


}
