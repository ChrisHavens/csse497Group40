package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;
import edu.rose_hulman.srproject.humanitarianapp.serialisation.SerilizationConstants;

/**
 * Created by Chris Havens on 10/4/2015.
 */
public class Group implements Serialisable {
    private long ID;
    private long projectID;
    private String name;
    private List<Long> workerIDs;
    private Person leader;
    private String description;
    private List<Note> notes = new ArrayList<>();
    private List<Checklist> checklists = new ArrayList<>();
    private List<Shipment> shipments = new ArrayList<>();

    private static List<Group> knownGroups = new ArrayList<Group>();
    private static long newGroupCount = 1;
    private static List<Group> localIDGroups = new ArrayList<Group>();

    /*
     * All of the variables post refactoring. Also, the order is important and based off type NOT
     * what logically belongs where.

     // A flag for each field denoting if it needs to be updated on the server.
     // This will need to be initulized in the constructors.
     private boolean[] isDirty = new boolean[9];
     //A flag for if the object was synced from the server or created locally
     private boolean[] onServer = false;

     private long ID;
     private long projectID;
     private String name;
     private Person leader;
     private String description;
     private List<Long> workerIDs;
     private List<Note> notes = new ArrayList<>();
     private List<Checklist> checklists = new ArrayList<>();
     private List<Shipment> shipments = new ArrayList<>();
     */


    public Group() {
        this.setUpID();
    }

    public Group(long ID) {
        this.ID = ID;
        knownGroups.add(this);
    }

    public Group(String name) {
        this.name = name;
        this.setUpID();
    }

    public Group(String name, long ID) {
        this.name = name;
        this.ID = ID;
        knownGroups.add(this);
    }

    public static Group createFullGroup(String name, String description, Project project) {
        if (name == null || name.length() == 0) {
            return null;
        }
        Group group = new Group();
        group.name = name;
        group.description = description;
        group.projectID = project.getID();
        return group;
    }

    public Group(String name, Project project) {
        this.name = name;
        this.projectID = project.getID();
        this.setUpID();
    }

    /*
    public Group(String name, Project project, List<Long> workerIDs) {
        this.name = name;
        this.workerIDs = workerIDs;
        this.projectID = project.getID();
        this.setUpID();
    }
    */
    private void setUpID() {
        Random rand = new Random();
        int localIDNum = rand.nextInt();
        //TODO: Add the user ID to the id as well
        this.ID = ((long) localIDNum) | SerilizationConstants.GROUP_NUM;
        knownGroups.add(this);
        localIDGroups.add(this);
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

    public void setID(long ID) {
        this.ID = ID;
    }

    public Project getProject() {
        return Project.getProjectByID(this.projectID);
    }

    public void setProject(Project project) {
        this.projectID = project.getID();
    }

    public void setProjectID(long ID) {
        this.projectID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Shipment> getShipments() {
        return this.shipments;
    }

    public void addShipment(Shipment shipment) {
        this.shipments.add(shipment);
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public List<Checklist> getChecklists() {
        return this.checklists;
    }

    public void addChecklist(Checklist checklist) {
        this.checklists.add(checklist);
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addNotes(Note note) {
        this.notes.add(note);
    }

    public List<Person> getWorkers() {
        List<Person> persons = new ArrayList<Person>();
        for (Long ID : this.workerIDs) {
            persons.add(Person.getWorkerByID(ID));
        }
        return persons;
    }

    public void addWorker(Person person) {
        this.workerIDs.add(person.getID());
    }

    public void addWorkerByID(long ID) {
        if (this.workerIDs == null) {
            this.workerIDs = new ArrayList<Long>();
        }
        this.workerIDs.add(ID);
    }

    public void setWorkers(List<Person> persons) {
        List<Long> newWorkerIDs = new ArrayList<Long>();
        for (Person person : persons) {
            newWorkerIDs.add(person.getID());
        }
        this.workerIDs = newWorkerIDs;
    }

    public Person getLeader() {
        return leader;
    }

    public void setLeader(Person leader) {
        this.leader = leader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateID(long newID) {
        long oldID = this.ID;
        this.ID = newID;
        localIDGroups.remove(this);
        for (Person person : Person.getKnownPersons()) {
            person.updateGroupIDs(oldID, newID);
        }
        for (Project project : Project.getKnownProjects()) {
            project.updateGroupIDs(oldID, newID);
        }
    }

    public void updateWorkerID(long oldID, long newID) {
        if (this.workerIDs.contains(oldID)) {
            this.workerIDs.remove(oldID);
            this.workerIDs.add(newID);
            return;
        }
    }

    public void updateProjectID(long oldID, long newID) {
        if (this.projectID == oldID) {
            this.projectID = newID;
        }
    }


    public static Group getGroupByID(long ID) {
        for (Group group : knownGroups) {
            if (group.ID == ID) {
                return group;
            }
        }
        return null;
    }

    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"name\": \"" + getName() + "\",");
        //sb.append("\"role\": \""+Roles.roles[role.ordinal()]+"\",");

        sb.append(getParentString());
        sb.append("}");
        return sb.toString();
    }

    public String getParentString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"projectIDs\": [");


        String formatted = String.format("prj%05d", projectID);
        sb.append("{\"projectID\": \"" + formatted + "\"}");

        sb.append("]");
        return sb.toString();
    }

    public static List<Group> getKnownGroups() {
        return knownGroups;
    }

    public static void addKnownGroup(Group group) {
        knownGroups.add(group);
    }
}
