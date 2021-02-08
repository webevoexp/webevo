package edu.xxxxxx.webevo;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CountElements {

	public static void main(String[] args) {

//		 System.setProperty("webdriver.chrome.driver", "/Users/me/Desktop/Research-CWRU/ImageCompare/chromedriver");
//		 System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
//		 ChromeOptions options = new ChromeOptions();
// 
//		 options.addArguments("disable-features=NetworkService");
//		 options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//         WebDriver driver = new ChromeDriver(options);
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\me\\Desktop\\PhD\\me\\cases_new\\geckodriver.exe");
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);	//accept popup automatically
      	WebDriver driver = new FirefoxDriver(dc);
        
  	String casePath="file://C:\\Users\\me\\Desktop\\PhD\\me\\cases_new\\classdojo\\";
      String oldURL=casePath+"2017.html";
   
      driver.get(oldURL);
      
     
     driver.manage().window().maximize();
    
     List<WebElement> eletest=new ArrayList<>();
     String[] tags= {"a",
    		 "abbr",
    		 "acronym",
    		 "address",
    		 "applet",
    		 "area",
    		 "article",
    		 "aside",
    		 "audio",
    		 "b",
    		 "base",
    		 "basefont",
    		 "bdi",
    		 "bdo",
    		 "big",
    		 "blockquote",
    		 "body",
    		 "br",
    		 "button",
    		 "canvas",
    		 "caption",
    		 "center",
    		 "cite",
    		 "code",
    		 "col",
    		 "colgroup",
    		 "data",
    		 "datalist",
    		 "dd",
    		 "del",
    		 "details",
    		 "dfn",
    		 "dialog",
    		 "dir",
    		 "div",
    		 "dl",
    		 "dt",
    		 "em",
    		 "embed",
    		 "fieldset",
    		 "figcaption",
    		 "figure",
    		 "font",
    		 "footer",
    		 "form",
    		 "frame",
    		 "frameset",
    		 "h1", "h2","h3","h4","h5", "h6",
    		 "head",
    		 "header",
    		 "hr",
    		 "html",
    		 "i",
    		 "iframe",
    		 "img",
    		 "input",
    		 "ins",
    		 "kbd",
    		 "label",
    		 "legend",
    		 "li",
    		 "link",
    		 "main",
    		 "map",
    		 "mark",
    		 "meta",
    		 "meter",
    		 "nav",
    		 "noframes",
    		 "noscript",
    		 "object",
    		 "ol",
    		 "optgroup",
    		 "option",
    		 "output",
    		 "p",
    		 "param",
    		 "picture",
    		 "pre",
    		 "progress",
    		 "q",
    		 "rp",
    		 "rt",
    		 "ruby",
    		 "s",
    		 "samp",
    		 "script",
    		 "section",
    		 "select",
    		 "small",
    		 "source",
    		 "span",
    		 "strike",
    		 "strong",
    		 "style",
    		 "sub",
    		 "summary",
    		 "sup",
    		 "svg",
    		 "table",
    		 "tbody",
    		 "td",
    		 "template",
    		 "textarea",
    		 "tfoot",
    		 "th",
    		 "thead",
    		 "time",
    		 "title",
    		 "tr",
    		 "track",
    		 "tt",
    		 "u",
    		 "ul",
    		 "var",
    		 "video",
    		 "wbr"};
     
     	 
     	for(int i=0;i<tags.length;i++) { 
     		eletest.addAll(driver.findElements(By.tagName(tags[i])));     		
     	}
         //System.out.println("total "+eletest.size()+" elements!");  
     	//List<String> lineLst = new ArrayList<String>();
     	for (int j =0; j<eletest.size(); j++) {
 			System.out.println();
 			WebElement element = eletest.get(j);
 			if(element.getText().trim().length()!=0) {
 				System.out.println(element.getText());
 				//lineLst.add(String.valueOf(element.getText()));
 			}
 			
 		}
//     	Path file = Paths.get(casePath+"text.txt");
//		try {
//			Files.write(file, lineLst, StandardCharsets.UTF_8);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//         driver.close();

	}
}
