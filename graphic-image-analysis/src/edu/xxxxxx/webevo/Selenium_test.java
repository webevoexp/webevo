package edu.xxxxxx.webevo;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Selenium_test {
 //    private static String url="http://blog.csdn.net/qq_30843221/article/details/53637268";
	   
       public static void main(String args[]){
    	   System.setProperty("webdriver.chrome.driver", "/Users/me/Desktop/Research-CWRU/ImageCompare/chromedriver");
			WebDriver webtest=new ChromeDriver();
			webtest.get("file:///Users/me/Desktop/research_test/tt/case1.html");
			System.out.println("line 125");
 
	        
	      //  System.out.println(webtest.findElement(By.cssSelector("a.w3-btn.sectionbtn")).getText());
	        System.out.println(webtest.findElement(By.cssSelector("div.w3-col.l4.w3-center.section.onethird")).getText());
	     //   System.out.println(webtest.findElement(By.xpath("/html")).getText().isEmpty());
	        
	        
			
       
			webtest.quit();
    	 

       }
       
     
       
       
       
}
