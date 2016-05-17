package edu.rose_hulman.srproject.humanitarianapp.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;

/**
 * Created by daveyle on 9/21/2015.
 */
public class Shipment implements Serialisable<Shipment>, Selectable {
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
    long parentID;
    private long ID;
    private boolean isHidden=false;
    private int dirtyBits = 0;
    private String datetime;

    /*
     All of the variables post refactoring. Also, the order is important and based off type NOT
     what logically belongs where.

     // A flag for each field denoting if it needs to be updated on the server.
     // This will need to be initulized in the constructors.
     private boolean[] isDirty = new boolean[12];
     //A flag for if the object was synced from the server or created locally
     private boolean[] onServer = false;

     private long id;               //#0
     private Location lastLocation; //#1
     private String contents;       //#2
     private String from;           //#3
     private String to;             //#4
     private String fromName;       //#5
     private String toName;         //#6
     private String time;           //#7
     private String date;           //#8
     private String name;           //#9
     private String status;         //#10
     private String parentID;       //#11
     */

    /*
    The new constructors

    //Does not do any setup, if this shipment is to be used later, try to avoid this constructor
    public Shipment() {
    }

    public Shipment(long id) {
        this.removeImplicitVariableDeclarations();
        this.id = id;
        Arrays.fill(isDirty, false);
        ApplicationWideData.addExistingShipment(this);
    }

    public Shipment(long id, String name) {
        this.removeImplicitVariableDeclarations();
        this.id = id;
        this.name = name;
        Arrays.fill(isDirty, false);
        ApplicationWideData.addExistingShipment(this);
    }

    public Shipment(String name) {
        this.setupAsNew();
        this.name = name;
    }

    public void setupAsNew() {
        this.removeImplicitVariableDeclarations();
        this.id = SerilizationConstants.generateID(SerilizationConstants.SHIPMENT_NUM);
        Arrays.fill(isDirty, true);
        this.onServer = false;
        ApplicationWideData.addNewShipment(this);
    }

    private void removeImplicitVariableDeclarations() {
        this.lastLocation = null;
        this.contents = "";
        this.from = "";
        this.to = "";
        this.fromName = "";
        this.toName = "";
        this.time = "";
        this.date = "";
        this.name = "";
        this.status = "";
        this.parentID = "";
    }

    public void fullClean() {
        Arrays.fill(isDirty, false);
        this.onServer = true;
    }

     */

    /*
    The new setters, getters can remain unchanged.

    public void setLastLocation(Location lastLocation){
        this.lastLocation = lastLocation;
        this.isDirty[1] = true;
    }

    public void setLastLocationClean (Location lastLocation) {
        this.lastLocation = lastLocation;
        this.isDirty[1] = false;
    }

    public void setLastLocationMaintain (Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public void setContents(String contents){
        this.contents = contents;
        this.isDirty[2] = true;
    }

    public void setContentsClean (String contents) {
        this.contents = contents;
        this.isDirty[2] = false;
    }

    public void setContentsMaintain (String contents) {
        this.contents = contents;
    }

    public void setFrom(String from){
        this.from = from;
        this.isDirty[3] = true;
    }

    public void setFromClean (String from) {
        this.from = from;
        this.isDirty[3] = false;
    }

    public void setFromMaintain (String from) {
        this.from = from;
    }

    public void setTo(String to){
        this.to = to;
        this.isDirty[4] = true;
    }

    public void setToClean (String to) {
        this.to = to;
        this.isDirty[4] = false;
    }

    public void setToMaintain (String to) {
        this.to = to;
    }

    public void setFromName(String fromName){
        this.fromName = fromName;
        this.isDirty[5] = true;
    }

    public void setFromNameClean (String ) {
        this.fromName = fromName;
        this.isDirty[5] = false;
    }

    public void setFromNameMaintain (String fromName) {
        this.fromName = fromName;
    }

    public void setToName(String toName){
        this.toName = toName;
        this.isDirty[6] = true;
    }

    public void setToNameClean (String toName) {
        this.toName = toName;
        this.isDirty[6] = false;
    }

    public void setToNameMaintain (String toName) {
        this.toName = toName;
    }

    public void setTime(String time){
        this.time = time;
        this.isDirty[7] = true;
    }

    public void setTimeClean (String time) {
        this.time = time;
        this.isDirty[7] = false;
    }

    public void setTimeMaintain (String time) {
        this.time = time;
    }

    public void setDate(String date){
        this.date = date;
        this.isDirty[8] = true;
    }

    public void setDateClean (String date) {
        this.date = date;
        this.isDirty[8] = false;
    }

    public void setDateMaintain (String date) {
        this.date = date;
    }

    public void setName(String name){
        this.name = name;
        this.isDirty[9] = true;
    }

    public void setNameClean (String name) {
        this.name = name;
        this.isDirty[9] = false;
    }

    public void setNameMaintain (String name) {
        this.name = name;
    }

    public void setStatus(String status){
        this. status= status;
        this.isDirty[10] = true;
    }

    public void setStatusClean (String status) {
        this.status = status;
        this.isDirty[10] = false;
    }

    public void setStatusMaintain (String status) {
        this.status = status;
    }

    public void setParentID(String parentID){
        this.parentID = parentID;
        this.isDirty[11] = true;
    }

    public void setParentIDClean (String parentID) {
        this. = parentID;
        this.isDirty[11] = false;
    }

    public void setParentIDMaintain (String parentID) {
        this.parentID = parentID;
    }
     */

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

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
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
    public static Shipment fromJSON(long lg, String json){
        ObjectMapper mapper=new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference=
                new TypeReference<HashMap<String, Object>>() {
                };
        try {
            HashMap<String, Object> source = mapper.readValue(json, typeReference);
            Shipment l=parseJSON(lg, source);
            return l;





        }catch(Exception e){

        }
        return null;
    }
    public static Shipment parseJSON(long lg, HashMap<String, Object> source){
        Shipment s= new Shipment(lg);
        s.setContents((String) source.get("contents"));
        s.setFrom((String) source.get("fromLocationID"));
        s.setTo((String) source.get("toLocationID"));
        s.setName((String) source.get("name"));
        String[] split=((String) source.get("pickupTime")).split(" ");
        if (split.length==2) {
            s.setDate(split[0]);
            s.setTime(split[1]);
        }
        s.setStatus((String) source.get("status"));
        return s;
    }

    public Shipment clone(){
        Shipment newShipment=new Shipment(this.getID());
        newShipment.setDate(this.getDate());
        newShipment.setFromName(this.fromName);
        newShipment.setFrom(this.from);
        newShipment.setName(this.name);
        newShipment.setParentID(this.parentID);
        newShipment.setTime(this.time);
        newShipment.setContents(this.contents);
        newShipment.setLastLocation(this.lastLocation);
        newShipment.setStatus(this.status);
        newShipment.setTo(this.to);
        newShipment.setToName(this.toName);
        return newShipment;
    }

    @Override
    public boolean isHidden() {
        return isHidden;
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
