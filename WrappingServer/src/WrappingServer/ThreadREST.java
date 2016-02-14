package WrappingServer;

import java.util.ArrayList;
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

@Path("/api/thread")
public class ThreadREST {
	//TODOs: Replace String returns with Response returns.
	//Adjust PUT to take id from server not user
	
	final String notHiddenFilter="{\"missing\": {\"field\": \"dateArchived\"}},";
	final String dateFilter="{\"range\": {\"timeModified\": {\"gte\": \"%s\"}}}";
	
	final String search=
			"\"query\": {"+"\"filtered\": {"+"\"filter\": {"+"\"bool\": {"+"\"must\": ["+
	"{\"has_parent\": {"+"\"parent_type\": \"thread\","+"\"filter\": {"+"\"ids\": {"+
					"\"values\": ["+"\"%s\""+"]"+"}"+"}"+"}},"+"{\"range\": {"+"\"sentDate\": {"+
	"\"gte\": \"%s\""+"}"+"}}"+"]"+""+"}"+"}"+"}}";
	final String search2=
			"\"query\": {"+"\"filtered\": {"+"\"filter\": {"+"\"has_parent\": {"
	+"\"parent_type\": \"thread\","+"\"filter\": {"+"\"ids\": {"+
					"\"values\": ["+"\"%s\""+"]"+"}"+"}"+""+""+"}"+"}"+"}}";
	final String sort="\"sort\": [{\"sentDate\": {"
			+ "\"order\": \"desc\"}}]";
	final String size1="\"from\" : %s";
	final String size2="\"size\" : %s";
	ClientConfig config = new ClientConfig();

