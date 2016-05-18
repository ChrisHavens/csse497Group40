package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.rose_hulman.srproject.humanitarianapp.controllers.MainActivity;
import edu.rose_hulman.srproject.humanitarianapp.controllers.MainServiceActions;

import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ProjectsListFragment;

import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Conflict;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.TakeAHugeDump;
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
    public static List<Note> knownNotes;
    public static List<MessageThread> knownMessageThreads;
    public static List<MessageThread.Message> knownMessages;

    public static long userID = 0;
    public static int createdObjectCounter;
    public static boolean manualSnyc = false;
    public static SQLiteDatabase db = null;
    public static String previousSyncTime;

    public static void initilizeKnownVariables(MainActivity activity) {
        knownChecklists = new ArrayList();
        knownGroups = new ArrayList();
        knownLocations = new ArrayList();
        knownPersons = new ArrayList();
        knownProjects = new ArrayList();
        knownShipments = new ArrayList();
        knownNotes = new ArrayList();
        knownMessageThreads = new ArrayList();
        knownMessages = new ArrayList();


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

    public static void clearEverything(){
        LocalDataSaver.clearAll();
        knownChecklists = new ArrayList();
        knownGroups = new ArrayList();
        knownLocations = new ArrayList();
        knownPersons = new ArrayList();
        knownProjects = new ArrayList();
        knownShipments = new ArrayList();
        knownNotes = new ArrayList();
        knownMessageThreads = new ArrayList();
        knownMessages = new ArrayList();
    }

    public static void addProjectHashMap(HashMap<Long, Project> projects){
        for(Long l: projects.keySet()){
            knownProjects.add(projects.get(l));
        }
    }

    public static void addGroupHashMap(HashMap<Long, Group> groups){
        for(Long l: groups.keySet()){
            knownGroups.add(groups.get(l));
        }
    }

    public static void addPersonHashMap(HashMap<Long, Person> people){
        for(Long l: people.keySet()){
            knownPersons.add(people.get(l));
        }
    }

    public static void addLocationHashMap(HashMap<Long, Location> locations){
        for(Long l: locations.keySet()){
            knownLocations.add(locations.get(l));
        }
    }

    public static void addShipmentHashMap(HashMap<Long, Shipment> shipments){
        for(Long l: shipments.keySet()){
            knownShipments.add(shipments.get(l));
        }
    }

    public static void addNoteHashMap(HashMap<Long, Note> notes){
        for(Long l: notes.keySet()){
            knownNotes.add(notes.get(l));
        }
    }

    public static void addMessageThreadHashMap(HashMap<Long, MessageThread> messages){
        for(Long l: messages.keySet()){
            knownMessageThreads.add(messages.get(l));
        }
    }

    public static void addMessageHashMap(HashMap<Long, MessageThread.Message> messages){
        for(Long l: messages.keySet()){
            knownMessages.add(messages.get(l));
        }
    }

    public static void addChecklistMap(HashMap<Long, Checklist> checklists){

    }

    public static void initialProjects(List<Project> projects){
        knownProjects.clear();
        knownProjects.addAll(projects);
    }

    public static void initialGroups(List<Group> groups){
        knownGroups.clear();
        knownGroups.addAll(groups);
    }

    public static void initialPeople(List<Person> people){
        knownPersons.clear();
        knownPersons.addAll(people);
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

    public static List<Group> getGroupsByProjectID(long id) {
        List<Group> groups = new ArrayList<Group>();
        for(Group group: knownGroups) {
            if (group.getProjectId() == id) {
                groups.add(group);
            }
        }
        return groups;
    }

    public static Object getGroupOrProject(long id){
        for(Project proj: knownProjects){
            if(proj.getID() == id){
                return proj;
            }
        }
        for(Group group: knownGroups){
            if(group.getID() == id){
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

    public static boolean deleteProjectByID(long id){
        for(Project existingProject: knownProjects){
            if (id == existingProject.getID()){
                boolean bool = knownProjects.remove(existingProject);
                bool = bool && LocalDataSaver.deleteProject(id);
                return bool;
            }
        }
        return false;
    }

    public static void addExistingProject(Project project) {
        for(Project existingProject: knownProjects){
            if (project.getID() == existingProject.getID()){
                knownProjects.remove(existingProject);
                break;
            }
        }
        knownProjects.add(project);
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

    public static List<Note> getAllNotes(){
        return knownNotes;
    }

    public static Note getNoteByID(long id) {
        for(Note note: knownNotes){
            if (note.getID() == id){
                return note;
            }
        }
        return null;
    }

    public static List<MessageThread> getAllMessageThreads(){
        return knownMessageThreads;
    }

    public static MessageThread getMessageThreadByID(long id){
        for(MessageThread thread: knownMessageThreads){
            if (thread.getID() == id){
                return thread;
            }
        }
        return null;
    }

    public static void switchSyncMode(MainActivity activity) {
        manualSnyc = !manualSnyc;
        PreferencesManager.setSyncType(manualSnyc);
            sync(activity);
    }

    public static void forceSync(MainActivity activity) {
        //Save all of the projects

        sync(activity);

    }

    public static void sync(MainActivity activity) {
        //Save all of the projects

        //saveNewProjects(activity);
        List<Selectable> added= LocalDataRetriver.getAllAdded();
        Toast.makeText(activity, added.size()+"", Toast.LENGTH_LONG).show();
        addNewItems(added);
        LocalDataSaver.clearAddedSelectables();
        NonLocalDataService service = new NonLocalDataService();

        HashMap<String, Project> updated=new HashMap<>();
        List<Selectable> selectables=LocalDataRetriver.getAllUpdated();
        for (Selectable s: selectables){
            if (s instanceof Project){
                updated.put(s.getID()+"", (Project)s);
            }
        }
        Toast.makeText(activity, updated.keySet().toString(), Toast.LENGTH_LONG).show();
        Log.wtf(userID + "", "USER ID2");
        service.service.getProjectList(Long.toString(userID), false, new ProjectListCallback(updated, activity));
        String time = PreferencesManager.getSyncTime();
        getDeletedList(time);
        TakeAHugeDump dump = new TakeAHugeDump(userID);


    }

    public static boolean getManualSync(){
        return manualSnyc;
    }
    private static void addNewItems(List<Selectable> added){
        NonLocalDataService service=new NonLocalDataService();
        for (Selectable s: added){
            if (s instanceof Project){
                service.addNewProject((Project) s, userID + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.wtf("s40 Adding New Projects", error.getMessage());
                    }
                });
            }
        }
        if (LocalDataRetriver.getAllUpdated().size()==0){
            String time = getCurrentTime();
            PreferencesManager.setSyncDate(time);
        }
    }

    private static class ProjectListCallback implements Callback<Response>{
        private HashMap<String, Project> updated;
        private List<String> ids=new ArrayList<>();
        private final MainActivity activity;
        public ProjectListCallback(HashMap<String,Project> updated, MainActivity activity){
            this.updated=updated;
            this.activity=activity;
        }

        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    ids.add((String) map.get("_id"));
                    Log.w("Found a project", map.toString());
                    HashMap<String, Object> source = (HashMap) map.get("_source");
                    String idString = (String) map.get("_id");
                    if (updated.containsKey(idString)){
                        doUpdateProject(updated.get(idString),activity);
                    }
                    else {
                        long id = Long.parseLong(idString);
                        String name = (String) source.get("name");

                        Project p = new Project(id, name);
                        if (source.get("dateArchived") == null) {

                            p.setHidden(false);
                        }
                        else {
                            p.setHidden(true);
                        }
                        ApplicationWideData.addExistingProject(p);
                        LocalDataSaver.updateProject(p);
                    }
                }
                for (String entry: updated.keySet()){
                    if (!ids.contains(entry)){
                        Toast.makeText(activity, "Deleting: "+entry, Toast.LENGTH_SHORT).show();
                        ApplicationWideData.deleteProjectByID(Long.parseLong(entry));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {

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

    public static void getDeletedList(String time){

        NonLocalDataService service= new NonLocalDataService();
        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void success (Response response, Response response2){
                HashMap<String,String> map = LocalDataRetriver.getDeletedHashMap(response);
                if(map == null){
                    return;
                }
                Set<String> longs = map.keySet();
                for(String stringVal: longs){
                    long val = Long.parseLong(stringVal);
                    String type = map.get(stringVal);
                    if (type.equals("Project")){
                        deleteProjectByID(val);
                    } else if(type.equals("Person")){
                        //deletePersonByID(val);
                    } else if(type.equals("Group")){
                        //deleteGroupByID(val);
                    } else if(type.equals("Location")){
                        //deleteGroupByID(val);
                    } else if (type.equals("Shipment")){
                        //deleteGroupByID(val);
                    } else if(type.equals("Checklist")) {
                        //deleteGroupByID(val);
                    } else if(type.equals("Message Thread")) {
                        //deleteGroupByID(val);
                    } else if(type.equals("Note")) {
                        //deleteGroupByID(val);
                    } else
                    {
                        Log.wtf("Recived deletion of type: ", type);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("RetroFitError", "Do deletion" + error.getMessage());
            }
        };
        service.getDeleted(time, callback);
    }
    public static void doUpdateProject(final Project project, final MainActivity activity) {
        NonLocalDataService service= new NonLocalDataService();
        service.updateProject(project, "{\"doc\":" + project.toJSON() + "}", userID + "",
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        LocalDataSaver.deleteUpdatedProject(project);
                        if (LocalDataRetriver.getAllUpdated().size() == 0) {
                            String time = getCurrentTime();
                            PreferencesManager.setSyncDate(time);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse().getStatus() == 418) {
//                    Log.wtf("Conflict FOUND:", error.getResponse().getBody().toString());
                            HashMap<Selectable, List<Conflict>> conflicts = ApplicationWideData.getConflicts(project, error.getResponse());
                            Log.wtf("Conflicts: ", conflicts.toString());
                            activity.showConflictResolution(conflicts);
                        }
                        Log.wtf("RetroFitError", "Do Update Project" + error.getMessage());
                    }
                });
    }

    public static String getCurrentTime(){
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.YEAR)+"-"+String.format("%02d", cal.get(Calendar.MONTH)+1)+"-"
                +String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))+
                " "+String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d", cal.get(Calendar.MINUTE));
    }

}
