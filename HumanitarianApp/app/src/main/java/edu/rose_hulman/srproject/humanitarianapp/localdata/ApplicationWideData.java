package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.rose_hulman.srproject.humanitarianapp.controllers.MainActivity;
import edu.rose_hulman.srproject.humanitarianapp.controllers.MainServiceActions;

import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ProjectsListFragment;

import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Conflict;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * Created by havenscs on 10/25/2015.
 */
public class ApplicationWideData {
    public static List<Checklist> knownChecklists;
    public static List<Group> knownGroups;
    public static List<Location> knownLocations;
    public static List<Person> knownPersons;
    public static List<Project> knownProjects;
    public static List<Shipment> knownShipments;

    public static int userID = 0;
    public static int createdObjectCounter;
    public static final boolean manualSnyc = false;
    public static SQLiteDatabase db = null;

    public static void initilizeKnownVariables(MainActivity activity) {
        knownChecklists = new ArrayList();
        knownGroups = new ArrayList();
        knownLocations = new ArrayList();
        knownPersons = new ArrayList();
        knownProjects = new ArrayList();
        knownShipments = new ArrayList();

        //Pull these numbers from local storage only when a user is known
        if (userID == 0) {
            Random rand = new Random();
            userID = rand.nextInt();
            createdObjectCounter = rand.nextInt();
        } else {
            //Will eventually only check the counter, but using random for now to avoid collisions.
            Random rand = new Random();
            createdObjectCounter = rand.nextInt();
        }
        //manualSnyc = PreferencesManager.getSyncType();
        LocalDataLoader.loadEverything();
        if (!manualSnyc) {
            sync(activity);
        }
    }

    public static void setDB(SQLiteDatabase newdb){
            newdb = db;
    }

    //Later when adding tracking of new additions for manual sync, the add new versions will do the
    // bookkeeping for that there

    public static void addNewChecklist(Checklist checklist) {
        knownChecklists.add(checklist);
    }

    public static void addExistingChecklist(Checklist checklist) {
        knownChecklists.add(checklist);
    }

    public static Checklist getChecklistByID(long id) {
        for(Checklist checklist: knownChecklists) {
            if (checklist.getID() == id) {
                return checklist;
            }
        }
        return null;
    }

    public static List<Checklist> getAllChecklists() {
        return knownChecklists;

    }

    public static void addNewGroup(Group group) {
        knownGroups.add(group);
    }

    public static void addExistingGroup(Group group) {
        knownGroups.add(group);
    }

    public static Group getGroupByID(long id) {
        for(Group group: knownGroups) {
            if (group.getID() == id) {
                return group;
            }
        }
        return null;
    }

    public static List<Group> getAllGroups() {
        return knownGroups;

    }

    public static void addNewLocation(Location location) {
        knownLocations.add(location);
    }

    public static void addExistingLocation(Location location) {
        knownLocations.add(location);
    }

    public static Location getLocationByID(long id) {
        for(Location location: knownLocations) {
            if (location.getID() == id) {
                return location;
            }
        }
        return null;
    }

    public static List<Location> getAllLocations() {
        return knownLocations;

    }

    public static void addNewPerson(Person person) {
        knownPersons.add(person);
    }

    public static void addExistingPerson(Person person) {
        knownPersons.add(person);
    }

    public static Person getPersonByID(long id) {
        for(Person person: knownPersons) {
            if (person.getID() == id) {
                return person;
            }
        }
        return null;
    }

    public static List<Person> getAllPersons() {
        return knownPersons;

    }

    public static void addNewProject(Project project) {
        for(Project existingProject: knownProjects){
            if (project.getID() == existingProject.getID()){
                return;
            }
        }
        knownProjects.add(project);
        LocalDataSaver.addProject(project);
    }

    public static void addExistingProject(Project project) {
        for(Project existingProject: knownProjects){
            if (project.getID() == existingProject.getID()){
                knownProjects.remove(existingProject);
                break;
            }
        }
        knownProjects.add(project);
        LocalDataSaver.updateProject(project);
    }

