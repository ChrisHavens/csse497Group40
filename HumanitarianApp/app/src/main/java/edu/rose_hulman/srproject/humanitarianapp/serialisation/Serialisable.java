package edu.rose_hulman.srproject.humanitarianapp.serialisation;

/**
 * Created by daveyle on 9/21/2015.
 */
public interface Serialisable<T extends Serialisable> {
    String serialise();
    T deserialise(String s);


}
