package edu.xxxxxx.webevo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TakeScreenshot {

	public static void main(String[] args) throws IOException { // TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "/Users/me/Desktop/Research-CWRU/ImageCompare/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("/Users/me/Desktop/case1.html"); // locating amazon logo
		WebElement logo = driver.findElement(By.id("nav-logo")); // Get entire page screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullScreen = ImageIO.read(screenshot); // Find location of the webelement logo on the page
		Point location = logo.getLocation(); // Find width and height of the located element logo
		int width = logo.getSize().getWidth();
		int height = logo.getSize().getHeight();
		// Now the main point, which is cropping the full image to get only the logo
		// screenshot
		BufferedImage logoImage = fullScreen.getSubimage(location.getX(), location.getY(), width, height);
		ImageIO.write(logoImage, "png", screenshot); // copy the file to the desired location
		FileUtils.copyFile(screenshot, new File("path of file"));
	}

}