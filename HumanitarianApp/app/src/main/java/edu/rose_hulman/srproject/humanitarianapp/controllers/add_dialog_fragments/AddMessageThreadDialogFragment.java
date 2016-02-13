package edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;

/**
 * Created by daveyle on 10/30/2015.
 */
public class AddMessageThreadDialogFragment extends DialogFragment {


    private AddMessageThreadListener mListener;
    private EditText nameField;
    private long parentID;
    //List<Checklist.ChecklistItem> checklistItemList=new ArrayList<>();
    HashMap<MessageThread.Message, View> map=new HashMap<>();
    //HashMap<MessageThread.SublistItem, View> subMap=new HashMap<>();



    public AddMessageThreadDialogFragment() {
        // Required empty public constructor
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments()!=null){
            parentID=getArguments().getLong("parentID");
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
                        MessageThread messageThread=new MessageThread();
                        String name = nameField.getText().toString();
                        messageThread.setTitle(name);
                        messageThread.setParentID(parentID);
                        mListener.addNewMessageThread(messageThread);
                        AddMessageThreadDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddMessageThreadDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_message_dialog, null);
        nameField=(EditText) view.findViewById(R.id.messageNameField);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddMessageThreadListener) activity;
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
    public interface AddMessageThreadListener {
        // TODO: Update argument type and name
        public void addNewMessageThread(MessageThread messageThread);
    }
}

