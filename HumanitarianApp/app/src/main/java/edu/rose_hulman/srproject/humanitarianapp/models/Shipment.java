package edu.rose_hulman.srproject.humanitarianapp.models;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;

/**
 * Created by daveyle on 9/21/2015.
 */
public class Shipment implements Serialisable<Shipment> {
    String contents;
    String from;
    String to;
    String fromName;
    String toName;
    String time;
    String date;
    Location lastLocation;
    String name;
    String status;
    String parentID;
    private long ID;

    public Shipment() {
    }
    public Shipment(long id){
        this.ID=id;
    }

    public Shipment(String contents, String from, String to, String time, String date) {
        this.contents = contents;
        this.from = from;
        this.to = to;
        this.time = time;
        this.date=date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String serialise() {
        return null;
    }

    @Override
    public Shipment deserialise(String s) {
        return null;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }


    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String toJSON(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("\"contents\": \""+getContents()+"\",");
        sb.append("\"lastLocationID\": \"lcn"+getLastLocation().getID()+"\",");
        sb.append("\"name\": \""+getName()+"\",");
        sb.append("\"parentID\": \""+getParentID()+"\",");
        sb.append("\"fromLocationID\": \""+getFrom()+"\",");
        sb.append("\"toLocationID\": \""+getTo()+"\",");
        sb.append("\"pickupTime\": \""+getDate()+"\",");
        sb.append("\"status\": \""+getStatus()+"\"");


        sb.append("}");
        return sb.toString();
    }
}
