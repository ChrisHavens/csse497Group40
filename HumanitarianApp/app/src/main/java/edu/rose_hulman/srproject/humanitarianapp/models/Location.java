package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private long ID;                //0
    private float lng;              //1
    private float lat;              //2
    private String name;            //3
    private List<Long> projectIDs;  //4
    private List<Long> groupIDs;    //5

    /*
     * All of the variables post refactoring. Also, the order is important and based off type NOT
     * what logically belongs where.

     // A flag for each field denoting if it needs to be updated on the server.
     // This will need to be initulized in the constructors.
     private boolean[] isDirty = new boolean[6];
     //A flag for if the object was synced from the server or created locally
     private boolean[] onServer = false;

     private long ID;
     private float lng;
     private float lat;
     private String name;
     private List<Long> projectIDs;
     private List<Long> groupIDs;
     */

    public Location(long id){
        this.ID=id;
        this.projectIDs=new ArrayList<>();
        this.groupIDs=new ArrayList<>();
    }

    public Location(String name, int latHour, int latMinute, int latSecond, int lngHour, int lngMinute, int lngSecond) {
        this.projectIDs=new ArrayList<>();
        this.groupIDs=new ArrayList<>();
        this.name=name;
        float hourMinAsFloat;
        float hourMinAsInt;
        hourMinAsInt = latMinute * Float.intBitsToFloat(60) + latSecond;
        hourMinAsFloat = hourMinAsInt / 3600;
        this.lat = latHour + hourMinAsFloat;

        hourMinAsInt = lngMinute * Float.intBitsToFloat(60) + lngSecond;
        hourMinAsFloat = hourMinAsInt / 3600;
        this.lat = lngHour + hourMinAsFloat;
    }
    public Location(String name) {
        this.name = name;
        this.projectIDs=new ArrayList<>();
        this.groupIDs=new ArrayList<>();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName() {
        return name;
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

    public List<Long> getProjectIDs() {
        return projectIDs;
    }

    public void setProjectIDs(List<Long> projectIDs) {
        this.projectIDs = projectIDs;
    }

    public List<Long> getGroupIDs() {
        return groupIDs;
    }

    public void setGroupIDs(List<Long> groupIDs) {
        this.groupIDs = groupIDs;
    }

    public void addNewProjectID(long projectID){
        this.projectIDs.add(projectID);
    }
    public void addNewGroupID(long groupID){
        this.groupIDs.add(groupID);
    }


    public String toJSON(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("\"lat\": \""+getLat()+"\",");
        sb.append("\"lng\": \""+getLng()+"\",");
        sb.append("\"name\": \""+getName()+"\",");
        sb.append(getParentString());
        sb.append("}");
        return sb.toString();
    }
    public String getParentString(){
        StringBuilder sb=new StringBuilder();
        sb.append("\"parentIDs\": [");


        for (int i=0; i<projectIDs.size()-1; i++){
//            String formatted = ""+;
            sb.append("{\"projectID\": \""+projectIDs.get(i)+"\"},");
        }
        if (projectIDs.size()>0){
            long proj=projectIDs.get(projectIDs.size()-1);
//            String formatted = ""+proj;
            sb.append("{\"projectID\": \""+proj+"\"}");
            if (groupIDs.size()>0){
                sb.append(",");
            }
        }
        for (int i=0; i<groupIDs.size()-1; i++){
            //String formatted = ""+;
            sb.append("{\"parentID\": \""+groupIDs.get(i)+"\"},");
        }
        if (groupIDs.size()>0){
            //String formatted = String.format("grp%05d",);
            sb.append("{\"parentID\": \""+groupIDs.get(groupIDs.size()-1)+"\"}");
        }
        sb.append("]");
        return sb.toString();
    }
}
