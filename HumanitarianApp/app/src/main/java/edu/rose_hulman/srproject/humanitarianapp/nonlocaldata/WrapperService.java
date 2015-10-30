package edu.rose_hulman.srproject.humanitarianapp.nonlocaldata;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by daveyle on 10/27/2015.
 */
public interface WrapperService {
    @POST("/api")
    @Headers("Accept: application/json")
    void add(@Body TypedInput body, Callback<Response> callback);

    @POST("/api")
    @Headers("Accept: application/json")
    void update(@Body TypedInput body, Callback<Response> callback);

    @POST("/api")
    @Headers("Accept: application/json")
    void search(@Body TypedInput body, Callback<Response> callback);

    @POST("/api")
    @Headers("Accept: application/json")
    void get(@Body TypedInput body, Callback<Response> callback);
}
