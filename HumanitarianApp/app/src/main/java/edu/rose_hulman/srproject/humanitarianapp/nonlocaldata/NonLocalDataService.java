package edu.rose_hulman.srproject.humanitarianapp.nonlocaldata;


import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
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
    public void updateProject(int projectID, String json, Callback<Response> callback){
        TypedInput typedInput=new TypedString(json);
        service.add("project", "prj"+String.format("%05d", projectID), typedInput, callback);
    }
    public void getAllProjects(Callback<Response> callback){
        service.getAllProjects(callback);
    }
    /*
    {
"query": {
  "filtered": {

    "filter": {
      "bool": {
        "must": [
          {"term": {
            "projectIDs.projectID": "prj01010"
          }}
        ]
      }
    }
  }
}
}
     */
    public void getAllGroups(Project p, Callback<Response> callback){
        StringBuilder sb= new StringBuilder();
        sb.append("{\"query\": {\"filtered\": {\"filter\": {\"bool\": { \"must\": [{\"term\": { \"projectIDs.projectID\": \"");
        sb.append(String.format("prj%05d", p.getID()));
        sb.append("\"}}]}}}}}");
        Log.w("JSON", sb.toString());
        service.getAllGroups(new TypedString(sb.toString()), callback);
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
        @POST("/s40/{type}/{id}/_update")
        void update(@Path(value="type") String type, @Path(value = "id") String id, @Body TypedInput body, Callback<Response> callback);



        @GET("/s40/project/_search")
        void getAllProjects(Callback<Response> callback);
    @POST("/s40/group/_search")
    void getAllGroups(@Body TypedInput body,  Callback<Response> callback);
    }

}
