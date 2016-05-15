/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import objects.Person;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author daveyle
 */
public class UserREST {
    public UserREST(){
        
    }
    //        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AdminServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AdminServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        } finally {
//            out.close();
//        }
    public Person doLogin(HttpServletRequest request, HttpServletResponse response){
        String userID=null;
        if (request.getParameter("username")!=null){
            userID=request.getParameter("username");
            ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(UriBuilder.fromUri(Utils.mainURL+"/person").build());
		Response newResponse=null;
		newResponse = target.path("/"+userID).request().get(Response.class);
                return getFormattedData(newResponse);
        }
        return null;
                
                //response.setContentType("text/html;charset=UTF-8");
                //System.out.println("newResponse");
        
//        try {
//            PrintWriter out = response.getWriter();
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AdminServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println(getFormattedData(newResponse));
//            out.println("</body>");
//            out.println("</html>");
//            out.close();
//        } catch(Exception e){
//            
//        }
//        }
    }
    private Person getFormattedData(Response response){
       try{
			
		String responseJson= response.readEntity(String.class);
		    ObjectMapper mapper=new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference=
                    new TypeReference<HashMap<String, Object>>() {
                    };
                StringBuilder sb=new StringBuilder();
                HashMap<String, Object> map = mapper.readValue(responseJson, typeReference);
                if (Boolean.parseBoolean(map.get("found").toString())){
                    return Person.parsePerson(map);
//               
                }
                else{
                    return null;
                }
                
           
		    
		    }catch (Exception e){
		    	e.printStackTrace();
		    	
		    }
       
		    return null;
    }
    
}
