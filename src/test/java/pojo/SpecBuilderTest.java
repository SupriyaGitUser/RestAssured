package pojo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Address ad = new Address();
		ad.setAccuracy(50);
		ad.setName("Supriya Test House3");
		ad.setPhoneNumber("(+91) 983 893 3937");
		ad.setAddress("29, side layout, cohen 09");
		ad.setWebsite("http://google.com");
		ad.setLanguage("French-IN");
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		ad.setLocation(l);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		RequestSpecification res = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").build();
		ResponseSpecification respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		
		
		String response = given().spec(res).body(ad).when().post("/maps/api/place/add/json")
		.then().log().all().spec(respec).extract().response().asString();
		
		System.out.println(response);

	}

}
