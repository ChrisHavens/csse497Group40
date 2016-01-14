package edu.rose_hulman.srproject.humanitarianapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by goldthea on 1/10/2016.
 */
public class CoordinatesGetter {
    Context mContext;
    Location mLocation = new Location("this should get changed");
    LocationListener locationListener;
    LocationManager locationManager;


    public CoordinatesGetter(Context context){
        this.mContext = context;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //do nothing. This is only used to trigger listner so getLastKnownLocation finds this
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
    }

    public Location getLocation(){
        //essentially, trigger a single update, then get the most recent location
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
        mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.d("location", mLocation.toString());
        return mLocation;
    }
}
