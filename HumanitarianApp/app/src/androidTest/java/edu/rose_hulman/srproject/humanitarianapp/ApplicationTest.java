package edu.rose_hulman.srproject.humanitarianapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import edu.rose_hulman.srproject.humanitarianapp.controllers.MainServiceActions;
import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    NonLocalDataService service;
    MainServiceActions act;
    public static boolean success;
    public ApplicationTest() {
        super(Application.class);
    }
    @Override
     protected void setUp() throws Exception {
        super.setUp();
        service = new NonLocalDataService();
    }
    public void testPreconditions() {

        assertNotNull("mFirstTestActivity is null", service);
    }
    @MediumTest
    public void testProjectCreation() {
        success = false;

        ApplicationWideData.knownProjects = new ArrayList();
        Project p = new Project("TestProject");
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
               ApplicationTest.success = true;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewProject(p, responseCallback);

        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        //service.deleteProject(p, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
    }
    @MediumTest
     public void testGroupCreation() {
        success = false;

        ApplicationWideData.knownGroups = new ArrayList();
        Group g = new Group("TestGroup");
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ApplicationTest.success = true;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewGroup(g, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        //service.deleteGroup(g,responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
    }
    @MediumTest
    public void testLocationCreation() {
        success = false;

        ApplicationWideData.knownLocations = new ArrayList();
        Location l = new Location("TestLocation");
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ApplicationTest.success = true;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewLocation(l, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        //service.deleteLocation(l,responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
    }
    @MediumTest
    public void testShipmentCreation() {
        success = false;

        ApplicationWideData.knownShipments = new ArrayList();
        Shipment s = new Shipment("Test data","TestLocation1", "TestLocation2", "Now","Today");
        s.setLastLocation(new Location("TestLocation3"));
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ApplicationTest.success = true;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewShipment(s, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        //service.deleteLocation(l,responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
    }
    @MediumTest
    public void testPersonCreation() {
        success = false;

        ApplicationWideData.knownPersons = new ArrayList();
        Person p = new Person("Billyjoebob","309-555-1061");
        p.setLastCheckin(new Person.PersonLocation());
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ApplicationTest.success = true;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewPerson(p, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        //service.deleteLocation(l,responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
    }
    @MediumTest
    public void testChecklistCreation() {
        success = false;
        assertFalse(success);
        ApplicationWideData.knownChecklists = new ArrayList();
        Checklist c = new Checklist("Peetah's hiking supplies");
        c.addItem(new Checklist.ChecklistItem("Camera"));
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ApplicationTest.success = true;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewChecklist(c,responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        //service.deleteLocation(l,responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
    }
}