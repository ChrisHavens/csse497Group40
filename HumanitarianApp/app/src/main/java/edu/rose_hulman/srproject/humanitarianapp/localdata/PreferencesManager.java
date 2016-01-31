package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Chris on 1/31/2016.
 */
public class PreferencesManager {
    private static SharedPreferences preferences;
    public static void setPreferencesFile(SharedPreferences sharedPreferences){
        preferences = sharedPreferences;
    }
}
