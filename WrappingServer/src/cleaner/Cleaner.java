package cleaner;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

@WebListener
public class Cleaner implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new CleanUpTask(), 0, 1, TimeUnit.DAYS);
        //scheduler.scheduleAtFixedRate(new EmailTask(), 0, 1, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
    
    public class CleanUpTask implements Runnable {

        @Override
        public void run() {
        	ClientConfig config = new ClientConfig();

    	    Client client = ClientBuilder.newClient(config);

    	    WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200").build());
    	    Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
            String date=String.format(".marvel-%04d.%02d.%02d", calendar.get
                    (Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
            target.path(date).request().delete(Response.class);
            // Do your job here.
        }

    }
    public class EmailTask implements Runnable {

        @Override
        public void run() {
//        	ClientConfig config = new ClientConfig();
//
//    	    Client client = ClientBuilder.newClient(config);
//
//    	    WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200").build());
//    	    String path="/_cat/health";
//            Response response=target.path(path).request().get(Response.class);
            //if (response.readEntity(String.class).contains("red")){
        	try{
            	SendEmail.main(null);
        	}catch(Exception e){
        		
        	}
            //}
            // Do your job here.
        }

    }
}