package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;
import edu.rose_hulman.srproject.humanitarianapp.serialisation.SerilizationConstants;

/**
 * Created by Chris Havens on 10/4/2015.
 */
public class Group implements Serialisable, Selectable {
//    private long id;
//    private long projectID;
//    private String name;
//    private List<Long> workerIDs;
//    private Person leader;
//    private String description;
//    private List<Note> notes = new ArrayList<>();
//    private List<Checklist> checklists = new ArrayList<>();
//    private List<Shipment> shipments = new ArrayList<>();
//
//    //Maybe pull these off into a list of global values?
//    //Yes, established elsewhere, but not used yet.
//    private static List<Group> knownGroups = new ArrayList<Group>();
//    private static long newGroupCount = 1;
//    private static List<Group> localIDGroups = new ArrayList<Group>();


    // A flag for each field denoting if it needs to be updated on the server.
    // This will need to be initulized in the constructors.
    private boolean[] isDirty = new boolean[9];
    //A flag for if the object was synced from the server or created locally
    private boolean onServer = false;

    private long id;
    private long projectID;
    private String datetime;
    private int dirtyBits = 0;
    private Person leader;
    private String name;
    private String description;
    private List<Long> workerIDs;
    private boolean isHidden=false;
    private List<Note> notes = new ArrayList<>();
    private List<Checklist> checklists = new ArrayList<>();
    private List<Shipment> shipments = new ArrayList<>();


    private Group() {
    }

    public Group(long id) {
        this.id = id;
        ApplicationWideData.addExistingGroup(this);
    }

    public Group(String name) {
        this.name = name;
        this.setUpID();
    }

    public Group(String name, long id) {
        this.name = name;
        this.id = id;
        ApplicationWideData.addExistingGroup(this);
    }

    /**
     * If you are using this for anything besides deserilization or loading stored groups from
     * the local database, you do not want to use this.
     *
     * @param id
     * @param projectId
     * @param name
     * @param description
     * @param isDirty
     * @return
     */
    public static Group createFullGroup(long id, long projectId, String name,
                                        String description, boolean[] isDirty, boolean onServer) {
        if (name == null || name.length() == 0) {
            return null;
        }
        Group group = new Group();
        group.name = name;
        group.id = id;
        group.description = description;
        group.projectID = projectId;
        group.isDirty = isDirty;
        group.onServer = onServer;
        return group;
    }

    public Group(String name, Project project) {
        this.name = name;
        this.projectID = project.getID();
        this.setUpID();
    }

    public Group(String name, Project project, List<Long> workerIDs) {
        this.name = name;
        this.workerIDs = workerIDs;
        this.projectID = project.getID();
        this.setUpID();
    }

    private void setUpID() {
        this.id = SerilizationConstants.generateID(SerilizationConstants.GROUP_NUM);
        ApplicationWideData.addNewGroup(this);
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
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return this.projectID;
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
        long oldID = this.id;
        this.id = newID;
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

    /**
     * This should be replaced with a call to the ApplicationWideData class, but leaving for now.
     *
     * @param id
     * @return group with that id
     */

    public static Group getGroupByID(long id) {
        return ApplicationWideData.getGroupByID(id);
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
//        sb.append("\"projectIDs\": [");


        String formatted = "" + projectID;
        sb.append("\"projectID\": \"" + formatted + "\"");

        //sb.append("]");
        return sb.toString();
    }

    public static List<Group> getKnownGroups() {
        return ApplicationWideData.getAllGroups();
    }

    public static void addKnownGroup(Group group) {
        ApplicationWideData.addExistingGroup(group);
    }


    @Override
    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
    }

    public int getDirtyBits() {
        return dirtyBits;
    }

    public void setDirtyBits(int dirtyBits) {
        this.dirtyBits = dirtyBits;
    }
    @Override
    public String getDateTimeModified() {
        return datetime;
    }

    @Override
    public void setDateTimeModified(String dateTime) {
        this.datetime=dateTime;
    }
}
