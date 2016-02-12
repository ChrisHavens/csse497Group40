package test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import WrappingServer.ThreadREST;

public class TestingThread {

	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/note").build());
	    
		
//		String doc="{\"doc\": {\"parentID\": \"501000\"}}";
//		Response response = target.path("/801000/update").request().post(Entity.entity(doc, MediaType.APPLICATION_JSON_TYPE));
//		System.out.println(response.toString());
		
		Response response2=target.path("/501000").request().get();
		System.out.println(response2.readEntity(String.class));

	}

}
