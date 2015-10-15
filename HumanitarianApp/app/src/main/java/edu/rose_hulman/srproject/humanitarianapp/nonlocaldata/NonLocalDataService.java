package edu.rose_hulman.srproject.humanitarianapp.nonlocaldata;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import edu.rose_hulman.srproject.humanitarianapp.models.Person;
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
        service.addPerson("psn"+person.getID(), typedInput, callback);
    }
    public void getAllProjects(Callback<Response> callback){
        service.getAllProjects(callback);
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
        @PUT("/s40/person/{id}")
        void addPerson(@Path(value = "id") String id, @Body TypedInput body, Callback<Response> callback);
        @GET("/s40/project/_search")
        void getAllProjects(Callback<Response> callback);
    }

}
