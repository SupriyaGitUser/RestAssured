package basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.File;

public class JiraBugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		RestAssured.baseURI = "https://supriyajirapractice.atlassian.net";
		
		String response = given().header("Content-Type","application/json").header("Authorization","Basic c3Vwcml5YTltYXlAZ21haWwuY29tOkFUQVRUM3hGZkdGMFl5aDdPSUNhd1VzQXNIcV9XM1EySndDZVJteUw2bml2aGlqVnc2bXhwcjdndldGZkV3c21LdG5Ld3l1NGFJU0ZiaVhZa1hEMUNVTDJWUDhzdG42MTZUUUhCb3JQX0NHQjZxWjlLbzg1S0FzLWdELUZJZHJlLTJ2RjY1QjZXU3UwZzhLeEg5aVdYS1E4aGV2WHNkSjB3SFZHdmc3MWtsVERKdGZ6UHl2d0tjND00NDlBRjg4RQ==")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"summary\": \"third issue is created!\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Task\"\r\n"
				+ "        },\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"KAN\"\r\n"
				+ "      \r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}")
		.when().post("/rest/api/3/issue")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String id = js.get("id");
		System.out.println(id);
		
		// Add attachment
		
		given().header("Content-Type","multipart/form-data").pathParam("key", id)
			.header("X-Atlassian-Token","nocheck")
			.header("Authorization","Basic c3Vwcml5YTltYXlAZ21haWwuY29tOkFUQVRUM3hGZkdGMFl5aDdPSUNhd1VzQXNIcV9XM1EySndDZVJteUw2bml2aGlqVnc2bXhwcjdndldGZkV3c21LdG5Ld3l1NGFJU0ZiaVhZa1hEMUNVTDJWUDhzdG42MTZUUUhCb3JQX0NHQjZxWjlLbzg1S0FzLWdELUZJZHJlLTJ2RjY1QjZXU3UwZzhLeEg5aVdYS1E4aGV2WHNkSjB3SFZHdmc3MWtsVERKdGZ6UHl2d0tjND00NDlBRjg4RQ==")
			.multiPart("file", new File("C:\\Users\\supri\\OneDrive\\Pictures\\Screenshots\\Screenshot 2025-03-19 120205.png")).log().all()
			.when().post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
			
		
		

	}

}
