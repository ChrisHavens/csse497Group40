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

import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.AbstractListFragment;
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
        manualSnyc = PreferencesManager.getSyncType();


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
//        Toast.makeText(activity.getApplicationContext(), "Loaded project count: " + knownProjects.size(), Toast.LENGTH_SHORT).show();
        List<Project> projects2 = LocalDataRetriver.getStoredProjectsSecond();
//        Toast.makeText(activity.getApplicationContext(), "Saved projects count: " + projects2.size(), Toast.LENGTH_SHORT).show();
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
            Project conflictingProj = null;
            for(Project existingProj: knownProjects){
                if(existingProj.getID() == l){
                    conflictingProj = existingProj;
                    break;
                }
            }
            if(conflictingProj != null){
                knownProjects.remove(conflictingProj);
            }
            knownProjects.add(projects.get(l));
        }
    }

    public static void addGroupHashMap(HashMap<Long, Group> groups){
        for(Long l: groups.keySet()){
            Group conflictingGroup = null;
            for(Group existingProj: knownGroups){
                if(existingProj.getID() == l){
                    conflictingGroup = existingProj;
                    break;
                }
            }
            if(conflictingGroup != null){
                knownGroups.remove(conflictingGroup);
            }
            knownGroups.add(groups.get(l));
        }
    }

    public static void addPersonHashMap(HashMap<Long, Person> people){
        for(Long l: people.keySet()){
            Person conflictingGroup = null;
            for(Person existingProj: knownPersons){
                if(existingProj.getID() == l){
                    conflictingGroup = existingProj;
                    break;
                }
            }
            if(conflictingGroup != null){
                knownPersons.remove(conflictingGroup);
            }
            knownPersons.add(people.get(l));
        }
    }

    public static void addLocationHashMap(HashMap<Long, Location> locations){
        for(Long l: locations.keySet()){
            Location conflictingGroup = null;
            for(Location existingProj: knownLocations){
                if(existingProj.getID() == l){
                    conflictingGroup = existingProj;
                    break;
                }
            }
            if(conflictingGroup != null){
                knownLocations.remove(conflictingGroup);
            }
            knownLocations.add(locations.get(l));
        }
    }

    public static void addShipmentHashMap(HashMap<Long, Shipment> shipments){
        for(Long l: shipments.keySet()){
            Shipment conflictingGroup = null;
        for(Shipment existingProj: knownShipments){
        if(existingProj.getID() == l){
        conflictingGroup = existingProj;
        break;
        }
        }
        if(conflictingGroup != null){
            knownShipments.remove(conflictingGroup);
        }
        knownShipments.add(shipments.get(l));
        }
    }

    public static void addNoteHashMap(HashMap<Long, Note> notes){
        for(Long l: notes.keySet()){
            Note conflictingGroup = null;
            for(Note existingProj: knownNotes){
                if(existingProj.getID() == l){
                    conflictingGroup = existingProj;
                    break;
                }
            }
            if(conflictingGroup != null){
                knownNotes.remove(conflictingGroup);
            }
            knownNotes.add(notes.get(l));
        }
    }

    public static void addMessageThreadHashMap(HashMap<Long, MessageThread> messages){
        for(Long l: messages.keySet()){
            MessageThread conflictingMessage = null;
            for(MessageThread existingProj: knownMessageThreads){
                if(existingProj.getID() == l){
                    conflictingMessage = existingProj;
                    break;
                }
            }
            if(conflictingMessage != null){
                knownGroups.remove(conflictingMessage);
            }
            knownMessageThreads.add(messages.get(l));
        }
    }

    public static void addMessageHashMap(HashMap<Long, MessageThread.Message> messages){
        for(Long l: messages.keySet()){
            MessageThread.Message conflictingMessage = null;
            for(MessageThread.Message existingProj: knownMessages){
                if(existingProj.getID() == l){
                    conflictingMessage = existingProj;
                    break;
                }
            }
            if(conflictingMessage != null){
                knownMessages.remove(conflictingMessage);
            }
            knownMessages.add(messages.get(l));
        }
    }

    public static void addChecklistHashMap(HashMap<Long, Checklist> checklists){
        for (Long l: checklists.keySet()){
            Checklist conflictingChecklist = null;
            for(Checklist existingProj: knownChecklists){
                if(existingProj.getID() == l){
                    conflictingChecklist = existingProj;
                    break;
                }
            }
            if(conflictingChecklist != null){
                knownChecklists.remove(conflictingChecklist);
            }
            knownChecklists.add(checklists.get(l));
        }
    }

    public static void initialProjects(List<Project> projects){
        knownProjects.clear();
        for(Project proj: projects){
            knownProjects.add(proj);
        }
    }

    public static void initialGroups(List<Group> groups){
        knownGroups.clear();
        knownGroups.addAll(groups);
    }

    public static void initialPeople(List<Person> people){
        knownPersons.clear();
        knownPersons.addAll(people);
    }

    public static void initialChecklists(List<Checklist> people){
        knownChecklists.clear();
        knownChecklists.addAll(people);
    }

    public static void initialLocations(List<Location> people){
        knownLocations.clear();
        knownLocations.addAll(people);
    }

    public static void initialMessageThreads(List<MessageThread> people){
        knownMessageThreads.clear();
        knownMessageThreads.addAll(people);
    }

    public static void initialMessages(List<MessageThread.Message> people){
        knownMessages.clear();
        knownMessages.addAll(people);
    }
    public static void initialNotes(List<Note> people){
        knownNotes.clear();
        knownNotes.addAll(people);
    }

    public static void initialShipments(List<Shipment> people){
        knownShipments.clear();
        knownShipments.addAll(people);
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
//        LocalDataSaver.addProject(project);
    }

    public static boolean deleteProjectByID(long id){
        for(Project existingProject: knownProjects){
            if (id == existingProject.getID()){
                boolean bool = knownProjects.remove(existingProject);
//                bool = bool && LocalDataSaver.deleteProject(id);
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
    public static MessageThread.Message getMessageByID(long id){
        for(MessageThread.Message message: knownMessages){
            if (message.getID() == id){
                return message;
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
//        Toast.makeText(activity, added.size()+"", Toast.LENGTH_LONG).show();
        addNewItems(added);
        LocalDataSaver.clearAddedSelectables();
        NonLocalDataService service = new NonLocalDataService();

        //HashMap<String, Selectable> updated=new HashMap<>();
        List<Selectable> selectables=LocalDataRetriver.getAllUpdated();
        doUpdates(selectables, activity);
//        Toast.makeText(activity, updated.keySet().toString(), Toast.LENGTH_LONG).show();
//
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
                final Project p=(Project)s;
                service.addNewProject(p, userID + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        LocalDataSaver.clearAddedSelectable(p);
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("s40 Adding New Projects", error.getMessage());
                    }
                });
            }
            else if (s instanceof Group){
                final Group p=(Group)s;
                service.addNewGroup(p, userID + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        LocalDataSaver.clearAddedSelectable(p);
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("s40 Adding New Groups", error.getMessage());
                    }
                });
            }
            else if (s instanceof Person){
                final Person p=(Person)s;
                service.addNewPerson(p, userID + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        LocalDataSaver.clearAddedSelectable(p);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("s40 Adding New Persons", error.getMessage());
                    }
                });
            }
            else if (s instanceof Checklist){
                final Checklist p=(Checklist)s;
                service.addNewChecklist(p, userID + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        LocalDataSaver.clearAddedSelectable(p);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("s40 Adding New Lists", error.getMessage());
                    }
                });
            }
            else if (s instanceof Location){
                final Location p=(Location)s;
                service.addNewLocation(p, userID + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        LocalDataSaver.clearAddedSelectable(p);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("s40 Adding New Locs", error.getMessage());
                    }
                });
            }
            else if (s instanceof MessageThread){
                final MessageThread p=(MessageThread)s;
                service.addNewThread(p, userID + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        LocalDataSaver.clearAddedSelectable(p);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("s40 Adding New Threads", error.getMessage());
                    }
                });
            }
            else if (s instanceof Note){
                final Note p=(Note)s;
                service.addNewNote(p, userID + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        LocalDataSaver.clearAddedSelectable(p);
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("s40 Adding New Notes", error.getMessage());
                    }
                });
            }
            else if (s instanceof Shipment){
                final Shipment p=(Shipment)s;
                service.addNewShipment(p, userID + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        LocalDataSaver.clearAddedSelectable(p);
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("s40 Adding New Shipment", error.getMessage());
                    }
                });
            }
            
            
        }
        if (LocalDataRetriver.getAllUpdated().size()==0){
            String time = getCurrentTime();
            PreferencesManager.setSyncDate(time);
        }
    }

    public static void doUpdates(List<Selectable> selectables, MainActivity activity){
        VeryNaughtyBoy Brian= new VeryNaughtyBoy(selectables, activity);
        Brian.doUpdates();
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
                    } else if (type.equals("Message")){

                    }
                    else
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


    public static String getCurrentTime(){
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.YEAR)+"-"+String.format("%02d", cal.get(Calendar.MONTH)+1)+"-"
                +String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))+
                " "+String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d", cal.get(Calendar.MINUTE));
    }

}
