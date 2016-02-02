/**
 * Created by daveyle on 1/26/2016.
 */

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

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.core.UriBuilder;
public class DummyData {
	static ClientConfig config = new ClientConfig();

    static Client client = ClientBuilder.newClient(config);

    static WebTarget target = client.target(UriBuilder.fromUri("http://s40server.csse.rose-hulman.edu:9200/s40").build());
    static String[] info={"project/101000", "project/101002","group/201001", "group/200001","person/3020", "person/3021", "note/501001","project/101001","project/101111",
            "project/100002","project/101010", "person/3000", "person/3105", "person/3005", "person/3001", "person/3002", "person/3003", "person/3004", "person/3010",
            "person/3011","person/3012","person/3013", "group/200000", "group/201000", "group/202001", "group/202222", "group/200004", "group/201002", "group/200010",
    "group/201010","group/201011","location/401000", "location/402000", "location/402001", "location/401001", "location/402001", "location/400002", "location/401002",
    "location/400011", "location/400010", "note/501000", "note/501002", "shipment/601002", "shipment/601000", "shipment/601001", "checklist/701000", "checklist/701001"};
    static String[] data={
             "{" +"\"timeModified\": \"2016-01-12 10:08\","+ "\"name\": \"Stop the Reapers\"," +"\"groupIDs\": [" +"{" +"\"groupID\": \"200000\"" +"}," +"{" +"\"groupID\": \"201000\"" +"}" +"]" +"" +"}",
            "{" +"\"timeModified\": \"2016-01-02 12:40\","+"\"name\": \"Harvest the organics\"," +"\"groupIDs\": [" +"{" +"\"groupID\": \"200001\"" +"}," 
                            +"{" +"\"groupID\": \"201001\"" +"}" +"]," +"\"dateArchived\": \"2183-10-04\"" +"" +"}",
            "{" +"\"timeModified\": \"2016-01-10 15:08\","+"\"name\": \"Reapers\"," +"\"projectIDs\": [" +"{\"projectID\": \"101002\"}" +"]," +"\"dateArchived\": \"2183-10-04\"" +"}",
            "{" +"\"timeModified\": \"2016-01-19 20:41\","+"\"name\": \"Collectors\"," +"\"projectIDs\": [" +"{\"projectID\": \"101002\"}" +"]," +"\"dateArchived\": \"2183-10-04\"" +"}",
            "{" +"\"timeModified\": \"2016-01-21 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"5.01\"," +" \"lng\": \"57.4\"," +" \"name\": \"Omega-4 Relay\","+" \"time\": \"2185-03-02 15:10\"" +
                            "}" +"," +"\"name\": \"Harbinger\"," +"\"email\": \"assumingDirectControl@collectors.extranet.org\"," +"\"phone\": \"555-555-5555\"," +"\"parentIDs\": [" +
                            "{\"parentID\": \"101002\"}," +"{\"parentID\": \"200001\"}" +"]," +"\"dateArchived\": \"2183-10-04\"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"{" +"\"lat\": \"38.9\"," +"\"lng\": \"43.61\"," +"\"name\": \"The Citadel\"," +" \"time\": \"2183-05-11 23:09\"" +"}" +"," +
                            "\"name\": \"Sovereign\"," +"\"email\": \"vanguardOfYourDestruction@reapers.extranet.org\"," +"\"phone\": \"555-555-5555\"," +"\"parentIDs\": [" +
                            "{\"parentID\": \"101002\"}," +"{\"parentID\": \"201001\"}" +"]," +"\"dateArchived\": \"2183-10-04\"" +"}" +"",
            "{" +"\"contents\": \"We impose order on the chaos of organic evolution. You exist because we allow it, and you will end because we demand it.\"," +
                            "\"lastModTime\": \"2183-04-17 06:23\"," +"\"name\": \"Things to say to intimidate Shepard\"," +"\"parentID\": \"201001\"," +"\"dateArchived\": \"2183-10-04\"" +"}" +"",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"Run the country\"," +"\"groupIDs\": [" +"{" +"\"groupID\": \"202001\"" +"}," +"{" +"\"groupID\": \"200004\"" +"}" +"]" +"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"s40\"," +"\"groupIDs\": [" +"{" +"\"groupID\": \"202222\"" +"}" +"]" +"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"Bring Mark Watney Home\"," +"\"groupIDs\": [" +"{" +"\"groupID\": \"201002\"" +"}" +"]" +"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"EWB Ghana\"," +"\"groupIDs\": [" +"{" +"\"groupID\": \"201010\"" +"}," +"{\"groupID\": \"201011\"}" +"]" +"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"45.01\"," +" \"lng\": \"57.34\"," +" \"name\": \"Normandy SR-2\"," +" \"time\": \"2185-07-28 09:01\"" +"}" +"," +
                            "\"name\": \"FemShep\"," +"\"email\": \"notDeadYet@normandy.extranet.org\"," +"\"phone\": \"555-555-5555\"," +"\"parentIDs\": [" +"{\"parentID\": \"101000\"}," +
                            "{\"parentID\": \"200000\"}," +"{\"parentID\": \"201000\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"45.01\"," +" \"lng\": \"57.34\"," +" \"name\": \"Rose-Hulman\"," +" \"time\": \"2015-12-06 17:33\"" +"}" +"," +
                            "\"name\": \"Rey\"," +"\"email\": \"dada@rose-hulman.edu\"," +"\"phone\": \"555-555-5555\"," +"\"parentIDs\": [" +"{\"parentID\": \"101111\"}," +
                            "{\"parentID\": \"101002\"}," +"{\"parentID\": \"202222\"}," +"{\"parentID\": \"200001\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"45.01\"," +" \"lng\": \"57.34\"," +" \"name\": \"Normandy SR-2\"," +" \"time\": \"2185-07-28 17:28\"" +
                            "}" +"," +"\"name\": \"Garrus Vakarian\"," +"\"email\": \"calibrator@normandy.extranet.org\"," +"\"phone\": \"555-555-5555\"," +"\"parentIDs\": [" +
                            "{\"parentID\": \"101000\"}," +"{\"parentID\": \"200000\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"45.01\"," +" \"lng\": \"57.34\"," +" \"name\": \"The White House\"," +" \"time\": \"2000-03-01 15:57\"" +
                            "}" +"," +"\"name\": \"Jed Bartlet\"," +"\"email\": \"whatsNext@thewhitehouse.gov\"," +"\"phone\": \"555-555-5556\"," +"\"parentIDs\": [" +
                            "{\"parentID\": \"101001\"}," +"{\"parentID\": \"202001\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"32.47\"," +" \"lng\": \"-27.9\"," +" \"name\": \"Mars\"," +" \"time\": \"2025-1-20 7:28\"" +"}" +"," +
                            "\"name\": \"Mark Watney\"," +"\"email\": \"spacePirate@ares3.nasa.gov\"," +"\"phone\": \"Unavailable\"," +"\"parentIDs\": [" +"{\"parentID\": \"100002\"}," +
                            "{\"parentID\": \"201002\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"45.01\"," +" \"lng\": \"57.34\"," +" \"name\": \"The White House\"," +" \"time\": \"2000-03-01 15:58\"" +"}" +"," +
                            "\"name\": \"C.J. Cregg\"," +"\"email\": \"flamingo@thewhitehouse.gov\"," +"\"phone\": \"555-555-5557\"," +"\"parentIDs\": [" +"{\"parentID\": \"101001\"}," +
                            "{\"parentID\": \"200004\"}," +"{\"parentID\": \"202001\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"45.01\"," +" \"lng\": \"57.34\"," +" \"name\": \"The White House\"," +" \"time\": \"2000-03-01 15:56\"" +"}" +"," +
                            "\"name\": \"Leo McGarry\"," +"\"email\": \"iKnowTheWayOut@thewhitehouse.gov\"," +"\"phone\": \"555-555-5558\"," +"\"parentIDs\": [" +"{\"parentID\": \"101001\"}," +
                            "{\"parentID\": \"202001\"}," +"{\"parentID\": \"200004\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"6.55\"," +" \"lng\": \"-1.3\"," +" \"name\": \"Little Village\"," +" \"time\": \"2015-10-20 16:34\"" +"}" +"," +
                            "\"name\": \"John Smith\"," +"\"email\": \"jsmith@gmail.com\"," +"\"phone\": \"555-555-5556\"," +"\"parentIDs\": [" +"{\"parentID\": \"101010\"}," +
                            "{\"parentID\": \"200010\"}, " +"{\"parentID\": \"201010\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"5.55\"," +" \"lng\": \"-.2\"," +" \"name\": \"HQ\"," +" \"time\": \"2015-10-20 16:34\"" +"}" +"," +
                            "\"name\": \"Jane Jones\"," +"\"email\": \"jjones@gmail.com\"," +"\"phone\": \"555-555-5557\"," +"\"parentIDs\": [" +"{\"parentID\": \"101010\"}," +
                            "{\"parentID\": \"200010\"}," +"{\"parentID\": \"201011\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"6.55\"," +" \"lng\": \"-1.3\"," +" \"name\": \"Little Village\"," +" \"time\": \"2015-10-20 16:34\"" +"}" +"," +
                            "\"name\": \"Michael Evans\"," +"\"email\": \"mevans@gmail.com\"," +"\"phone\": \"555-555-5558\"," +"\"parentIDs\": [" +"{\"parentID\": \"101010\"}," +
                            "{\"parentID\": \"201010\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lastLocation\": " +"" +"{" +" \"lat\": \"5.55\"," +" \"lng\": \"-.2\"," +" \"name\": \"HQ\"," +" \"time\": \"2015-10-20 16:34\"" +"}" +"," +
                            "\"name\": \"Edward Brown\"," +"\"email\": \"ebrown@gmail.com\"," +"\"phone\": \"555-555-5559\"," +"\"parentIDs\": [" +"{\"parentID\": \"101010\"}," +
                            "{\"parentID\": \"201011\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-19 10:08\","+"\"name\": \"Normandy Crew\"," +"\"projectIDs\": [" +"{\"projectID\": \"101000\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"Spectres\"," +"\"projectIDs\": [" +"{\"projectID\": \"101000\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"White House Senior Staff\"," +"\"projectIDs\": [" +"{\"projectID\": \"101001\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"Testing\"," +"\"projectIDs\": [" +"{\"projectID\": \"101111\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"White House Senior Staff No Pres\"," +"\"projectIDs\": [" +"{\"projectID\": \"101001\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"Mars Inhabitants\"," +"\"projectIDs\": [" +"{\"projectID\": \"100002\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"Supervisors\"," +"\"projectIDs\": [" +"{\"projectID\": \"101010\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"Little Village Workers\"," +"\"projectIDs\": [" +"{\"projectID\": \"101010\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"HQ Workers\"," +"\"projectIDs\": [" +"{\"projectID\": \"101010\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lat\": \"45.01\"," +"\"lng\": \"57.34\"," +"\"name\": \"Normandy SR-2\"," +"\"parentIDs\": [" +"{\"parentID\": \"101000\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lat\": \"38.9\"," +"\"lng\": \"43.61\"," +"\"name\": \"The Citadel\"," +"\"parentIDs\": [" +"{\"parentID\": \"101000\"}," +
                            "{\"parentID\": \"101002\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+" \"lat\": \"5.01\"," +" \"lng\": \"57.4\"," +" \"name\": \"Omega-4 Relay\"," +"\"parentIDs\":[" +"{\"parentID\": \"101002\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lat\": \"38.8977\"," +"\"lng\": \"-77.0365\"," +"\"name\": \"The White House\"," +"\"parentIDs\": [" +"{\"parentID\": \"101001\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lat\": \"38.889803\", " +"\"lng\": \"-77.009114\"," +"\"name\": \"The U.S. Capitol\"," +"\"parentIDs\": [" +"{\"parentID\": \"101001\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lat\": \"32.47\", " +"\"lng\": \"-27.9\"," +"\"name\": \"Mars\"," +"\"parentIDs\": [" +"{\"parentID\": \"100002\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lat\": \"29.6\"," +"\"lng\": \"-9.1\"," +"\"name\": \"Mars-Earth route\"," +"\"parentIDs\": [" +"{\"parentID\": \"100002\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lat\": \"5.55\", " +"\"lng\": \"-.2\"," +"\"name\": \"HQ\"," +"\"parentIDs\": [" +"{\"parentID\": \"101010\"}" +"]" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"lat\": \"6.55\", " +"\"lng\": \"-1.3\"," +"\"name\": \"Little Village\"," +"\"parentIDs\": [" +"{\"parentID\": \"101010\"}" +"]" +"}",
            "{" +"\"contents\": \"Commander Shepard is no longer allowed to drive the Mako. --The Crew\"," +"\"lastModTime\": \"2183-05-17 14:32\"," +
                            "\"name\": \"Things the Commander is Not Allowed To Do\"," +"\"parentID\": \"200000\"" +"}",
            "{" +"\"contents\": \"How come Aquaman can control whales? They're mammals! Makes no sense.\"," +"\"lastModTime\": \"2025-03-17 14:32\"," +
                            "\"name\": \"Log Entry: Sol 61\"," +"\"parentID\": \"201002\"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"contents\": \"One MAV, one space pirate named Mark Watney\"," +"\"toLocationID\": \"401002\"," +"\"fromLocationID\": \"400002\"," +
                            "\"name\": \"The Watneymobile\"," +"\"parentID\": \"201002\"," +"\"pickupTime\": \"2026-01-04 21:56\"," +"\"status\": \"En-route\"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"contents\": \"Something that someone else couldn't be bothered to get so they had Shepard do it\"," +"\"toLocationID\": \"402000\"," +
                            "\"fromLocationID\": \"401000\"," +"\"name\": \"Yet another fetch quest\"," +"\"parentID\": \"200000\"," +"\"pickupTime\": \"2185-10-12 04:08\"," +
                            "\"status\": \"En-route\"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"contents\": \"One half-done Human Reaper\"," +"\"toLocationID\": \"402000\"," +"\"fromLocationID\": \"402001\"," +"\"name\": \"World Domination\"," +
                            "\"parentID\": \"201001\"," +"\"pickupTime\": \"2185-03-12 10:08\"," +"\"status\": \"En-route\"," +"\"dateArchived\": \"2183-10-04\"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"Shepard's to do list\"," +"\"parentID\":\"200000\"," +"\"checklistItems\": [" +"{\"checklistItemID\": \"7010001\"," +"\"isDone\": false," +
                            "\"personID\": \"3000\"," +"\"task\": \"Show people how you really feel\"," +"\"sublistItems\": [" +"{" +"\"sublistItemID\": \"70100011\"," +
                            "\"task\": \"Tell the Council where to shove it.\"," +"\"isDone\": false," +"\"personID\": \"3000\"" +"}," +"{" +"\"sublistItemID\": \"70100012\"," +
                            "\"task\": \"Punch a reporter\"," +"\"isDone\": true," +"\"personID\": \"3000\"" +"}," +"{" +"\"sublistItemID\": \"70100013\"," +
                            "\"task\": \"Headbutt a reporter\"," +"\"isDone\": false," +"\"personID\": \"3000\"" +"}," +"{" +"\"sublistItemID\": \"70100014\"," +
                            "\"task\": \"Call a hanar a 'big stupid jellyfish'\"," +"\"isDone\": true," +"\"personID\": \"3000\"" +"}" +"]" +"}" +"]" +"" +"}",
            "{" +"\"timeModified\": \"2016-01-12 10:08\","+"\"name\": \"Races to destroy\"," +"\"parentID\":\"201001\"," +"\"checklistItems\": [" +"{\"checklistItemID\": \"70100101\"," +"\"isDone\": false," +
                            "\"personID\": \"3021\"," +"\"task\": \"Harvest:\"," +"\"sublistItems\": [" +"{" +"\"sublistItemID\": \"7010010101\"," +"\"task\": \"Humans\"," +
                            "\"isDone\": false," +"\"personID\": \"3021\"" +"}," +"{" +"\"sublistItemID\": \"7010010102\"," +"\"task\": \"Protheans\"," +"\"isDone\": true," +
                            "\"personID\": \"3021\"" +"}," +"{" +"\"sublistItemID\": \"7010010103\"," +"\"task\": \"Turians\"," +"\"isDone\": false," +"\"personID\": \"3021\"" +"}," +
                            "{" +"\"sublistItemID\": \"7010010104\"," +"\"task\": \"Asari\"," +"\"isDone\": false," +"\"personID\": \"3021\"" +"}," +"{" +"\"sublistItemID\": \"7010010105\"," +
                            "\"task\": \"Salarians\"," +"\"isDone\": false," +"\"personID\": \"3021\"" +"}," +"{" +"\"sublistItemID\": \"7010010106\"," +"\"task\": \"Krogan\"," +
                            "\"isDone\": false," +"\"personID\": \"3021\"" +"}," +"{" +"\"sublistItemID\": \"7010010107\"," +"\"task\": \"Quarians\"," +"\"isDone\": false," +
                            "\"personID\": \"3021\"" +"}" +"]" +"}" +"]," +"\"dateArchived\": \"2183-10-04\"" +"" +"}"
    };
    public static void main(String[] args){
        for (int i=0; i<info.length; i++){
            String[] currInfo=info[i].split("/");
            String currData=data[i];
            Response response = target.path("/"+currInfo[0]+"/"+currInfo[1]).request().put(Entity.entity(currData, MediaType.APPLICATION_JSON_TYPE));
        }

    }
}
