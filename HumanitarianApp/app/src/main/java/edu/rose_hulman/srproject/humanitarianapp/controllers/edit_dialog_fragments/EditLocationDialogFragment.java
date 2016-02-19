package edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 *

 */
public class EditLocationDialogFragment extends DialogFragment {


    private EditText nameField;
    private EditText latField;
    private EditText lngField;
    private boolean nameEdited=false;
    private boolean latEdited=false;
    private boolean lngEdited=false;
    Location location;
    private long ID;



    public EditLocationDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments()!=null){
            ID=getArguments().getLong("ID");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View v=onCreateView(inflater);
        builder.setView(v)
                // Edit action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
//                        String name=nameField.getText().toString();
//
//
                                if (nameEdited || latEdited || latEdited) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("{\"doc\":{");
                                    if (nameEdited) {
                                        String name = nameField.getText().toString();
                                        sb.append("\"name\": \"" + name + "\"");
                                        location.setName(name);
                                        if (latEdited || lngEdited) {
                                            sb.append(",");
                                        }
                                    }
                                    if (latEdited) {
                                        float lat = Float.parseFloat(latField.getText().toString());
                                        sb.append("\"lat\": \"" + lat + "\"");
                                        location.setLat(lat);
                                        if (lngEdited) {
                                            sb.append(",");
                                        }
                                    }
                                    if (lngEdited) {
                                        float lng = Float.parseFloat(lngField.getText().toString());
                                        sb.append("\"lng\": \"" + lng + "\"");
                                        location.setLng(lng);
                                    }
                                    sb.append("}}");

                                    NonLocalDataService service = new NonLocalDataService();
//                                String phone = phoneField.getText().toString();
//                                String email = emailField.getText().toString();
                                    //TODO implement role

                                    service.updateLocation(location.getID(), sb.toString(), new Callback<Response>() {
                                        @Override
                                        public void success(Response response, Response response2) {
                                            Log.wtf("s40", "Successful edit of location " + location.getName());
                                            //Toast.makeText(getActivity(), "Successful edit of project "+p.getName(), Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.e("s40 RetroFitError", error.getMessage());
                                        }
                                    });
                                }
                                EditLocationDialogFragment.this.getDialog().dismiss();
                            }
                        }

                )
                            .

                    setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    EditLocationDialogFragment.this.getDialog().dismiss();
                                }
                            }

                    );
                    return builder.create();

                }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_location_dialog, null);
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
        latField=(EditText) view.findViewById(R.id.latField);
        latField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable e) {
                latEdited = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        lngField=(EditText)view.findViewById(R.id.lngField);
        lngField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable e) {
                lngEdited = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        NonLocalDataService service=new NonLocalDataService();
        service.service.getLocation("" + ID, new Callback<Response>() {
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
                    location = new Location(Integer.parseInt(((String) map.get("_id"))));
                    location.setName((String) source.get("name"));
                    location.setLat(Float.parseFloat((String) source.get("lat")));
                    location.setLng(Float.parseFloat((String) source.get("lng")));
                    //Log.w("Type of lastLocation", .get("lastLocation"))
                    ArrayList<HashMap<String, Object>> parents = (ArrayList) source.get("parentIDs");
                    for (HashMap parent : parents) {
                        if (parent.containsKey("projectID")) {
                            location.addNewProjectID(Long.parseLong((String) parent.get("projectID")));
                        } else if (parent.containsKey("groupID")) {
                            location.addNewGroupID(Long.parseLong((String) parent.get("groupID")));
                        }
                    }

                    nameField.setText(location.getName());
                    latField.setText(location.getLat() + "");
                    lngField.setText(location.getLng() + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("s40 RetrofitError", error.getMessage());
            }
        });
        return view;
    }




}
