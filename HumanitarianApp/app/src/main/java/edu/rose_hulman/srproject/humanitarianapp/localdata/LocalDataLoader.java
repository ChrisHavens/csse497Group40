package edu.rose_hulman.srproject.humanitarianapp.localdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataLoader {
    /*
    All of the loadInitialXXXXX load only the data stored in each table, none of the relations, this
    means everything except for the lists.
     */

    public static void loadChecklists() {
        loadInitialChecklists();

    }

    public static void loadGroups() {
        loadInitialGroups();

    }

    public static void loadLocations() {
        loadInitialLocations();

    }

    public static void loadNotes() {
        loadInitialNotes();

    }

    public static void loadPersons() {
        loadInitialPersons();

    }

    public static void loadProjects() {
        loadInitialProjects();
        loadProjectListFields();
    }

    private static void loadInitialProjects() {
        List<Project> projects = LocalDataRetriver.getStoredProjects();
        for (Project project : projects) {
            ApplicationWideData.addExistingProject(project);
        }
    }

    private static void loadProjectListFields() {
        List<Project> projects = ApplicationWideData.getAllProjects();
        List<Group> groups = ApplicationWideData.getAllGroups();
        Map<Long, Project> projectMap = new HashMap<>();
        for (Project project : projects) {
            projectMap.put(project.getID(), project);
        }
        for(Group group: groups) {
            long id = group.getID();
            long projectId = group.getProjectId();
            if (projectMap.containsKey(projectId)){
                Project project = projectMap.get(projectId);
                project.addGroupIDsMaintain(id);
            }
        }
    }

    public static void loadShipments() {
        loadInitialShipments();

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

    public static void loadInitialEverything() {
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
        List<Group> groups = LocalDataRetriver.getStoredGroups();
        for (Group group : groups) {
            ApplicationWideData.addExistingGroup(group);
        }
    }

    private static void loadInitialLocations() {

    }

    private static void loadInitialNotes() {

    }

    private static void loadInitialPersons() {

    }

    private static void loadInitialShipments() {

    }
}
