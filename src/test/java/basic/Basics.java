package basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payloads;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//given() - all input details
		// when() - submit
		// then() - validate response
		
		
		// Add Place
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\supri\\eclipse-workspace\\RestAssured\\src\\test\\java\\files\\AddPlace.json")))).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server","Apache/2.4.52 (Ubuntu)").extract().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		// Update Place
		String newAddress = "70 Summer walk, USA";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json").then().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		
		// Get Place
		
		String addressResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js1 = new JsonPath(addressResponse);
		String address = js1.get("address");
		
		Assert.assertEquals(address, newAddress);
		
		
		System.out.println(address);
		

	}

}
