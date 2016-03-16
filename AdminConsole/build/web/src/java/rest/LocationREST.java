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
import objects.Location;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author daveyle
 */
public class LocationREST {
    public Location getSingleLocation(String locationID){
        
            ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/location").build());
		Response newResponse=null;
		newResponse = target.path("/"+locationID).request().get(Response.class);
                return getFormattedDataSingle(newResponse);
       
    }
    public List<Location> getAllLocations(){
       
            ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/location").build());
		Response newResponse=null;
		newResponse = target.queryParam("show_hidden", true).queryParam("time", null).request().get(Response.class);
                return getFormattedDataMany(newResponse);
       
    }
    public List<Location> getAllLocationsByProject(String projectID){
        ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/location").build());
		Response newResponse=null;
		newResponse = target.queryParam("projectID", projectID).queryParam("show_hidden", true).queryParam("time", null).request().get(Response.class);
                return getFormattedDataMany(newResponse);
    }
     public List<Location> getAllLocationsByGroup(String groupID){
        ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/location").build());
		Response newResponse=null;
		newResponse = target.queryParam("groupID", groupID).queryParam("show_hidden", true).queryParam("time", null).request().get(Response.class);
                return getFormattedDataMany(newResponse);
    }
    
    private Location getFormattedDataSingle(Response response){
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
                    return Location.parseLocation(map);
//               
                }
                else{
                    return null;
                }
            }catch(Exception e){
                return null;
            }	    
    }
    private List<Location> getFormattedDataMany(Response response){
        List<Location> locations = new ArrayList<Location>();
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
                    Location p =Location.parseLocation(map);
                    locations.add(p);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return locations;
    }
}
