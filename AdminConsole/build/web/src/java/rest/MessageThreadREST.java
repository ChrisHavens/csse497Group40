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
import objects.MessageThread;
import objects.MessageThread.Message;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author daveyle
 */
public class MessageThreadREST {
    public MessageThread getSingleMessageThread(String messagethreadID){
        
            ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/thread").build());
		Response newResponse=null;
		newResponse = target.path("/"+messagethreadID).request().get(Response.class);
                return getFormattedDataSingle(newResponse);
       
    }
    public List<MessageThread> getAllMessageThreads(){
       
            ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/thread").build());
		Response newResponse=null;
		newResponse = target.queryParam("show_hidden", true).queryParam("time", null).request().get(Response.class);
                return getFormattedDataMany(newResponse);
       
    }

     public List<MessageThread> getAllMessageThreadsByGroup(String groupID){
        ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/thread").build());
		Response newResponse=null;
		newResponse = target.queryParam("groupID", groupID).queryParam("show_hidden", true).queryParam("time", null).request().get(Response.class);
                return getFormattedDataMany(newResponse);
    }
    
    private MessageThread getFormattedDataSingle(Response response){
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
                     return MessageThread.parseThread(map);
                  
                }
                else{
                    return null;
                }
            }catch(Exception e){
                return null;
            }	    
    }
   
    private List<MessageThread> getFormattedDataMany(Response response){
        List<MessageThread> messagethreads = new ArrayList<MessageThread>();
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
                    MessageThread p =MessageThread.parseThread(map);
                    messagethreads.add(p);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return messagethreads;
    }
    public List<MessageThread.Message> getMessages(String messageThreadID){
         ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/thread").build());
		Response newResponse=null;
		newResponse = target.path("/"+messageThreadID+"/messages").request().get(Response.class);
                return getFormattedDataMessages(newResponse);
    }
    private List<MessageThread.Message> getFormattedDataMessages(Response response){
            List<MessageThread.Message> messages = new ArrayList<MessageThread.Message>();
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
                    Message p =Message.parseMessage(map);
                    messages.add(p);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return messages;
    }
}
