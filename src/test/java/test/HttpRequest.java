package test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class HttpRequest {
	
  String id=null;
  
	@Test(priority=1)
	public void createUser(){
		
		HashMap<String,String> payload=new HashMap<String,String>();
		payload.put("firstName","Akash");
		payload.put("lastName","Negi");
		payload.put("email","akashn4@gmail.com");
		
	     Response res=given()
		    .header("app-id","63fcdcf0c44459765258a803")
		    .contentType("application/json")
		    .body(payload)
		.when()
			.post("https://dummyapi.io/data/v1/user/create");
	    
	    id=res.jsonPath().get("id").toString();
	    Assert.assertEquals(res.getStatusCode(), 200);
	    Assert.assertEquals(res.getHeader("Content-Type"), "application/json; charset=utf-8");
								
	}
	
	@Test(priority=2,dependsOnMethods="createUser")
	public void updateUser(){
		
		HashMap<String,String> payload=new HashMap<String,String>();
		payload.put("title","mr");
		payload.put("picture","https://randomuser.me/api/portraits/med/men/27.jpg");
		payload.put("gender","male");
		payload.put("dateOfBirth","1996-04-09T19:26:49.610Z");
		payload.put("phone","8214634220");
		
		given()
		    .header("app-id","63fcdcf0c44459765258a803")
		    .contentType("application/json")
		    .body(payload)
		.when()
			.put("https://dummyapi.io/data/v1/user/"+id)
		.then()
			.statusCode(200)
		    .log().all();
		
	}
	@Test(priority=3)
	public void readUser(){
		
		given()
		    .header("app-id","63fcdcf0c44459765258a803")
		.when()
			.get("https://dummyapi.io/data/v1/user/"+id)
		.then()
			.statusCode(200)
			.body("firstName", equalTo("Akash"))
			.body("picture", equalTo("https://randomuser.me/api/portraits/med/men/27.jpg"))
			.body("email", equalTo("akashn4@gmail.com"))
		    .log().all();
	}
	
	@Test(priority=4)
	public void deleteUser(){
		
		given()
		    .header("app-id","63fcdcf0c44459765258a803")
		.when()
			.delete("https://dummyapi.io/data/v1/user/"+id)
		.then()
			.statusCode(200)
		    .log().all();	
	}
}
