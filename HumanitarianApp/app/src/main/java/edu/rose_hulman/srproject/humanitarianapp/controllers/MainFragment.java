package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.ChecklistFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.GroupFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.LocationFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.NoteFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.ProjectFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.ShipmentFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.PersonFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ChecklistsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.GroupsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.LocationsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.NotesListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.PeopleListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ProjectsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ShipmentsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.widgets.TabHeader;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        PersonFragment.WorkerFragmentListener, GroupFragment.OnFragmentInteractionListener,
        ChecklistsListFragment.ChecklistsListListener,
        NotesListFragment.NotesListListener,  ShipmentsListFragment.ShipmentsListListener,
        ShipmentFragment.ShipmentFragmentListener,
        ChecklistFragment.ChecklistFragmentListener, NoteFragment.NoteFragmentListener,
        LocationsListFragment.LocationsListListener, LocationFragment.LocationFragmentListener,
    Backable, CRUDInterface {

	private CRUDListener mListener;
	
    
    private TabHeader tabHeader;
    private boolean isFromProject;

    

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("In MAINFRAGMENT", "");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_main, container, false);
        tabHeader = new TabHeader();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabheadercontainer, tabHeader);
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
        try{
            mListener=(CRUDListener) activity;
        }catch(ClassCastException e){
            Log.e("Class Cast Exception", e.getMessage());
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onProjectsButtonClicked() {
        Fragment fragment = new ProjectsListFragment();
        Log.wtf("HERE", "HERE");

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
        mListener.setSelectedProject(project);
        Fragment fragment = new ProjectFragment();
        tabHeader.setAddButtonVisible(View.GONE);
        tabHeader.setEditButtonVisible(View.VISIBLE);
        tabHeader.setHideButtonVisible(View.VISIBLE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();

    }


    @Override
    public Project getSelectedProject() {
        return mListener.getSelectedProject();
    }

    @Override
    public void onItemSelected(Group group) {
        mListener.setSelectedGroup(group);
        Fragment fragment = new GroupFragment();
        tabHeader.setAddButtonVisible(View.GONE);
        tabHeader.setEditButtonVisible(View.VISIBLE);
        tabHeader.setHideButtonVisible(View.VISIBLE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();

    }





    @Override
    public Group getSelectedGroup() {
        return mListener.getSelectedGroup();
    }






    public void showGroups(){
        Fragment fragment = new GroupsListFragment();
        tabHeader.setAddButtonVisible(View.VISIBLE);
        tabHeader.setEditButtonVisible(View.GONE);
        tabHeader.setHideButtonVisible(View.GONE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    public void showPeople(boolean fromProject){
        Fragment fragment = new PeopleListFragment();
        this.isFromProject=fromProject;
        Bundle args=new Bundle();
        args.putBoolean("isFromProject", fromProject);
        fragment.setArguments(args);
        tabHeader.setAddButtonVisible(View.VISIBLE);
        tabHeader.setEditButtonVisible(View.GONE);
        tabHeader.setHideButtonVisible(View.GONE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    public void showLocations(){
        Fragment fragment = new LocationsListFragment();
        tabHeader.setAddButtonVisible(View.VISIBLE);
        tabHeader.setEditButtonVisible(View.GONE);
        tabHeader.setHideButtonVisible(View.GONE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    public void showNotes(){
        Fragment fragment = new NotesListFragment();
        tabHeader.setAddButtonVisible(View.VISIBLE);
        tabHeader.setEditButtonVisible(View.GONE);
        tabHeader.setHideButtonVisible(View.GONE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    public void showChecklists(){
        Fragment fragment = new ChecklistsListFragment();
        tabHeader.setAddButtonVisible(View.VISIBLE);
        tabHeader.setEditButtonVisible(View.GONE);
        tabHeader.setHideButtonVisible(View.GONE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    public void showShipments(){
        Fragment fragment = new ShipmentsListFragment();
        tabHeader.setAddButtonVisible(View.VISIBLE);
        tabHeader.setEditButtonVisible(View.GONE);
        tabHeader.setHideButtonVisible(View.GONE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Checklist t) {
        mListener.setSelectedChecklist(t);
        Fragment fragment = new ChecklistFragment();
        tabHeader.setAddButtonVisible(View.GONE);
        tabHeader.setEditButtonVisible(View.VISIBLE);
        tabHeader.setHideButtonVisible(View.VISIBLE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Note t) {
        mListener.setSelectedNote(t);
        Fragment fragment = new NoteFragment();
        tabHeader.setAddButtonVisible(View.GONE);
        tabHeader.setEditButtonVisible(View.VISIBLE);
        tabHeader.setHideButtonVisible(View.VISIBLE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Person t) {
        mListener.setSelectedPerson(t);
        Fragment fragment = new PersonFragment();
        tabHeader.setAddButtonVisible(View.GONE);
        tabHeader.setEditButtonVisible(View.VISIBLE);
        tabHeader.setHideButtonVisible(View.VISIBLE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    @Override
    public boolean isFromProject() {
        return this.isFromProject;
    }

    @Override
    public void onItemSelected(Shipment t) {
        mListener.setSelectedShipment(t);
        Fragment fragment = new ShipmentFragment();
        tabHeader.setAddButtonVisible(View.GONE);
        tabHeader.setEditButtonVisible(View.VISIBLE);
        tabHeader.setHideButtonVisible(View.VISIBLE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }

    public Checklist getSelectedChecklist() {
        return mListener.getSelectedChecklist();
    }

    @Override
    public void editChecklist(Checklist c) {
        mListener.editChecklist(c);
    }

    public Note getSelectedNote() {
        return mListener.getSelectedNote();
    }

    @Override
    public void saveNote(String title, String body){
        body=body.replaceAll("\n", "\\\n");
        mListener.getSelectedNote().setTitle(title);
        mListener.getSelectedNote().setBody(body);
        NonLocalDataService service=new NonLocalDataService();
        service.updateNote(mListener.getSelectedNote().getID(), title, body, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {

            }

            @Override
            public void failure(RetrofitError error) {
                Log.w("RetrofitError", error.getMessage());
            }
        });

    }



    public Shipment getSelectedShipment() {
        return mListener.getSelectedShipment();
    }

    public Person getSelectedPerson() {
        return mListener.getSelectedPerson();
    }

    @Override
    public void goBack() {

        FragmentManager fm = getChildFragmentManager();
        if (fm.getBackStackEntryCount()>1) {
            fm.popBackStack();
        }
    }

    @Override
    public Location getSelectedLocation() {
        return mListener.getSelectedLocation();
    }

    @Override
    public void onItemSelected(Location t) {
        mListener.setSelectedLocation(t);
        Fragment fragment = new LocationFragment();
        tabHeader.setAddButtonVisible(View.GONE);
        tabHeader.setEditButtonVisible(View.VISIBLE);
        tabHeader.setHideButtonVisible(View.VISIBLE);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
	@Override
    public void add() {
        Fragment f=getChildFragmentManager().findFragmentById(R.id.tabContentContainer);

        if (f instanceof ProjectsListFragment){
            mListener.addProject();
        }
        else if (f instanceof GroupsListFragment) {
            mListener.addGroup();
        }
        else if (f instanceof ChecklistsListFragment){
            mListener.addChecklist();
        }
        else if (f instanceof LocationsListFragment){
            mListener.addLocation();
        }
        else if (f instanceof NotesListFragment){
            mListener.addNote();
        }
        else if (f instanceof PeopleListFragment){
            mListener.addPerson();
        }
        else if (f instanceof ShipmentsListFragment){
            mListener.addShipment();
        }


    }

    @Override
    public void edit() {
        Fragment f=getChildFragmentManager().findFragmentById(R.id.tabContentContainer);

        if (f instanceof ProjectFragment){
            mListener.showEditProject();
        }
        else if (f instanceof GroupFragment){
            mListener.showEditGroup();
        }
        else if (f instanceof ChecklistFragment){
            mListener.showEditChecklist();
        }
        else if (f instanceof LocationFragment){
            mListener.showEditLocation();
        }
        else if (f instanceof NoteFragment){
            mListener.showEditNote();
        }
        else if (f instanceof PersonFragment){
            mListener.showEditPerson();
        }
        else if (f instanceof ShipmentFragment){
            mListener.showEditShipment();
        }

    }

    @Override
    public void hide() {
        Fragment f=getChildFragmentManager().findFragmentById(R.id.tabContentContainer);

        if (f instanceof ProjectFragment){
            mListener.hideProject();
        }
        else if (f instanceof GroupFragment){
            mListener.hideGroup();
        }
        else if (f instanceof ChecklistFragment){
            mListener.hideChecklist();
        }
        else if (f instanceof LocationFragment){
            mListener.hideLocation();
        }
        else if (f instanceof NoteFragment){
            mListener.hideNote();
        }
        else if (f instanceof PersonFragment){
            mListener.hidePerson();
        }
        else if (f instanceof ShipmentFragment){
            mListener.hideShipment();
        }
    }

    @Override
    public void delete() {

    }

    public interface CRUDListener{
        //will probably need additional parameters added
        void addProject();
        void addGroup();
        void addChecklist();
        void addLocation();
        void addNote();
        void addPerson();
        void addShipment();
        
        void showEditProject();
        void showEditGroup();
        void showEditChecklist();
        void showEditLocation();
        void showEditNote();
        void showEditPerson();
        void showEditShipment();

        void hideProject();
        void hideGroup();
        void hideChecklist();
        void hideLocation();
        void hideNote();
        void hidePerson();
        void hideShipment();
        
        

        void editChecklist(Checklist c);


        public void setSelectedGroup(Group g);
        public void setSelectedPerson(Person p);
        public void setSelectedChecklist(Checklist c);
        public void setSelectedNote(Note n);
        public void setSelectedLocation(Location l);
        public void setSelectedShipment(Shipment s);
        public void setSelectedProject(Project p);

        public Group getSelectedGroup();
        public Person getSelectedPerson();
        public Checklist getSelectedChecklist();
        public Note getSelectedNote();
        public Location getSelectedLocation();
        public Shipment getSelectedShipment();
        public Project getSelectedProject();
        
    }

    
    
    
}