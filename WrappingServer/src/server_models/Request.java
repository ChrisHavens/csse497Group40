package server_models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Request {
	private String URI;
	private String JSON;
	private String method;
	public Request(){
		
	}
	public Request(String URI, String JSON, String method){
		this.URI=URI;
		this.JSON=JSON;
		this.method=method;
	}
	@JsonProperty("uri")
	public String getURI(){
		return this.URI;
	}
	public void setURI(String uri){
		this.URI=uri;
	}
	
	@JsonProperty("method")
	public String getMethod(){
		return this.method;
	}
	public void setMethod(String method){
		this.method=method;
	}
	@JsonProperty("json")
	@JsonRawValue()
	public String getJSON(){
		return this.JSON;
	}
	public void setJSON(String json){
		this.JSON=json;
	}

}
