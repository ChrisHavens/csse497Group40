package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataSaver {

    public static void saveChecklist(Checklist checklist){
        long id = checklist.getID();
        String body = checklist.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", "Checklist");
        values.put("Body", body);
        String tableName = "[AllData]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveProject(Project project){
        long id = project.getID();
        String body = project.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", "Project");
        values.put("Body", body);
        String tableName = "[AllData]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveGroup(Group group){
        long id = group.getID();
        String body = group.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", "Group");
        values.put("Body", body);
        String tableName = "[AllData]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void savePerson(Person person){
        long id = person.getID();
        String body = person.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", "Person");
        values.put("Body", body);
        String tableName = "[AllData]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveLocation(Location location){
        long id = location.getID();
        String body = location.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", "Location");
        values.put("Body", body);
        String tableName = "[AllData]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveShipment(Shipment shipment){
        long id = shipment.getID();
        String body = shipment.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", "Shipment");
        values.put("Body", body);
        String tableName = "[AllData]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveMessage(MessageThread.Message message){
        long id = message.getItemID();
        String body = message.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", "Message");
        values.put("Body", body);
        String tableName = "[AllData]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveMessageThread(MessageThread messageThread){
        long id = messageThread.getID();
        String body = messageThread.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", "MessageThread");
        values.put("Body", body);
        String tableName = "[AllData]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void updateProject(Project project) {
        String name = project.getName();
        String description = project.getDescription();
        long id = project.getID();
        int dirtyBits = project.getDirtyBits();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Description", description);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Project]";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        ApplicationWideData.db.update(tableName, values, selection, selectionArgs);


        //Save all of the lists to the relations table
    }

    public static boolean deleteProject(long id){

        String tableName = "[Project]";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        int ret = ApplicationWideData.db.delete(tableName, selection, selectionArgs);
        return ret != 0;
    }

    public static boolean addProject(Project project) {
        String name = project.getName();
        String description = project.getDescription();
        long id = project.getID();
        int dirtyBits = project.getDirtyBits();
        String dateModified = project.getDateTimeModified();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Name", name);
        values.put("Description", description);
        values.put("DateModified", dateModified);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Project]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);


        return true;

        //Save all of the lists to the relations table
    }
    public static boolean addNewSelectable(Selectable selectable, String type){
        ContentValues values=new ContentValues();
        values.put("Type", type);
        values.put("ID",selectable.getID()+"");
        ApplicationWideData.db.insertWithOnConflict("[AddedIDs]", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return true;
    }
    public static boolean addUpdatedSelectable(Selectable selectable, String type){
        ContentValues values=new ContentValues();
        values.put("Type", type);
        values.put("ID",selectable.getID()+"");
        ApplicationWideData.db.insertWithOnConflict("[UpdatedIDs]", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return true;
    }
    public static void updatePerson(Person person) {
        String name = person.getName();
        long id = person.getID();

        //Save the above to the person table

        ContentValues values = new ContentValues();
        values.put("Name", name);
        String tableName = "[People]";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        ApplicationWideData.db.update(tableName, values, selection, selectionArgs);


        //Save all of the lists to the relations table
    }
    public static boolean deleteUpdatedProject(Project project){
        ContentValues values = new ContentValues();
        return ApplicationWideData.db.delete("[Project]", "ID" + "=" + project.getID()+"", null) > 0;

    }

    public static boolean addPerson(Person person) {
        String name = person.getName();
        long id = person.getID();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Name", name);
        String tableName = "[People]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return true;

        //Save all of the lists to the relations table
    }
    public static boolean clearUpdatedSelectables(){
        ApplicationWideData.db.delete("[UpdatedIDs]", null, null);
        return true;
    }
    public static boolean clearAddedSelectables(){
        ApplicationWideData.db.delete("[AddedIDs]", null, null);
        return true;
    }
    public static boolean clearAll(){
        ApplicationWideData.db.delete("[AllData]", null, null);
        return true;
    }

    public static void updateGroup(Group group) {
        String name = group.getName();
        long id = group.getID();
        long projectId = group.getProjectId();
        int dirtyBits = group.getDirtyBits();

        //Save the above to the group table

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("SuperID", projectId);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Group]";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        ApplicationWideData.db.update(tableName, values, selection, selectionArgs);

        //Save all of the lists to the relations table
    }

    public static boolean addGroup(Group group) {
        String name = group.getName();
        long id = group.getID();
        long projectId = group.getProjectId();
        int dirtyBits = group.getDirtyBits();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Name", name);
        values.put("SuperID", projectId);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Group]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return true;

        //Save all of the lists to the relations table
    }

    public static void updateLocation(Location location) {
        String name = location.getName();
        long id = location.getID();
        float lat = location.getLat();
        float lon = location.getLng();
        int dirtyBits = location.getDirtyBits();

        //Save the above to the location table

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("DirtyBits", dirtyBits);
        values.put("Lat", lat);
        values.put("Lon", lon);
        String tableName = "[Location]";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        ApplicationWideData.db.update(tableName, values, selection, selectionArgs);

        //Save all of the lists to the relations table
    }

    public static boolean addLocation(Location location) {
        String name = location.getName();
        long id = location.getID();
        float lat = location.getLat();
        float lon = location.getLng();
        int dirtyBits = location.getDirtyBits();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Name", name);
        values.put("Lat", lat);
        values.put("Lon", lon);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Location]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return true;

        //Save all of the lists to the relations table
    }

    public static void updateChecklist(Checklist checklist) {
        String name = checklist.getTitle();
        long id = checklist.getID();
        long parentId = checklist.getParentID();
        int dirtyBits = checklist.getDirtyBits();

        //Save the above to the checklist table

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("DirtyBits", dirtyBits);
        values.put("SuperID", parentId);
        String tableName = "[Checklist]";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        ApplicationWideData.db.update(tableName, values, selection, selectionArgs);

        //Save all of the subchecklists, going to take lots of time
    }

    public static boolean addChecklist(Checklist checklist) {
        String name = checklist.getTitle();
        long id = checklist.getID();
        long parentId = checklist.getParentID();
        int dirtyBits = checklist.getDirtyBits();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Name", name);
        values.put("SuperID", parentId);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Checklist]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return true;
        //Save all of the subchecklists, going to take lots of time
    }

    public static void updateShipment(Shipment shipment) {
        //TODO: Include more data once shipment table is updated.
        String name = shipment.getName();
        long id = shipment.getID();
        String contents = shipment.getContents();
        String status = shipment.getStatus();
        int dirtyBits = shipment.getDirtyBits();

        //Save the above to the shipment table

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Contents", contents);
        values.put("Status", status);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Shipment]";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        ApplicationWideData.db.update(tableName, values, selection, selectionArgs);
    }

    public static boolean addShipment(Shipment shipment) {
        String name = shipment.getName();
        long id = shipment.getID();
        String contents = shipment.getContents();
        String status = shipment.getStatus();
        int dirtyBits = shipment.getDirtyBits();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Name", name);
        values.put("Contents", contents);
        values.put("Status", status);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Shipment]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return true;
    }

    public static void updateNote(Note note) {
        String name = note.getTitle();
        long id = note.getID();
        String body = note.getBody();
        long parentId = note.getParentID();
        int dirtyBits = note.getDirtyBits();
        //Save the above to the note table

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Contents", body);
        values.put("OwnerID", parentId);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Note]";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        ApplicationWideData.db.update(tableName, values, selection, selectionArgs);
    }

    public static boolean addNote(Note note) {
        String name = note.getTitle();
        long id = note.getID();
        String body = note.getBody();
        long parentId = note.getParentID();
        int dirtyBits = note.getDirtyBits();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Contents", body);
        values.put("OwnerID", parentId);
        values.put("Name", name);
        values.put("DirtyBits", dirtyBits);
        String tableName = "[Note]";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return true;
    }
}
