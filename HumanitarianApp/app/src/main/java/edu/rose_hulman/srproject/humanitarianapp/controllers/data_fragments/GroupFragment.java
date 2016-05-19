package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 */
public class GroupFragment extends Fragment implements AbstractDataFragment{

    private Group group;

    private OnFragmentInteractionListener mListener;



    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        group=mListener.getSelectedGroup();
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_group, container, false);
        TextView title=(TextView)v.findViewById(R.id.title);
        title.setText(group.getName());
        Button peopleButton=(Button) v.findViewById(R.id.peopleButton);
        Button notesButton=(Button) v.findViewById(R.id.notesButton);
        Button checklistsButton=(Button)v.findViewById(R.id.checklistsButton);
        Button shipmentsButton=(Button) v.findViewById(R.id.shipmentsButton);
        Button threadsButton=(Button) v.findViewById(R.id.threadsButton);
        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showPeople(false);
            }
        });
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showNotes();
            }
        });
        checklistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showChecklists();
            }
        });
        shipmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showShipments();
            }
        });
        threadsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showMessageThreads();
            }
        });
        return v;

    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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
    public interface OnFragmentInteractionListener {
        Group getSelectedGroup();
        void showPeople(boolean fromProjects);
        void showNotes();
        void showChecklists();
        void showShipments();
        void showMessageThreads();
    }

}
