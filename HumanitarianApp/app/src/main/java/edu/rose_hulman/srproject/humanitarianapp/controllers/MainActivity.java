package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.DialogFragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;


import edu.rose_hulman.srproject.humanitarianapp.CoordinatesGetter;
import edu.rose_hulman.srproject.humanitarianapp.R;

import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddChecklistDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddGroupDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddLocationDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddNoteDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddPersonDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddProjectDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddShipmentDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.ChecklistFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.GroupFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.LocationFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.NoteFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.PersonFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.ProjectFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.ShipmentFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments.MessageThreadFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditChecklistDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditGroupDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditLocationDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditPersonDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditProjectDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditShipmentDialogFragment;


import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ChecklistsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.GroupsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.LocationsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.NotesListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.PeopleListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ProjectsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ShipmentsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.MessageThreadsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataDBHelper;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataRetriver;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;

import edu.rose_hulman.srproject.humanitarianapp.localdata.PreferencesManager;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;

import edu.rose_hulman.srproject.humanitarianapp.models.*;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


public class MainActivity extends ActionBarActivity implements //TabSwitchListener,
        AddPersonDialogFragment.AddPersonListener, //MainFragment.CRUDListener,
        AddProjectDialogFragment.AddProjectListener,
        AddGroupDialogFragment.AddGroupListener,
        AddChecklistDialogFragment.AddChecklistListener,
        //EditPersonDialogFragment.EditPersonListener,
        AddLocationDialogFragment.AddLocationListener,
        EditChecklistDialogFragment.EditChecklistListener,
        AddNoteDialogFragment.AddNoteListener,
        AddShipmentDialogFragment.AddShipmentListener,
        EditShipmentDialogFragment.EditShipmentListener,
        ProjectsListFragment.ProjectsListListener, ProjectFragment.ProjectFragmentListener,
        PeopleListFragment.PeopleListListener, GroupsListFragment.GroupsListListener,
        PersonFragment.WorkerFragmentListener, GroupFragment.OnFragmentInteractionListener,
        ChecklistsListFragment.ChecklistsListListener,
        NotesListFragment.NotesListListener,  ShipmentsListFragment.ShipmentsListListener,
        ShipmentFragment.ShipmentFragmentListener,
        ChecklistFragment.ChecklistFragmentListener, NoteFragment.NoteFragmentListener,
        LocationsListFragment.LocationsListListener, LocationFragment.LocationFragmentListener, CRUDInterface,
        android.support.v4.app.FragmentManager.OnBackStackChangedListener,
        MessageThreadFragment.ThreadFragmentListener, MessageThreadsListFragment.ThreadsListListener,
                GoogleApiClient.OnConnectionFailedListener
        //,
        //EditProjectDialogFragment.EditProjectListener,
        //EditGroupDialogFragment.AddGroupListener
{

    public static String GoogleMapsAPIKey="AIzaSyCJLQb_7gSUe-Vg5S0jMvigSCJkbcJ_8aE";
    //NonLocalDataService service=new NonLocalDataService();
    MainServiceActions actions;

    private Toolbar toolbar;
    private boolean showHidden=false;
    private String userID;
    private CoordinatesGetter coordGetter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        userID=intent.getStringExtra("userID");
        Toast.makeText(this, "User id: "+userID, Toast.LENGTH_LONG).show();
        // Startup Code Here
        PreferencesManager.setPreferencesFile(getPreferences(Context.MODE_PRIVATE));
        LocalDataDBHelper dbHelper = new LocalDataDBHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ApplicationWideData.db = db;
        setContentView(R.layout.activity_main);
        ApplicationWideData.initilizeKnownVariables();
        actions=new MainServiceActions(getApplicationContext());
        actions.setStoredProjects(LocalDataRetriver.getStoredProjects());
        toolbar=(Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_dark_am);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        Log.d("TAG", "Does it get here?");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportFragmentManager().addOnBackStackChangedListener(new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    //refreshContent() TODO, causes infinite loop?
                }
            }
        });


        onProjectsButtonClicked();
    }

    //this is used to fix the issue of the back button going too far back
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            super.onBackPressed();
        Log.d("TAG", "About to refresh content after back button");
        refreshContent();


        //else do nothing, it is already at the home page
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setVisibilityAdd(true);
        setVisibilityEdit(false);
        setVisibilityShow(false);
        setVisibilityHide(false);
        setVisibilityShowHidden(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id== R.id.hideButton){
            hide();
            return true;
        }

        else if(id== android.R.id.home)
        {
            Log.d("ED", "they pressed the android back button");
            //refreshContent();
            this.onBackPressed();
            return true;
        }

        else if (id== R.id.editButton){
            edit();
            return true;
        }
        else if (id== R.id.addButton){
            add();
            return true;
        }
        else if (id== R.id.button2){
            try {
                checkInUser();
            }
            catch (IOException e) {
                Log.d("ED","failed to check in user");
            }
            return true;
        }
        else if (id==R.id.showHiddenButton){
            if (showHidden){
                showHidden=false;
                MenuItem showHiddenItem=toolbar.getMenu().findItem(R.id.showHiddenButton);
                if (showHiddenItem!=null)
                showHiddenItem.setTitle(R.string.showHiddenItems);

            }
            else{
                showHidden=true;
                MenuItem showHiddenItem=toolbar.getMenu().findItem(R.id.showHiddenButton);
                if (showHiddenItem!=null)
                showHiddenItem.setTitle(R.string.hideHiddenItems);
            }
            refreshContent();
            return true;
        }
        if (id== R.id.changeSync){
            ApplicationWideData.switchSyncMode();
            return true;
        }

        if (id== R.id.forceSync){
            ApplicationWideData.sync();
            return true;
        }
        if (id==R.id.logout){
            switchToLogin();
        }




        return super.onOptionsItemSelected(item);
    }

    private void checkInUser() throws IOException
    {
        coordGetter = new CoordinatesGetter(this.getApplicationContext());
        android.location.Location loc = coordGetter.getLocation();
        Context context = getApplicationContext();
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        Random rand = new Random();

        double lat = loc.getLatitude();
        double lng = loc.getLongitude() + rand.nextInt(20);
        String latS = loc.getLatitude() + "";
        String lngS = loc.getLongitude() + "";
        String city = "";
        String country = "";
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date());
        Log.d("ED", date);
        List<Address> addresses = gcd.getFromLocation(lat, lng , 1);
        if (addresses.size() > 0) {
            city = addresses.get(0).getLocality();
            country = addresses.get(0).getCountryName();
        }
        CharSequence text = "Location is " + loc.getLatitude() + " " + loc.getLongitude() + " " + city + ", " + country;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


        //TODO, replace hard coded ID with actual one

        long ID = 3105;
        String JSONed = String.format("{\"doc\":{\"lastLocation\":{\"lat\":\"%s\",\"lng\":\"%s\",\"name\":\"%s\",\"time\":\"%s\"}}}", lat, lng, city + ", " + country, date);
        Log.d("ED", JSONed);
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Log.d("ED", "successfully changed person location");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        //{"doc":{"lastLocation":{"lat":"80.01","lng":"57.34","name":"test","time":"2015-12-06 17:33"}}}
        actions.service.updatePerson(ID, JSONed, responseCallback);



    }
    private void resetToolbar(){
        setVisibilityAdd(false);
        setVisibilityEdit(true);
        setVisibilityShow(false);
        setVisibilityHide(true);
        setVisibilityShowHidden(false);
    }

    private void refreshContent(){
        Log.d("TAG", "GOT Refreshed");
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.tabContentContainer);
        if (fragment instanceof ProjectsListFragment){
            changeFragmentToListNoBackStack(new ProjectsListFragment());
        }
        else if(fragment instanceof ProjectFragment){
            Log.d("TAG", "ProjectFragment");
            resetToolbar();
        }
        else if (fragment instanceof GroupsListFragment){
            changeFragmentToListNoBackStack(new GroupsListFragment());
        }
        else if(fragment instanceof GroupFragment){

        }
        else if (fragment instanceof PeopleListFragment){
            Fragment plfragment = new PeopleListFragment();

            Bundle args=new Bundle();
            args.putBoolean("isFromProject", actions.isFromProject());
            plfragment.setArguments(args);
            changeFragmentToListNoBackStack(plfragment);
        }
        else if(fragment instanceof PersonFragment){

        }
        else if (fragment instanceof LocationsListFragment){
            changeFragmentToListNoBackStack(new LocationsListFragment());
        }
        else if(fragment instanceof LocationFragment){

        }
        else if (fragment instanceof ChecklistsListFragment){
            changeFragmentToListNoBackStack(new ChecklistsListFragment());
        }
        else if(fragment instanceof ChecklistFragment){

        }
        else if (fragment instanceof NotesListFragment){
            changeFragmentToListNoBackStack(new NotesListFragment());
        }
        else if(fragment instanceof NoteFragment){

        }
        else if (fragment instanceof ShipmentsListFragment){
            changeFragmentToListNoBackStack(new ShipmentsListFragment());
        }
        else if(fragment instanceof ShipmentFragment){

        }
    }
    private void changeFragmentToSelected(Fragment fragment, Selectable selected){
        Log.d("ED", "changing fragments");
        setVisibilityAdd(false);
        setVisibilityEdit(true);
        setVisibilityHide(true);
        setVisibilityShow(false);

//        setVisibilityShow(false);
//        setVisibilityHide(true);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    private void changeFragmentToList(Fragment fragment){
        setVisibilityAdd(true);
        setVisibilityEdit(false);
        setVisibilityShow(false);
        setVisibilityHide(false);
        setVisibilityShowHidden(true);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    private void changeFragmentToSelectedNoBackStack(Fragment fragment, Selectable selected){
        setVisibilityAdd(false);
        setVisibilityEdit(true);
        setVisibilityShowHidden(false);
        if (selected.isHidden()){
            setVisibilityShow(true);
            setVisibilityHide(false);
        }
        else{
            setVisibilityHide(true);
            setVisibilityShow(false);
        }

//        setVisibilityShow(false);
//        setVisibilityHide(true);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fm.popBackStack();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }
    private void changeFragmentToListNoBackStack(Fragment fragment){
        Log.d("TAG", "This is a certain type of fragment");
        setVisibilityAdd(true);
        setVisibilityEdit(false);
        setVisibilityShow(false);
        setVisibilityHide(false);
        setVisibilityShowHidden(true);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.addToBackStack("backstack");
        transaction.commit();
    }



    public void onProjectsButtonClicked() {
        changeFragmentToList(new ProjectsListFragment());


    }

    @Override
    public void onItemSelected(Project project) {
        actions.setSelectedProject(project);
        changeFragmentToSelected(new ProjectFragment(), project);
    }
    @Override
    public void onItemSelected(Group group) {
        actions.setSelectedGroup(group);
        changeFragmentToSelected(new GroupFragment(), group);
    }
    @Override
    public void onItemSelected(Checklist t) {
        actions.setSelectedChecklist(t);
        changeFragmentToSelected(new ChecklistFragment(), t);
    }

    @Override
    public void onItemSelected(Note t) {
        actions.setSelectedNote(t);
        changeFragmentToSelected(new NoteFragment(), t);
    }

    @Override
    public void onItemSelected(Person t) {
        actions.setSelectedPerson(t);
        changeFragmentToSelected(new PersonFragment(),t);
    }
    @Override
    public void onItemSelected(Shipment t) {
        actions.setSelectedShipment(t);
        changeFragmentToSelected(new ShipmentFragment(),t);
    }
    @Override
    public void onItemSelected(Location t) {
        actions.setSelectedLocation(t);
        changeFragmentToSelected(new LocationFragment(), t);
    }
    @Override
    public void onItemSelected(MessageThread t) {
        actions.setSelectedMessageThread(t);
        changeFragmentToSelected(new MessageThreadFragment(), t);
    }





    private void setVisibilityAdd(boolean vis){
        MenuItem item=toolbar.getMenu().findItem(R.id.addButton);
        if (item!=null)
        item.setVisible(vis);
    }
    private void setVisibilityEdit(boolean vis){
        MenuItem item=toolbar.getMenu().findItem(R.id.editButton);
        if (item!=null)
        item.setVisible(vis);

    }
    private void setVisibilityShow(boolean vis){


    }
    private void setVisibilityHide(boolean vis){
        MenuItem item=toolbar.getMenu().findItem(R.id.hideButton);
        if (item!=null)
        item.setVisible(vis);

    }
    private void setVisibilityShowHidden(boolean vis){
        MenuItem item=toolbar.getMenu().findItem(R.id.showHiddenButton);
        if (item!=null)
        item.setVisible(vis);

    }


    public void showGroups(){
        changeFragmentToList(new GroupsListFragment());
    }
    public void showPeople(boolean fromProject){
        actions.setIsFromProject(fromProject);
        Fragment fragment = new PeopleListFragment();

        Bundle args=new Bundle();
        args.putBoolean("isFromProject", fromProject);
        fragment.setArguments(args);
        changeFragmentToList(fragment);
    }
    public void showLocations(){
        changeFragmentToList(new LocationsListFragment());
    }

    public void showNotes(){
        changeFragmentToList(new NotesListFragment());
    }
    public void showChecklists(){
        changeFragmentToList(new ChecklistsListFragment());
    }
    public void showMessageThreads(){
        changeFragmentToList(new MessageThreadsListFragment());
    }
    public void showShipments(){
        changeFragmentToList(new ShipmentsListFragment());
    }

    public void makePhoneCall(String s) {
        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + s.replaceAll("[^0-9|\\+]", "")));
        if (intent.resolveActivity(pm) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Not able to make phone calls!", Toast.LENGTH_LONG).show();
        }

    }

    public void makeText(String number) {
        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number));
        if (intent.resolveActivity(pm) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Not able to make text messages!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void saveNote(String title, String body){

        actions.saveNote(title, body);



    }
    public void checkIn() {
//        Client client = new TransportClient()
//   .addTransportAddress(new InetSocketTransportAddress("s40server.csse.rose-hulman.edu", 9300));
//	UpdateRequest updateRequest = new UpdateRequest();
//	updateRequest.index("s40");
//	updateRequest.type("person");
//	updateRequest.id("psn000");
//        try {
//            updateRequest.doc(jsonBuilder()
//.startObject()
//.field("location", getCurrentLocation())
//.endObject());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            client.update(updateRequest).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

    }


    private String getCurrentLocation(){
        String[] locations={"lcn01000", "lcn00002", "lcn02001", "lcn01001"};
        Random rand=new Random();
        return locations[rand.nextInt(4)];
    }



    //    @Override
//    public void editPerson(final String name, String phone, String email, Roles.PersonRoles role, long personID){
//        Person person = Person.getWorkerByID(personID);
//        person.setName(name);
//        person.setPhoneNumber(phone);
//        person.setEmail(email);
//        person.setRole(role);
//        Callback<Response> responseCallback=new Callback<Response>() {
//            @Override
//            public void success(Response response, Response response2) {
//                Toast.makeText(getApplicationContext(), "Successful editing of new person: "+name, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e("RetrofitError", error.getMessage());
//            }
//        };
//        service.updatePerson(person, responseCallback);
//    }
    @Override
    public void addNewPerson(final String name, String phone, String email, Roles.PersonRoles role) {
        actions.addNewPerson(name,phone,email,role);
    }
    @Override

    public void addNewProject(final String name) {
        actions.addNewProject(name);
    }

    @Override
    public void addNewGroup(final String name) {
        actions.addNewGroup(name);
    }

    @Override

    public void addNewLocation(final Location l) {
        actions.addNewLocation(l);
    }


    @Override
    public void addNewNote(String name, String contents){
        actions.addNewNote(name, contents);
    }
    @Override
    public void addNewShipment(final Shipment l) {
        actions.addNewShipment(l);
    }


    public void addProject() {
        //Toast.makeText(getApplicationContext(), "Number of projects in DB: " + this.storedProjects.size(), Toast.LENGTH_LONG).show();
        DialogFragment newFragment = new AddProjectDialogFragment();
        newFragment.show(getFragmentManager(), "addProject");
    }


    public void addGroup() {
        DialogFragment newFragment = new AddGroupDialogFragment();
        newFragment.show(getFragmentManager(), "addGroup");
    }


    public void addChecklist() {
        DialogFragment newFragment = new AddChecklistDialogFragment();

        Bundle b=new Bundle();
        b.putLong("parentID", actions.getSelectedGroup().getId());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "addChecklist");
    }


    public void addLocation() {
        DialogFragment newFragment = new AddLocationDialogFragment();
        Bundle b=new Bundle();
        b.putLong("projectID", actions.getSelectedProject().getId());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "addLocation");
    }


    public void addNote() {
        DialogFragment newFragment = new AddNoteDialogFragment();

//        Bundle b=new Bundle();
//        b.putLong("parentID", g.getId());
//        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "addNote");
    }


    public void addPerson() {
        DialogFragment newFragment = new AddPersonDialogFragment();
        newFragment.show(getFragmentManager(), "addPerson");
    }


    public void addShipment() {
        DialogFragment newFragment = new AddShipmentDialogFragment();
        Bundle b=new Bundle();
        b.putLong("groupID", actions.getSelectedGroup().getId());
        b.putLong("projectID", actions.getSelectedProject().getId());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "addShipment");
    }





    public void showEditProject() {
        //Log.wtf("ProjectID", selectedProject.getId()+"");
        DialogFragment newFragment = new EditProjectDialogFragment();
        Bundle b=new Bundle();
        b.putLong("projectID", actions.getSelectedProject().getId());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editProject");
    }


    public void showEditGroup() {
        //Log.wtf("GroupID", selectedGroup.getId()+"");
        DialogFragment newFragment = new EditGroupDialogFragment();
        Bundle b=new Bundle();
        b.putLong("groupID", actions.getSelectedGroup().getId());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editGroup");
    }


    public void showEditChecklist() {

        //Log.wtf("ChecklistID", selectedChecklist.getID()+"");

        DialogFragment newFragment = new EditChecklistDialogFragment();
//        Bundle b=new Bundle();
//        b.putLong("checklistID", c.getId());
//        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editChecklist");
    }


    public void showEditLocation() {
        DialogFragment newFragment = new EditLocationDialogFragment();
        Bundle b=new Bundle();
        b.putLong("ID", actions.getSelectedLocation().getID());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editLocation");
    }


    public void showEditNote() {

    }


    public void showEditPerson() {
       // Log.wtf("PersonID", selectedPerson.getID()+"");
        DialogFragment newFragment = new EditPersonDialogFragment();
        Bundle b=new Bundle();
        b.putLong("personID", actions.getSelectedPerson().getID());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editPerson");
    }


    public void showEditShipment() {
        DialogFragment newFragment = new EditShipmentDialogFragment();
        Bundle b=new Bundle();
        b.putLong("groupID", actions.getSelectedGroup().getId());
        b.putLong("projectID", actions.getSelectedProject().getId());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editShipment");
    }




    public void editProject(Project p){


    }
    public void editChecklist(final Checklist c){
        actions.editChecklist(c);
    }

    @Override
    public Group getSelectedGroup() {
        return actions.getSelectedGroup();
    }


    public void setSelectedGroup(Group selectedGroup) {
        actions.setSelectedGroup(selectedGroup);
    }

    @Override
    public Person getSelectedPerson() {
        return actions.getSelectedPerson();
    }

    @Override
    public void addNewChecklist(final Checklist checklist) {
        actions.addNewChecklist(checklist);
	}

    public void setSelectedPerson(Person selectedPerson) {
        actions.setSelectedPerson(selectedPerson);
    }

    @Override
    public Checklist getSelectedChecklist() {
        return actions.getSelectedChecklist();
    }


    public void setSelectedChecklist(Checklist selectedChecklist) {
        actions.setSelectedChecklist(selectedChecklist);
    }

    @Override
    public Note getSelectedNote() {
        return actions.getSelectedNote();
    }


    public void setSelectedNote(Note selectedNote) {
        actions.setSelectedNote(selectedNote);
    }

    @Override
    public Location getSelectedLocation() {
        return actions.getSelectedLocation();
    }


    public void setSelectedLocation(Location selectedLocation) {
        actions.setSelectedLocation(selectedLocation);
    }

    @Override
    public Shipment getSelectedShipment() {
        return actions.getSelectedShipment();
    }


    public void setSelectedShipment(Shipment selectedShipment) {
        actions.setSelectedShipment(selectedShipment);
    }

    @Override
    public Project getSelectedProject() {
        return actions.getSelectedProject();
    }



    @Override
    public boolean getShowHidden() {
        return showHidden;
    }


    public void setSelectedProject(Project selectedProject) {
        actions.setSelectedProject(selectedProject);
    }

    @Override
    public MessageThread getSelectedThread() {
        return actions.getSelectedMessageThread();
    }

    public String getUserID(){
        return this.userID;
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void add() {
        Fragment f=getSupportFragmentManager().findFragmentById(R.id.tabContentContainer);

        if (f instanceof ProjectsListFragment){
            addProject();
        }
        else if (f instanceof GroupsListFragment) {
            addGroup();
        }
        else if (f instanceof ChecklistsListFragment){
            addChecklist();
        }
        else if (f instanceof LocationsListFragment){
            addLocation();
        }
        else if (f instanceof NotesListFragment){
            addNote();
        }
        else if (f instanceof PeopleListFragment){
            addPerson();
        }
        else if (f instanceof ShipmentsListFragment){
            addShipment();
        }


    }

    @Override
    public void edit() {
        Fragment f=getSupportFragmentManager().findFragmentById(R.id.tabContentContainer);

        if (f instanceof ProjectFragment){
            showEditProject();
        }
        else if (f instanceof GroupFragment){
            showEditGroup();
        }
        else if (f instanceof ChecklistFragment){
            showEditChecklist();
        }
        else if (f instanceof LocationFragment){
            showEditLocation();
        }
        else if (f instanceof NoteFragment){
            showEditNote();
        }
        else if (f instanceof PersonFragment){
            showEditPerson();
        }
        else if (f instanceof ShipmentFragment){
            showEditShipment();
        }

    }

    @Override
    public void hide() {
        Fragment f=getSupportFragmentManager().findFragmentById(R.id.tabContentContainer);
            if (f instanceof ProjectFragment) {
                actions.hideProject();
            } else if (f instanceof GroupFragment) {
                actions.hideGroup();
            } else if (f instanceof ChecklistFragment) {
                actions.hideChecklist();
            } else if (f instanceof LocationFragment) {
                actions.hideLocation();
            } else if (f instanceof NoteFragment) {
                actions.hideNote();
            } else if (f instanceof PersonFragment) {
                actions.hidePerson();
            } else if (f instanceof ShipmentFragment) {
                actions.hideShipment();
            }
        Toast.makeText(this, "Visibility was changed", Toast.LENGTH_LONG).show();

        }
    @Override
    public void delete() {

    }
    @Override
    public boolean isFromProject() {
        return actions.isFromProject();
    }
    private void switchToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("logMeOut", true);
        startActivity(intent);
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>1;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        Fragment backFragment;
        getSupportFragmentManager().popBackStack();
        //Log.d("tag", getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 2).getName());
        return true;
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("s40", "onConnectionFailed:" + connectionResult);
    }

}
