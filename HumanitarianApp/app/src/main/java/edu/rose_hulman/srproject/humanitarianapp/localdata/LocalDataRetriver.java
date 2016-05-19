package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.database.Cursor;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import retrofit.client.Response;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataRetriver {

    private static String allProjectsQuery = "Select * From [Project]";
    private static String mostDataQuery = "Select * From [AllData] Where Type =?";
    private static String allGroupsQuery = "Select * From [Group]";
    private static String allUpdatedQuery = "Select * From [UpdatedIDs]";
    private static String allAddedQuery = "Select * From [AddedIDs]";
    public static List<Project> getStoredProjectsSecond(){
        List<Project> projects = new ArrayList<Project>();
        String type = "Project";
        String[] params = {type};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();

        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(3);
            Project project = null;
            project = Project.fromJSON(id, text);
            if(project != null) {
                projects.add(project);
            }
                Log.wtf("Load project", text);
            cursor.moveToNext();

        }
        return projects;
    }
    public static List<Group> getStoredGroupsSecond(){
        List<Group> groups = new ArrayList<Group>();
        String[] params = {"Group"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(3);
            Group group = null;
            group = Group.fromJSON(id, text);

            if (group!=null) {
                groups.add(group);
            }
                Log.wtf("Load Group", text);

            cursor.moveToNext();
        }
        return groups;
    }
    public static List<Person> getStoredPeopleSecond(){
        List<Person> people = new ArrayList<Person>();
        String[] params = {"People"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(3);
            Person person = null;
            person = Person.fromJSON(id, text);
            if (person!=null) {
                people.add(person);
            }
                Log.wtf("Load Person", text);

            cursor.moveToNext();
        }
        return people;
    }

    public static List<Checklist> getStoredChecklistSecond(){
        List<Checklist> people = new ArrayList<Checklist>();
        String[] params = {"Checklist"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(3);
            Checklist person = null;
            person = Checklist.fromJSON(id, text);
            if (person!=null) {
                people.add(person);
            }
                Log.wtf("Load Checklist", text);

            cursor.moveToNext();
        }
        return people;
    }

    public static List<Location> getStoredLocationSecond(){
        List<Location> locations = new ArrayList<Location>();
        String[] params = {"Location"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(3);
            Location location = null;
            location = Location.fromJSON(id, text);
            if (location!=null) {
                locations.add(location);
            }
                Log.wtf("Load Location", text);

            cursor.moveToNext();
        }
        return locations;
    }

    public static List<MessageThread> getStoredMessageThreadSecond(){
        List<MessageThread> messageThreads = new ArrayList<MessageThread>();
        String[] params = {"MessageThread"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(3);
            MessageThread messageThread = null;
            messageThread = MessageThread.fromJSON(id, text);
            if (messageThread!=null) {
                messageThreads.add(messageThread);
            }
                Log.wtf("Load MessageThread", text);

            cursor.moveToNext();
        }
        return messageThreads;
    }

    public static List<MessageThread.Message> getStoredMessageSecond(){
        List<MessageThread.Message> people = new ArrayList<MessageThread.Message>();
        String[] params = {"Message"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(3);
            MessageThread.Message message = null;
            message = MessageThread.Message.fromJSON(id, text);
            if (message!=null) {
                people.add(message);
            }
                Log.wtf("Load Message", text);

            cursor.moveToNext();
        }
        return people;
    }

    public static List<Note> getStoredNoteSecond(){
        List<Note> notes = new ArrayList<Note>();
        String[] params = {"Note"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(3);
            Note note = null;
            note = Note.fromJSON(id, text);
            if (note!=null) {
                notes.add(note);
            }
                Log.wtf("Load Note", text);
            cursor.moveToNext();

        }
        return notes;
    }

    public static List<Shipment> getStoredShipmentSecond(){
        List<Shipment> shipments = new ArrayList<Shipment>();
        String[] params = {"Shipment"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(3);
            Shipment shipment = null;
            shipment = Shipment.fromJSON(id, text);
            if (shipment!=null) {
                shipments.add(shipment);
            }
                Log.wtf("Load shipment", text);
            cursor.moveToNext();

        }
        return shipments;
    }

    public static List<Selectable> getAllUpdated(){
        List<Selectable> items= new ArrayList<>();
        Cursor cursor= ApplicationWideData.db.rawQuery(allUpdatedQuery, null);
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++, cursor.moveToNext()){
            Long id= cursor.getLong(0);
            String type= cursor.getString(1);
            items.add(retrieveItem(id, type));
        }
        return items;
    }
    public static List<Selectable> getAllAdded(){
        List<Selectable> items= new ArrayList<>();
        Cursor cursor= ApplicationWideData.db.rawQuery(allAddedQuery, null);
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++, cursor.moveToNext()){
            Long id= cursor.getLong(0);
            String type= cursor.getString(1);
            items.add(retrieveItem(id, type));
        }
        return items;
    }
    public static HashMap<String, String> getDeletedHashMap(Response response){
        HashMap<String, String> results=new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference =
                new TypeReference<HashMap<String, Object>>() {

                };
        try {
            HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
            if (o.containsKey("deleted")){
                ArrayList<HashMap<String, Object>> list=(ArrayList)o.get("deleted");
                for (HashMap<String, Object> item: list){
                    if (item.containsKey("id") && item.containsKey("type")){
                        results.put((String)item.get("id"), (String)item.get("type"));
                    }
                }
            }
            return results;
        }catch(Exception e){
            Log.wtf("Error in get Deleted HashMap", e.getMessage());
        }
        return null;

    }

    public static Selectable retrieveItem(Long ID, String type){
        Selectable result = null;
        if (type.equals("Project")){
            result = ApplicationWideData.getProjectByID(ID);
        } else if(type.equals("Person")){
            result = ApplicationWideData.getPersonByID(ID);
        } else if(type.equals("Group")){
            result = ApplicationWideData.getGroupByID(ID);
        } else if(type.equals("Location")){
            result = ApplicationWideData.getLocationByID(ID);
        } else if (type.equals("Shipment")){
            result = ApplicationWideData.getShipmentByID(ID);
        } else if(type.equals("Checklist")) {
            result = ApplicationWideData.getChecklistByID(ID);
        } else if(type.equals("Message Thread")) {
            result = ApplicationWideData.getMessageThreadByID(ID);
        } else if(type.equals("Note")) {
            result = ApplicationWideData.getNoteByID(ID);
        } else if(type.equals("Message")){
            result = ApplicationWideData.getMessageByID(ID);
        }
        else
        {
            Log.wtf("Attempted to retrive item of type ", type);
        }
        return result;
    }
}