    Client client = ClientBuilder.newClient(config);

    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/thread").build());
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList(
			@DefaultValue("false") @QueryParam("show_hidden") boolean showHidden, 
			@DefaultValue("null") @QueryParam("groupID") String groupID,
			@DefaultValue("null") @QueryParam("time") String time,
			@DefaultValue("25") @QueryParam("count") int count){
		Response response=null;
		 if (groupID.equals("null") && showHidden && (time==null || time.equals("null"))){
				
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
	    			payload.append("{\"term\": { \"parentID\": \"");
		    		payload.append(""+groupID+"\"}}");
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
	@Produces(MediaType.APPLICATION_JSON)
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
	@GET @Path("{id}/messages")
	@Produces(MediaType.APPLICATION_JSON)
		public Response getMessageList(
			
			@PathParam("id") String parentID,
			@DefaultValue("null") @QueryParam("start") String start,
			@DefaultValue("null") @QueryParam("size") String end,
//			@DefaultValue("false") @QueryParam("show_hidden") boolean showHidden, 
//			@DefaultValue("null") @QueryParam("groupID") String groupID,
			@DefaultValue("null") @QueryParam("time") String time
			){
		Response response=null;
		 if (parentID==null || parentID.equals("null")){
				
				response = Response.status(Status.BAD_REQUEST).build();
				return response;
				//return response.readEntity(String.class);
			}
		    else{
		    	StringBuilder payload= new StringBuilder();
		    	payload.append("{");
	    		if (time==null || time.equals("null")){
	    			payload.append(String.format(search2, parentID));
	    			payload.append(",");
	    		}
	    		else{
	    			payload.append(String.format(search, parentID, time));
	    			payload.append(",");
	    		}
	    		if (start!=null && !start.equals("null")){
	    			payload.append(String.format(size1, start));
	    			payload.append(",");
	    		}
	    		if (end!=null && !end.equals("null")){
	    			payload.append(String.format(size2, end));
	    			payload.append(",");
	    		}
	    		payload.append(sort);
	    		payload.append("}");
	    		WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/message").build());
	    		response = target.path("/_search").request().post(Entity.entity(payload.toString(), MediaType.APPLICATION_JSON_TYPE));
				
		    }
			 try{
				    return response;
				    }catch (NullPointerException e){
				    	e.printStackTrace();
				    }
			 return Response.status(Status.BAD_REQUEST).build();
	}
	@POST @Path("{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateThread(@PathParam("id") String id, String json){
		if (!isSame(id, json)){
			String endJson=json.substring(1);
			String currTime=Utils.getCurrentDateTimeAsString();
			String modJson=String.format("{\"timeModified\": \"%s\",", currTime)+endJson;
			Response response = target.path("/"+id+"/_update").request().post(Entity.entity(modJson, MediaType.APPLICATION_JSON_TYPE));
			return response;
		}
		return Response.status(CustomStatus.NO_CHANGES).build();
	}
//	@GET @Path("{id}/messages/{subid}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getMessage(@PathParam("id") String id, @PathParam("subid") String subid){
//		WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200/s40/message").build());
//		Response response = target.path("/"+subid).queryParam("parent", id).request().
//	}
	
	@PUT @Path("{id}/messages/add/{subid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewMessage(@PathParam("id") String parentID,@PathParam("subid") String id, 
			String json){
		System.out.println(parentID);
		if (parentID==null || parentID.equals("null")){
			return Response.status(Status.BAD_GATEWAY).build();
		}
		//System.out.println(json);
		WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/message").build());
		Response response = target.path("/"+id).queryParam("parent", parentID).request().put(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
		return response;
	}
//	public Response addMessage(@PathParam("id") String id, @PathParam("subid") String subid, String json){
//		addNewMessage()
////		//"messageID": "8010005",
////		String json2="{\"messageID\": \""+subid+"\","+json.substring(1);
////		String object=get(id, null).readEntity(String.class);
////		ObjectMapper mapper=new ObjectMapper();
////		TypeReference<HashMap<String, Object>> typeReference=
////				new TypeReference<HashMap<String, Object>>() {
////		};
////
////		try {
////			HashMap<String, Object> mapOldO = mapper.readValue(object, typeReference);
////			HashMap<String, Object> mapOld;
////			if (mapOldO.containsKey("_source")){
////				mapOld=(HashMap<String, Object>)mapOldO.get("_source");
////				if  (mapOld.containsKey("messageItems")){
////					
////					ArrayList<HashMap<String, Object>> items=(ArrayList<HashMap<String, Object>>)mapOld.get("messageItems");
////					//HashMap<String, Object> newMessage1=mapper.readValue(json, typeReference);
//////					m+=newMessage1.get("message").getClass().toString();
////					//m+=json;
////					//items.add(newMessage1);
////					String result=mapper.writeValueAsString(items);
////					String newItems=String.format("[%s, %s]", result.substring(1, result.length()-1), json2);
////					String result2="{\"doc\": %s}";
////					String endJson="\"messageItems\":" + newItems;
////					
////					String currTime=Utils.getCurrentDateTimeAsString();
////					String modJson=String.format("{\"timeModified\": \"%s\",", currTime)+endJson+"}";
////					Response response = target.path("/"+id+"/_update").request().post(Entity.entity(String.format(result2, modJson), MediaType.APPLICATION_JSON_TYPE));
////					String responseStr=response.readEntity(String.class);
////					return response;
//////					try{
//////					HashMap<String, Object> responseObj=mapper.readValue(responseStr, typeReference);
//////					if (responseObj.containsKey("_id") && ((String)responseObj.get("_id")).equals(id) 
//////							&& responseObj.containsKey("_version") && ((int)responseObj.get("_version"))>1){
//////						return Response.status(Status.OK).build().readEntity(String.class);
//////					}
//////					else{
//////						return Response.status(Status.BAD_REQUEST).build().readEntity(String.class);
//////					}
//////						
//////					}catch(Exception e){
//////						return Response.status(Status.BAD_REQUEST).build().readEntity(String.class);
//////					}
////					
////				}}
////				return Response.status(Status.BAD_REQUEST).build();
////			}catch(Exception e){
////			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
////			}
//		
//	}
	@POST @Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchThread(String json){
		Response response = target.path("/_search").request().post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
		return response;
	}
	
	
	@PUT @Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addThread(@PathParam("id") String id, String json){
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
        
        return updateThread(id, sb.toString());
	}
	@DELETE @Path("{id}")
	public Response deleteThread(@PathParam("id") String id){
		Response response = target.path("/"+id).request().delete(Response.class);
		return response;
	}
	
	public boolean isSame(String id, String json){
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
	
	public String isSameDebug(String id, String json){
		//TODO: not implemented
		String object=get(id, null).readEntity(String.class);
		ObjectMapper mapper=new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeReference=
				new TypeReference<HashMap<String, Object>>() {
		};

		try {
			System.out.println(object);
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
								return "F1";
							}
							System.out.println("Key: "+ key);
							System.out.println(mapOld.get(key));
							System.out.println(mapDoc.get(key));
							
						}
						else{
							return "F2";
						}
					}
					return "T1";
				}
				else{
					for (String key: mapNew.keySet()){
						if (mapOld.containsKey(key)){
							if (mapOld.get(key)==null || !mapOld.get(key).equals(mapNew.get(key))){
								return "F3";
							}
						}
						else{
							return "F4";
						}
					}
					return "T2";
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return "F5";
		}
		return "F6";
		}
	
	
	
}
