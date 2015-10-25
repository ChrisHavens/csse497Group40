package edu.rose_hulman.srproject.humanitarianapp.localdata;

import java.util.ArrayList;
import java.util.List;

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
    public static List<Shipment> knownShipemnts;
    public static List<Location> knownLocations;
    public static List<Person> knownPersons;
    public static List<Group> knownGroups;
    public static List<Project> knownProjects;
    public static int userID;
    public static int createdObjectCounter;

    public static void initilizeKnownVariables() {
        knownChecklists = new ArrayList();
        knownShipemnts = new ArrayList();
        knownGroups = new ArrayList();
        knownLocations = new ArrayList();
        knownPersons = new ArrayList();
        knownProjects = new ArrayList();
        createdObjectCounter = 0;
    }

    /*
    One of these for all of the lists in this class
    public void addNewXXXX(XXXX object);
    public void addExistingXXXX(XXXX object);
    public object getXXXXByID(long id);
    public List<Object> getAllXXXX();
     */

}
