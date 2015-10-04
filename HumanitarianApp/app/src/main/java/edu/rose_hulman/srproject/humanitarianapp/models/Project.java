package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;

/**
 * Created by Chris Havens on 10/4/2015.
 */
public class Project implements Serialisable{
    private final int ID;
    private List<Group> groups;
    private Worker manager; //List???
    private String name;
    private String description;
    private List<Location> locations;
    private List<Worker> workers;
    //private List<Worker> admins; ???

    public Project(int ID) {
        this.ID = ID;
    }

    @Override
    public String serialise() {
        return null;
    }

    @Override
    public Serialisable deserialise(String s) {
        return null;
    }

    public int getID() {
        return ID;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Worker getManager() {
        return manager;
    }

    public void setManager(Worker manager) {
        this.manager = manager;
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

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
    //private List<Worker> admins; ???


}
