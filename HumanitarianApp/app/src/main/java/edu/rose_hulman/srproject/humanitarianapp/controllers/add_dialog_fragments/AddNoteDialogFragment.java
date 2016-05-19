package edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import edu.rose_hulman.srproject.humanitarianapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNoteDialogFragment.AddNoteListener} interface
 * to handle interaction events.

 */
public class AddNoteDialogFragment extends DialogFragment {


    private AddNoteListener mListener;
    private EditText nameField;
    private EditText contentsField;



    public AddNoteDialogFragment() {
        // Required empty public constructor
    }

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
                        String contents=format(contentsField.getText().toString());
                        mListener.addNewNote(name, contents);
                        AddNoteDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddNoteDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_note_dialog, null);
        nameField=(EditText) view.findViewById(R.id.nameField);
        contentsField=(EditText)view.findViewById(R.id.contentField);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddNoteListener) activity;
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
    private String format(String s){
        s.replaceAll("\\n", "\\u000A");
        return s;
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
    public interface AddNoteListener {
        // TODO: Update argument type and name
        void addNewNote(String name, String text);
    }

}
