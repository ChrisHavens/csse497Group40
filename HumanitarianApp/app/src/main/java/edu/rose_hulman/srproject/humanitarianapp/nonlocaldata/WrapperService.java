package edu.rose_hulman.srproject.humanitarianapp.nonlocaldata;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

/**
 * Created by daveyle on 10/27/2015.
 */
public interface WrapperService {
    @POST("/api")
    @Headers("Accept: application/json")
    void general(@Body TypedInput body, Callback<Response> callback);
//    @POST("/api")
//    @Headers("Accept: application/json")
//    void add(@Body TypedInput body, Callback<Response> callback);
//
//    @POST("/api")
//    @Headers("Accept: application/json")
//    void update(@Body TypedInput body, Callback<Response> callback);
//
//    @POST("/api")
//    @Headers("Accept: application/json")
//    void search(@Body TypedInput body, Callback<Response> callback);
//
//    @POST("/api")
//    @Headers("Accept: application/json")
//    void get(@Body TypedInput body, Callback<Response> callback);

    /*
    Projects
     */
    @GET("/api/project")
    void getProjectList(@Query("userID") String userID,
                        @Query("show_hidden") boolean showHidden,
                        Callback<Response> callback);
    @GET("/api/project")
    void getProjectList(@Query("userID") String userID,
                        Callback<Response> callback);

    @GET("/api/project")
    void getProjectList(@Query("show_hidden") boolean showHidden,
                        Callback<Response> callback);

    @GET("/api/project")
    void getProjectList(Callback<Response> callback);

    @GET("/api/project/{id}")
    void getProject(@Path("id") String projectID,
                        Callback<Response> callback);

    @PUT("/api/project/{id}")
    @Headers("Accept: application/json")
    void addNewProject(@Path("id") String projectID,
                       @Body TypedInput body,
                       Callback<Response> callback);

    @POST("/api/project/{id}/update")
    @Headers("Accept: application/json")
    void updateProject(@Path("id") String projectID,
                       @Body TypedInput body,
                       Callback<Response> callback);

    @POST("/api/project/search")
    @Headers("Accept: application/json")
    void searchProjects(@Body TypedInput body,
                       Callback<Response> callback);

    @POST("/api/project/{id}/visibility")
    @Headers("Accept: application/json")
    void changeVisibilityProject(@Path("id") String projectID,
                       @Query("status") String status,
                       Callback<Response> callback);

    /*
    Groups
     */
    @GET("/api/group")
    void getGroupList(@Query("userID") String userID,
                        @Query("show_hidden") boolean showHidden,
                        @Query("projectID") String projectID,
                        Callback<Response> callback);
    @GET("/api/group")
    void getGroupList(@Query("userID") String userID,
                      @Query("projectID") String projectID,
                        Callback<Response> callback);

    @GET("/api/group")
    void getGroupList(@Query("show_hidden") boolean showHidden,
                      @Query("projectID") String projectID,
                        Callback<Response> callback);

    @GET("/api/group")
    void getGroupList(@Query("userID") String userID,
                      @Query("show_hidden") boolean showHidden,
                      Callback<Response> callback);

    @GET("/api/group")
    void getGroupList(@Query("userID") String userID,
                      Callback<Response> callback);
    @GET("/api/group")
    void getGroupList(@Query("show_hidden") boolean showHidden,
                      Callback<Response> callback);
    @GET("/api/group")
    void getGroupListProjectID(@Query("projectID") String projectID,
                      Callback<Response> callback);

    @GET("/api/group")
    void getGroupList(Callback<Response> callback);

    @GET("/api/group/{id}")
    void getGroup(@Path("id") String id,
                    Callback<Response> callback);

    @PUT("/api/group/{id}")
    @Headers("Accept: application/json")
    void addNewGroup(@Path("id") String id,
                       @Body TypedInput body,
                       Callback<Response> callback);

    @POST("/api/group/{id}/update")
    @Headers("Accept: application/json")
    void updateGroup(@Path("id") String id,
                       @Body TypedInput body,
                       Callback<Response> callback);

