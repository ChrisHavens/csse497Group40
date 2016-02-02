package WrappingServer;

import java.io.IOException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import server_models.LoginRequest;
import server_models.Request;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/api/login")
public class LoginService {

  // This method is called if TEXT_PLAIN is request
	
//  @GET @Path("{username}")
//  public String get(@PathParam("username") String id){
//		ClientConfig config = new ClientConfig();
//
//	    Client client = ClientBuilder.newClient(config);
//
//	    WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200/login/user").build());
//		Response response=null;
//		response = target.path("/"+id).request().get(Response.class);
//		try{
//			
//		    return response.readEntity(String.class);
//		    }catch (NullPointerException e){
//		    	e.printStackTrace();
//		    }
//		    return Response.status(Status.BAD_REQUEST).build().readEntity(String.class);
//	}
  @GET @Path("{username}")
  public String get(@PathParam("username") String id){
	  if (id.equals("admin")){
		  String jsonDetails=String.format("{\"found\": %s,"
          		+ "\"personId\": \"%s\""
          		+"}", true+"", "-1");
          return jsonDetails;
	  }
		ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200/login/user").build());
		Response response=null;
		response = target.path("/"+id).request().get(Response.class);
		try{
			
		    String responseJson= response.readEntity(String.class);
		    ObjectMapper mapper=new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference=
                    new TypeReference<HashMap<String, Object>>() {
                    };
            
                HashMap<String, Object> map = mapper.readValue(responseJson, typeReference);
                boolean found = (boolean) map.get("found");
                if (found) {
                    HashMap<String, Object> source=(HashMap)map.get("_source");
                    String personID=(String) source.get("personId");
                    String jsonDetails=String.format("{\"found\": %s,"
                    		+ "\"personId\": \"%s\""
                    		+"}", found+"", personID);
                    return jsonDetails;
                }
                else{
                	 String jsonDetails=String.format("{\"found\": %s"
                     		+"}", found+"");
                     return jsonDetails;
                }
           
		    
		    }catch (Exception e){
		    	e.printStackTrace();
		    	
		    }
		    return Response.status(Status.BAD_REQUEST).build().readEntity(String.class);
	}
  

} 
