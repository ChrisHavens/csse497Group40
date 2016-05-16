package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Conflict;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by daveyle on 5/10/2016.
 */
public class ConflictResolutionDialogFragment extends DialogFragment {
    private ConflictResolutionListener mListener;
    private ConflictResolutionListAdapter adapter;
    private List<Conflict> conflicts;
    private AlertDialog alert;
    //private Selectable object;
    private AbsListView mListView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        adapter=new ConflictResolutionListAdapter(getActivity(), R.layout.list_conflict_resolution,conflicts);
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
                        mListener.resolveConflicts(conflicts);
                        ConflictResolutionDialogFragment.this.getDialog().dismiss();
//                        URLCheckReturns returns = checkURL();
//                        if (returns == URLCheckReturns.NOT_HTTPS) {
//                            alert = new AlertDialog.Builder(getActivity()).setMessage(R.string.badURL)
//                                    .setPositiveButton(R.string.acceptBadURL, new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            // FIRE ZE MISSILES!
//                                            mListener.setURL(URLField.getText().toString());
//                                            alert.dismiss();
//                                            ConflictResolutionDialogFragment.this.getDialog().dismiss();
//
//                                        }
//                                    })
//                                    .setNegativeButton(R.string.rejectBadURL, new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            // User cancelled the dialog
//                                            alert.dismiss();
//                                            ConflictResolutionDialogFragment.this.getDialog().dismiss();
//                                        }
//                                    }).create();
//
//
//                            alert.show();
//
//                        } else if (returns == URLCheckReturns.GOOD) {
//                            mListener.setURL(URLField.getText().toString());
//                            ConflictResolutionDialogFragment.this.getDialog().dismiss();
//                        }

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConflictResolutionDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_conflict_resolution_dialog, null);
//        TextView title=(TextView) view.findViewById(R.id.title);
//        title.setText(getTitle());
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.listView);
        mListView.setAdapter(adapter);
//        View emptyView = inflater.inflate(R.layout.list_empty, container, false);
//        TextView emptyText=(TextView) emptyView.findViewById(R.id.emptyViewText);
//        String format1=getResources().getString(R.string.noneFound);
//
//        emptyText.setText(String.format(format1, getItemName()));
//        mListView.setEmptyView(emptyView);

        // Set OnItemClickListener so we can be notified on item clicks
//        mListView.setOnItemClickListener(this);

//        return view;
        //URLField=(EditText) view.findViewById(R.id.urlField);



//        roleSpinner=(Spinner)view.findViewById(R.id.roleSpinner);
//        roleSpinner.setAdapter(new RoleSpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1,Roles.roles));
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ConflictResolutionListener) activity;
            this.conflicts=mListener.getConflicts();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ConflictResolutionListener");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface ConflictResolutionListener{
        public void resolveConflicts(List<Conflict> conflicts);
        public List<Conflict> getConflicts();

    }

    private class ConflictResolutionListAdapter extends ListArrayAdapter<Conflict>{
        private EditText otherOption;
        LinearLayout other_layout;
        public ConflictResolutionListAdapter(Context context, int layout, List<Conflict> conflictList){
            super(context, layout, conflictList);
        }

        @Override
        public View customiseView(View v, final Conflict conflict) {
            TextView serverValue=(TextView)v.findViewById(R.id.serverTextField);
            TextView localValue=(TextView)v.findViewById(R.id.localTextField);
            TextView fieldName=(TextView)v.findViewById(R.id.fieldNameField);
            fieldName.setText(conflict.fieldName);
            serverValue.setText(conflict.serverVersion);
            localValue.setText(conflict.localVersion);
            RadioButton serverButton=(RadioButton)v.findViewById(R.id.radioButtonServer);
            RadioButton localButton=(RadioButton)v.findViewById(R.id.radioButtonLocal);
            //RadioButton otherButton=(RadioButton)v.findViewById(R.id.radioButtonOther);
//            other_layout=(LinearLayout)v.findViewById(R.id.other_layout);
//            other_layout.setVisibility(View.GONE);
//            serverButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    conflict.setChosenVersionToServer();
//                }
//            });
//            localButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    conflict.setChosenVersionToLocal();
//                }
//            });
//            otherButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    other_layout.setVisibility(View.VISIBLE);
//                }
//            });
//            otherOption=(EditText) other_layout.findViewById(R.id.changedVersion);
            Button buttonOk=(Button) other_layout.findViewById(R.id.buttonOK);
            Button buttonCancel=(Button) other_layout.findViewById(R.id.buttonCancel);
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    conflict.setChosenVersion(otherOption.getText().toString());
                }
            });
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //other_layout.setVisibility(View.GONE);
                }
            });
            return v;

        }

    }




}
