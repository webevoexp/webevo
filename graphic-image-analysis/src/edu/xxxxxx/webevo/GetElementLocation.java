package edu.xxxxxx.webevo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GetElementLocation {

	public static void main(String[] args) {
		 
		System.setProperty("webdriver.chrome.driver", "/Users/me/Desktop/Research-CWRU/ImageCompare/chromedriver");
		WebDriver webtest=new ChromeDriver();
		webtest.get("file:///Users/me/Desktop/research_test/tt/case.html");
		System.out.println("line 125");
		System.out.println("text 126"+webtest.findElement(By.className("w3-button w3-dark-grey")).getText());
	}
	

}
