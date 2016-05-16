package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import retrofit.client.Response;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataRetriver {

    private static String allProjectsQuery = "Select * From [Project]";
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

    public static List<Long> getGroupIDForProject(long projectId) {
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
            String[] args={Long.toString(ID)};
            Cursor cursor=ApplicationWideData.db.query("["+type+"]", null, "ID=?", args, null, null, "ID");
            cursor.moveToFirst();
            if (type.equals("Project")){
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
                return project;
            }
            else{
                return null;
            }
    }
}
