/*
 * To change this license header, choose License Headers in Group Properties.
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import objects.Person;

import objects.Group;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author daveyle
 */
public class GroupREST {
    public Group getSingleGroup(String groupID){
        
            ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/group").build());
		Response newResponse=null;
		newResponse = target.path("/"+groupID).request().get(Response.class);
                return getFormattedDataSingle(newResponse);
       
    }
    public List<Group> getAllGroups(){
       
            ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/group").build());
		Response newResponse=null;
		newResponse = target.queryParam("show_hidden", true).queryParam("time", null).request().get(Response.class);
                return getFormattedDataMany(newResponse);
       
    }
    public List<Group> getAllGroupsByProject(String projectID){
        ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/group").build());
		Response newResponse=null;
		newResponse = target.queryParam("projectID", projectID).queryParam("show_hidden", true).queryParam("time", null).request().get(Response.class);
                return getFormattedDataMany(newResponse);
    }
    
    private Group getFormattedDataSingle(Response response){
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
                    return Group.parseGroup(map);
//               
                }
                else{
                    return null;
                }
            }catch(Exception e){
                return null;
            }	    
    }
    private List<Group> getFormattedDataMany(Response response){
        List<Group> groups = new ArrayList<Group>();
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
                    Group p =Group.parseGroup(map);
                    groups.add(p);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return groups;
    }
    
}