    @POST("/api/group/search")
    @Headers("Accept: application/json")
    void searchGroups(@Body TypedInput body,
                        Callback<Response> callback);

    @POST("/api/group/{id}/visibility")
    @Headers("Accept: application/json")
    void changeVisibilityGroup(@Path("id") String id,
                                 @Query("status") String status,
                                 Callback<Response> callback);
    /*
   Persons
    */
    @GET("/api/person")
    void getPersonList(@Query("show_hidden") boolean showHidden,
                       @Query("groupID") String groupID,
                      @Query("projectID") String projectID,
                       Callback<Response> callback);
    @GET("/api/person")
    void getPersonListByGroupID( @Query("show_hidden") boolean showHidden,
                       @Query("groupID") String groupID,
                       //@Query("projectID") String projectID,
                       Callback<Response> callback);
    @GET("/api/person")
    void getPersonListByProjectID(@Query("show_hidden") boolean showHidden,
                       //@Query("groupID") String groupID,
                       @Query("projectID") String projectID,
                       Callback<Response> callback);
    @GET("/api/person")
    void getPersonList(//@Query("show_hidden") boolean showHidden,
                       @Query("groupID") String groupID,
                       @Query("projectID") String projectID,
                       Callback<Response> callback);

    @GET("/api/person")
    void getPersonList( @Query("show_hidden") boolean showHidden,
                       //@Query("groupID") String groupID,
                       //@Query("projectID") String projectID,
                       Callback<Response> callback);





    @GET("/api/person")
    void getPersonListByGroupID(@Query("groupID") String groupID,
                       Callback<Response> callback);

    @GET("/api/person")
    void getPersonListByProjectID(@Query("projectID") String projectID,
                       Callback<Response> callback);
    @GET("/api/person")
    void getPersonList(Callback<Response> callback);



    @GET("/api/person/{id}")
    void getPerson(@Path("id") String id,
                  Callback<Response> callback);

    @PUT("/api/person/{id}")
    @Headers("Accept: application/json")
    void addNewPerson(@Path("id") String id,
                     @Body TypedInput body,
                     Callback<Response> callback);

    @POST("/api/person/{id}/update")
    @Headers("Accept: application/json")
    void updatePerson(@Path("id") String id,
                     @Body TypedInput body,
                     Callback<Response> callback);

    @POST("/api/person/search")
    @Headers("Accept: application/json")
    void searchPersons(@Body TypedInput body,
                      Callback<Response> callback);

    @POST("/api/person/{id}/visibility")
    @Headers("Accept: application/json")
    void changeVisibilityPerson(@Path("id") String id,
                               @Query("status") String status,
                               Callback<Response> callback);
    /*
  Locations
   */
    @GET("/api/location")
    void getLocationList(@Query("show_hidden") boolean showHidden,
                       @Query("groupID") String groupID,
                       @Query("projectID") String projectID,
                       Callback<Response> callback);
    @GET("/api/location")
    void getLocationListByGroupID(@Query("show_hidden") boolean showHidden,
                                @Query("groupID") String groupID,
                                //@Query("projectID") String projectID,
                                Callback<Response> callback);
    @GET("/api/location")
    void getLocationListByProjectID(@Query("show_hidden") boolean showHidden,
                                  //@Query("groupID") String groupID,
                                  @Query("projectID") String projectID,
                                  Callback<Response> callback);
    @GET("/api/location")
    void getLocationList(@Query("groupID") String groupID,
                       @Query("projectID") String projectID,
                       Callback<Response> callback);

    @GET("/api/location")
    void getLocationList(@Query("show_hidden") boolean showHidden,
                       //@Query("groupID") String groupID,
                       //@Query("projectID") String projectID,
                       Callback<Response> callback);
    @GET("/api/location")
    void getLocationListByGroupID(
                                //@Query("show_hidden") boolean showHidden,
                                @Query("groupID") String groupID,
                                //@Query("projectID") String projectID,
                                Callback<Response> callback);

