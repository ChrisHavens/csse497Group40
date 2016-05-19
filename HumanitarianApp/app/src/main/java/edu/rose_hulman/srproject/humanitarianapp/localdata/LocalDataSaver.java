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
        String type = "Checklist";
        String body = checklist.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", type);
        values.put("DateModified", checklist.getDateTimeModified());
        values.put("Body", body);
        String tableName = "[AllData]";
        String[] whereArgs = {Long.toString(id), type};
        ApplicationWideData.db.delete(tableName, "ID = ? and Type = ? ", whereArgs);
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }
    public static void saveNote(Note note){
        long id = note.getID();
        String type = "Note";
        String body = note.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", type);
        values.put("DateModified", note.getDateTimeModified());
        values.put("Body", body);
        String tableName = "[AllData]";
        String[] whereArgs = {Long.toString(id), type};
        ApplicationWideData.db.delete(tableName, "ID = ? and Type = ? ", whereArgs);
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveProject(Project project){
        long id = project.getID();
        String type = "Project";
        String body = project.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", type);
        values.put("DateModified", project.getDateTimeModified());
        values.put("Body", body);
        String tableName = "[AllData]";
        String[] whereArgs = {Long.toString(id), type};
        ApplicationWideData.db.delete(tableName, "ID = ? and Type = ? ", whereArgs);
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveGroup(Group group){
        long id = group.getID();
        String type = "Group";
        String body = group.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", type);
        values.put("DateModified", group.getDateTimeModified());
        values.put("Body", body);
        String tableName = "[AllData]";
        String[] whereArgs = {Long.toString(id), type};
        ApplicationWideData.db.delete(tableName, "ID = ? and Type = ? ", whereArgs);
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void savePerson(Person person){
        long id = person.getID();
        String type = "People";
        String body = person.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", type);
        values.put("Body", body);
        values.put("DateModified", person.getDateTimeModified());
        String tableName = "[AllData]";
        String[] whereArgs = {Long.toString(id), type};
        ApplicationWideData.db.delete(tableName, "ID = ? and Type = ? ", whereArgs);
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveLocation(Location location){
        long id = location.getID();
        String type = "Location";
        String body = location.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", type);
        values.put("Body", body);
        values.put("DateModified", location.getDateTimeModified());
        String tableName = "[AllData]";
        String[] whereArgs = {Long.toString(id), type};
        ApplicationWideData.db.delete(tableName, "ID = ? and Type = ? ", whereArgs);
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveShipment(Shipment shipment){
        long id = shipment.getID();
        String type = "Shipment";
        String body = shipment.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", type);
        values.put("DateModified", shipment.getDateTimeModified());
        values.put("Body", body);
        String tableName = "[AllData]";
        String[] whereArgs = {Long.toString(id), type};
        ApplicationWideData.db.delete(tableName, "ID = ? and Type = ? ", whereArgs);
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveMessage(MessageThread.Message message){
        long id = message.getID();
        String type = "Message";
        String body = message.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type", type);
        values.put("Body", body);
        values.put("DateModified", message.getDateTimeModified());
        String tableName = "[AllData]";
        String[] whereArgs = {Long.toString(id), type};
        ApplicationWideData.db.delete(tableName, "ID = ? and Type = ? ", whereArgs);
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void saveMessageThread(MessageThread messageThread){
        long id = messageThread.getID();
        String type = "MessageThread";
        String body = messageThread.toJSON();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Type",type );
        values.put("DateModified", messageThread.getDateTimeModified());
        values.put("Body", body);
        String tableName = "[AllData]";
        String[] whereArgs = {Long.toString(id), type};
        ApplicationWideData.db.delete(tableName, "ID = ? and Type = ? ", whereArgs);
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
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

}
