package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private double ID;
    private float lng;
    private float lat;
    private String name;
    private List<Double> projectIDs=new ArrayList<Double>();
    private List<Double> groupIDs=new ArrayList<Double>();

    public Location(double id){
        this.ID=id;
    }

    public Location(String name, int latHour, int latMinute, int latSecond, int lngHour, int lngMinute, int lngSecond) {
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
    }

    public double getID() {
        return ID;
    }

    public void setID(double ID) {
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
            String formatted = String.format("prj%05d", projectIDs.get(i));
            sb.append("{\"projectID\": \""+formatted+"\"},");
        }
        if (projectIDs.size()>0){
            double proj=projectIDs.get(projectIDs.size()-1);
            String formatted = String.format("prj%05d", proj);
            sb.append("{\"projectID\": \""+formatted+"\"}");
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
}
