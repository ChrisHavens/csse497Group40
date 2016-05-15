/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.HashMap;

/**
 *
 * @author daveyle
 */
public class Shipment {
    private String contents;
    private String from;
    private String to;
    private String fromName;
    private String toName;
    private String time;
    private String date;
    private String lastLocationID;
    private String name;
    private String status;
    private long parentID;
    private long Id;
    private boolean isHidden=false;
    
    public Shipment(){
        
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

    public String getLastLocationID() {
        return lastLocationID;
    }

    public void setLastLocation(String lastLocationID) {
        this.lastLocationID = lastLocationID;
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

    public long getId() {
        return Id;
    }

    public void setId(long ID) {
        this.Id = ID;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }
    
    public static Shipment parseShipment(HashMap<String, Object> map){
         HashMap<String, Object> source=(HashMap)map.get("_source");
                  
                    final Shipment s =new Shipment();
                    s.setId(Integer.parseInt(((String)map.get("_id"))));
                    s.setName((String) source.get("name"));
                    if(source.get("dateArchived") == null)
                        s.setHidden(false);
                    else
                        s.setHidden(true);
                    s.setContents((String)source.get("contents"));
                    s.setLastLocation((String)source.get("lastLocationID"));
                    String s1=(String) source.get("pickupTime");
                    s.setTime(s1.split(" ")[1]);
                    s.setDate(s1.split(" ")[0]);
                    s.setFrom((String) source.get("fromLocationID"));
                    s.setTo((String)source.get("toLocationID"));
        return s;
    }
    
    
}
