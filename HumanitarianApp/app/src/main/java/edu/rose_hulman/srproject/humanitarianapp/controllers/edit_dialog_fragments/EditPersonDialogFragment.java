package edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.Interfaces;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by havenscs on 10/19/2015.
 */
public class EditPersonDialogFragment extends DialogFragment {


    private Interfaces.UserIDGetter mListener;
    private EditText nameField;
    private EditText phoneField;
    private EditText emailField;
    private Spinner roleSpinner;

    private boolean nameEdited=false;
    private boolean phoneEdited=false;
    private boolean emailEdited=false;
    Person person;
    //private boolean roleEdited=false;
    private long personToEditID;



    public EditPersonDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments()!=null){
            personToEditID=getArguments().getLong("personID");
            Log.w("Got personID", "" + personToEditID);
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
                                if (nameEdited || phoneEdited || emailEdited) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("{\"doc\":{");
                                    if (nameEdited) {
                                        String name = nameField.getText().toString();
                                        sb.append("\"name\": \"" + name + "\"");
                                        person.setName(name);
                                        if (phoneEdited || emailEdited) {
                                            sb.append(",");
                                        }
                                    }
                                    if (phoneEdited) {
                                        String phone = phoneField.getText().toString();
                                        sb.append("\"phone\": \"" + phone + "\"");
                                        person.setPhoneNumber(phone);
                                        if (emailEdited) {
                                            sb.append(",");
                                        }
                                    }
                                    if (emailEdited) {
                                        String email = emailField.getText().toString();
                                        sb.append("\"email\": \"" + email + "\"");
                                        person.setName(email);
                                    }
                                    sb.append("}}");
                                    NonLocalDataService service = new NonLocalDataService();
//                                String phone = phoneField.getText().toString();
//                                String email = emailField.getText().toString();
                                    //TODO implement role
                                    Roles.PersonRoles role = Roles.PersonRoles.valueOf(((String) roleSpinner.getSelectedItem()).toUpperCase());
                                    Log.d("ED", sb.toString());
                                    service.updatePerson(person, sb.toString(), mListener.getUserID(),new Callback<Response>() {
                                        @Override
                                        public void success(Response response, Response response2) {
                                            Log.wtf("s40", "Successful edit of person " + person.getName());
                                            //Toast.makeText(getActivity(), "Successful edit of project "+p.getName(), Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.e("s40 RetroFitError", error.getMessage());
                                        }
                                    });
                                }
                                //mListener.editPerson(name, phone, email, role, personToEditID);
                                EditPersonDialogFragment.this.getDialog().dismiss();
                            }
                        }

                )
                .

                        setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        EditPersonDialogFragment.this.getDialog().dismiss();
                                }
                            }

                    );
                    return builder.create();

                }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_person_dialog, null);
        nameField=(EditText) view.findViewById(R.id.nameField);
        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable e) {
                nameEdited = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        phoneField=(EditText) view.findViewById(R.id.phoneField);
        phoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable e) {
                phoneEdited = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        emailField=(EditText)view.findViewById(R.id.emailField);
        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable e) {
                emailEdited = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        NonLocalDataService service=new NonLocalDataService();
        service.service.getPerson("" + personToEditID, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<HashMap<String, Object>> typeReference =
                        new TypeReference<HashMap<String, Object>>() {
                        };
                try {
                    HashMap<String, Object> map = mapper.readValue(response.getBody().in(), typeReference);
                    HashMap<String, Object> source = (HashMap) map.get("_source");
                    for (String s : source.keySet()) {
                        Log.e("Result", s);
                    }
                    person = new Person(Integer.parseInt(((String) map.get("_id"))));
                    person.setName((String) source.get("name"));
                    person.setEmail((String) source.get("email"));
                    person.setPhoneNumber((String) source.get("phone"));
                    //Log.w("Type of lastLocation", .get("lastLocation"))
                    if (source.containsKey("lastLocation") ){

                        HashMap<String, Object> lastLoc = (HashMap) source.get("lastLocation");
                        Person.PersonLocation personLoc = new Person.PersonLocation();
                        personLoc.setLat(Float.parseFloat((String) lastLoc.get("lat")));
                        personLoc.setLng(Float.parseFloat((String) lastLoc.get("lng")));
                        personLoc.setName((String) lastLoc.get("name"));
                        personLoc.setTime((String) lastLoc.get("time"));

                        person.setLastCheckin(personLoc);
                    }
                    nameField.setText(person.getName());
                    phoneField.setText(person.getPhoneNumber());
                    emailField.setText(person.getEmail());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("s40 RetrofitError", error.getMessage());
            }
        });

//                Person.getWorkerByID(personToEditID);
//        nameField.setText(person.getName(), TextView.BufferType.EDITABLE);
//        phoneField.setText(person.getPhoneNumber(), TextView.BufferType.EDITABLE);
//        emailField.setText(person.getEmail(), TextView.BufferType.EDITABLE);
//        roleSpinner=(Spinner)view.findViewById(R.id.roleSpinner);
//        roleSpinner.setAdapter(new RoleSpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1,Roles.roles));
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
//    public interface EditPersonListener {
//        public void editPerson(String name, String phone, String email, Roles.PersonRoles role, long personID);
//    }
    //
    private class RoleSpinnerAdapter extends ArrayAdapter<String> {
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
