package com.example.goldthea.gpstestapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button GPScoordsButton = (Button) findViewById(R.id.GPScoordsButton);
        Button NetcoordsButton = (Button) findViewById(R.id.NetcoordsButton);

        GPScoordsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
// Acquire a reference to the system Location Manager
                Context thisContext = v.getContext();
                LocationManager locationManager = (LocationManager) thisContext.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        Context context = getApplicationContext();
                        String Coords = location.getLatitude() + " " + location.getLongitude();
                        CharSequence text = "Current Location is" + Coords;

                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        mLocationManager.removeUpdates(this);

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };

// Register the listener with the Location Manager to receive location updates
                //http://inthecheesefactory.com/blog/things-you-need-to-know-about-android-m-permission-developer-edition/en
                //int permission2 = thisContext.requestPermission
                // int permission = thisContext.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION");
                // if(permission == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                // }
               /* else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Permission denied???";

                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                */


            }
        });


        NetcoordsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
// Acquire a reference to the system Location Manager
                Context thisContext = v.getContext();
                LocationManager locationManager = (LocationManager) thisContext.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        String Coords = location.getLatitude() + " " + location.getLongitude();
                        Log.d("coordinates", "Current Location is " + Coords);
                        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        mLocationManager.removeUpdates(this);

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {}

                    public void onProviderDisabled(String provider) {}
                };

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
