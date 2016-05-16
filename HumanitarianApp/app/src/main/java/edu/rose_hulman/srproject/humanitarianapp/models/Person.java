package edu.rose_hulman.srproject.humanitarianapp.models;

//import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.SerilizationConstants;

/**
 * Created by daveyle on 9/21/2015.
 */
public class Person implements Serializable, Selectable {
    private String name;
    private String phoneNumber;
    private String email;
    private Roles.PersonRoles role;
    private PersonLocation lastCheckin;
    private Date lastCheckinTime;
    //private List<PersonLocation> lastLocations=new ArrayList<>();
    private List<Long> groupIDs =  new ArrayList<Long>();
    private List<Long> projectIDs =  new ArrayList<Long>();
    private List<PersonLocation> locations =  new ArrayList<PersonLocation>();
    private long ID;
    private boolean isHidden=false;

    //Maybe pull these off into a list of global values?
    //Yes, established elsewhere, but not used yet.
    private static List<Person> knownPersons = new ArrayList<Person>();
    private static long newWorkerCount = (new Random()).nextInt(900)+100;
    private static List<Person> localIDPersons = new ArrayList<Person>();
    private String datetime;

    /*
     * All of the variables post refactoring. Also, the order is important and based off type NOT
     * what logically belongs where.

     // A flag for each field denoting if it needs to be updated on the server.
     // This will need to be initulized in the constructors.
     private boolean[] isDirty = new boolean[10];
     //A flag for if the object was synced from the server or created locally
     private boolean[] onServer = false;

     private long ID;
     private Date lastCheckinTime;
     private Roles.PersonRoles role;
     private String name;
     private String phoneNumber;
     private String email;
     private PersonLocation lastCheckin;

     private List<Long> groupIDs =  new ArrayList<Long>();
     private List<Long> projectIDs =  new ArrayList<Long>();
     private List<Location> locations =  new ArrayList<Location>();

     */


     public Person() {
        this.setUpID();
    }

    public Person(long id){
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
    public Person(long id, String name, String phoneNumber, String email) {
        this.ID=id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }



    public Person(String name, String phoneNumber, long ID) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
        knownPersons.add(this);
    }

    public Person(String name, String phoneNumber, String email, long ID) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ID = ID;
        knownPersons.add(this);
    }

    private void setUpID(){
        this.ID = SerilizationConstants.generateID(SerilizationConstants.PERSON_NUM);
        knownPersons.add(this);
        localIDPersons.add(this);
    }

    public static Person createFullWorker(String name, String phoneNumber, Location initialAssignment, Project initialProject, Group initialGroup) {
        if (name == null || name.length() == 0) {
            return null;
        }
        Person person = new Person(name, phoneNumber);
        if (initialAssignment != null) {
            //person.addLocation(initialAssignment);
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
    public void checkIn(PersonLocation location){
        this.lastCheckin=location;
        this.locations.add(location);

    }

    public List<PersonLocation> getLocations() {
        return locations;
    }

    public void addLocation(PersonLocation location) {
        this.locations.add(location);
        if (lastCheckin!=null) {
            if (location.getTime().compareTo(lastCheckin.getTime()) > 0) {
                lastCheckin = location;
            }
        }
        else{
            lastCheckin=location;
        }
    }

    public void setLocations(List<PersonLocation> locations) {
        this.locations = locations;
    }

    public boolean isInProject(long projectID){
        return this.projectIDs.contains(projectID);
    }
    public boolean isInGroup(long groupID){
        return this.groupIDs.contains(groupID);
    }

    public long getID(){
        return this.ID;
    }

    /**
     * With the current configuration for ID generation this method should
     * almost never be called. This function is for if the server says that
     * the local version of an ID is wrong and this is the one to update to.
     * @param newID
     */
    public void updateID(long newID) {
        long oldID = this.ID;
        this.ID = newID;
        localIDPersons.remove(this);
        for (Group group : Group.getKnownGroups()) {
            group.updateWorkerID(oldID, newID);
        }
        for (Project project : Project.getKnownProjects()){
            project.updateWorkerID(oldID, newID);
        }
    }

    public static Person getWorkerByID(long ID) {
        for (Person person : knownPersons){
            if (person.ID == ID){
                return person;
            }
        }
        return null;
    }

    public void updateGroupIDs(long oldID, long newID){
        if (this.groupIDs.contains(oldID)) {
            this.groupIDs.remove(oldID);
            this.groupIDs.add(newID);
        }
    }
    public void addGroupID(long id){
        this.groupIDs.add(id);
    }
    public void addProjectID(long id){
        this.projectIDs.add(id);
    }
    public void removeGroupID(long id){this.groupIDs.remove(id);}
    public void removeProjectID(long id){this.projectIDs.remove(id);}


    public void updateProjectIDs(long oldID, long newID){
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
//        if (!lastLocations.isEmpty()) {
//            sb.append(lastLocationsToString());
//        }
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

            sb.append("{\"parentID\": \""+projectIDs.get(i)+"\"},");
        }
        if (projectIDs.size()>0){
            long proj=projectIDs.get(projectIDs.size() - 1);
            //String formatted = String.format("prj%05d", proj);
            sb.append("{\"parentID\": \""+proj+"\"}");
            if (groupIDs.size()>0){
                sb.append(",");
            }
        }
        for (int i=0; i<groupIDs.size()-1; i++){
            //String formatted = String.format("grp%05d", );
            sb.append("{\"parentID\": \""+groupIDs.get(i)+"\"},");
        }
        if (groupIDs.size()>0){
            //String formatted = String.format("grp%05d",);
            sb.append("{\"parentID\": \""+groupIDs.get(groupIDs.size()-1)+"\"}");
        }
        sb.append("]");
        return sb.toString();
    }
//    private String lastLocationsToString(){
//        StringBuilder sb=new StringBuilder();
////        sb.append("\"recentLocations\": [");
////        for (PersonLocation location: lastLocations){
////            sb.append("{"+location.toJSON()+"},");
////        }
//        if (sb.charAt(sb.length()-1)==','){
//            sb.deleteCharAt(sb.length()-1);
//        }
//        sb.append("],");
//        return sb.toString();
//    }

    public Roles.PersonRoles getRole() {
        return role;
    }

    public void setRole(Roles.PersonRoles role) {
        this.role = role;
    }

    @Override
    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
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
            sb.append("\"lat\": \""+lat+"\",");
            sb.append("\"lng\": \""+lng+"\",");
            sb.append("\"name\": \""+name+"\",");
            sb.append("\"time\": \""+time+"\"");
            return sb.toString();
        }
    }
    @Override
    public String getDateTimeModified() {
        return datetime;
    }

    @Override
    public void setDateTimeModified(String dateTime) {
        this.datetime=dateTime;
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
