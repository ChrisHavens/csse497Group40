package edu.rose_hulman.srproject.humanitarianapp.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by daveyle on 9/21/2015.
 */
public class Worker implements Serializable {
    private String name;
    private String phoneNumber;
    private List<Location> locations;
    private Location lastCheckin;
    private Date lastCheckinTime;
    private String title;
    private List<ProjectGroupCombo> projects;

    public Worker() {
    }

    public Worker(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProjectGroupCombo> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectGroupCombo> projects) {
        this.projects = projects;
    }

    public Date getLastCheckinTime() {
        return lastCheckinTime;
    }

    public void setLastCheckinTime(Date lastCheckinTime) {
        this.lastCheckinTime = lastCheckinTime;
    }

    public Location getLastCheckin() {
        return lastCheckin;
    }

    public void setLastCheckin(Location lastCheckin) {
        this.lastCheckin = lastCheckin;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

//    @Override
//    public String serialise() {
//        return null;
//    }
//
//    @Override
//    public Worker deserialise(String s) {
//        return null;
//    }
}