    @GET("/api/location")
    void getLocationListByProjectID(
                                  //@Query("show_hidden") boolean showHidden,
                                  //@Query("groupID") String groupID,
                                  @Query("projectID") String projectID,
                                  Callback<Response> callback);

    @GET("/api/location")
    void getLocationList(Callback<Response> callback);



    @GET("/api/location/{id}")
    void getLocation(@Path("id") String id,
                   Callback<Response> callback);

    @PUT("/api/location/{id}")
    @Headers("Accept: application/json")
    void addNewLocation(@Path("id") String id,
                      @Body TypedInput body,
                      Callback<Response> callback);

    @POST("/api/location/{id}/update")
    @Headers("Accept: application/json")
    void updateLocation(@Path("id") String id,
                      @Body TypedInput body,
                      Callback<Response> callback);

    @POST("/api/location/search")
    @Headers("Accept: application/json")
    void searchLocations(@Body TypedInput body,
                       Callback<Response> callback);

    @POST("/api/location/{id}/visibility")
    @Headers("Accept: application/json")
    void changeVisibilityLocation(@Path("id") String id,
                                @Query("status") String status,
                                Callback<Response> callback);
    /*
   Notes
    */
    @GET("/api/note")
    void getNoteList(@Query("show_hidden") boolean showHidden,
                      @Query("groupID") String groupID,
                      Callback<Response> callback);


    @GET("/api/note")
    void getNoteList(@Query("show_hidden") boolean showHidden,
                      Callback<Response> callback);
    @GET("/api/note")
    void getNoteList(@Query("groupID") String groupID,
                               Callback<Response> callback);

    @GET("/api/note")
    void getNoteList(Callback<Response> callback);

    @GET("/api/note/{id}")
    void getNote(@Path("id") String id,
                  Callback<Response> callback);

    @PUT("/api/note/{id}")
    @Headers("Accept: application/json")
    void addNewNote(@Path("id") String id,
                     @Body TypedInput body,
                     Callback<Response> callback);

    @POST("/api/note/{id}/update")
    @Headers("Accept: application/json")
    void updateNote(@Path("id") String id,
                     @Body TypedInput body,
                     Callback<Response> callback);

    @POST("/api/note/search")
    @Headers("Accept: application/json")
    void searchNotes(@Body TypedInput body,
                      Callback<Response> callback);

    @POST("/api/note/{id}/visibility")
    @Headers("Accept: application/json")
    void changeVisibilityNote(@Path("id") String id,
                               @Query("status") String status,
                               Callback<Response> callback);

    /*
       Checklists
        */
    @GET("/api/checklist")
    void getChecklistList(@Query("show_hidden") boolean showHidden,
                     @Query("groupID") String groupID,
                     Callback<Response> callback);


    @GET("/api/checklist")
    void getChecklistList(@Query("show_hidden") boolean showHidden,
                     Callback<Response> callback);
    @GET("/api/checklist")
    void getChecklistList(@Query("groupID") String groupID,
                     Callback<Response> callback);

    @GET("/api/checklist")
    void getChecklistList(Callback<Response> callback);

    @GET("/api/checklist/{id}")
    void getChecklist(@Path("id") String id,
                 Callback<Response> callback);

    @PUT("/api/checklist/{id}")
    @Headers("Accept: application/json")
    void addNewChecklist(@Path("id") String id,
                    @Body TypedInput body,
                    Callback<Response> callback);

    @POST("/api/checklist/{id}/update")
    @Headers("Accept: application/json")
    void updateChecklist(@Path("id") String id,
                    @Body TypedInput body,
                    Callback<Response> callback);

    @POST("/api/checklist/search")
    @Headers("Accept: application/json")
    void searchChecklists(@Body TypedInput body,
                     Callback<Response> callback);

    @POST("/api/checklist/{id}/visibility")
    @Headers("Accept: application/json")
    void changeVisibilityChecklist(@Path("id") String id,
                              @Query("status") String status,
                              Callback<Response> callback);


    /*
   Threads
    */
    @GET("/api/thread")
    void getThreadList(@Query("show_hidden") boolean showHidden,
                          @Query("groupID") String groupID,
                          Callback<Response> callback);


