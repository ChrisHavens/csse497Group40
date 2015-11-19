package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.List;
import java.util.Random;


import edu.rose_hulman.srproject.humanitarianapp.R;

import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddChecklistDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddGroupDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddLocationDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddNoteDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddPersonDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.add_dialog_fragments.AddProjectDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditChecklistDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditGroupDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditPersonDialogFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments.EditProjectDialogFragment;

import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataDBHelper;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataLoader;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataRetriver;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
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
        EditChecklistDialogFragment.EditChecklistListener,
        AddNoteDialogFragment.AddNoteListener

        //,
        //EditProjectDialogFragment.EditProjectListener,
        //EditGroupDialogFragment.AddGroupListener
{
    public static String GoogleMapsAPIKey = "AIzaSyCJLQb_7gSUe-Vg5S0jMvigSCJkbcJ_8aE";
    NonLocalDataService service = new NonLocalDataService();
    private Checklist checklist;
	private long parentID;
    private List<Project> storedProjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Startup Code Here
        LocalDataDBHelper dbHelper = new LocalDataDBHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ApplicationWideData.db = db;
        setContentView(R.layout.activity_main);
        ApplicationWideData.initilizeKnownVariables();
        storedProjects = LocalDataRetriver.getStoredProjects();
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
    @Override
    public void addNewPerson(final String name, String phone, String email, Roles.PersonRoles role) {
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
        
        //location.setId(10000);
        p.setLastCheckin(location);
        p.addProjectID(projectID);
        if (groupID != -1) {
            p.addGroupID(groupID);
        }
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful adding of new person: " + name, Toast.LENGTH_LONG).show();
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

    public void addNewProject(final String name) {
//        Random rand=new Random();
//        long i= rand.nextInt(90000)+10000;
//        i+=100000;
//        Project p= new Project(name, i);
        Project p = new Project(name);


        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful adding of new project: " + name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        boolean success = LocalDataSaver.addProject(p);
        if (success) {
            Toast.makeText(getApplicationContext(), "Successful adding of new project: " + name + " to local database", Toast.LENGTH_LONG).show();
        }
        service.addNewProject(p, responseCallback);
    }

    @Override
    public void addGroup(final String name) {
        Random rand = new Random();
        long i = rand.nextInt(90000) + 10000;
        i += 200000;
        MainFragment f = (MainFragment) getFragmentManager().findFragmentById(R.id.fragment);
        Project project = f.getSelectedProject();
        Group g = new Group(i);
        g.setName(name);
        g.setProject(project);
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful adding of new group: " + name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewGroup(g, responseCallback);
        Callback<Response> responseCallback2 = new Callback<Response>() {
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
        //service.updateProject(project.getId(), sb.toString(), responseCallback2);


    }

    @Override
    public void addLocation(final String name, String lat, String lng) {
        Random rand = new Random();
        long i = rand.nextInt(90000) + 10000;
        i += 400000;
        MainFragment f = (MainFragment) getFragmentManager().findFragmentById(R.id.fragment);
        Project project = f.getSelectedProject();
        Location l = new Location(name);
        l.setLat(Float.parseFloat(lat));
        l.setLng(Float.parseFloat(lng));
        l.getProjectIDs().add(project.getId());
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful adding of new location: " + name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewLocation(l, responseCallback);

    }

    @Override
    public void addNewChecklist(final Checklist checklist) {
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
    public void addNewNote(String name, String contents){
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=500000;
        final Note note=new Note(i);
        note.setParentID(parentID);
        note.setTitle(name);
        note.setBody(contents);
//        Date date=new Date();
//
//        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm");
//        String s=sdf.format(date);
//        //LocalDateTime time;
//        note.setLastModified(s);
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful adding of new note: "+note.getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewNote(note, responseCallback);
    }

    @Override
    public void addProject() {
        Toast.makeText(getApplicationContext(), "Number of projects in DB: " + this.storedProjects.size(), Toast.LENGTH_LONG).show();
        DialogFragment newFragment = new AddProjectDialogFragment();
        newFragment.show(getFragmentManager(), "addProject");
    }

    @Override
    public void addGroup(Project project) {
        DialogFragment newFragment = new AddGroupDialogFragment();
        newFragment.show(getFragmentManager(), "addGroup");
    }

    @Override
    public void addChecklist(Group g) {
        DialogFragment newFragment = new AddChecklistDialogFragment();
        Bundle b = new Bundle();
        b.putLong("parentID", g.getId());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "addChecklist");
    }

    @Override
    public void addLocation(Project p) {
        DialogFragment newFragment = new AddLocationDialogFragment();
        newFragment.show(getFragmentManager(), "addLocation");
    }

    @Override
    public void addNote(Group g) {
        DialogFragment newFragment = new AddNoteDialogFragment();
        parentID=g.getID();
//        Bundle b=new Bundle();
//        b.putLong("parentID", g.getID());
//        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "addNote");
    }

    @Override
    public void addPerson() {
        DialogFragment newFragment = new AddPersonDialogFragment();
        newFragment.show(getFragmentManager(), "addPerson");
    }

    @Override
    public void addShipment() {

    }

    @Override
    public void showEditProject(Project p) {
        Log.wtf("ProjectID", p.getId() + "");
        DialogFragment newFragment = new EditProjectDialogFragment();
        Bundle b = new Bundle();
        b.putLong("projectID", p.getId());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editProject");
    }

    @Override
    public void showEditGroup(Group g) {
        Log.wtf("GroupID", g.getId() + "");
        DialogFragment newFragment = new EditGroupDialogFragment();
        Bundle b = new Bundle();
        b.putLong("groupID", g.getId());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editGroup");
    }

    @Override
    public void showEditChecklist(Checklist c) {
        checklist = c;
        Log.wtf("ChecklistID", c.getID() + "");
        DialogFragment newFragment = new EditChecklistDialogFragment();
//        Bundle b=new Bundle();
//        b.putLong("checklistID", c.getId());
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
        Log.wtf("PersonID", p.getID() + "");
        DialogFragment newFragment = new EditPersonDialogFragment();
        Bundle b = new Bundle();
        b.putLong("personID", p.getID());
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "editPerson");
    }

    @Override
    public void showEditShipment(Shipment s) {

    }

    public void editProject(Project p) {

    }
    public void editChecklist(Checklist c){
        addNewChecklist(c);
    }



    @Override
    public void addChecklist(final Checklist checklist) {
        Random rand = new Random();
        long i = rand.nextInt(90000) + 10000;
        i += 700000;
        checklist.setID(i);
        checklist.setItemIDs();

        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(getApplicationContext(), "Successful adding of new checklist: " + checklist.getTitle(), Toast.LENGTH_LONG).show();
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
