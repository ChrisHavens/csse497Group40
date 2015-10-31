package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import com.google.android.gms.location.LocationResult;



import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataContract;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataDBHelper;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


public class MainActivity extends Activity implements TabSwitchListener,
        AddPersonDialogFragment.AddPersonListener, MainFragment.CRUDListener,
        AddProjectDialogFragment.AddProjectListener,
        AddGroupDialogFragment.AddGroupListener,
        AddChecklistDialogFragment.AddChecklistListener,
        //EditPersonDialogFragment.EditPersonListener,
        AddLocationDialogFragment.AddLocationListener,
        EditChecklistDialogFragment.EditChecklistListener

        //,
        //EditProjectDialogFragment.EditProjectListener,
        //EditGroupDialogFragment.AddGroupListener
        {
    public static String GoogleMapsAPIKey="AIzaSyCJLQb_7gSUe-Vg5S0jMvigSCJkbcJ_8aE";
    NonLocalDataService service=new NonLocalDataService();
            private Checklist checklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Startup Code Here
        //LocalDataDBHelper dbHelper = new LocalDataDBHelper(getBaseContext());
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onProjectsButtonClicked() {
        Fragment fragment = new MainFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment, fragment);
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
    public void makePhoneCall(String s){
        PackageManager pm=getPackageManager();
        Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+s.replaceAll("[^0-9|\\+]", "")));
        if (intent.resolveActivity(pm)!=null) {
            startActivity(intent);
        }else{
            Toast.makeText(this, "Not able to make phone calls!", Toast.LENGTH_LONG).show();
        }

    }
    public void makeText(String number){
        PackageManager pm=getPackageManager();
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+number));
        if (intent.resolveActivity(pm)!=null) {
            startActivity(intent);
        }else{
            Toast.makeText(this, "Not able to make text messages!", Toast.LENGTH_LONG).show();
        }
    }
    public void checkIn(){
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
@Override
    public void addPerson(final String name, String phone, String email, Roles.PersonRoles role) {
    MainFragment f=(MainFragment)getFragmentManager().findFragmentById(R.id.fragment);
    long projectID=f.getSelectedProject().getID();
    long groupID=-1;
    if (f.getSelectedGroup()!=null) {
        groupID = f.getSelectedGroup().getID();
    }
    Person p=new Person(name, phone, email);
    edu.rose_hulman.srproject.humanitarianapp.models.Person.PersonLocation location=new edu.rose_hulman.srproject.humanitarianapp.models.Person.PersonLocation();
    location.setName("Omega 4 Relay");
    location.setTime("2185-04-05 14:45");
    location.setLat(34.56f);
    location.setLng(-5.45f);
    //location.setID(10000);
    p.setLastCheckin(location);
    p.addProjectID(projectID);
    if (groupID!=-1) {
        p.addGroupID(groupID);
    }
    Callback<Response> responseCallback=new Callback<Response>() {
        @Override
        public void success(Response response, Response response2) {
            Toast.makeText(getApplicationContext(), "Successful adding of new person: "+name, Toast.LENGTH_LONG).show();
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitError", error.getMessage());
        }
    };
    //NonLocalDataService service=new NonLocalDataService();
    service.addNewPerson(p, responseCallback);
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
    public void addProject(final String name) {
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=100000;
        Project p= new Project(name, i);

        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful adding of new project: "+name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewProject(p, responseCallback);
    }
    @Override
    public void addGroup(final String name) {
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=200000;
        MainFragment f=(MainFragment)getFragmentManager().findFragmentById(R.id.fragment);
        Project project=f.getSelectedProject();
        Group g= new Group(i);
        g.setName(name);
        g.setProject(project);
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful adding of new group: "+name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewGroup(g, responseCallback);
        Callback<Response> responseCallback2=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful editing of project", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewProject(project, responseCallback);
//        sb.append("{\"doc\": {");
//        sb.append(project.getGroupString());
//        sb.append("}}");
        //service.updateProject(project.getID(), sb.toString(), responseCallback2);



    }
    @Override
    public void addLocation(final String name, String lat, String lng) {
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=400000;
        MainFragment f=(MainFragment)getFragmentManager().findFragmentById(R.id.fragment);
        Project project=f.getSelectedProject();
        Location l=new Location(name);
        l.setLat(Float.parseFloat(lat));
        l.setLng(Float.parseFloat(lng));
        l.getProjectIDs().add(project.getID());
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful adding of new location: "+name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewLocation(l, responseCallback);

    }

    @Override
    public void addProject() {

    }

    @Override
    public void addGroup(Project project) {
        DialogFragment newFragment = new AddGroupDialogFragment();
        newFragment.show(getFragmentManager(), "addGroup");
    }

    @Override
    public void addChecklist(Group g) {
        DialogFragment newFragment = new AddChecklistDialogFragment();
        Bundle b=new Bundle();
        b.putLong("parentID", g.getID());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "addChecklist");
    }

    @Override
    public void addLocation() {
        DialogFragment newFragment = new AddLocationDialogFragment();
        newFragment.show(getFragmentManager(), "addLocation");
    }

    @Override
    public void addNote() {

    }

    @Override
    public void addPerson() {
        DialogFragment newFragment = new AddProjectDialogFragment();
        newFragment.show(getFragmentManager(), "addProject");
    }

    @Override
    public void addShipment() {

    }

    @Override
    public void showEditProject(Project p) {
        Log.wtf("ProjectID", p.getID()+"");
        DialogFragment newFragment = new EditProjectDialogFragment();
        Bundle b=new Bundle();
        b.putLong("projectID", p.getID());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editProject");
    }

    @Override
    public void showEditGroup(Group g) {
        Log.wtf("GroupID", g.getID()+"");
        DialogFragment newFragment = new EditGroupDialogFragment();
        Bundle b=new Bundle();
        b.putLong("groupID", g.getID());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editGroup");
    }

    @Override
    public void showEditChecklist(Checklist c) {
        checklist=c;
        Log.wtf("ChecklistID", c.getID()+"");
        DialogFragment newFragment = new EditChecklistDialogFragment();
//        Bundle b=new Bundle();
//        b.putLong("checklistID", c.getID());
//        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editChecklist");
    }

    @Override
    public void showEditLocation(edu.rose_hulman.srproject.humanitarianapp.models.Location l) {

    }

    @Override
    public void showEditNote(Note n) {

    }

    @Override
    public void showEditPerson(Person p) {
        Log.wtf("PersonID", p.getID()+"");
        DialogFragment newFragment = new EditPersonDialogFragment();
        Bundle b=new Bundle();
        b.putLong("personID", p.getID());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editPerson");
    }

    @Override
    public void showEditShipment(Shipment s) {

    }
    public void editProject(Project p){

    }


            @Override
            public void addChecklist(final Checklist checklist) {
                Random rand=new Random();
                long i= rand.nextInt(90000)+10000;
                i+=700000;
                checklist.setID(i);
                checklist.setItemIDs();

                Callback<Response> responseCallback=new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(getApplicationContext(), "Successful adding of new checklist: "+checklist.getTitle(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("RetrofitError", error.getMessage());
                    }
                };

                service.addNewChecklist(checklist, responseCallback);
            }

            @Override
            public Checklist getChecklist() {
                return checklist;
            }
        }
