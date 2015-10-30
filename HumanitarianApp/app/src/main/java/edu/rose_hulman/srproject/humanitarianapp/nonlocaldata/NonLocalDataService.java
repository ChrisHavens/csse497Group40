package edu.rose_hulman.srproject.humanitarianapp.nonlocaldata;


import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import retrofit.Callback;

import retrofit.RestAdapter;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedString;

/**
 * Created by daveyle on 10/13/2015.
 */
public class NonLocalDataService {

    RestAdapter adapter = new RestAdapter.Builder()
            .setEndpoint("http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest")
            .build();
    WrapperService service=adapter.create(WrapperService.class);
    /*
    Add requests --Request to database server has body and is of type PUT!
     */
    private TypedInput getAddPayload(String type, String id, String json){
        String uri=String.format("uri=s40/%s/%s",type, id);
        String method="method=PUT";
        String my_json="json="+json;
        return new TypedJsonString(String.format("%s&%s&%s", uri, method,my_json));
    }
    public void addNewPerson(Person person, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("person", "psn" + person.getID(), person.toJSON());
        service.add(typedInput, callback);
    }
    public void addNewProject(Project project, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("project", ""+project.getID(), project.toJSON());
        service.add(typedInput, callback);
    }
    public void addNewGroup(Group group, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("group", ""+group.getID(), group.toJSON());
        service.add(typedInput, callback);
    }
    public void addNewLocation(Location location, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("location", "lcn"+String.format("%05d", location.getID()),location.toJSON());
        service.add( typedInput, callback);
    }
    public void addNewNote(Note note, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("note", "not"+String.format("%05d", note.getID()),note.toJSON());
        service.add( typedInput, callback);
    }
    public void addNewShipment(Shipment shipment, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("note", "shp"+String.format("%05d", shipment.getID()),shipment.toJSON());
        service.add( typedInput, callback);
    }

    /*
    Update requests --Request to database server has body and is of type POST!
     */
    private TypedInput getUpdatePayload(String type, String id, String json){
        String uri=String.format("uri=s40/%s/%s/_update", type, id);
        String method="method=POST";
        String my_json="json="+json;
        return new TypedJsonString(String.format("%s&%s&%s", uri, method,my_json));
    }
    public void updateProject(double projectID, String json, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("project", "" + projectID, json);
        service.add(typedInput, callback);
    }
    public void updatePerson(Person person, Callback<Response> callback){

        TypedInput typedInput=getUpdatePayload("person", "psn" + String.format("%03d", person.getID()), person.toJSON());
        service.update(typedInput, callback);
    }
    public void updateNote(double id, String title, String body, Callback<Response> callback){
        StringBuilder sb=new StringBuilder();
        sb.append("{\"doc\":{\"contents\": \""+body+"\", \"title\": \""+title+"\"}}");
        Log.w("Note:", id+" "+sb.toString());
        TypedInput typedInput=getUpdatePayload("note", "not" + String.format("%05d", id), sb.toString());
        service.update( typedInput, callback);
    }

    /*
    Search requests --Request to database server has body and is of type POST!
     */
    private TypedInput getSearchPayload(String type, String json){
        String uri=String.format("uri=s40/%s/_search",type);
        String method="method=POST";
        if (json!=null && json!="") {
            String my_json = "json=" + json;
            String res=String.format("%s&%s&%s", uri, method, my_json);
            Log.w("SearchPayload: ", res);
            return new TypedJsonString(res);
        }
        else{
            String res=String.format("%s&%s", uri, method);
            Log.w("SearchPayload: ", res);
            return new TypedJsonString(res);
        }
    }
    public void getAllProjects(Callback<Response> callback){
        TypedInput typedInput= getSearchPayload("project", null);


        service.search(typedInput, callback);
    }
    public void getAllGroups(Project p, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"projectIDs.projectID\": \"");
        sb.append(""+p.getID());
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());

        service.search(getSearchPayload("group", sb.toString()), callback);
    }
    public void getAllPeople(Project p, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.parentID\": \"");
        sb.append(""+p.getID());
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("person", sb.toString()), callback);

    }
    public void getAllPeople(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.parentID\": \"");
        sb.append(g.getID());
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("person", sb.toString()), callback);

    }
    public void getAllNotes(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentID\": \"");
        sb.append(g.getID());
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("note", sb.toString()), callback);

    }
    public void getAllChecklists(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentID\": \"");
        sb.append(g.getID());
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("checklist", sb.toString()), callback);

    }
    public void getAllShipments(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentID\": \"");
        sb.append(g.getID());
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("shipment", sb.toString()), callback);

    }
    public void getAllLocations(Project p, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.projectID\": \"");
        sb.append(""+p.getID());
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("location", sb.toString()), callback);

    }
    public void getAllLocations(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.groupID\": \"");
        sb.append(g.getID());
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("location", sb.toString()), callback);

    }


    /*
    Get requests --Request to database server is bodyless and of type GET!
     */
    private TypedInput getGetPayload(String type, String id){
        String uri=String.format("uri=s40/%s/%s",type, id);
        String method="method=GET";
        return new TypedJsonString(String.format("%s&%s", uri, method));
    }
    public void get(String type, String id, Callback<Response> callback){
        service.get(getGetPayload(type, id), callback);
    }
    public class TypedJsonString extends TypedString {
        public TypedJsonString(String body) {
            super(body);
        }

        @Override public String mimeType() {
            return "application/json";
        }
    }
}
