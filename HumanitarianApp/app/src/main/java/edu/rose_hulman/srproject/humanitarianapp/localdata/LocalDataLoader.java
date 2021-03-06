package edu.rose_hulman.srproject.humanitarianapp.localdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;

/**
 * Created by Chris on 11/2/2015.
 */
public class LocalDataLoader {

    /*
    All of the loadInitialXXXXX load only the data stored in each table, none of the relations, this
    means everything except for the lists.
     */

    public static void loadChecklists() {
        //loadInitialChecklists();

    }


    public static void loadLocations() {
        //loadInitialLocations();

    }

    public static void loadNotes() {
        //loadInitialNotes();

    }

    public static void loadPersons() {
        //loadInitialPersons();

    }

    public static void loadShipments() {
        //loadInitialShipments();

    }


    public static void loadEverything() {
        List<Project> projects = LocalDataRetriver.getStoredProjectsSecond();
        List<Group> groups = LocalDataRetriver.getStoredGroupsSecond();
        List<Person> people = LocalDataRetriver.getStoredPeopleSecond();
        List<Checklist> checklists = LocalDataRetriver.getStoredChecklistSecond();
        List<Location> locations = LocalDataRetriver.getStoredLocationSecond();
        List<MessageThread.Message> messages = LocalDataRetriver.getStoredMessageSecond();
        List<MessageThread> messageThreads = LocalDataRetriver.getStoredMessageThreadSecond();
        List<Note> notes = LocalDataRetriver.getStoredNoteSecond();
        List<Shipment> shipments = LocalDataRetriver.getStoredShipmentSecond();
        ApplicationWideData.initialProjects(projects);
        ApplicationWideData.initialGroups(groups);
        ApplicationWideData.initialPeople(people);

        loadPersons();
        loadLocations();
        loadChecklists();
        loadShipments();
        loadNotes();
    }
}
