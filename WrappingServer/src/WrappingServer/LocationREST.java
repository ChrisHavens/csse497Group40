package WrappingServer;
import java.util.Calendar;
import java.util.HashMap;

import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import server_models.Request;

@Path("/api/location")
public class LocationREST {
	//TODOs: Replace String returns with Response returns.
	//Adjust PUT to take id from server not user
	
	final String notHiddenFilter="{\"missing\": {\"field\": \"dateArchived\"}},";
	final String dateFilter="{\"range\": {\"timeModified\": {\"gte\": \"%s\"}}}";
	ClientConfig config = new ClientConfig();

    Client client = ClientBuilder.newClient(config);

    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/location").build());
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList(
			@DefaultValue("false") @QueryParam("show_hidden") boolean showHidden, 
			@DefaultValue("null") @QueryParam("projectID") String projectID,
			@DefaultValue("null") @QueryParam("groupID") String groupID,
			@DefaultValue("null") @QueryParam("time") String time){
		
Response response=null;
		
	    if (projectID.equals("null") && groupID.equals("null") && showHidden && (time==null || time.equals("null"))){
			
			response = target.path("/_search").request().get(Response.class);
			//return response.readEntity(String.class);
		}
	    else{
	    	StringBuilder payload= new StringBuilder();
    		payload.append("{" +
    				"  \"query\": {" +
    				"    \"filtered\": {" +
    				"      \"filter\": {"+
    				"\"bool\": {"
    				+ "\"must\":[");
    		if (showHidden==false){
    			payload.append(notHiddenFilter);
    		}
    		if (time!=null && !time.equals("null")){
    			payload.append(String.format(dateFilter,  time)+",");
    		}
    		if (!groupID.equals("null")){
    			payload.append("{\"term\": { \"parentIDs.parentID\": \"");
	    		payload.append(""+groupID+"\"}},");
    		}
    		if (!projectID.equals("null")){
    			payload.append("{\"term\": { \"parentIDs.parentID\": \"");
	    		payload.append(""+projectID+"\"}}");
    		}
    		if (payload.toString().charAt(payload.length()-1)==','){
    			payload.deleteCharAt(payload.length()-1);
    		}
    		payload.append("]}}}}}");
    		response = target.path("/_search").request().post(Entity.entity(payload.toString(), MediaType.APPLICATION_JSON_TYPE));
			
	    }
		 try{
			    return response;
			    }catch (NullPointerException e){
			    	e.printStackTrace();
			    }
		 return Response.status(Status.BAD_REQUEST).build();
	}
	@GET @Path("{id}")
	public Response get(@PathParam("id") String id,@DefaultValue("null") @QueryParam("time") String time){
		Response response=null;
		response = target.path("/"+id).request().get(Response.class);
		try{
			String result=response.readEntity(String.class);
			if (time==null ||time.equals("null")){
				return response;
			}
			
			
			
			ObjectMapper mapper=new ObjectMapper();
	        TypeReference<HashMap<String, Object>> typeReference=
	                new TypeReference<HashMap<String, Object>>() {
	                };
	        
	            try {
					HashMap<String, Object> map = mapper.readValue(result, typeReference);
					HashMap<String, Object> mapSource=(HashMap<String, Object>)map.get("_source");
					if (!mapSource.containsKey("timeModified")){
						return response;
					}
					
					
					//YYYY-MM-dd HH:mm
					//0123456789012345
					
					
					if (((String)mapSource.get("timeModified")).compareTo(time)>0){
						return response;
					}
					else{
						return Response.status(Status.NOT_MODIFIED).build();
					}
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            
		    }catch (NullPointerException e){
		    	e.printStackTrace();
		    }
		
		    return Response.status(Status.BAD_REQUEST).build();
	}
	@POST @Path("{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateLocation(@PathParam("id") String id, String json){
		if (!isSame(id, json)){
			String endJson=json.substring(1);
			String currTime=Utils.getCurrentDateTimeAsString();
			String modJson=String.format("{\"timeModified\": \"%s\",", currTime)+endJson;
			Response response = target.path("/"+id+"/_update").request().post(Entity.entity(modJson, MediaType.APPLICATION_JSON_TYPE));
			return response;
		}
		return Response.status(CustomStatus.NO_CHANGES).build();
	}
	@POST @Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchLocation(String json){
		Response response = target.path("/_search").request().post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
		return response;
	}
	
	
	@PUT @Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLocation(@PathParam("id") String id, String json){
		Response response = target.path("/"+id).request().put(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
		return response;
	}
	@POST @Path("{id}/visibility")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeProjectStatus(@PathParam("id") String id, @QueryParam("status") String status){
		 StringBuilder sb=new StringBuilder();
		sb.append("{\"doc\":{\"dateArchived\": ");
		if (status.equalsIgnoreCase("hide")){
			 String date=Utils.getCurrentDateAsString();
        sb.append("\""+date+"\"");
		}
		else if (status.equalsIgnoreCase("show")){
			sb.append("null");
		}
		else{
			return Response.status(Status.BAD_REQUEST).build();
		}
        sb.append("}}");
        
        return updateLocation(id, sb.toString());
	}
	@DELETE @Path("{id}")
	public Response deleteLocation(@PathParam("id") String id){
		Response response = target.path("/"+id).request().delete(Response.class);
		return response;
	}
	private boolean isSame(String id, String json){
		//TODO: not implemented
		String object=get(id, null).readEntity(String.class);
		ObjectMapper mapper=new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeReference=
				new TypeReference<HashMap<String, Object>>() {
		};

		try {
			HashMap<String, Object> mapOldO = mapper.readValue(object, typeReference);
			HashMap<String, Object> mapOld;
			if (mapOldO.containsKey("_source")){
				mapOld=(HashMap<String, Object>)mapOldO.get("_source");

				HashMap<String, Object> mapNew = mapper.readValue(json, typeReference);
				if (mapNew.containsKey("doc")){
					HashMap<String, Object> mapDoc=(HashMap<String, Object>) mapNew.get("doc");
					for (String key: mapDoc.keySet()){
						if (mapOld.containsKey(key)){
							if (mapOld.get(key)==null || !mapOld.get(key).equals(mapDoc.get(key))){
								return false;
							}
						}
						else{
							return false;
						}
					}
					return true;
				}
				else{
					for (String key: mapNew.keySet()){
						if (mapOld.containsKey(key)){
							if (mapOld.get(key)==null || !mapOld.get(key).equals(mapNew.get(key))){
								return false;
							}
						}
						else{
							return false;
						}
					}
					return true;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
		}
	
	

}
