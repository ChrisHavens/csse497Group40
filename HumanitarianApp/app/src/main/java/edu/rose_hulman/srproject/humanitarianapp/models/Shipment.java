package edu.rose_hulman.srproject.humanitarianapp.models;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;

/**
 * Created by daveyle on 9/21/2015.
 */
public class Shipment implements Serialisable<Shipment> {
    String contents;
    String from;
    String to;
    String time;
    String date;
    Location lastLocation;
    private int ID;

    public Shipment() {
    }

    public Shipment(String contents, String from, String to, String time, String date) {
        this.contents = contents;
        this.from = from;
        this.to = to;
        this.time = time;
        this.date=date;
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

    @Override
    public String serialise() {
        return null;
    }

    @Override
    public Shipment deserialise(String s) {
        return null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }
}
