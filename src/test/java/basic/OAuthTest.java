package basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourse;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.List;

public class OAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust").log().all().when()
		.post("oauthapi/oauth2/resourceOwner/token")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String token = js.get("access_token");
		System.out.println(token);
		
		// returning in string
		/*String response2 = given().queryParam("acccess_token", token)
		.when().get("oauthapi/getCourseDetails").then().log().all().extract().asString();
		System.out.println(response2);*/
		
		// After implementation of POJO class
		GetCourse gc = given().queryParam("acccess_token",token)
		.when().get("oauthapi/getCourseDetails").then().log().all().extract().as(GetCourse.class);
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
	List<API> apis =	gc.getCourses().getApi();
	
	for(int i=0; i<apis.size(); i++)
	{
		if(gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
				{
					System.out.println(gc.getCourses().getApi().get(i).getPrice());
				}
	}
	
	// Get the courses of webautomation
	
	List<WebAutomation> webautomation =	gc.getCourses().getWebAutomation();
	
	for(int i=0; i<webautomation.size(); i++)
	{
		System.out.println(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
	}

	}

}