    public static Project getProjectByID(long id) {
        for(Project project: knownProjects) {
            if (project.getID() == id) {
                return project;
            }
        }
        return null;
    }

    public static List<Project> getAllProjects() {
        return knownProjects;

    }

    public static void addNewShipment(Shipment shipment) {
        knownShipments.add(shipment);
    }

    public static void addExistingShipment(Shipment shipment) {
        knownShipments.add(shipment);
    }

    public static Shipment getShipmentByID(long id) {
        for(Shipment shipment: knownShipments) {
            if (shipment.getID() == id) {
                return shipment;
            }
        }
        return null;
    }

    public static List<Shipment> getAllShipments() {
        return knownShipments;

    }

    public static void switchSyncMode(MainActivity activity) {
        //manualSnyc = !manualSnyc;
        PreferencesManager.setSyncType(manualSnyc);
        if(!manualSnyc) {
            sync(activity);
        }
    }

    public static void forceSync(MainActivity activity) {
        //Save all of the projects

        sync(activity);

    }

    public static void sync(MainActivity activity) {
        //Save all of the projects

        saveNewProjects(activity);
        String time = MessageThread.getCurrTime();
        PreferencesManager.setSyncDate(time);
        NonLocalDataService service = new NonLocalDataService();
        service.service.getProjectList(Integer.toString(userID), false, new ProjectListCallback());


    }

    public static boolean getManualSync(){
        return manualSnyc;
    }

    private static void saveNewProjects(final MainActivity activity) {
        NonLocalDataService service = new NonLocalDataService();
        for(final Project project : knownProjects){
            if (project.getIsDirty()[0]) {
                Callback<Response> responseCallback = new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse().getStatus()==418){
                            HashMap<Selectable, List<Conflict>> conflicts=ApplicationWideData.getConflicts(project, error.getResponse());
                            activity.showConflictResolution(conflicts);
                        }
                        Log.e("RetrofitError", error.getMessage());
                    }
                };
                StringBuilder sb=new StringBuilder();
                sb.append("{\"doc\": ");
                sb.append(project.toJSON());
                sb.append("}");
                service.updateProject(project.getID(), sb.toString(), userID+"", responseCallback);
                project.fullClean();
                LocalDataSaver.addProject(project);
            }
        }
    }
    public static HashMap<Selectable, List<Conflict>> getConflicts(Project project, Response response){
        HashMap<Selectable, List<Conflict>> map=new HashMap<Selectable, List<Conflict>>();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference =
                new TypeReference<HashMap<String, Object>>() {
                };
        List<Conflict> conflicts=new ArrayList<>();
        try {
            HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
            for (String key: o.keySet()){
                HashMap<String, Object> keyMap=(HashMap)o.get(key);
                Conflict c = new Conflict(key, (String)keyMap.get("server"), (String)keyMap.get("local"));
                conflicts.add(c);
            }
        }catch(Exception e){

        }
        map.put(project, conflicts);
        return map;
    }

    private static void emptyProjectTable(){
        knownProjects = new ArrayList<>();
        db.delete("[Project]", null, null);
    }

    public static class ProjectListCallback implements Callback<Response> {

        @Override
        public void success(Response response, Response response2) {
            Log.wtf("URL", response.getUrl());
            Log.wtf("SUCCESS", "PRJListCallbacks");
            ObjectMapper mapper = new ObjectMapper();
            List<Project> projectList;
            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                //emptyProjectTable();
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    Log.w("Found a project", map.toString());
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Project p = new Project(Long.parseLong(((String) map.get("_id"))));
                    p.setName((String) source.get("name"));
                    if(source.get("dateArchived") == null)
                        p.setHidden(false);
                    else
                        p.setHidden(true);
                    ApplicationWideData.addExistingProject(p);
                    //LocalDataSaver.addProject(p);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitErrorPLF", error.getMessage());
        }
    }

}
