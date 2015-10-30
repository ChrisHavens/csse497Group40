package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;
import edu.rose_hulman.srproject.humanitarianapp.serialisation.SerilizationConstants;

/**
 * Created by Chris Havens on 10/4/2015.
 */
public class Project implements Serialisable{
    private boolean[] isDirty = new boolean[8];

    private long ID;
    private long managerID;
    private String name;
    private String description;

    private List<Long> groupIDs =  new ArrayList<Long>();
    private List<Long> workerIDs =  new ArrayList<Long>();
    private List<Location> locations =  new ArrayList<Location>();
    private List<Checklist> checklists = new ArrayList<Checklist>();

    private static List<Project> knownProjects = new ArrayList<Project>();
    private static long newProjectCount = 1;
    private static List<Project> localIDProjects = new ArrayList<Project>();

    /*
     * All of the variables post refactoring. Also, the order is important and based off type NOT
     * what logically belongs where.

     // A flag for each field denoting if it needs to be updated on the server.
     // This will need to be initulized in the constructors.
     private boolean[] isDirty = new boolean[9];
     //A flag for if the object was synced from the server or created locally
     private boolean[] onServer = false;

     // Any project with the same ID is said to be the same project, across all instances
     private long ID;
     private String name;
     private String description;
     private List<Long> groupIDs =  new ArrayList<Long>();
     private List<Long> workerIDs =  new ArrayList<Long>();
     private List<Long> adminIDs =  new ArrayList<Long>();
     private List<Location> locations =  new ArrayList<Location>();
     private List<Checklist> checklists = new ArrayList<Checklist>();
     private List<Shipment> shipments = new ArrayList<Shipment>();

     */

    public Project() {
        this.setUpDefaultID();
    }

    public Project(long ID) {
        this.ID = ID;
        knownProjects.add(this);
    }

    public Project(String name) {
        this.name = name;
        this.setUpDefaultID();
    }

    public Project (String name, long ID) {
        this.name = name;
        this.ID = ID;
        knownProjects.add(this);

    }

    private void setUpDefaultID(){
        Random rand=new Random();
        int localIDNum = rand.nextInt();
        //TODO: Add the user ID to the id as well
        this.ID = ((long) localIDNum) | SerilizationConstants.PROJECT_NUM;
        localIDProjects.add(this);
        knownProjects.add(this);
    }

    public static Project createFullProject(String name, String description, long managerID) {
        Project project = new Project();
        if (name == null || name.length() == 0) {
            return null;
        }
        project.name = name;
        project.description = description;
        project.managerID = managerID;
        return project;
    }

    @Override
    public String serialise() {
        return null;
    }

    @Override
    public Serialisable deserialise(String s) {
        return null;
    }

    public long getID() {
        return ID;
    }

    public Person getManager() {
        return Person.getWorkerByID(this.managerID);
    }

    public void setManager(Person manager) {
        this.managerID = manager.getID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocations(Location location) {
        this.locations.add(location);
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Checklist> getChecklist() {
        return checklists;
    }

    public void addChecklist(Checklist checklist) {
        this.checklists.add(checklist);
    }

    public void setChecklist(List<Checklist> checklist) {
        this.checklists = checklist;
    }

    public List<Person> getWorkers() {
        List<Person> persons = new ArrayList<Person>();
        for (Long ID: this.workerIDs) {
            persons.add(Person.getWorkerByID(ID));
        }
        return persons;
    }

    public void setWorkers(List<Person> persons) {
        List<Long> newWorkerIDs = new ArrayList<Long>();
        for(Person person : persons) {
            newWorkerIDs.add(person.getID());
        }
        this.workerIDs = newWorkerIDs;
    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<Group>();
        for (Long ID: this.groupIDs) {
            groups.add(Group.getGroupByID(ID));
        }
        return groups;
    }

    public void updateWorkerID(long oldID, long newID) {
            if (this.workerIDs.contains(oldID)) {
                this.workerIDs.remove(oldID);
                this.workerIDs.add(newID);
            }
    }

    public void updateGroupIDs(long oldID, long newID){
            if (this.groupIDs.contains(oldID)) {
                this.groupIDs.remove(oldID);
                this.groupIDs.add(newID);
            }
    }

    public void addWorkerByID(long ID) {
        if (this.workerIDs == null) {
            this.workerIDs = new ArrayList<Long>();
        }
        this.workerIDs.add(ID);
    }

    public void addGroupByID(long ID) {
        if (this.groupIDs == null) {
            this.groupIDs = new ArrayList<Long>();
        }
        this.groupIDs.add(ID);
    }

    public void updateID(long newID){
        long oldID = this.ID;
        this.ID = newID;
        localIDProjects.remove(this);
        for (Person person : Person.getKnownPersons()){
            person.updateGroupIDs(oldID, newID);
        }
        for (Group group : Group.getKnownGroups()) {
            group.updateWorkerID(oldID, newID);
        }
    }
    /*
    {
  "name": "Stop the Reapers",
  "groupIDs": [
    {
      "groupID": "grp00000"
    },
    {
      "groupID": "grp01000"
    }
    ]

}
     */
    public String toJSON(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("\"name\": \"" + getName() + "\",");
        //sb.append("\"role\": \""+Roles.roles[role.ordinal()]+"\",");

        sb.append(getGroupString());
        sb.append("}");
        return sb.toString();
    }
    public String getGroupString(){
        StringBuilder sb=new StringBuilder();
        sb.append("\"groupIDs\": [");
        for (int i=0; i<groupIDs.size()-1; i++){
//            String formatted = String.format("grp%05d", );
            sb.append("{\"groupID\": \""+groupIDs.get(i)+"\"},");
        }
        if (groupIDs.size()>0){
//            String formatted = String.format("grp%05d",);
            sb.append("{\"groupID\": \""+groupIDs.get(groupIDs.size()-1)+"\"}");
        }
        sb.append("]");
        return sb.toString();
    }

    public static Project getProjectByID(long ID) {
        for (Project project: knownProjects){
            if (project.ID == ID){
                return project;
            }
        }
        return null;
    }

    public static List<Project> getKnownProjects() {
        return knownProjects;
    }
}
