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
            .setEndpoint("http://s40server.csse.rose-hulman.edu:9200")
            .build();
    Service service=adapter.create(Service.class);
    public void addNewPerson(Person person, Callback<Response> callback){
        TypedInput typedInput=new TypedString(person.toJSON());
        service.add("person","psn"+person.getID(), typedInput, callback);
    }
    public void addNewProject(Project project, Callback<Response> callback){
        TypedInput typedInput=new TypedString(project.toJSON());
        service.add("project", "prj"+String.format("%05d", project.getID()), typedInput, callback);
    }
    public void addNewGroup(Group group, Callback<Response> callback){
        TypedInput typedInput=new TypedString(group.toJSON());
        service.add("group", "grp"+String.format("%05d", group.getID()), typedInput, callback);
    }
    public void addNewLocation(Location location, Callback<Response> callback){
        TypedInput typedInput=new TypedString(location.toJSON());
        service.add("location", "lcn"+String.format("%05d", location.getID()), typedInput, callback);
    }
    public void addNewNote(Note note, Callback<Response> callback){
        TypedInput typedInput=new TypedString(note.toJSON());
        service.add("note", "not"+String.format("%05d", note.getID()), typedInput, callback);
    }
    public void addNewShipment(Shipment shipment, Callback<Response> callback){
        TypedInput typedInput=new TypedString(shipment.toJSON());
        service.add("note", "shp"+String.format("%05d", shipment.getID()), typedInput, callback);
    }

    public void updateProject(double projectID, String json, Callback<Response> callback){
        TypedInput typedInput=new TypedString(json);
        service.add("project", "prj"+String.format("%05d", projectID), typedInput, callback);
    }
    public void updatePerson(Person person, Callback<Response> callback){
        TypedInput typedInput=new TypedString(person.toJSON());
        service.update("person", "psn" + String.format("%03d", person.getID()), typedInput, callback);
    }
    public void updateNote(double id, String title, String body, Callback<Response> callback){
        StringBuilder sb=new StringBuilder();
        sb.append("{\"doc\":{\"contents\": \""+body+"\", \"title\": \""+title+"\"}}");
        Log.w("Note:", id+" "+sb.toString());
        TypedInput typedInput=new TypedString(sb.toString());
        service.update("note", "not"+String.format("%05d", id), typedInput, callback);
    }
    public void getAllProjects(Callback<Response> callback){
        service.getAllProjects(callback);
    }

    public void getAllGroups(Project p, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"projectIDs.projectID\": \"");
        sb.append(String.format("prj%05d", p.getID()));
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.getAllGroups(new TypedString(sb.toString()), callback);
    }

    public void getAllPeople(Project p, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.parentID\": \"");
        sb.append(String.format("prj%05d", p.getID()));
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.getAllPeople(new TypedString(sb.toString()), callback);

    }
    public void getAllPeople(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.parentID\": \"");
        sb.append(String.format("grp%05d", g.getID()));
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.getAllPeople(new TypedString(sb.toString()), callback);

    }
    public void getAllNotes(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentID\": \"");
        sb.append(String.format("grp%05d", g.getID()));
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.getAllNotes(new TypedString(sb.toString()), callback);

    }
    public void getAllChecklists(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentID\": \"");
        sb.append(String.format("grp%05d", g.getID()));
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.getAllChecklist(new TypedString(sb.toString()), callback);

    }
    public void getAllShipments(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentID\": \"");
        sb.append(String.format("grp%05d", g.getID()));
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.getAllShipments(new TypedString(sb.toString()), callback);

    }
    public void getAllLocations(Project p, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.projectID\": \"");
        sb.append(String.format("prj%05d", p.getID()));
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.getAllLocations(new TypedString(sb.toString()), callback);

    }
    public void getAllLocations(Group g, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"parentIDs.groupID\": \"");
        sb.append(String.format("grp%05d", g.getID()));
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.getAllLocations(new TypedString(sb.toString()), callback);

    }
    public void get(String type, String id, Callback<Response> callback){
        service.get(type, id, callback);
    }




//    Retrofit retrofit=new Retrofit.Builder()
//            .baseUrl("http://s40server.csse.rose-hulman.edu:9200/")
//            .addConverterFactory(JacksonConverterFactory.create())
//            .build();
//    Service service=retrofit.create(Service.class);
//    public void getAllProjects(){
//        //String string="{\"fields\": [\"_id\"]}";
//        Call<SearchResponse> proj=service.getProjects();
//        proj.enqueue(new Callback<SearchResponse>() {
//
//            public void onResponse(Response<SearchResponse> response, Retrofit retrofit) {
//                System.out.println(response.body().getHits().getHits()[0].getId());
//            }
//
//
//            public void onFailure(Throwable t) {
//                System.out.println(t.getMessage());
//            }
//        });
//
//    }
    public interface Service{
        @PUT("/s40/{type}/{id}")
        void add(@Path(value="type") String type, @Path(value = "id") String id, @Body TypedInput body, Callback<Response> callback);
        @GET("/s40/{type}/{id}")
        void get(@Path(value="type") String type, @Path(value = "id") String id, Callback<Response> callback);
        @POST("/s40/{type}/{id}/_update")
        void update(@Path(value="type") String type, @Path(value = "id") String id, @Body TypedInput body, Callback<Response> callback);



        @GET("/s40/project/_search")
        void getAllProjects(Callback<Response> callback);
    @POST("/s40/group/_search")
    void getAllGroups(@Body TypedInput body,  Callback<Response> callback);

    @POST("/s40/person/_search")
    void getAllPeople(@Body TypedInput body,  Callback<Response> callback);

    @POST("/s40/note/_search")
    void getAllNotes(@Body TypedInput body,  Callback<Response> callback);

    @POST("/s40/checklist/_search")
    void getAllChecklist(@Body TypedInput body,  Callback<Response> callback);

    @POST("/s40/shipment/_search")
    void getAllShipments(@Body TypedInput body,  Callback<Response> callback);

    @POST("/s40/location/_search")
    void getAllLocations(@Body TypedInput body,  Callback<Response> callback);



    }

}
