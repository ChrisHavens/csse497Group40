package edu.rose_hulman.srproject.humanitarianapp.nonlocaldata;

/**
 * Created by daveyle on 10/13/2015.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
//
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
        private String s;
    private Hits hits;
    public SearchResponse(){}

    //    @JsonProperty("hits")
//    public void setHits(String s){
//        this.s=s;
//    }
//    public String getString(){
//        return this.s;
//    }
    @JsonProperty("hits")
    public void setHits(Hits hits){
        this.hits=hits;
    }
    public Hits getHits(){
        return this.hits;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hits{
        private Hit[] hits;
        public Hits(){}
        @JsonProperty("hits")
        public void setHits(Hit[] hits){
            this.hits=hits;
        }
        public Hit[] getHits(){
            return this.hits;
        }

    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hit{
        String id;
        Source source;
        public Hit(){}
        @JsonProperty("_id")
        public void setId(String id){
            this.id=id;
        }
        public String getId(){
            return this.id;
        }

    }
    public static class Source{

    }
}
