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

    public void addNewPerson(Person person, Callback<Response> callback){
        service.addNewPerson(person.getID() + "", new TypedJsonString(person.toJSON()), callback);
    }
    public void addNewThread(MessageThread thread, Callback<Response> callback){
        service.addNewThread(thread.getID() + "", new TypedJsonString(thread.toJSON()), callback);
    }
    public void addNewMessage(MessageThread thread, MessageThread.Message message,  Callback<Response> callback){
        service.addNewMessage(thread.getID()+"", message.getItemID()+"",new TypedJsonString(message.toMessageSendString()), callback);
    }
    public void addNewProject(Project project, Callback<Response> callback){
        service.addNewProject(project.getId() + "", new TypedJsonString(project.toJSON()), callback);
    }
    public void addNewGroup(Group group, Callback<Response> callback){
        service.addNewGroup(group.getId() + "", new TypedJsonString(group.toJSON()), callback);
    }
    public void addNewLocation(Location location, Callback<Response> callback){
        service.addNewLocation(location.getID() + "", new TypedJsonString(location.toJSON()), callback);
    }
    public void addNewNote(Note note, Callback<Response> callback){
        service.addNewNote(note.getID() + "", new TypedJsonString(note.toJSON()), callback);
    }
    public void addNewShipment(Shipment shipment, Callback<Response> callback){
        service.addNewShipment(shipment.getID() + "", new TypedJsonString(shipment.toJSON()), callback);
    }
    public void addNewChecklist(Checklist checklist, Callback<Response> callback){
        service.addNewChecklist(checklist.getID() + "", new TypedJsonString(checklist.toJSON()), callback);

    }

    /*
    Update requests --Request to database server has body and is of type POST!
     */

    public void updateProject(long projectID, String json, Callback<Response> callback){
        service.updateProject(projectID + "", new TypedJsonString(json), callback);

    }
    public void updateGroup(long groupID, String json, Callback<Response> callback){
        service.updateGroup(groupID + "", new TypedJsonString(json), callback);

    }

    public void updatePerson(long personId, String json, Callback<Response> callback){
        service.updatePerson(personId + "", new TypedJsonString(json), callback);


    }
    public void updateLocation(long locationId, String json, Callback<Response> callback){
        service.updateLocation(locationId + "", new TypedJsonString(json), callback);

    }
    public void updateNote(double id, String title, String body, Callback<Response> callback){
        StringBuilder sb=new StringBuilder();
        sb.append("{\"doc\":{\"contents\": \""+body+"\", \"title\": \""+title+"\"}}");
        Log.w("Note:", id+" "+sb.toString());

        service.updateNote(id + "", new TypedJsonString(sb.toString()), callback);
    }

    public void getMessagesList(String id, int start, int size, String time, Callback<Response>callback){
        service.getMessagesList(id, start+"", size+"", time, callback);
    }
    public void getMessagesList(String id, int start, int size, Callback<Response>callback){
        service.getMessagesList(id, start+"", size+"", null, callback);
    }
    public void getMessagesList(String id, String time, Callback<Response>callback){
        service.getMessagesList(id, null, null, time, callback);
    }
    public void getMessagesList(String id, Callback<Response>callback){
        service.getMessagesList(id, null, null, null, callback);
    }
    public void getMessagesListStart(String id, int start, String time, Callback<Response>callback){
        service.getMessagesList(id, start+"", null, time, callback);
    }
    public void getMessagesListStart(String id, int start, Callback<Response>callback){
        service.getMessagesList(id, start+"", null, null, callback);
    }
    public void getMessagesListSize(String id, int size, String time, Callback<Response>callback){
        service.getMessagesList(id, null, size+"", time, callback);
    }
    public void getMessagesListSize(String id, int size, Callback<Response>callback){
        service.getMessagesList(id, null, size+"", null, callback);
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
    public void deleteProject(Project p, Callback<Response> callback){
        service.deleteProject(p.getId() + "", callback);
    }
    public void deleteGroup(Group g, Callback<Response> callback){
        service.deleteGroup(g.getId() + "", callback);
    }
    public void deletePerson(Person p, Callback<Response> callback){
        service.deletePerson(p.getID() + "", callback);
    }
    public void deleteLocation(Location l, Callback<Response> callback){
        service.deleteLocation(l.getID() + "", callback);
    }
    public void deleteNote(Note n, Callback<Response> callback){
        service.deleteNote(n.getID() + "", callback);
    }
    public void deleteChecklist(Checklist checklist, Callback<Response> callback){
        service.deleteChecklist(checklist.getID() + "", callback);
    }
    public void deleteThread(MessageThread thread, Callback<Response> callback){
        service.deleteThread(thread.getID() + "", callback);
    }
    public void deleteShipment(Shipment s, Callback<Response> callback){
        service.deleteChecklist(s.getID() + "", callback);
    }
}
