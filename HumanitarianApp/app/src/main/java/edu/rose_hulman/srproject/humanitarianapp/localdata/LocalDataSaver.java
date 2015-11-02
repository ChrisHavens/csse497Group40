package edu.rose_hulman.srproject.humanitarianapp.localdata;

import edu.rose_hulman.srproject.humanitarianapp.models.Project;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataSaver {

    public static void saveProject(Project project) {
        String name = project.getName();
        String description = project.getDescription();
        long id = project.getId();
        int dirtyBits = project.getDirtyBits();

        //Save the above to the project table



        //Save all of the lists to the relations table
    }
}
