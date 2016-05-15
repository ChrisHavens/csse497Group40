/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author daveyle
 */
public class Project implements Serializable{
    private long id;                       //#0
     private String name;                   //#1
     private String description;            //#2
     private List<Long> groupIDs;           //#3
     private List<Long> workerIDs;          //#4
     private List<Long> adminIDs;           //#5
     private List<Long> locationIDs;        //#6
     private List<Long> checklistIDs;       //#7
     private List<Long> shipmentIDs;        //#8
    private boolean isHidden=false;
    
    public Project(){
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Long> getGroupIDs() {
        return groupIDs;
    }

    public void setGroupIDs(List<Long> groupIDs) {
        this.groupIDs = groupIDs;
    }

    public List<Long> getWorkerIDs() {
        return workerIDs;
    }

    public void setWorkerIDs(List<Long> workerIDs) {
        this.workerIDs = workerIDs;
    }

    public List<Long> getAdminIDs() {
        return adminIDs;
    }

    public void setAdminIDs(List<Long> adminIDs) {
        this.adminIDs = adminIDs;
    }

    public List<Long> getLocationIDs() {
        return locationIDs;
    }

    public void setLocationIDs(List<Long> locationIDs) {
        this.locationIDs = locationIDs;
    }

    public List<Long> getChecklistIDs() {
        return checklistIDs;
    }

    public void setChecklistIDs(List<Long> checklistIDs) {
        this.checklistIDs = checklistIDs;
    }

    public List<Long> getShipmentIDs() {
        return shipmentIDs;
    }

    public void setShipmentIDs(List<Long> shipmentIDs) {
        this.shipmentIDs = shipmentIDs;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }
    public static Project parseProject(HashMap<String, Object> map){
         HashMap<String, Object> source = (HashMap) map.get("_source");

                    Project p = new Project();
                    p.setId(Integer.parseInt(((String) map.get("_id"))));
                    p.setName((String) source.get("name"));
                    if(source.get("dateArchived") == null)
                        p.setHidden(false);
                    else
                        p.setHidden(true);
                    
                    return p;
    }
    
    

    
    
}
