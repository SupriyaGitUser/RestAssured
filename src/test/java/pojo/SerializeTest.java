package pojo;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {

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
		
		String response = given().queryParam("key", "qaclick123").body(ad).when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);

	}

}
