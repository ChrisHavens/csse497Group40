package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
        edit.putBoolean("ManualSync", manualSync);
        edit.commit();
    }

    public static void setSyncDate(String date){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("SyncTime", date);
        edit.commit();
    }


    public static boolean getSyncType(){
        return preferences.getBoolean("ManualSync", false);
    }

    public static String getSyncTime(){
        return preferences.getString("SyncTime", "01-01-1970 00:00:00");
    }


    public static void setURL(String url){
        if (url.startsWith("https://")){
            url=url+":8443";
        }
        else if (url.startsWith("http://")){
            url=url+":8080";
        }

        SharedPreferences.Editor edit=preferences.edit();
        edit.putString("URL", url);
        edit.commit();
        Log.wtf("URL", PreferencesManager.getURL());
    }

    public static String getURL(){
        return preferences.getString("URL", "http://s40server.csse.rose-hulman.edu:8080");
    }
    public static void setID(String id){
        SharedPreferences.Editor edit=preferences.edit();
        edit.putString("ID", id);
        edit.commit();
    }
    public static String getID(){
        return preferences.getString("ID", "-1");
    }
}
