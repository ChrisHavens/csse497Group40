package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

import java.util.Timer;
import java.util.TimerTask;

import edu.rose_hulman.srproject.humanitarianapp.R;


public class MainActivity extends Activity implements TabSwitchListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    @Override
    public void onProjectsButtonClicked() {
        Fragment fragment = new MainFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();
    }

    @Override
    public void onPeopleButtonClicked() {

    }

    @Override
    public void onNotesButtonClicked() {

    }

    @Override
    public void onChecklistsButtonClicked() {

    }
    public void makePhoneCall(String s){
        PackageManager pm=getPackageManager();
        Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+s.replaceAll("[^0-9|\\+]", "")));
        if (intent.resolveActivity(pm)!=null) {
            startActivity(intent);
        }else{
            Toast.makeText(this, "Not able to make phone calls!", Toast.LENGTH_LONG).show();
        }

    }
    public void makeText(String number){
        PackageManager pm=getPackageManager();
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+number));
        if (intent.resolveActivity(pm)!=null) {
            startActivity(intent);
        }else{
            Toast.makeText(this, "Not able to make text messages!", Toast.LENGTH_LONG).show();
        }
    }
    public void checkIn(){
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_LONG).show();


    }

}
