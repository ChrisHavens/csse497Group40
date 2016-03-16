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
public class Location {
    private long Id;                //0
    private float lng;              //1
    private float lat;              //2
    private String name;            //3
    private List<Long> projectIDs;  //4
    private List<Long> groupIDs;    //5
    private boolean isHidden=false;
    public Location(){
        
    }

    public long getId() {
        return Id;
    }

    public void setId(long ID) {
        this.Id = ID;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }
    public static Location parseLocation(HashMap<String, Object> map){
         HashMap<String, Object> source=(HashMap)map.get("_source");
                  
                    final Location p=new Location();
                    p.setId(Integer.parseInt(((String)map.get("_id"))));
                    p.setName((String) source.get("name"));
                    if(source.get("dateArchived") == null)
                        p.setHidden(false);
                    else
                        p.setHidden(true);
                    
                    p.setLat(Float.parseFloat((String)source.get("lat")));
                    p.setLng(Float.parseFloat((String)source.get("lng")));
        return p;
    }
}
