package com.connectelco.griot.app.rs;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.techventus.server.voice.Voice;

@Path( "/sms" ) 
public class SendSMSRestService {
	private static final String userName = "nonassimile@gmail.com";
	private static final String pass = "Fayd5#49";
	
	private static final String txt = "Salut Djiby, comment vas-tu?";

	@Produces( { "application/json" } )
	@GET
	public JsonArray sendSMS() {
		return Json.createArrayBuilder()
		    .add( Json.createObjectBuilder()
		      .add( "firstName", "Tom" )
		      .add( "lastName", "Tommyknocker" )
		      .add( "email", "a@b.com" ) )
		    .build();
	}
	
	@GET
	@Path( "/send/{message}")
	public void composeEmail(@PathParam("message") String message) {
		System.out.println("msg: " + message);
		String originNumber = "6785902762";
		 
	    String destinationNumber = "9048662409";
	    
	    try {
			Voice voice = new Voice(userName, pass);
			voice.sendSMS(destinationNumber, txt + " " + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
