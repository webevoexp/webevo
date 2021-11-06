package edu.xxxxxx.webevo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class StringSimilarity {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "/Users/me/Desktop/Research-CWRU/ImageCompare/chromedriver");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("enable-automation");
		// options.addArguments("--headless");
		// options.addArguments("--window-size=1920,1080");
		// options.addArguments("--no-sandbox");
		// options.addArguments("--disable-extensions");
		// options.addArguments("--dns-prefetch-disable");
		// options.addArguments("--disable-gpu");
		// options.addArguments("enable-features=NetworkServiceInProcess");
		options.addArguments("disable-features=NetworkService");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		WebDriver driver = new ChromeDriver(options);

		String oldURL = "file:///Users/me/Desktop/research_test/1/2016.html";
		String newURL = "file:///Users/me/Desktop/research_test/1/2019.html";
		driver.get(oldURL);

		WebElement elementOld = driver.findElement(By.xpath("/html/body/div[4]/div/a[3]"));
		driver.manage().window().maximize();
		String oldText = elementOld.getText();
		System.out.println("oldText:" + elementOld.getLocation().x + "," + elementOld.getLocation().y + ","
				+ elementOld.getSize().width + "," + elementOld.getSize().height);
		System.out.println(oldText);

		// driver.get(newURL);
		// WebElement
		// elementNew=driver.findElement(By.xpath("/html/body/div[9]/div[7]/div[4]/div"));
		// driver.manage().window().maximize();
		// String newText=elementNew.getText();
		// System.out.println("newText:");
		// System.out.println(newText);
		//
		//
		// String str = "I like Java7";
		// String target = "I like Java8" ;
		//
		// System.out.println("similarityRatio="+ getSimilarityRatio(oldText, newText));
	}

	private static int compare(String str, String target) {
		int d[][];
		int n = str.length();
		int m = target.length();
		int i;
		int j;
		char ch1;
		char ch2;
		int temp;
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}

		for (i = 1; i <= n; i++) {
			ch1 = str.charAt(i - 1);
			//
			for (j = 1; j <= m; j++) {
				ch2 = target.charAt(j - 1);
				if (ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}

				//
				d[i][j] = Math.min(d[i - 1][j] + 1, Math.min(d[i][j - 1] + 1, d[i - 1][j - 1] + temp));
			}
		}
		return d[n][m];
	}

	private int min(int one, int two, int three) {
		return (one = one < two ? one : two) < three ? one : three;
	}

	public static float getSimilarityRatio(String str, String target) {
		return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
	}

}
