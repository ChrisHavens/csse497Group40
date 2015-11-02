package server_models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginRequest {
	private String name;
	public LoginRequest(){
		
	}
	public LoginRequest(String name){
		this.name=name;
		
	}
	@JsonProperty("name")
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	

}
