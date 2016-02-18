package edu.rose_hulman.srproject.humanitarianapp.nonlocaldata;


import android.util.Log;


import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import retrofit.Callback;

import retrofit.RestAdapter;

import retrofit.client.Response;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedString;

/**
 * Created by daveyle on 10/13/2015.
 */
public class NonLocalDataService {

    RestAdapter adapter = new RestAdapter.Builder()
            .setEndpoint("http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest")
            .build();
    public WrapperService service=adapter.create(WrapperService.class);
    final String notHiddenFilter="{\"missing\": {\"field\": \"dateArchived\"}},";

    /*
    Add requests --Request to database server has body and is of type PUT!
     */

    public void addNewPerson(Person person,  String changerID,Callback<Response> callback){
        service.addNewPerson(person.getID() + "",changerID, new TypedJsonString(person.toJSON()), callback);
    }
    public void addPersonToProject(String personID, String projectID, String changerID,Callback<Response> callback){
        String json="{\"projectId\": \""+projectID+"\"}";
        service.addPersonToProject(personID, changerID,new TypedJsonString(json), callback);
    }
    public void addPersonToGroup(String personID, String groupID, String changerID,Callback<Response> callback){
        String json="{\"groupId\": \""+groupID+"\"}";
        service.addPersonToGroup(personID, changerID,new TypedJsonString(json), callback);
    }
    public void addNewThread(MessageThread thread, String changerID,Callback<Response> callback){
        service.addNewThread(thread.getID() + "", changerID,new TypedJsonString(thread.toJSON()), callback);
    }
    public void addNewMessage(String parentID, MessageThread.Message message,  String changerID,Callback<Response> callback){
        service.addNewMessage(parentID, message.getItemID() + "", changerID,new TypedJsonString(message.toMessageSendString()), callback);
    }
    public void addNewProject(Project project, String changerID,Callback<Response> callback){
        service.addNewProject(project.getId() + "", changerID,new TypedJsonString(project.toJSON()), callback);
    }
    public void addNewGroup(Group group, String changerID,Callback<Response> callback){
        service.addNewGroup(group.getId() + "", changerID,new TypedJsonString(group.toJSON()), callback);
    }
    public void addNewLocation(Location location, String changerID,Callback<Response> callback){
        service.addNewLocation(location.getID() + "", changerID,new TypedJsonString(location.toJSON()), callback);
    }
    public void addNewNote(Note note, String changerID,Callback<Response> callback){
        service.addNewNote(note.getID() + "", changerID,new TypedJsonString(note.toJSON()), callback);
    }
    public void addNewShipment(Shipment shipment, String changerID,Callback<Response> callback){
        service.addNewShipment(shipment.getID() + "",changerID, new TypedJsonString(shipment.toJSON()), callback);
    }
    public void addNewChecklist(Checklist checklist, String changerID,Callback<Response> callback){
        service.addNewChecklist(checklist.getID() + "", changerID,new TypedJsonString(checklist.toJSON()), callback);

    }

    /*
    Update requests --Request to database server has body and is of type POST!
     */

    public void updateProject(long projectID, String json, String changerID,Callback<Response> callback){
        service.updateProject(projectID + "", changerID,new TypedJsonString(json), callback);

    }
    public void updateGroup(long groupID, String json, String changerID,Callback<Response> callback){
        service.updateGroup(groupID + "", changerID,new TypedJsonString(json), callback);

    }

    public void updatePerson(long personId, String json, String changerID,Callback<Response> callback){
        service.updatePerson(personId + "", changerID,new TypedJsonString(json), callback);


    }
    public void updateLocation(long locationId, String json,String changerID, Callback<Response> callback){
        service.updateLocation(locationId + "",changerID, new TypedJsonString(json), callback);

    }
    public void updateNote(double id, String title, String body, String changerID,Callback<Response> callback){
        StringBuilder sb=new StringBuilder();
        sb.append("{\"doc\":{\"contents\": \""+body+"\", \"title\": \""+title+"\"}}");
        Log.w("Note:", id+" "+sb.toString());

        service.updateNote(id + "",changerID, new TypedJsonString(sb.toString()), callback);
    }

    public void getMessagesList(String id, int start, int size, String time, Callback<Response>callback){
        service.getMessagesList(id, start + "", size + "", time, callback);
    }
    public void getMessagesList(String id, int start, int size, Callback<Response>callback){
        service.getMessagesList(id, start + "", size + "", null, callback);
    }
    public void getMessagesList(String id, String time, Callback<Response>callback){
        service.getMessagesList(id, null, null, time, callback);
    }
    public void getMessagesList(String id, Callback<Response>callback){
        service.getMessagesList(id, null, null, null, callback);
    }
    public void getMessagesListStart(String id, int start, String time, Callback<Response>callback){
        service.getMessagesList(id, start + "", null, time, callback);
    }
    public void getMessagesListStart(String id, int start, Callback<Response>callback){
        service.getMessagesList(id, start + "", null, null, callback);
    }
    public void getMessagesListSize(String id, int size, String time, Callback<Response>callback){
        service.getMessagesList(id, null, size + "", time, callback);
    }
    public void getMessagesListSize(String id, int size, Callback<Response>callback){
        service.getMessagesList(id, null, size + "", null, callback);
    }




