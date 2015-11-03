package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.models.Project;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataRetriver {

    private static String allProjectsQuery = "Select * From Project";

    public static List<Project> getStoredProjects() {
        List<Project> projects = new ArrayList<Project>();
        Cursor cursor =  ApplicationWideData.db.rawQuery(allProjectsQuery, null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++, cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            boolean[] isDirty = new boolean[9];
            Arrays.fill(isDirty, false);
            int dirtyBits = cursor.getInt(3);
            for (int j = 0; j < 9; j++){
                isDirty[j] = (dirtyBits & 1 << j) > 0;
            }
            boolean onServer = isDirty[0];
            Project project = new Project(id, name, description, isDirty, onServer);
            projects.add(project);
        }



        return projects;
    }
}
