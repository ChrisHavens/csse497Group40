package edu.rose_hulman.srproject.humanitarianapp.localdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;

/**
 * Created by havenscs on 10/25/2015.
 */
public class ApplicationWideData {
    public static List<Checklist> knownChecklists;
    public static List<Group> knownGroups;
    public static List<Location> knownLocations;
    public static List<Person> knownPersons;
    public static List<Project> knownProjects;
    public static List<Shipment> knownShipments;

    public static int userID;
    public static int createdObjectCounter;

    public static void initilizeKnownVariables() {
        knownChecklists = new ArrayList();
        knownGroups = new ArrayList();
        knownLocations = new ArrayList();
        knownPersons = new ArrayList();
        knownProjects = new ArrayList();
        knownShipments = new ArrayList();

        //Pull these numbers from local storage
        Random rand = new Random();
        int userID = rand.nextInt();
        createdObjectCounter = 0;
    }

    //Later when adding tracking of new additions for manual sync, the add new versions will do the
    // bookkeeping for that there

    public static void addNewChecklist(Checklist checklist) {
        knownChecklists.add(checklist);
    }

    public static void addExistingChecklist(Checklist checklist) {
        knownChecklists.add(checklist);
    }

    public static Checklist getChecklistByID(long id) {
        for(Checklist checklist: knownChecklists) {
            if (checklist.getID() == id) {
                return checklist;
            }
        }
        return null;
    }

    public static List<Checklist> getAllChecklists() {
        return knownChecklists;

    }

    public static void addNewGroup(Group group) {
        knownGroups.add(group);
    }

    public static void addExistingGroup(Group group) {
        knownGroups.add(group);
    }

    public static Group getGroupByID(long id) {
        for(Group group: knownGroups) {
            if (group.getId() == id) {
                return group;
            }
        }
        return null;
    }

    public static List<Group> getAllGroups() {
        return knownGroups;

    }

    public static void addNewLocation(Location location) {
        knownLocations.add(location);
    }

    public static void addExistingLocation(Location location) {
        knownLocations.add(location);
    }

    public static Location getLocationByID(long id) {
        for(Location location: knownLocations) {
            if (location.getID() == id) {
                return location;
            }
        }
        return null;
    }

    public static List<Location> getAllLocations() {
        return knownLocations;

    }

    public static void addNewPerson(Person person) {
        knownPersons.add(person);
    }

    public static void addExistingPerson(Person person) {
        knownPersons.add(person);
    }

    public static Person getPersonByID(long id) {
        for(Person person: knownPersons) {
            if (person.getID() == id) {
                return person;
            }
        }
        return null;
    }

    public static List<Person> getAllPersons() {
        return knownPersons;

    }

    public static void addNewProject(Project project) {
        knownProjects.add(project);
    }

    public static void addExistingProject(Project project) {
        knownProjects.add(project);
    }

    public static Project getProjectByID(long id) {
        for(Project project: knownProjects) {
            if (project.getId() == id) {
                return project;
            }
        }
        return null;
    }

    public static List<Project> getAllProjects() {
        return knownProjects;

    }

    public static void addNewShipment(Shipment shipment) {
        knownShipments.add(shipment);
    }

    public static void addExistingShipment(Shipment shipment) {
        knownShipments.add(shipment);
    }

    public static Shipment getShipmentByID(long id) {
        for(Shipment shipment: knownShipments) {
            if (shipment.getID() == id) {
                return shipment;
            }
        }
        return null;
    }

    public static List<Shipment> getAllShipments() {
        return knownShipments;

    }

}
