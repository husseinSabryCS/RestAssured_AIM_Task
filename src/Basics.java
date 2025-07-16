import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;

public class Basics {

	public static void main(String[] args) {

		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
	String response =	given().log().all().queryParam("key", "qaclick123")
		.body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
	String placeId =	js.getString("place_id");
	
		
String updateResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", "cbeef2741cce7486303e61bbc3df2945")
	.body("{\n"
			+ "\"place_id\":\"" +placeId + "\",\n"
			+ "\"address\":\"70 winter walk, USA\",\n"
			+ "\"key\":\"qaclick123\"\n"
			+ "}")
	.when().put("maps/api/place/update/json")
	.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"))
	.extract().response().asString();
		System.out.println(updateResponse);		
		
		
		given().log().all().queryParam("key", "qaclick123")	
		.queryParam("place_id",placeId )
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
		.body("location.latitude", equalTo("-38.383494"));
		
		given().log().all().queryParam("key", "qaclick123")
		.body("{\n"
				+ "\n"
				+ "    \"place_id\":\"" +placeId + "\"\n"
				+ "}\n"
				+ "")
		.when().delete("maps/api/place/delete/json")
		.then().log().all().assertThat().statusCode(200)
		.body("status",equalTo( "OK"))
		.extract().response().asString();
		
		
		
		

	
		
		
		
	}

}
