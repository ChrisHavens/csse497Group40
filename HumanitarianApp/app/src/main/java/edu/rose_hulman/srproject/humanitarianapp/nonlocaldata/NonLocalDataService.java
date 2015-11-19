package edu.rose_hulman.srproject.humanitarianapp.nonlocaldata;


import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
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
    final String notHiddenFilter="{\"missing\": {\"field\": \"dateArchived\"}},";

    /*
    Add requests --Request to database server has body and is of type PUT!
     */
    private TypedInput getAddPayload(String type, String id, String json){
        String uri=String.format("uri=s40/%s/%s",type, id);
        String method="method=PUT";
        String my_json="json="+json;
        String res=String.format("%s&%s&%s", uri, method, my_json);
        Log.w("SearchPayload: ", res);
        return new TypedJsonString(res);
    }
    public void addNewPerson(Person person, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("person", "" + person.getID(), person.toJSON());
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
        TypedInput typedInput=getAddPayload("location", ""+location.getID(),location.toJSON());
        service.add( typedInput, callback);
    }
    public void addNewNote(Note note, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("note", "" + note.getID(), note.toJSON());
        service.add( typedInput, callback);
    }
    public void addNewShipment(Shipment shipment, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("shipment", "" + shipment.getID(), shipment.toJSON());
        service.add( typedInput, callback);
    }
    public void addNewChecklist(Checklist checklist, Callback<Response> callback){
        TypedInput typedInput=getAddPayload("checklist", "" + checklist.getID(), checklist.toJSON());
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
    public void updateProject(long projectID, String json, Callback<Response> callback){
        TypedInput typedInput=getUpdatePayload("project", "" + projectID, json);
        service.add(typedInput, callback);
    }
    public void updateGroup(long groupID, String json, Callback<Response> callback){
        TypedInput typedInput=getUpdatePayload("group", "" + groupID, json);
        service.add(typedInput, callback);
    }

    public void updatePerson(long personId, String json, Callback<Response> callback){

        TypedInput typedInput=getUpdatePayload("person", personId+"", json);
        service.update(typedInput, callback);
    }
    public void updateLocation(long locationId, String json, Callback<Response> callback){

        TypedInput typedInput=getUpdatePayload("location", locationId+"", json);
        service.update(typedInput, callback);
    }
    public void updateNote(double id, String title, String body, Callback<Response> callback){
        StringBuilder sb=new StringBuilder();
        sb.append("{\"doc\":{\"contents\": \""+body+"\", \"title\": \""+title+"\"}}");
        Log.w("Note:", id+" "+sb.toString());
        TypedInput typedInput=getUpdatePayload("note", id+"", sb.toString());
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
    public void getAllProjects(boolean showHidden, Callback<Response> callback){
        TypedInput typedInput;
        if (showHidden) {
            typedInput = getSearchPayload("project", null);
        }
        else{
            String notHiddenPayload="{" +
                    "  \"query\": {" +
                    "    \"filtered\": {" +
                    "      \"filter\": {" +
                    "        \"missing\": {" +
                    "          \"field\": \"dateArchived\"" +
                    "        }" +
                    "      }" +
                    "    }" +
                    "  }" +
                    "}";
            typedInput=getSearchPayload("project", notHiddenPayload);
        }
        service.search(typedInput, callback);
    }
    public void getAllGroups(Project p, boolean showHidden, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        if (showHidden){
            sb.append("{\"query\": " +
                    "{\"filtered\": {" +
                    "\"filter\": {\"bool\": { \"must\": ["+
            "{\"term\": { \"projectIDs.projectID\": \"");
            sb.append(""+p.getID());
            sb.append("\"}}]}}}}}");
        }
        else{

            sb.append("{\"query\": " +
                    "{\"filtered\": {" +
                    "\"filter\": {\"bool\": { \"must\": ["+
                    notHiddenFilter+
                    "{\"term\": { \"projectIDs.projectID\": \"");
            sb.append("" + p.getID());
            sb.append("\"}}]}}}}}");
        }

        Log.w("JSON", sb.toString());

        service.search(getSearchPayload("group", sb.toString()), callback);
    }
    public void getAllPeople(Project p, boolean showHidden, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        if (showHidden) {
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.parentID\": \"");
            sb.append("" + p.getID());
            sb.append("\"}}]}}}}}");
        }
        else{
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": ["+
                    notHiddenFilter+
                    "{\"term\": { \"parentIDs.parentID\": \"");
            sb.append("" + p.getID());
            sb.append("\"}}]}}}}}");
        }
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("person", sb.toString()), callback);

    }
    public void getAllPeople(Group g, boolean showHidden, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        if (showHidden) {
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        else{
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": ["+
                    notHiddenFilter+
                    "{\"term\": { \"parentIDs.parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("person", sb.toString()), callback);

    }
    public void getAllNotes(Group g, boolean showHidden, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        if (showHidden) {
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        else{
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": ["+
                    notHiddenFilter+"{\"term\": { \"parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("note", sb.toString()), callback);

    }
    public void getAllChecklists(Group g, boolean showHidden, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        if (showHidden) {
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        else{
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": ["+
                    notHiddenFilter+"{\"term\": { \"parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("checklist", sb.toString()), callback);

    }
    public void getAllShipments(Group g, boolean showHidden, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        if (showHidden) {
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        else{
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": ["+
                    notHiddenFilter+"{\"term\": { \"parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("shipment", sb.toString()), callback);

    }
    public void getAllLocations(Project p, boolean showHidden, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        if (showHidden) {
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.parentID\": \"");
            sb.append("" + p.getID());
            sb.append("\"}}]}}}}}");
        }
        else{
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": ["+
                    notHiddenFilter+"{\"term\": { \"parentIDs.parentID\": \"");
            sb.append("" + p.getID());
            sb.append("\"}}]}}}}}");
        }
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("location", sb.toString()), callback);

    }
    public void getAllLocations(Group g, boolean showHidden, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        if (showHidden) {
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        else{
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": ["+
                    notHiddenFilter+"{\"term\": { \"parentIDs.parentID\": \"");
            sb.append(g.getID());
            sb.append("\"}}]}}}}}");
        }
        Log.w("JSON", sb.toString());
        service.search(getSearchPayload("location", sb.toString()), callback);

    }
    public void getAllLocations(long parentId, boolean parentIsGroup, boolean showHidden, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        if (showHidden) {
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.");
            if (parentIsGroup) {
                sb.append("parentID");
            } else {
                sb.append("parentID");
            }
            sb.append("\": \"");
            sb.append(parentId);
            sb.append("\"}}]}}}}}");
        }
        else{
            sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": ["+
                    notHiddenFilter+"{\"term\": { \"parentIDs.");
            if (parentIsGroup) {
                sb.append("parentID");
            } else {
                sb.append("parentID");
            }
            sb.append("\": \"");
            sb.append(parentId);
            sb.append("\"}}]}}}}}");
        }
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
    private TypedInput getHidePayload(String type, String id){
        String uri=String.format("uri=s40/%s/%s/_update", type, id);
        String method="method=POST";
        Calendar calendar=Calendar.getInstance();
        String date=String.format("%04d-%02d-%02d", calendar.get
                (Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        StringBuilder sb=new StringBuilder();
        sb.append("{\"doc\":{\"dateArchived\": \""+date+"\"}}");
        String my_json="json="+sb.toString();
        return new TypedJsonString(String.format("%s&%s&%s", uri, method,my_json));
    }
    public void hide(String type, String id, Callback<Response> callback){
        service.update(getHidePayload(type, id), callback);
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
