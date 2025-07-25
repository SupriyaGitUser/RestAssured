package basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payloads;

public class DynamicJson {
	
	@Test(dataProvider = "BooksData")
	public void addBookTest(String isbn, String aisle){
	
		RestAssured.baseURI = "http://216.10.245.166";		
		String response = given().headers("Content-Type","application/json").body(Payloads.addBook(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).body("Msg", equalTo("successfully added")).extract().response().asString();		
		JsonPath js = new JsonPath(response);
		System.out.println(js.getString("ID"));

	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getData()
	{
		// Array = collection of elements
		// Multidimentional array = collection of arrays
		
		return new Object[][] {{"Sup","211"},{"Sup","212"},{"Sup","213"}};
	}
	
	@Test(dataProvider = "BooksData")
	public void deleteBookTest(String isbn, String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		given().headers("Content-Type","application/json").body(Payloads.deleteBook(isbn+aisle))
		.when().delete("Library/DeleteBook.php").then().assertThat().statusCode(200).body("msg",equalTo("book is successfully deleted"));
	}

}
