package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ChecklistsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.NotesListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.PeopleListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ShipmentsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.models.Worker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 */
public class GroupFragment extends Fragment implements ChecklistsListFragment.ChecklistsListListener,
        NotesListFragment.NotesListListener, PeopleListFragment.PeopleListListener, ShipmentsListFragment.ShipmentsListListener,
        ShipmentFragment.ShipmentFragmentListener, WorkerFragment.WorkerFragmentListener,
        ChecklistFragment.ChecklistFragmentListener, NoteFragment.NoteFragmentListener{

    private Group group;
    private Checklist selectedChecklist;
    private Note selectedNote;
    private Worker selectedWorker;
    private Shipment selectedShipment;
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
        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPeople();
            }
        });
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotes();
            }
        });
        checklistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChecklists();
            }
        });
        shipmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShipments();
            }
        });
        return v;

    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) getParentFragment();
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
    private void showPeople(){
        Fragment fragment = new PeopleListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }
    private void showNotes(){
        Fragment fragment = new NotesListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }
    private void showChecklists(){
        Fragment fragment = new ChecklistsListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }
    private void showShipments(){
        Fragment fragment = new ShipmentsListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onItemSelected(Checklist t) {
        selectedChecklist=t;
        Fragment fragment = new ChecklistFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onItemSelected(Note t) {
        selectedNote=t;
        Fragment fragment = new NoteFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onItemSelected(Worker t) {
        selectedWorker=t;
        Fragment fragment = new WorkerFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onItemSelected(Shipment t) {
        selectedShipment=t;
        Fragment fragment = new ShipmentFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }

    public Checklist getSelectedChecklist() {
        return selectedChecklist;
    }

    public Note getSelectedNote() {
        return selectedNote;
    }

    public Shipment getSelectedShipment() {
        return selectedShipment;
    }

    public Worker getSelectedWorker() {
        return selectedWorker;
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
        public Group getSelectedGroup();
    }

}
