package ecom;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ecom.pojo.LoginRequest;
import ecom.pojo.LoginResponse;
import ecom.pojo.OrderDetails;
import ecom.pojo.Orders;

public class EcommerceAppTest {
	
	
	public static void main(String[] args )
	{
		
	RequestSpecification res = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
	ResponseSpecification respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	
	
	LoginRequest lr = new LoginRequest();
	lr.setUserEmail("supriya@test1.com");
	lr.setUserPassword("Welcome1@");
	
	LoginResponse response = given().spec(res).body(lr).when().post("/api/ecom/auth/login").then().spec(respec).log().all().extract().response().as(LoginResponse.class);
	String token = response.getToken();
	String userId = response.getUserId();
	
	
	// AddProduct
	
	RequestSpecification addProductRes = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
	
	String addProductResponse = given().spec(addProductRes).log().all().param("productName", "qwerty")
										   .param("productAddedBy", userId)
										   .param("productCategory", "fashion")
										   .param("productSubCategory", "shirts")
										   .param("productPrice", "11500")
										   .param("productDescription", "Addias Originals")
										   .param("productFor", "women")
		.multiPart("productImage",new File("C:\\Users\\supri\\OneDrive\\Pictures\\Screenshots\\Screenshot 2025-04-09 142231.png"))
		.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
	
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");
		
		// Place Order
		
		
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);
		List<OrderDetails> myList = new ArrayList<OrderDetails>();
		myList.add(orderDetails);
		
		Orders orders = new Orders();
		orders.setOrders(myList);
		
		
		RequestSpecification placeOrderSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		String placeOrderResponse = given().spec(placeOrderSpec).body(orders).when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
		JsonPath js1 = new JsonPath(placeOrderResponse);
		System.out.println(js1.getString("msg"));
		
		// Delete Product
		
		RequestSpecification deleteProductSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
		String deleteProductResponse = given().log().all().spec(deleteProductSpec).pathParams("productId",productId)
		.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		
		JsonPath js2 = new JsonPath(placeOrderResponse);
		System.out.println(js2.getString("message"));
		
	
		
		
	}
	
	
	

}
