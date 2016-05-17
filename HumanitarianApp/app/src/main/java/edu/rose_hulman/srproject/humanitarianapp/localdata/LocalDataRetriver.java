package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.database.Cursor;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import retrofit.client.Response;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataRetriver {

    private static String allProjectsQuery = "Select * From [Project]";
    private static String mostDataQuery = "Select * From [AllData] Where Type = ?";
    private static String allGroupsQuery = "Select * From [Group]";
    private static String allUpdatedQuery = "Select * From [UpdatedIDs]";
    private static String allAddedQuery = "Select * From [AddedIDs]";

    public static List<Project> getStoredProjects() {
        List<Project> projects = new ArrayList<Project>();
        Cursor cursor = ApplicationWideData.db.rawQuery(allProjectsQuery, null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++, cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            boolean[] isDirty = new boolean[9];
            Arrays.fill(isDirty, false);
            int dirtyBits = cursor.getInt(3);
            for (int j = 0; j < 9; j++){
                isDirty[j] = (dirtyBits & (1 << j)) > 0;
            }
            boolean onServer = isDirty[0];
            Project project = new Project(id, name, description, isDirty, onServer);
            projects.add(project);
        }
        return projects;
    }

    public static List<Project> getStoredProjectsSecond(){
        List<Project> projects = new ArrayList<Project>();
        String[] params = {"Project"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(2);
            Project project = null;
            //project = Project.fromJSON(id, text);
            projects.add(project);
        }
        return projects;
    }
    public static List<Group> getStoredGroupsSecond(){
        List<Group> groups = new ArrayList<Group>();
        String[] params = {"Group"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(2);
            Group group = null;
            //group = Group.fromJSON(id, text);
            groups.add(group);
        }
        return groups;
    }
    public static List<Person> getStoredPeopleSecond(){
        List<Person> people = new ArrayList<Person>();
        String[] params = {"People"};
        Cursor cursor = ApplicationWideData.db.rawQuery(mostDataQuery, params);
        int length = cursor.getCount();
        for(int i = 0; i < length; i++){
            Long id = cursor.getLong(0);
            String text = cursor.getString(2);
            Person person = null;
            //person = Person.fromJSON(id, text);
            people.add(person);
        }
        return people;
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


    public static List<Group> getStoredGroups() {
        List<Group> groups = new ArrayList<Group>();
        Cursor cursor = ApplicationWideData.db.rawQuery(allGroupsQuery, null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++, cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            Long projectID = cursor.getLong(1);
            String name = cursor.getString(2);
            String description = cursor.getString(3);
            boolean[] isDirty = new boolean[9];
            Arrays.fill(isDirty, false);
            int dirtyBits = cursor.getInt(3);
            for (int j = 0; j < 9; j++){
                isDirty[j] = (dirtyBits & (1 << j)) > 0;
            }
            boolean onServer = isDirty[0];
            Group group = Group.createFullGroup(id, projectID, name, description, isDirty, onServer);
            groups.add(group);
        }



        return groups;
    }

    public static List<Long> getGroupIDsForProject(long projectId) {
        List<Long> groupIds = new ArrayList<>();
        String tableName = "[Group]";
        String selection = "SuperID=?";
        String[] selectionArgs = {Long.toString(projectId)};
        String[] columns = {"ID", "SuperID"};
        Cursor cursor = ApplicationWideData.db.query(tableName, columns, selection, selectionArgs, null, null, "ID");
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++, cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            groupIds.add(id);
        }

        return groupIds;
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
        } else
        {
            Log.wtf("Attempted to retrive item of type ", type);
        }
        return result;
    }
}
