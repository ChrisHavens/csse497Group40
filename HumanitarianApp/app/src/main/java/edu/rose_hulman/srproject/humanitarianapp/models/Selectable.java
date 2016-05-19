package edu.rose_hulman.srproject.humanitarianapp.models;

/**
 * Created by daveyle on 12/1/2015.
 */
public interface Selectable {
    boolean isHidden();
    long getID();
    String getDateTimeModified();
    void setDateTimeModified(String dateTime);
}
