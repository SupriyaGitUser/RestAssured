package practice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class OauthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		// OAuth 2.0 is a protocol that allows third-party services to exchange information without sharing credentials.	
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = RestAssured.given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").log().all().when()
				.post("oauthapi/oauth2/resourceOwner/token")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String token = js.get("access_token");
		System.out.println(token);

		
		// Getting the course details using the access token
		
		String response2 = RestAssured.given().queryParam("access_token", token)
				.when().get("oauthapi/getCourseDetails")
				.then().log().all().extract().asString();
		System.out.println(response2);
		
		// If you want to extract the response as a POJO class, you can do it like this:
	GetCoursePojo gc = RestAssured.given().queryParam("access_token", token)
				.when().get("oauthapi/getCourseDetails")
				.then().log().all().extract().as(GetCoursePojo.class);
	
	System.out.println(gc.getCourses().getApi().get(0).getCourseTitle());
	System.out.println(gc.getCourses().getWebAutomation().get(2).getCourseTitle());
				
	}

}
