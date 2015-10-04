package edu.rose_hulman.srproject.humanitarianapp.serialisation;

import java.lang.reflect.Constructor;

/**
 * Created by daveyle on 9/21/2015.
 */
public class Serialiser {
    public static String serialise(Serialisable o){
        return o.serialise();
    }
    public static Serialisable deserialise(String s){
        Class c=Serialiser.getClass(s); //get the class that the string is a serialised version of
        try {
            Constructor constructor=c.getConstructor(null); //get empty constructor
            Serialisable serialisable=(Serialisable)constructor.newInstance(null);
            return serialisable.deserialise(s);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Class getClass(String s){
        //Do some processing to figure out which class this is a serialised version off
        return null;
    }
}
