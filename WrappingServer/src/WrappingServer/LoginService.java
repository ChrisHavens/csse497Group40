package WrappingServer;

import java.io.IOException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
  @GET @Path("{id}")
  public Response get(@PathParam("id") String id){
	  if (id.equals("admin")){
		  String jsonDetails=String.format("{\"found\": %s,"
          		+ "\"personId\": \"%s\""
          		+"}", true+"", "-1");
          return Response.status(Status.OK).entity(jsonDetails).build();
	  }
		ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/user").build());
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
                    return Response.status(Status.OK).entity(jsonDetails).build();
                }
                else{
                	 String jsonDetails=String.format("{\"found\": %s"
                     		+"}", found+"");
                     return Response.status(CustomStatus.NO_SUCH_USER).entity(jsonDetails).build();
                }
           
		    
		    }catch (Exception e){
		    	e.printStackTrace();
		    	
		    }
		    return Response.status(Status.BAD_REQUEST).build();
	}
  
  @PUT @Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewUser(String userIdJson){
		try{
			ObjectMapper mapper=new ObjectMapper();
			TypeReference<HashMap<String, Object>> typeReference=
					new TypeReference<HashMap<String, Object>>() {
			};

			HashMap<String, Object> map1 = mapper.readValue(userIdJson, typeReference);
			String userId= (String) map1.get("id");
				if (userId!=null){
					Integer object=3000;
					ClientConfig config1 = new ClientConfig();
					Client client1 = ClientBuilder.newClient(config1);

					WebTarget target1 = client1.target(UriBuilder.fromUri(Utils.databaseURL+"/info").build());
					Response response=null;
					response = target1.path("/info").request().get(Response.class);
					

						String responseJson= response.readEntity(String.class);
						//					    return responseJson;
//						ObjectMapper mapper=new ObjectMapper();
//						TypeReference<HashMap<String, Object>> typeReference=
//								new TypeReference<HashMap<String, Object>>() {
//						};

						HashMap<String, Object> map = mapper.readValue(responseJson, typeReference);
						//			                
						boolean found = (boolean) map.get("found");
						if (found) {
							HashMap<String, Object> source=(HashMap)map.get("_source");
							object= (Integer.parseInt((String) source.get("lastUsedPersonID")))+1;
							WebTarget target2 = client1.target(UriBuilder.fromUri(Utils.databaseURL+"/info").build());
							String doc="{\"doc\":{\"lastUsedPersonID\":\""+object+"\"}}";
							Response response2=target2.path("/info/_update").request().post(Entity.entity(doc, MediaType.APPLICATION_JSON_TYPE));
							//			                    return (String)map.get("_source");
						}


					

					WebTarget target = client1.target(UriBuilder.fromUri(Utils.mainURL+"/user").build());
					StringBuilder sb=new StringBuilder();
					sb.append("{\"personId\":\"");
					sb.append(object);
					sb.append("\"}");
					response = target.path("/"+userId).request().put(Entity.entity(sb.toString(), MediaType.APPLICATION_JSON_TYPE));
					return Response.status(Status.OK).entity(sb.toString()).build();



				}
				return Response.status(Status.BAD_GATEWAY).build();
			
		}catch(Exception e){
			return Response.status(Status.BAD_REQUEST).entity("{\"error\":\""+e.getMessage()+"\"}").build();
		}
		
		
	}
} 
