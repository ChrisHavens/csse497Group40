package WrappingServer;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
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
@Path("/login")
public class LoginService {

  // This method is called if TEXT_PLAIN is request
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  public String get(String content) {
	  System.out.println(content);
	 
		
		LoginRequest request=new LoginRequest();
		
			String[] param=content.split("=");
			if (param[0].equals("name")){
				request.setName(param[1]);
			}
			
		
		
		 ClientConfig config = new ClientConfig();

		    Client client = ClientBuilder.newClient(config);

		    WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200").build());
		    Response response=null;
		    String uri="s40/person/_search";
		    StringBuilder sb=new StringBuilder();
		    sb.append(String.format("{\"filter\":{\"term\": {\"email\": \"%s\"}}}",request.getName()));
		    response = target.path(uri).request().post(Entity.entity(sb.toString(), MediaType.APPLICATION_JSON_TYPE));
		    
		    //response = target.path(uri).request().get(Response.class);
		   
		    		
		    
		    
		    try{
		    return response.readEntity(String.class);
		    }catch (NullPointerException e){
		    	e.printStackTrace();
		    }
		    return null;
		    

//		    String plainAnswer = target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class);
//		    String xmlAnswer = target.path("rest").path("hello").request().accept(MediaType.TEXT_XML).get(String.class);
//		    String htmlAnswer= target.path("rest").path("hello").request().accept(MediaType.TEXT_HTML).get(String.class);
	
	  
	  
    
  }

  // This method is called if XML is request
  @GET
  @Produces(MediaType.TEXT_XML)
  public String sayXMLHello() {
    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
  }

  // This method is called if HTML is request
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHtmlHello() {
    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
  }

} 
