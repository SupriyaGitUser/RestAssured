package basic;

import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;

import files.Payloads;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*1. Print No of courses returned by API

2.Print Purchase Amount

3. Print Title of the first course

4. Print All course titles and their respective Prices

5. Print no of copies sold by RPA Course

6. Verify if Sum of all Course prices matches with Purchase Amount*/
		
		
		JsonPath js = new JsonPath(Payloads.complexJson());
		
		int count = js.getInt("courses.size()");		
		System.out.println(count);
		
		//Print Purchase Amount
		
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");		
		System.out.println(purchaseAmount);
		
		//Print Title of the first course
		String firstCourse = js.getString("courses.title[0]");
		System.out.println(firstCourse);
		
		//Print All course titles and their respective Prices
		List<String> courses = js.getList("courses.title");
		courses.stream().forEach(s->System.out.println(s));
		
		for(int i=0; i<count; i++)
		{
			System.out.print(js.getString("courses.title["+i+"]"));
			System.out.print(" ");
			System.out.println(js.getInt("courses.price["+i+"]"));
		}
		
		
		System.out.println("--------------------------------------------------");
		//Print no of copies sold by RPA Course
		List<String> rpaCourses = courses.stream().filter(s->s.contains("RPA")).collect(Collectors.toList());
		System.out.println(rpaCourses.size());
		int noOfcopies =0 ;
		
		for(int i=0; i<count; i++)
		{
			String courseName = js.getString("courses.title["+i+"]");
			if(courseName.contains("RPA"))
			{
				noOfcopies = js.getInt("courses.copies["+i+"]");
				break;
			}
		}
		
		System.out.println("no of RPA courses "+ noOfcopies);
		
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int totalAmount = 0;
		
		for(int i=0; i<count; i++)
		{
	
				int courseAmount = js.getInt("courses.price["+i+"]")*js.getInt("courses.copies["+i+"]");
				totalAmount = totalAmount + courseAmount;

		}
		System.out.println(totalAmount);
		
		Assert.assertEquals(totalAmount, purchaseAmount);

	}

}