    @GET("/api/thread")
    void getThreadList(@Query("show_hidden") boolean showHidden,
                          Callback<Response> callback);
    @GET("/api/thread")
    void getThreadList(@Query("groupID") String groupID,
                          Callback<Response> callback);

    @GET("/api/thread")
    void getThreadList(Callback<Response> callback);

    @GET("/api/thread/{id}")
    void getThread(@Path("id") String id,
                      Callback<Response> callback);

    @PUT("/api/thread/{id}")
    @Headers("Accept: application/json")
    void addNewThread(@Path("id") String id,
                         @Body TypedInput body,
                         Callback<Response> callback);

    @POST("/api/thread/{id}/update")
    @Headers("Accept: application/json")
    void updateThread(@Path("id") String id,
                         @Body TypedInput body,
                         Callback<Response> callback);

    @POST("/api/thread/search")
    @Headers("Accept: application/json")
    void searchThreads(@Body TypedInput body,
                          Callback<Response> callback);

    @POST("/api/thread/{id}/visibility")
    @Headers("Accept: application/json")
    void changeVisibilityThread(@Path("id") String id,
                                   @Query("status") String status,
                                   Callback<Response> callback);

    /*
   Shipments
    */
    @GET("/api/shipment")
    void getShipmentList(@Query("show_hidden") boolean showHidden,
                     @Query("groupID") String groupID,
                     Callback<Response> callback);


    @GET("/api/shipment")
    void getShipmentList(@Query("show_hidden") boolean showHidden,
                     Callback<Response> callback);
    @GET("/api/shipment")
    void getShipmentList(@Query("groupID") String groupID,
                     Callback<Response> callback);

    @GET("/api/shipment")
    void getShipmentList(Callback<Response> callback);

    @GET("/api/shipment/{id}")
    void getShipment(@Path("id") String id,
                 Callback<Response> callback);

    @PUT("/api/shipment/{id}")
    @Headers("Accept: application/json")
    void addNewShipment(@Path("id") String id,
                    @Body TypedInput body,
                    Callback<Response> callback);

    @POST("/api/shipment/{id}/update")
    @Headers("Accept: application/json")
    void updateShipment(@Path("id") String id,
                    @Body TypedInput body,
                    Callback<Response> callback);

    @POST("/api/shipment/search")
    @Headers("Accept: application/json")
    void searchShipments(@Body TypedInput body,
                     Callback<Response> callback);

    @POST("/api/shipment/{id}/visibility")
    @Headers("Accept: application/json")
    void changeVisibilityShipment(@Path("id") String id,
                              @Query("status") String status,
                              Callback<Response> callback);

    // Deletion - testing use only
    @DELETE("/api/project/{id}")
    @Headers("Accept: application/json")
    void deleteProject(@Path("id") String id,
                       Callback<Response> callback);
    @DELETE("/api/group/{id}")
    @Headers("Accept: application/json")
    void deleteGroup(@Path("id") String id,
                       Callback<Response> callback);
    @DELETE("/api/person/{id}")
    @Headers("Accept: application/json")
    void deletePerson(@Path("id") String id,
                        Callback<Response> callback);
    @DELETE("/api/location/{id}")
    @Headers("Accept: application/json")
    void deleteLocation(@Path("id") String id,
                     Callback<Response> callback);
    @DELETE("/api/note/{id}")
    @Headers("Accept: application/json")
    void deleteNote(@Path("id") String id,
                    Callback<Response> callback);
    @DELETE("/api/checklist/{id}")
    @Headers("Accept: application/json")
    void deleteChecklist(@Path("id") String id,
                                  Callback<Response> callback);
    @DELETE("/api/thread/{id}")
    @Headers("Accept: application/json")
    void deleteThread(@Path("id") String id,
                         Callback<Response> callback);

    @DELETE("/api/shipment/{id}")
    @Headers("Accept: application/json")
    void deleteShipment(@Path("id") String id,
                         Callback<Response> callback);


}
