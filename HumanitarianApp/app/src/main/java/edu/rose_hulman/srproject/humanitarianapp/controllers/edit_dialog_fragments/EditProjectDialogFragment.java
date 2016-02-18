package edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import edu.rose_hulman.srproject.humanitarianapp.R;
//import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.controllers.Interfaces;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by daveyle on 10/30/2015.
 */
public class EditProjectDialogFragment extends DialogFragment {

    private Interfaces.UserIDGetter mListener;
    //private EditProjectListener mListener;
    private EditText nameField;
    private long projectID;
    Project p;



    public EditProjectDialogFragment() {
        // Required empty public constructor
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments()!=null){
            projectID=getArguments().getLong("projectID");
            Log.w("Got projectID", "" + projectID);
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
                        String name=nameField.getText().toString();
                        p.setName(name);
                        LocalDataSaver.updateProject(p);
                        NonLocalDataService service=new NonLocalDataService();
                        StringBuilder sb=new StringBuilder();
                        sb.append("{\"doc\":{\"name\": \""+name+"\"}}");
                        service.updateProject(p.getId(), sb.toString(), mListener.getUserID(),new Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                Log.wtf("s40", "Successful edit of project "+p.getName());
                                //Toast.makeText(getActivity(), "Successful edit of project "+p.getName(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e("s40 RetroFitError", error.getMessage());
                            }
                        });
                        EditProjectDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditProjectDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_project_dialog, null);
        nameField=(EditText) view.findViewById(R.id.nameField);
        p= Project.getProjectByID(projectID);
        nameField.setText(p.getName());

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (Interfaces.UserIDGetter) activity;
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
//    public interface EditProjectListener {
//        // TODO: Update argument type and name
//        public void editProject(Project p);
//
//    }

}