package edu.xxxxxx.webevo;
  
import java.awt.Rectangle;  
import java.awt.image.BufferedImage;  
import java.io.*;  
import java.util.Iterator;  
import javax.imageio.ImageIO;  
import javax.imageio.ImageReadParam;  
import javax.imageio.ImageReader;  
import javax.imageio.stream.ImageInputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;  
  
public class ScreenPNG {  
    public static void cutJPG(InputStream input, OutputStream out, int x,  
            int y, int width, int height) throws IOException {  
        ImageInputStream imageStream = null;  
        try {  
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");  
          //  System.out.println("fdf: 1");
            ImageReader reader = readers.next();  
            imageStream = ImageIO.createImageInputStream(input);  
          //  System.out.println("fdf: 2");
            reader.setInput(imageStream, true);  
           
            ImageReadParam param = reader.getDefaultReadParam();  
        
            Rectangle rect = new Rectangle(x, y, width, height); 
           
            try {
            param.setSourceRegion(rect);  
            }catch(Exception e) {
            	 return;
            }
           
            BufferedImage bi = reader.read(0, param);  
           
            ImageIO.write(bi, "png", out);  
           
        } finally {  
            imageStream.close();  
        }  
    }  
      
      
    public static void cutPNG(InputStream input, OutputStream out, int x,  
            int y, int width, int height) throws IOException {  
        ImageInputStream imageStream = null;  
        try {  
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");  
            ImageReader reader = readers.next();  
            imageStream = ImageIO.createImageInputStream(input);  
            reader.setInput(imageStream, true);  
            ImageReadParam param = reader.getDefaultReadParam();  
              
            System.out.println(reader.getWidth(0));  
            System.out.println(reader.getHeight(0));  
              
            Rectangle rect = new Rectangle(x, y, width, height);  
            param.setSourceRegion(rect);  
            BufferedImage bi = reader.read(0, param);  
            ImageIO.write(bi, "png", out);  
        } finally {  
            imageStream.close();  
        }  
    }  
      
//    public static void cutImage(InputStream input, OutputStream out, String type,int x,  
//            int y, int width, int height) throws IOException {  
//        ImageInputStream imageStream = null;  
//        try {  
//            String imageType=(null==type||"".equals(type))?"png":type;  
//            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageType);  
//            ImageReader reader = readers.next();  
//            imageStream = ImageIO.createImageInputStream(input);  
//            reader.setInput(imageStream, true);  
//            ImageReadParam param = reader.getDefaultReadParam();  
//            Rectangle rect = new Rectangle(x, y, width, height);  
//            param.setSourceRegion(rect);  
//            BufferedImage bi = reader.read(0, param);  
//            ImageIO.write(bi, imageType, out);  
//        } finally {  
//            imageStream.close();  
//        }  
//    }  
  
      
    public static void main(String[] args) throws Exception {  
//    	System.setProperty("webdriver.gecko.driver", "C:\\Users\\me\\Desktop\\PhD\\me\\cases_new\\geckodriver.exe");
//        
////    	 System.setProperty("webdriver.chrome.driver", "/Users/me/Desktop/Research-CWRU/ImageCompare/chromedriver");
//    	
//    	DesiredCapabilities dc = new DesiredCapabilities();
//	      dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);	//accept popup automatically
//	      WebDriver driver = new FirefoxDriver(dc);
//
//         driver.get("file://C:\\Users\\me\\Desktop\\PhD\\me\\cases_new\\zoom\\2020.html");
//         
//         WebElement element=driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[1]/div[7]"));
//    	 
//         driver.manage().window().maximize();
//         String targetText=element.getText();
//         double targetX=element.getLocation().x;
//         double targetY=element.getLocation().y;
//         double targetW=element.getSize().getWidth();
//         double targetH=element.getSize().getHeight();
//         String targetTag=element.getTagName();
//         System.out.println("x:"+targetX+" Y:"+targetY+" W:"+targetW+" H:"+targetH);
//         
//         
//         ScreenPNG.cutJPG(new FileInputStream("C:\\Users\\me\\Desktop\\PhD\\me\\cases_new\\zoom\\new.png"),  
//                  new FileOutputStream("C:\\Users\\me\\Desktop\\PhD\\me\\cases_new\\zoom\\test.png"), (int)(targetX),(int)(targetY),(int)(targetW),(int)(targetH));  
         
         System.out.println(new File("C:\\Users\\me\\Desktop\\PhD\\me\\cases_new\\zoom\\new.png").length());

        
//        ScreenPNG.cutJPG(new FileInputStream("/Users/me/Desktop/old.png"),  
//                new FileOutputStream("/Users/me/Desktop/test3.png"), 513*2,(int) (2749*2),(int)(153*2),(int) (40*2));  
       
    }  
}  