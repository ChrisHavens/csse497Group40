package edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPersonDialogFragment.AddPersonListener} interface
 * to handle interaction events.

 */
public class AddPersonDialogFragment extends DialogFragment {
    
    private AddPersonListener mListener;
    private EditText idField;
    LinearLayout nameLayout;
    LinearLayout emailLayout;
    TextView nameTextView;
    TextView emailTextView;
    TextView personDNE;
    Person p;
    NonLocalDataService service=new NonLocalDataService();

//    private EditText nameField;
//    private EditText phoneField;
//    private EditText emailField;
    //private Spinner roleSpinner;


    
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
                        //String idNum=idField.getText().toString();

//                        String name=nameField.getText().toString();
//                        String phone=phoneField.getText().toString();
//                        String email=emailField.getText().toString();
//                        Roles.PersonRoles role= Roles.PersonRoles.valueOf(((String)roleSpinner.getSelectedItem()).toUpperCase());
                        //mListener.addNewPerson(name, phone, email, role);
                        mListener.addNewPerson(p);
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
        idField=(EditText)view.findViewById(R.id.idField);
        Button checkButton=(Button) view.findViewById(R.id.checkButton);
        nameLayout=(LinearLayout)view.findViewById(R.id.nameLayout);
        emailLayout=(LinearLayout)view.findViewById(R.id.emailLayout);
        nameTextView=(TextView) view.findViewById(R.id.nameTextView);
        emailTextView=(TextView)view.findViewById(R.id.emailTextView);
        personDNE=(TextView)view.findViewById(R.id.nosuchpersonTV);
        nameLayout.setVisibility(View.GONE);
        emailLayout.setVisibility(View.GONE);
        personDNE.setVisibility(View.GONE);
        checkButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               check();
                                           }
                                       }
        );

//        nameField=(EditText) view.findViewById(R.id.nameField);
//        phoneField=(EditText) view.findViewById(R.id.phoneField);
//        emailField=(EditText)view.findViewById(R.id.emailField);
//        roleSpinner=(Spinner)view.findViewById(R.id.roleSpinner);
        //roleSpinner.setAdapter(new RoleSpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1,Roles.roles));
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
    private void check(){
        String id=idField.getText().toString();
        personExists(id);

    }
    public void personExists(String id){
        Callback<Response> callback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<HashMap<String, Object>> typeReference =
                        new TypeReference<HashMap<String, Object>>() {
                        };
                try {
                    HashMap<String, Object> map = mapper.readValue(response.getBody().in(), typeReference);
                    HashMap<String, Object> source = (HashMap) map.get("_source");
                    if (source != null) {
                        p=new Person(Integer.parseInt(((String)map.get("_id"))));
                        p.setName((String) source.get("name"));
                        p.setEmail((String) source.get("email"));
                        p.setPhoneNumber((String) source.get("phone"));
                        if(source.get("dateArchived") == null)
                            p.setHidden(false);
                        else
                            p.setHidden(true);
                        //Log.w("Type of lastLocation", .get("lastLocation"))
                        if (source.get("lastLocation")!=null) {
                            HashMap<String, Object> lastLoc = (HashMap) source.get("lastLocation");

                            Person.PersonLocation personLoc = new Person.PersonLocation();
                            personLoc.setLat(Float.parseFloat((String) lastLoc.get("lat")));
                            personLoc.setLng(Float.parseFloat((String) lastLoc.get("lng")));
                            personLoc.setName((String) lastLoc.get("name"));
                            personLoc.setTime((String) lastLoc.get("time"));
                            p.setLastCheckin(personLoc);
                        }
                        nameLayout.setVisibility(View.VISIBLE);
                        emailLayout.setVisibility(View.VISIBLE);
                        personDNE.setVisibility(View.GONE);
                        nameTextView.setText(p.getName());
                        emailTextView.setText(p.getEmail());

                    }
                    else{
                        nameLayout.setVisibility(View.GONE);
                        emailLayout.setVisibility(View.GONE);
                        personDNE.setVisibility(View.VISIBLE);
                    }


                } catch (Exception e) {

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());
                Log.wtf("s40", error.getUrl());
                nameLayout.setVisibility(View.GONE);
                emailLayout.setVisibility(View.GONE);
                personDNE.setVisibility(View.VISIBLE);
            }
        };
        service.service.getPerson(id, callback);
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
        public void addNewPerson(Person p);

    }

    //
//    private class RoleSpinnerAdapter extends ArrayAdapter<String>{
//        private final String[] objects;
//        private final int layout;
//
//
//        public RoleSpinnerAdapter(Context context, int resource, String[] objects) {
//            super(context, resource, objects);
//            this.objects=objects;
//            this.layout=resource;
//
//        }
//        public View getView(int position, View convertView, ViewGroup parent){
//            LayoutInflater inflater = (LayoutInflater) getContext()
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(layout, parent, false);
//            TextView line1=(TextView) view.findViewById(android.R.id.text1);
//            line1.setText(objects[position]);
//            return view;
//
//        }
//    }

}
