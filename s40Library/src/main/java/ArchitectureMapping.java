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
public class ArchitectureMapping {
    public static final String HOST="s40server.csse.rose-hulman.edu";
    public static final String INDEXNAME="marauders";
    public static void main(String[] args){
        Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress(HOST, 9300));
        client.admin().indices().create(new CreateIndexRequest(INDEXNAME)).actionGet();





        XContentBuilder student=getMappingStudent();
        XContentBuilder insult= getMappingInsult();
        XContentBuilder data=getMappingData();
        PutMappingResponse putMappingResponse = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("student")
                .setSource(student)
                .execute().actionGet();
        PutMappingResponse putMappingResponse2 = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("insult")
                .setSource(insult)
                .execute().actionGet();
        PutMappingResponse putMappingResponse3 = client.admin().indices()
                .preparePutMapping(INDEXNAME)
                .setType("data")
                .setSource(data)
                .execute().actionGet();

        // on shutdown
        client.close();
    }

    public static XContentBuilder getMappingStudent(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("student")
                            .startObject("properties")
                                .startObject("name")
                                    .field("type", "string")
                                    .field("index", "not_analyzed")
                                .endObject()
                                .startObject("house")
                                    .field("type", "string")
                                .endObject()
                                .startObject("location")
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
    public static XContentBuilder getMappingInsult(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("insult")
                    .startObject("properties")
                    .startObject("code")
                    .field("type", "string")
                    .endObject()
                    .startObject("message")
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
    public static XContentBuilder getMappingData(){
        try {
            XContentBuilder mapping = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("data")
                    .startObject("properties")
                    .startObject("lastStudentID")
                    .field("type", "string")
                    .endObject()
                    .startObject("maxInsultID")
                    .field("type", "string")
                    .endObject()
                    .startObject("passphrase")
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

}
