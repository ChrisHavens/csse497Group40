package edu.rose_hulman.srproject.humanitarianapp.localdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.models.Group;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataLoader {
    /*
    All of the loadInitialXXXXX load only the data stored in each table, none of the relations, this
    means everything except for the lists.
     */

    private static void loadChecklists() {

    }

    private static void loadGroups() {

    }

    private static void loadLocations() {

    }

    private static void loadNotes() {

    }

    private static void loadPersons() {

    }

    private static void loadProjects() {

    }

    private static void loadShipments() {

    }

    public static void loadEverything() {
        loadInitialEverything();
        loadProjects();
        loadGroups();
        loadPersons();
        loadLocations();
        loadChecklists();
        loadShipments();
        loadNotes();
    }

    private static void loadInitialEverything() {
        loadInitialProjects();
        loadInitialGroups();
        loadInitialPersons();
        loadInitialLocations();
        loadInitialChecklists();
        loadInitialShipments();
        loadInitialNotes();
    }

    private static void loadInitialChecklists() {
        //Going to be a nightmare, leave for later
    }

    private static void loadInitialGroups() {
        List<Group> groupTable = new ArrayList<Group>();
        //This will actually be looping over the lines from the SQL tabel
        for (Group groupLine: groupTable) {
            long newId = groupLine.getId();
            long newProjectId = groupLine.getProjectId();
            String newName = groupLine.getName();
            String newDescription = groupLine.getDescription();
            boolean[] dirtyBits = new boolean[9];
            //Actually pull out each boolean from the line and put in the right spot, but holding
            // off until using actual lines from the db
            Arrays.fill(dirtyBits, false);

            Group newGroup = Group.createFullGroup(newId, newProjectId, newName,
                    newDescription, dirtyBits);
            ApplicationWideData.addExistingGroup(newGroup);
        }
    }

    private static void loadInitialLocations() {

    }

    private static void loadInitialNotes() {

    }

    private static void loadInitialPersons() {

    }

    private static void loadInitialProjects() {

    }

    private static void loadInitialShipments() {

    }
}
