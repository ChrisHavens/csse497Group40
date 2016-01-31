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

    public static void setSyncType(boolean manualSync){

    }

    public static void setSyncDate(String date){

    }

    public static boolean getSyncType(){
        return preferences.getBoolean("ManualSync", false);
    }

    public static String getSyncTime(String date){
        return preferences.getString("SyncTime", "01-01-1970 00:00:00");
    }
}
