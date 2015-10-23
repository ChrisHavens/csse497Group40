package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;

/**
 * Created by Chris Havens on 10/4/2015.
 */
public class Project implements Serialisable{
    private double ID;
    private double managerID;
    private String name;
    private String description;

    private List<Double> groupIDs =  new ArrayList<Double>();
    private List<Double> workerIDs =  new ArrayList<Double>();
    private List<Location> locations =  new ArrayList<Location>();
    private List<Checklist> checklists = new ArrayList<Checklist>();

    private static List<Project> knownProjects = new ArrayList<Project>();
    private static double newProjectCount = 1;
    private static List<Project> localIDProjects = new ArrayList<Project>();

    public Project() {
        this.setUpDefaultID();
    }

    public Project(double ID) {
        this.ID = ID;
        knownProjects.add(this);
    }

    public Project(String name) {
        this.name = name;
        this.setUpDefaultID();
    }

    public Project (String name, double ID) {
        this.name = name;
        this.ID = ID;
        knownProjects.add(this);

    }

    private void setUpDefaultID(){
        this.ID = newProjectCount;
        newProjectCount ++;
        localIDProjects.add(this);
        knownProjects.add(this);
    }

    public static Project createFullProject(String name, String description, double managerID) {
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

    public double getID() {
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
        for (Double ID: this.workerIDs) {
            persons.add(Person.getWorkerByID(ID));
        }
        return persons;
    }

    public void setWorkers(List<Person> persons) {
        List<Double> newWorkerIDs = new ArrayList<Double>();
        for(Person person : persons) {
            newWorkerIDs.add(person.getID());
        }
        this.workerIDs = newWorkerIDs;
    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<Group>();
        for (Double ID: this.groupIDs) {
            groups.add(Group.getGroupByID(ID));
        }
        return groups;
    }

    public void updateWorkerID(double oldID, double newID) {
            if (this.workerIDs.contains(oldID)) {
                this.workerIDs.remove(oldID);
                this.workerIDs.add(newID);
            }
    }

    public void updateGroupIDs(double oldID, double newID){
            if (this.groupIDs.contains(oldID)) {
                this.groupIDs.remove(oldID);
                this.groupIDs.add(newID);
            }
    }

    public void addWorkerByID(double ID) {
        if (this.workerIDs == null) {
            this.workerIDs = new ArrayList<Double>();
        }
        this.workerIDs.add(ID);
    }

    public void addGroupByID(double ID) {
        if (this.groupIDs == null) {
            this.groupIDs = new ArrayList<Double>();
        }
        this.groupIDs.add(ID);
    }

    public void updateID(double newID){
        double oldID = this.ID;
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
            String formatted = String.format("grp%05d", groupIDs.get(i));
            sb.append("{\"groupID\": \""+formatted+"\"},");
        }
        if (groupIDs.size()>0){
            String formatted = String.format("grp%05d",groupIDs.get(groupIDs.size()-1));
            sb.append("{\"groupID\": \""+formatted+"\"}");
        }
        sb.append("]");
        return sb.toString();
    }

    public static Project getProjectByID(double ID) {
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
