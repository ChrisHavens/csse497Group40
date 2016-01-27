package edu.rose_hulman.srproject.humanitarianapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    NonLocalDataService service;
    MainServiceActions act;
    public static boolean success;
    public static Response data;
    public static ObjectMapper mapper;
    public static TypeReference<HashMap<String, Object>> typeReference;
    public static Callback<Response> responseCallback;
    public ApplicationTest() {
        super(Application.class);
    }
    @Override
     protected void setUp() throws Exception {
        super.setUp();
        service = new NonLocalDataService();
        mapper = new ObjectMapper();
        typeReference = new TypeReference<HashMap<String, Object>>() {
        };
        responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ApplicationTest.success = true;
                ApplicationTest.data = response;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
    }
    public void testPreconditions() {

        assertNotNull("mFirstTestActivity is null", service);
    }

    @MediumTest
    public void testProjectCreation() {
        success = false;

        ApplicationWideData.knownProjects = new ArrayList();
        Project p = new Project("TestProject");

        service.addNewProject(p, responseCallback);

        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        success = false;
        service.service.getProject(p.getId() + "", responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        String name = "";
        try {
            HashMap<String, Object> o = mapper.readValue(ApplicationTest.data.getBody().in(), typeReference);
            name = (String) ((HashMap) o.get("_source")).get("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(p.getName(), name);
        success = false;
        service.deleteProject(p, responseCallback);
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

        service.addNewGroup(g, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);

        success = false;
        service.service.getGroup(g.getId() + "", responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        String name = "";
        try {
            HashMap<String, Object> o = mapper.readValue(ApplicationTest.data.getBody().in(), typeReference);
            name = (String) ((HashMap) o.get("_source")).get("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(g.getName(), name);

        success = false;
        service.deleteGroup(g, responseCallback);
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

        service.addNewPerson(p, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);

        success = false;
        service.service.getPerson(p.getID() + "", responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        String name = "";
        try {
            HashMap<String, Object> o = mapper.readValue(ApplicationTest.data.getBody().in(), typeReference);
            name = (String) ((HashMap) o.get("_source")).get("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(p.getName(), name);

        success = false;
        service.deletePerson(p, responseCallback);
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

        service.addNewLocation(l, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);

        success = false;
        service.service.getLocation(l.getID() + "", responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        String name = "";
        try {
            HashMap<String, Object> o = mapper.readValue(ApplicationTest.data.getBody().in(), typeReference);
            name = (String) ((HashMap) o.get("_source")).get("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(l.getName(), name);

        success = false;
        service.deleteLocation(l, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
    }
    /*
    @MediumTest
     public void testNoteCreation() {
        success = false;

        ApplicationWideData.knownNotes = new ArrayList();
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
    }*/
    @MediumTest
    public void testChecklistCreation() {
        success = false;
        assertFalse(success);
        ApplicationWideData.knownChecklists = new ArrayList();
        Checklist c = new Checklist("Peetah's hiking supplies");
        c.addItem(new Checklist.ChecklistItem("Camera"));
        service.addNewChecklist(c, responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);

        success = false;
        service.service.getChecklist(c.getID() + "", responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        String name = "";
        try {
            HashMap<String, Object> o = mapper.readValue(ApplicationTest.data.getBody().in(), typeReference);
            name = (String) ((HashMap) o.get("_source")).get("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(c.getTitle(), name);

        success = false;
        service.deleteChecklist(c, responseCallback);
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
        service.addNewShipment(s, responseCallback);
        try {
        Thread.sleep(1000);
        }catch(Exception e){
        e.printStackTrace();
        }
        assertTrue(success);

        success = false;
        service.service.getShipment(s.getID() + "", responseCallback);
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertTrue(success);
        System.out.println(s.getID());
        String name = "";
        try {
            HashMap<String, Object> o = mapper.readValue(ApplicationTest.data.getBody().in(), typeReference);
            name = (String) ((HashMap) o.get("_source")).get("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(s.getName(), name);

        success = false;
        service.deleteShipment(s,responseCallback);
        try {
        Thread.sleep(1000);
        }catch(Exception e){
        e.printStackTrace();
        }
        assertTrue(success);
        }


        }