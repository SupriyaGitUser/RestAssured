package practice;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

public class AddPlaceTest {
	
	public static void main(String[] args) {
		
		
		AddressPojo address = new AddressPojo();
		address.setAccuracy(50);
		address.setAddress("29, side layout, cohen 09");
		address.setLanguage("French-IN");
		address.setName("Supriya Test House3");
		address.setPhoneNumber("(+91) 983 893 3937");
		address.setWebsite("http://google.com");
		LocationPojo location = new LocationPojo();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		
		List<String> types = new ArrayList<>();
		types.add("shoe park");
		types.add("shop");
		address.setTypes(types);
		
		
		address.setLocation(location);
		
		 RestAssured.baseURI = "https://rahulshettyacademy.com";
		 
		 RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").setContentType("application/json").build();	
		 
		 ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType("application/json").build();
		 
		String response = given().spec(reqSpec).when().post("/maps/api/place/add/json").
		 then().spec(resSpec).log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		
		System.out.println(placeId);
		
	
	}

}
