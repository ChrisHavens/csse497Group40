package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.List;

/**
 * Created by havenscs on 10/4/2015.
 */
public class ProjectGroupCombo {
    private Project project;
    private List<Group> groupViewSend;
    private List<Group> groupViewOnly;
    /**
     * The goal of this class is to help keep straight which groups to pull up when diving down into the hierarchy
     */
    public ProjectGroupCombo(){

    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Group> getGroupViewSend() {
        return groupViewSend;
    }

    public void setGroupViewSend(List<Group> groupViewSend) {
        this.groupViewSend = groupViewSend;
    }

    public List<Group> getGroupViewOnly() {
        return groupViewOnly;
    }

    public void setGroupViewOnly(List<Group> groupViewOnly) {
        this.groupViewOnly = groupViewOnly;
    }
}
