package edu.rose_hulman.srproject.humanitarianapp.models;

public class Location {
    private int ID;
    private float lng;
    private float lat;
    private String name;

    public Location(String name, int latHour, int latMinute, int latSecond, int lngHour, int lngMinute, int lngSecond) {
        float hourMinAsFloat;
        float hourMinAsInt;
        hourMinAsInt = latMinute * Float.intBitsToFloat(60) + latSecond;
        hourMinAsFloat = hourMinAsInt / 3600;
        this.lat = latHour + hourMinAsFloat;

        hourMinAsInt = lngMinute * Float.intBitsToFloat(60) + lngSecond;
        hourMinAsFloat = hourMinAsInt / 3600;
        this.lat = lngHour + hourMinAsFloat;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
}
