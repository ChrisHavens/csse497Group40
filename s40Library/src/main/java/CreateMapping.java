import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

/**
 * Created by daveyle on 10/13/2015.
 */
public class CreateMapping {
    public static final String HOST="s40server.csse.rose-hulman.edu";
    public static final String INDEXNAME="s40";
    public static void main(String[] args){
        Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress(HOST, 9300));
        client.admin().indices().create(new CreateIndexRequest(INDEXNAME)).actionGet();





        XContentBuilder location=getMappingLocation();
        XContentBuilder project= getMappingProject();
        XContentBuilder group=getMappingGroup();
        XContentBuilder person=getMappingPerson();
        XContentBuilder shipment=getMappingShipment();
        XContentBuilder note=getMappingNote();
        XContentBuilder checklist=getMappingChecklist();
        PutMappingResponse putMappingResponse = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("location")
                .setSource(location)
                .execute().actionGet();
        PutMappingResponse putMappingResponse2 = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("project")
                .setSource(project)
                .execute().actionGet();
        PutMappingResponse putMappingResponse3 = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("group")
                .setSource(group)
                .execute().actionGet();
        PutMappingResponse putMappingResponse4 = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("person")
                .setSource(person)
                .execute().actionGet();
        PutMappingResponse putMappingResponse5 = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("shipment")
                .setSource(shipment)
                .execute().actionGet();
        PutMappingResponse putMappingResponse6 = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("note")
                .setSource(note)
                .execute().actionGet();
        PutMappingResponse putMappingResponse7 = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("checklist")
                .setSource(checklist)
                .execute().actionGet();




        // on shutdown
        client.close();
    }

    public static XContentBuilder getMappingLocation(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("location")
                            .startObject("properties")
                                .startObject("dateArchived")
                                    .field("type", "date")
                                    .field("format", "YYYY-MM-dd")
                                .endObject()
                                .startObject("lat")
                                    .field("type", "string")
                                .endObject()
                                .startObject("lng")
                                    .field("type", "string")
                                .endObject()
                                .startObject("name")
                                    .field("type", "string")
                                .endObject()
                                .startObject("parentIDs")
                                    .startObject("properties")
                                        .startObject("parentID")
                                            .field("type", "string")
                                        .endObject()
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            return mapping;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static XContentBuilder getMappingProject(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("project")
                            .startObject("properties")
                    .startObject("dateArchived")
                    .field("type", "date")
                    .field("format", "YYYY-MM-dd")
                    .endObject()
                                .startObject("name")
                    .field("type", "string")
                    .endObject()
                    .startObject("groupIDs")
                    .startObject("properties")
                                        .startObject("groupID")
                                            .field("type", "string")
                                        .endObject()
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            return mapping;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static XContentBuilder getMappingGroup(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("group")
                    .startObject("properties")
                    .startObject("dateArchived")
                    .field("type", "date")
                    .field("format", "YYYY-MM-dd")
                    .endObject()
                    .startObject("name")
                    .field("type", "string")
                    .endObject()
                    .startObject("projectIDs")
                        .startObject("properties")
                            .startObject("projectID")
                                .field("type", "string")
                            .endObject()
                        .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();
            return mapping;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static XContentBuilder getMappingPerson(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("person")
                    .startObject("properties")
                    .startObject("dateArchived")
                    .field("type", "date")
                    .field("format", "YYYY-MM-dd")
                    .endObject()
                    .startObject("name")
                    .field("type", "string")
                    .endObject()
                    .startObject("phone")
                    .field("type", "string")
                    .endObject()
                    .startObject("email")
                    .field("type", "string")
                    .endObject()
                    .startObject("lastLocation")
                        .startObject("properties")
                            .startObject("lat")
                                .field("type", "string")
                            .endObject()
                            .startObject("lng")
                                .field("type", "string")
                            .endObject()
                            .startObject("time")
                                .field("type", "date")
                                .field("format", "YYYY-MM-dd HH:mm")
                            .endObject()
                            .startObject("name")
                                .field("type", "string")
                            .endObject()
                        .endObject()
                    .endObject()
                    .startObject("parentIDs")
                    .startObject("properties")
                    .startObject("parentID")
                    .field("type", "string")
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();
            return mapping;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static XContentBuilder getMappingShipment(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("shipment")
                            .startObject("properties")
                    .startObject("dateArchived")
                    .field("type", "date")
                    .field("format", "YYYY-MM-dd")
                    .endObject()
                                .startObject("name")
                    .field("type", "string")
                    .endObject()
                    .startObject("contents")
                    .field("type", "string")
                    .endObject()
                    .startObject("toLocationID")
                    .field("type", "string")
                    .endObject()
                    .startObject("fromLocationID")
                    .field("type", "string")
                    .endObject()
                    .startObject("lastLocationID")
                    .field("type", "string")
                    .endObject()
                    .startObject("status")
                    .field("type", "string")
                    .endObject()
                    .startObject("pickupTime")
                    .field("type", "date")
                    .field("format", "YYYY-MM-dd HH:mm")
                    .endObject()
                                .startObject("parentID")
                                    .field("type", "string")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            return mapping;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static XContentBuilder getMappingNote(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("note")
                    .startObject("properties")
                    .startObject("dateArchived")
                    .field("type", "date")
                    .field("format", "YYYY-MM-dd")
                    .endObject()
                    .startObject("name")
                    .field("type", "string")
                    .endObject()
                    .startObject("contents")
                    .field("type", "string")
                    .endObject()
                    .startObject("lastModTime")
                    .field("type", "date")
                    //TODO add TimeZone Offset
                    .field("format", "YYYY-MM-dd HH:mm")
                    .endObject()
                    .startObject("parentID")
                    .field("type", "string")
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();
            return mapping;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static XContentBuilder getMappingChecklist(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("checklist")
                    .startObject("properties")
                    .startObject("dateArchived")
                    .field("type", "date")
                    .field("format", "YYYY-MM-dd")
                    .endObject()
                    .startObject("name")
                    .field("type", "string")
                    .endObject()
                    .startObject("parentID")
                    .field("type", "string")
                    .endObject()
                    .startObject("checklistItems")
                    .startObject("properties")
                    .startObject("checkListItemID")
                    .field("type", "string")
                    .endObject()
                    .startObject("task")
                    .field("type", "string")
                    .endObject()
                    .startObject("isDone")
                    .field("type", "boolean")
                    .endObject()
                    .startObject("personID")
                    .field("type", "string")
                    .endObject()
                    .startObject("sublistItems")
                    .startObject("properties")
                    .startObject("sublistItemID")
                    .field("type", "string")
                    .endObject()
                    .startObject("task")
                    .field("type", "string")
                    .endObject()
                    .startObject("isDone")
                    .field("type", "boolean")
                    .endObject()
                    .startObject("personID")
                    .field("type", "string")
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();
            return mapping;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
