package edu.rose_hulman.srproject.humanitarianapp.models;

//import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by daveyle on 9/21/2015.
 */
public class Person implements Serializable {
    private String name;
    private String phoneNumber;
    private String email;
    private Roles.PersonRoles role;
    private PersonLocation lastCheckin;
    private Date lastCheckinTime;
    private List<Integer> groupIDs =  new ArrayList<Integer>();
    private List<Integer> projectIDs =  new ArrayList<Integer>();
    private List<Location> locations =  new ArrayList<Location>();
    private int ID;

    //Maybe pull these off into a list of global values
    private static List<Person> knownPersons = new ArrayList<Person>();

    //New worker count allows the local system to have their own ids that
    // will never interfere with the IDs given by the server. These ids
    // will be changed to new ones next time a sync happens.
    private static int newWorkerCount = (new Random()).nextInt(900)+100;
    private static List<Person> localIDPersons = new ArrayList<Person>();


    public Person() {
        this.setUpID();
    }

    public Person(int id){
        this.ID=id;
    }
    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.setUpID();
    }

    public Person(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }



    public Person(String name, String phoneNumber, int ID) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
        knownPersons.add(this);
    }

    public Person(String name, String phoneNumber, String email, int ID) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ID = ID;
        knownPersons.add(this);
    }

    private void setUpID(){

        this.ID = newWorkerCount;
        newWorkerCount++;
        knownPersons.add(this);
        localIDPersons.add(this);
    }

    public static Person createFullWorker(String name, String phoneNumber, Location initialAssignment, Project initialProject, Group initialGroup) {
        if (name == null || name.length() == 0) {
            return null;
        }
        Person person = new Person(name, phoneNumber);
        if (initialAssignment != null) {
            person.addLocation(initialAssignment);
        }
        if (initialProject != null) {
            person.projectIDs.add(initialProject.getID());
        }
        if (initialGroup != null) {
            person.groupIDs.add(initialGroup.getID());
        }
        return person;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getLastCheckinTime() {
        return lastCheckinTime;
    }

    public void setLastCheckinTime(Date lastCheckinTime) {
        this.lastCheckinTime = lastCheckinTime;
    }

    public PersonLocation getLastCheckin() {
        return lastCheckin;
    }

    public void setLastCheckin(PersonLocation lastCheckin) {
        this.lastCheckin = lastCheckin;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }


    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }


    public int getID(){
        return this.ID;
    }

    /**
     * With the current configuration for ID generation this method should
     * almost never be called. This function is for if the server says that
     * the local version of an ID is wrong and this is the one to update to.
     * @param newID
     */
    public void updateID(int newID) {
        int oldID = this.ID;
        this.ID = newID;
        localIDPersons.remove(this);
        for (Group group : Group.getKnownGroups()) {
            group.updateWorkerID(oldID, newID);
        }
        for (Project project : Project.getKnownProjects()){
            project.updateWorkerID(oldID, newID);
        }
    }

    public static Person getWorkerByID(int ID) {
        for (Person person : knownPersons){
            if (person.ID == ID){
                return person;
            }
        }
        return null;
    }

    public void updateGroupIDs(int oldID, int newID){
        if (this.groupIDs.contains(oldID)) {
            this.groupIDs.remove(oldID);
            this.groupIDs.add(newID);
        }
    }
    public void addGroupID(int id){
        this.groupIDs.add(id);
    }
    public void addProjectID(int id){
        this.projectIDs.add(id);
    }


    public void updateProjectIDs(int oldID, int newID){
        if (this.projectIDs.contains(oldID)) {
            this.projectIDs.remove(oldID);
            this.projectIDs.add(newID);
        }
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static List<Person> getKnownPersons() {
        return knownPersons;
    }

    /*
    {
  "location": "lcn01000",
  "name": "Fem!Shep",
  "email": "notDeadYet@normandy.extranet.org",
  "phone": "555-555-5555",
  "parentIDs": [
  {"parentID": "prj01000"},
  {"parentID": "grp00000"}
  ]
}
     */

    public String toJSON(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append(lastCheckin.toJSON()+",");
        sb.append("\"name\": \""+getName()+"\",");
        sb.append("\"email\": \""+getEmail()+"\",");
        sb.append("\"phone\": \""+getPhoneNumber()+"\",");
        //sb.append("\"role\": \""+Roles.roles[role.ordinal()]+"\",");
        sb.append(getParentString());
        sb.append("}");
        return sb.toString();
    }
    public String getParentString(){
        StringBuilder sb=new StringBuilder();
        sb.append("\"parentIDs\": [");


        for (int i=0; i<projectIDs.size()-1; i++){
            String formatted = String.format("prj%05d", projectIDs.get(i));
            sb.append("{\"parentID\": \""+formatted+"\"},");
        }
        if (projectIDs.size()>0){
            int proj=projectIDs.get(projectIDs.size()-1);
            String formatted = String.format("prj%05d", proj);
            sb.append("{\"parentID\": \""+formatted+"\"}");
            if (groupIDs.size()>0){
                sb.append(",");
            }
        }
        for (int i=0; i<groupIDs.size()-1; i++){
            String formatted = String.format("grp%05d", groupIDs.get(i));
            sb.append("{\"parentID\": \""+formatted+"\"},");
        }
        if (groupIDs.size()>0){
            String formatted = String.format("grp%05d",groupIDs.get(groupIDs.size()-1));
            sb.append("{\"parentID\": \""+formatted+"\"}");
        }
        sb.append("]");
        return sb.toString();
    }

    public Roles.PersonRoles getRole() {
        return role;
    }

    public void setRole(Roles.PersonRoles role) {
        this.role = role;
    }

    public static class PersonLocation{
        private float lat;
        private float lng;
        private String name;
        private String time;
        public PersonLocation(){

        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
        public String toJSON(){
            StringBuilder sb=new StringBuilder();
            sb.append("\"lastLocation\": {");
            sb.append("\"lat\": \""+lat+"\",");
            sb.append("\"lng\": \""+lng+"\",");
            sb.append("\"name\": \""+name+"\",");
            sb.append("\"time\": \""+time+"\"");
            sb.append("}");
            return sb.toString();
        }
    }

//    @Override
//    public String serialise() {
//        return null;
//    }
//
//    @Override
//    public Person deserialise(String s) {
//        return null;
//    }
}