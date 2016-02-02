package WrappingServer;

import java.io.IOException;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/api/info")
public class IDGettingService {
	@GET @Path("{type}")
	public String getInfo(@PathParam("type") String type){
		ClientConfig config = new ClientConfig();
	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200/database_info/info").build());
		Response response=null;
		response = target.path("/info").request().get(Response.class);
			try{
				
			    String responseJson= response.readEntity(String.class);
//			    return responseJson;
			    ObjectMapper mapper=new ObjectMapper();
	            TypeReference<HashMap<String, Object>> typeReference=
	                    new TypeReference<HashMap<String, Object>>() {
	                    };
	            
	                HashMap<String, Object> map = mapper.readValue(responseJson, typeReference);
//	                
	                boolean found = (boolean) map.get("found");
	                if (found) {
	                    HashMap<String, Object> source=(HashMap)map.get("_source");
	                    Integer object= (Integer) source.get("lastUsed"+type+"ID");
	                    return "{\"item\": "+object+"}";
//	                    return (String)map.get("_source");
	                }
	                else{
	                	 return "{\"item\": -1}";
	                }
	           
			    
			    }catch (Exception e){
			    	e.printStackTrace();
			    	
			    }
			return Response.status(Status.BAD_REQUEST).build().readEntity(String.class);
		}
	
	@POST @Path("{type}/increment")
	public String incrementInfo(@PathParam("type") String type){
		ClientConfig config = new ClientConfig();
	    Client client = ClientBuilder.newClient(config);
	    StringBuilder sb=new StringBuilder();
	    sb.append("{\"doc\":{\"lastUsed"+type+"ID\": ");
	    String s=getInfo(type);
	    Integer id=0;
	    ObjectMapper mapper=new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference=
                new TypeReference<HashMap<String, Object>>() {
                };
        
            try {
				HashMap<String, Object> map = mapper.readValue(s, typeReference);
				id=(Integer)map.get("item");
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    sb.append(id);
	    sb.append("}}");
	    WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200/database_info/info").build());
		Response response = target.path("/info/_update").request().post(Entity.entity(sb.toString(), MediaType.APPLICATION_JSON_TYPE));
		
		return response.readEntity(String.class);
	}

}
