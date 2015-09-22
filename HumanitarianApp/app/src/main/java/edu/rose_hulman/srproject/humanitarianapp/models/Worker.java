package edu.rose_hulman.srproject.humanitarianapp.models;

import java.io.Serializable;

import edu.rose_hulman.srproject.humanitarianapp.serialisation.Serialisable;

/**
 * Created by daveyle on 9/21/2015.
 */
public class Worker implements Serializable {
    public String name;
    public String phoneNumber;

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