    public void getPersonLocationsList(String id, int start, int size, String time, Callback<Response>callback){
        service.getPersonLocationsList(id, start + "", size + "", time, callback);
    }
    public void getPersonLocationsList(String id, int start, int size, Callback<Response>callback){
        service.getPersonLocationsList(id, start + "", size + "", null, callback);
    }
    public void getPersonLocationsList(String id, String time, Callback<Response>callback){
        service.getPersonLocationsList(id, null, null, time, callback);
    }
    public void getPersonLocationsList(String id, Callback<Response>callback){
        service.getPersonLocationsList(id, null, null, null, callback);
    }
    public void getPersonLocationsListStart(String id, int start, String time, Callback<Response>callback){
        service.getPersonLocationsList(id, start + "", null, time, callback);
    }
    public void getPersonLocationsListStart(String id, int start, Callback<Response>callback){
        service.getPersonLocationsList(id, start + "", null, null, callback);
    }
    public void getPersonLocationsListSize(String id, int size, String time, Callback<Response>callback){
        service.getPersonLocationsList(id, null, size + "", time, callback);
    }
    public void getPersonLocationsListSize(String id, int size, Callback<Response>callback){
        service.getPersonLocationsList(id, null, size + "", null, callback);
    }

    public void checkIn(String personID, String locationID, String json, Callback<Response> callback){
        service.checkIn(personID, locationID, new TypedJsonString(json), callback);
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


    public class TypedJsonString extends TypedString {
        public TypedJsonString(String body) {
            super(body);
        }

        @Override public String mimeType() {
            return "application/json";
        }
    }

    // Delete requests- for testing use only
    public void deleteProject(Project p, String changerID,Callback<Response> callback){
        service.deleteProject(p.getId() + "", changerID,callback);
    }
    public void deleteGroup(Group g, String changerID,Callback<Response> callback){
        service.deleteGroup(g.getId() + "", changerID, callback);
    }
    public void deletePerson(Person p, String changerID,Callback<Response> callback){
        service.deletePerson(p.getID() + "", changerID, callback);
    }
    public void deleteLocation(Location l, String changerID,Callback<Response> callback){
        service.deleteLocation(l.getID() + "", changerID, callback);
    }
    public void deleteNote(Note n, String changerID,Callback<Response> callback){
        service.deleteNote(n.getID() + "", changerID, callback);
    }
    public void deleteChecklist(Checklist checklist, String changerID,Callback<Response> callback){
        service.deleteChecklist(checklist.getID() + "", changerID, callback);
    }
    public void deleteThread(MessageThread thread, String changerID,Callback<Response> callback){
        service.deleteThread(thread.getID() + "", changerID, callback);
    }
    public void deleteShipment(Shipment s, String changerID,Callback<Response> callback){
        service.deleteChecklist(s.getID() + "", changerID, callback);
    }

    public void verify(String username, Callback<Response> callback) {
        service.login(username, callback);
    }
    public void signUp(String token, Callback<Response> callback){
        NonLocalDataService.TypedJsonString json= new NonLocalDataService.TypedJsonString("{\"id\":\""+token+"\"}");
        service.addNewuser(json, callback);
    }


    public void changeVisibilityShipment(String id,
                                         String status,
                                         String changerID,
                                         Callback<Response> callback)
    {
        service.changeVisibilityShipment(id, status, changerID, callback);
    }
    public void changeVisibilityPerson(String id,
                                         String status,
                                       String changerID,
                                         Callback<Response> callback)
    {
        service.changeVisibilityPerson(id, status, changerID, callback);
    }
    public void changeVisibilityNote(String id,
                                         String status,
                                     String changerID,
                                         Callback<Response> callback)
    {
        service.changeVisibilityNote(id, status, changerID, callback);
    }
    public void changeVisibilityLocation(String id,
                                         String status,
                                         String changerID,
                                         Callback<Response> callback)
    {
        service.changeVisibilityLocation(id, status, changerID, callback);
    }
    public void changeVisibilityChecklist(String id,
                                         String status,
                                          String changerID,
                                         Callback<Response> callback)
    {
        service.changeVisibilityChecklist(id, status, changerID, callback);
    }
    public void changeVisibilityGroup(String id,
                                         String status,
                                      String changerID,
                                         Callback<Response> callback)
    {
        service.changeVisibilityGroup(id, status, changerID, callback);
    }
    public void changeVisibilityProject(String id,
                                         String status,
                                        String changerID,
                                         Callback<Response> callback)
    {
        service.changeVisibilityProject(id, status, changerID, callback);
    }
}
