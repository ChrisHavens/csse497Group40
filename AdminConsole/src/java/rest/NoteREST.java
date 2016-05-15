/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import objects.Note;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author daveyle
 */
public class NoteREST {
    public Note getSingleNote(String noteID){
        
            ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/note").build());
		Response newResponse=null;
		newResponse = target.path("/"+noteID).request().get(Response.class);
                return getFormattedDataSingle(newResponse);
       
    }
    public List<Note> getAllNotes(){
       
            ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/note").build());
		Response newResponse=null;
		newResponse = target.queryParam("show_hidden", true).queryParam("time", null).request().get(Response.class);
                return getFormattedDataMany(newResponse);
       
    }

     public List<Note> getAllNotesByGroup(String groupID){
        ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/note").build());
		Response newResponse=null;
		newResponse = target.queryParam("groupID", groupID).queryParam("show_hidden", true).queryParam("time", null).request().get(Response.class);
                return getFormattedDataMany(newResponse);
    }
    
    private Note getFormattedDataSingle(Response response){
        String responseJson= response.readEntity(String.class);
        System.out.println(responseJson);
        ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            StringBuilder sb=new StringBuilder();
            try{
                HashMap<String, Object> map = mapper.readValue(responseJson, typeReference);
                if (Boolean.parseBoolean(map.get("found").toString())){
                    return Note.parseNote(map);
//               
                }
                else{
                    return null;
                }
            }catch(Exception e){
                return null;
            }	    
    }
    private List<Note> getFormattedDataMany(Response response){
        List<Note> notes = new ArrayList<Note>();
        String responseJson= response.readEntity(String.class);
        System.out.println(responseJson);
        ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(responseJson, typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    Note p =Note.parseNote(map);
                    notes.add(p);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return notes;
    }
}
