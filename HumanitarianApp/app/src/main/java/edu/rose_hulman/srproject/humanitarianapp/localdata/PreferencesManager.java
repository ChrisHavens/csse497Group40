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
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("ManualSync",manualSync);
    }

    public static void setSyncDate(String date){

    }

    public static boolean getSyncType(){
        return preferences.getBoolean("ManualSync", false);
    }

    public static String getSyncTime(String date){
        return preferences.getString("SyncTime", "01-01-1970 00:00:00");
    }
    public static void setURL(String url){
        SharedPreferences.Editor edit=preferences.edit();
        edit.putString("URL", url);
    }
    public static String getURL(){
        return preferences.getString("URL", "s40server.csse.rose-hulman.edu");
    }
    public static void setID(String id){
        SharedPreferences.Editor edit=preferences.edit();
        edit.putString("ID", id);
    }
    public static String getID(){
        return preferences.getString("ID", "-1");
    }
}
