package WrappingServer;

import java.util.Calendar;

import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;


import org.glassfish.jersey.client.ClientConfig;

import server_models.Request;

@Path("/api/person")
public class PersonREST {
	//TODOs: Replace String returns with Response returns.
	//Adjust PUT to take id from server not user
	
	final String notHiddenFilter="{\"missing\": {\"field\": \"dateArchived\"}},";
	ClientConfig config = new ClientConfig();

    Client client = ClientBuilder.newClient(config);

    WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200/s40/person").build());
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getList(
			@DefaultValue("false") @QueryParam("show_hidden") boolean showHidden, 
			@DefaultValue("null") @QueryParam("projectID") String projectID,
			@DefaultValue("null") @QueryParam("groupID") String groupID){
		
	    Response response=null;
		
		if (projectID.equals("null") && groupID.equals("null") && showHidden){
			
			response = target.path("/_search").request().get(Response.class);
		}
		StringBuilder sb= new StringBuilder();
		
        
            sb.append("{\"query\": " +
                    "{\"filtered\": {" +
                    "\"filter\": {\"bool\": { \"must\": [");
            if (showHidden){
            	sb.append(notHiddenFilter);
            }
            sb.append("{\"term\": { \"parentIDs.parentID\": \"");
            if (!groupID.equals("null")){
            	sb.append(""+groupID);
            }
            else if (!projectID.equals("null")){
            	sb.append(""+projectID);
            }
            sb.append("\"}}]}}}}}");
        
		
			
			response = target.path("/_search").request().post(Entity.entity(sb.toString(), MediaType.APPLICATION_JSON_TYPE));
		
		
		 try{
			    return response.readEntity(String.class);
			    }catch (NullPointerException e){
			    	e.printStackTrace();
			    }
		 return Response.status(Status.BAD_REQUEST).build().readEntity(String.class);
	}
	@GET @Path("{id}")
	public String get(@PathParam("id") String id){
		Response response=null;
		response = target.path("/"+id).request().get(Response.class);
		try{
		    return response.readEntity(String.class);
		    }catch (NullPointerException e){
		    	e.printStackTrace();
		    }
		    return Response.status(Status.BAD_REQUEST).build().readEntity(String.class);
	}
	@POST @Path("{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updatePerson(@PathParam("id") String id, String json){
		Response response = target.path("/"+id+"/_update").request().post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
		return response.readEntity(String.class);
	}
	@POST @Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updatePerson(String json){
		Response response = target.path("/_search").request().post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
		return response.readEntity(String.class);
	}
	
	
	@PUT @Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addPerson(@PathParam("id") String id, String json){
		Response response = target.path("/"+id).request().put(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
		return response.readEntity(String.class);
	}
	@POST @Path("{id}/hide")
	@Produces(MediaType.APPLICATION_JSON)
	public String hidePerson(@PathParam("id") String id){
        Calendar calendar=Calendar.getInstance();
        String date=String.format("%04d-%02d-%02d", calendar.get
                (Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        StringBuilder sb=new StringBuilder();
        sb.append("{\"doc\":{\"dateArchived\": \""+date+"\"}}");
        
        return updatePerson(id, sb.toString());
	}
	@POST @Path("{id}/show")
	@Produces(MediaType.APPLICATION_JSON)
	public String showPerson(@PathParam("id") String id){
		StringBuilder sb=new StringBuilder();
        sb.append("{\"doc\":{\"dateArchived\": null}}");
        
        return updatePerson(id, sb.toString());
	}
	
	

}
