package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;

/**
 * Created by Chris Havens on 10/4/2015.
 */
public class Group implements Serialisable{
    private int ID;
    private Project project;
    private String name;
    private List<Worker> workers;
    private Worker leader;
    private String description;
    private List<Note> notes;
    private List<Checklist> checklists;
    private List<Shipment> shipments;
    //private List<Worker> admins; ???


    public Group(String name, Project project) {
        this.name = name;
        this.project = project;
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public Worker getLeader() {
        return leader;
    }

    public void setLeader(Worker leader) {
        this.leader = leader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
