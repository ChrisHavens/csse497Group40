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
public class Group {
    private long id;
    private long projectID;
    private String name;
    private String description;
    private boolean isHidden=false;
    public Group(){
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
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

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }
    public static Group parseGroup(HashMap<String, Object> map){
        //TODO: locations
        HashMap<String, Object> source=(HashMap)map.get("_source");
                  
                    final Group p=new Group();
                    p.setId(Integer.parseInt(((String)map.get("_id"))));
                    p.setName((String) source.get("name"));
                    if(source.get("dateArchived") == null)
                        p.setHidden(false);
                    else
                        p.setHidden(true);
        return p;
                    
    }
    
}
