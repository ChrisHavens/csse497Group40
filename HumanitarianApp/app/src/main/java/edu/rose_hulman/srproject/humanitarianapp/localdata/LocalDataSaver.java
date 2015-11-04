package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataSaver {

    public static void updateProject(Project project) {
        String name = project.getName();
        String description = project.getDescription();
        long id = project.getId();
        int dirtyBits = project.getDirtyBits();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Description", description);
        values.put("DirtyBits", dirtyBits);
        String tableName = "Project";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        ApplicationWideData.db.update(tableName, values, selection, selectionArgs);

        //Save all of the lists to the relations table
    }

    public static boolean addProject(Project project) {
        String name = project.getName();
        String description = project.getDescription();
        long id = project.getId();
        int dirtyBits = project.getDirtyBits();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Name", name);
        values.put("Description", description);
        values.put("DirtyBits", dirtyBits);
        String tableName = "Project";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return true;

        //Save all of the lists to the relations table
    }
    public static void updatePerson(Person person) {
        String name = person.getName();
        long id = person.getID();

        //Save the above to the person table

        ContentValues values = new ContentValues();
        values.put("Name", name);
        String tableName = "Person";
        String selection = "ID=?";
        String[] selectionArgs = {Long.toString(id)};
        ApplicationWideData.db.update(tableName, values, selection, selectionArgs);

        //Save all of the lists to the relations table
    }

    public static boolean addPerson(Person person) {
        String name = person.getName();
        long id = person.getID();

        //Save the above to the project table

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Name", name);
        String tableName = "Person";
        ApplicationWideData.db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return true;

        //Save all of the lists to the relations table
    }
}
