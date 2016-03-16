/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author daveyle
 */
public class Person {
     private String name;
    private String phoneNumber;
    private String email;
    private List<Long> groupIDs =  new ArrayList<Long>();
    private List<Long> projectIDs =  new ArrayList<Long>();
    private long ID;
    private boolean isHidden=false;
    private PersonLocation lastCheckin;
    private List<PersonLocation> locations =  new ArrayList<PersonLocation>();
    
    
    public Person(){
        
    }
    public static Person parsePerson(HashMap<String, Object> map){
        //TODO: locations
        HashMap<String, Object> source=(HashMap)map.get("_source");
                  
                    final Person p=new Person();
                    p.setId(Integer.parseInt(((String)map.get("_id"))));
                    p.setName((String) source.get("name"));
                    p.setEmail((String) source.get("email"));
                    p.setPhoneNumber((String) source.get("phone"));
                    if(source.get("dateArchived") == null)
                        p.setHidden(false);
                    else
                        p.setHidden(true);
        return p;
                    
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getGroupIDs() {
        return groupIDs;
    }

    public void setGroupIDs(List<Long> groupIDs) {
        this.groupIDs = groupIDs;
    }

    public List<Long> getProjectIDs() {
        return projectIDs;
    }

    public void setProjectIDs(List<Long> projectIDs) {
        this.projectIDs = projectIDs;
    }

    public long getId() {
        return ID;
    }

    public void setId(long ID) {
        this.ID = ID;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public PersonLocation getLastCheckin() {
        return lastCheckin;
    }

    public void setLastCheckin(PersonLocation lastCheckin) {
        this.lastCheckin = lastCheckin;
    }

    public List<PersonLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<PersonLocation> locations) {
        this.locations = locations;
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
}
