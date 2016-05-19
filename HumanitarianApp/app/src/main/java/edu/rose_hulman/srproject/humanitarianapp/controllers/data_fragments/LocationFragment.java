package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;

/**
 * Created by daveyle on 10/6/2015.
 */
public class LocationFragment extends Fragment implements AbstractDataFragment{

    private LocationFragmentListener mListener;
    private Location location;



    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        location=mListener.getSelectedLocation();
        View view= inflater.inflate(R.layout.fragment_location, container, false);
        TextView name=(TextView)view.findViewById(R.id.nameField);
        name.setText(location.getName());
        TextView lat=(TextView)view.findViewById(R.id.latField);
        lat.setText(location.getLat()+"");
        TextView lng=(TextView) view.findViewById(R.id.longField);
        lng.setText(location.getLng() + "");

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (LocationFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoteFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void refreshContent() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

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
    public interface LocationFragmentListener {

        Location getSelectedLocation();

    }
}
