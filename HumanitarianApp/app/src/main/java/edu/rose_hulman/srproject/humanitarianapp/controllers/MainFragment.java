package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.ChecklistFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.GroupFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.NoteFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.ProjectFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.ShipmentFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.WorkerFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ChecklistsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.GroupsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.NotesListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.PeopleListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ProjectsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ShipmentsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.widgets.TabHeader;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.models.Worker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabSwitchListener} interface
 * to handle interaction events.
 *
 */
public class MainFragment extends Fragment implements TabSwitchListener,
        ProjectsListFragment.ProjectsListListener, ProjectFragment.ProjectFragmentListener,
        PeopleListFragment.PeopleListListener, GroupsListFragment.GroupsListListener,
        WorkerFragment.WorkerFragmentListener, GroupFragment.OnFragmentInteractionListener,
        ChecklistsListFragment.ChecklistsListListener,
        NotesListFragment.NotesListListener,  ShipmentsListFragment.ShipmentsListListener,
        ShipmentFragment.ShipmentFragmentListener,
        ChecklistFragment.ChecklistFragmentListener, NoteFragment.NoteFragmentListener,
    Backable{


    private Group selectedGroup;
    private Worker selectedWorker;
    private Checklist selectedChecklist;
    private Note selectedNote;


    private Shipment selectedShipment;
   Project selected;

    public MainFragment() {
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
        View v= inflater.inflate(R.layout.fragment_main, container, false);
        Fragment fragment = new TabHeader();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabheadercontainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();

        onProjectsButtonClicked();




//        mTabHost = (TabHost)v.findViewById(android.R.id.tabhost);
//        mTabHost.setup();
//        addTabs();
        return v;
    }
    private void addTabs(){
//        TabHost.TabSpec shipments=mTabHost.newTabSpec(this.getResources().getString(R.string.shipments));
//        TabHost.TabSpec people=mTabHost.newTabSpec(this.getResources().getString(R.string.people));
//        TabHost.TabSpec checklists=mTabHost.newTabSpec(this.getResources().getString(R.string.checklists));
//        TabHost.TabSpec notes=mTabHost.newTabSpec(this.getResources().getString(R.string.notes));
//        mTabHost.addTab(shipments);
//        mTabHost.addTab(people);
//        mTabHost.addTab(checklists);
//        mTabHost.addTab(notes);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onProjectsButtonClicked() {
        Fragment fragment = new ProjectsListFragment();


        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    @Override
    public void onPeopleButtonClicked() {

    }

    @Override
    public void onNotesButtonClicked() {

    }

    @Override
    public void onChecklistsButtonClicked() {

    }


    @Override
    public void onItemSelected(Project project) {
        this.selected=project;
        Fragment fragment = new ProjectFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();

    }


    @Override
    public Project getSelectedProject() {
        return selected;
    }

    @Override
    public void onItemSelected(Group group) {
        selectedGroup=group;
        Fragment fragment = new GroupFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();

    }





    @Override
    public Group getSelectedGroup() {
        return selectedGroup;
    }






    public void showGroups(){
        Fragment fragment = new GroupsListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    public void showPeople(){
        Fragment fragment = new PeopleListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    public void showNotes(){
        Fragment fragment = new NotesListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    public void showChecklists(){
        Fragment fragment = new ChecklistsListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    public void showShipments(){
        Fragment fragment = new ShipmentsListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Checklist t) {
        selectedChecklist=t;
        Fragment fragment = new ChecklistFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Note t) {
        selectedNote=t;
        Fragment fragment = new NoteFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Worker t) {
        selectedWorker=t;
        Fragment fragment = new WorkerFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Shipment t) {
        selectedShipment=t;
        Fragment fragment = new ShipmentFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
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

    @Override
    public void goBack() {

        FragmentManager fm = getChildFragmentManager();
        fm.popBackStack();
    }
}