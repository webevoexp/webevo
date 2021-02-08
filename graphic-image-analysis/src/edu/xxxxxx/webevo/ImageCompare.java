package edu.xxxxxx.webevo;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
//import org.opencv.core.Core;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.core.MatOfPoint;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.utils.Converters;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
/*
 * FP: actually not changed, api say it does change, found candidate, it does not change
 * FN: actually changed, api say it does not change, location changed, name changed etc.., found candidate, 
 * 
 * 1. look for same tag as target
 * 2. if no similar pic,look for same text
 * */


public class ImageCompare {

	 static List<String> allXpaths = new ArrayList<>();
     static List<String> allTypes =new ArrayList<>();
     static List<String> allResults=new ArrayList<>();
     static String newURL="";
     static String oldURL="";
     static String newImage="";
     static String oldImage="";
     static String targetText;
     static String targetTag;
     static int targetX;
     static int targetY;
     static int targetW;
     static int targetH;
     static String candidateLocation;
     static String finalCandidate;
     static String targetXpath;
     static int targetName;
     static Boolean isPresent;
     static String targetLocation;
     static int TotalChange;
     static int FpChange;
     static int FnChange;
    public static void main(String[] args) 
    {
    	//  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
          System.setProperty("webdriver.chrome.driver", "/Users/me/Desktop/Research-CWRU/ImageCompare/chromedriver");
          WebDriver driver = new ChromeDriver();
          
          //(1)init
          newURL="file:///Users/me/Desktop/research_test/10/2019.html";
          oldURL="file:///Users/me/Desktop/research_test/10/2016.html";
          newImage="/Users/me/Desktop/research_test/10/new.png";
          oldImage="/Users/me/Desktop/research_test/10/old.png";
          String csvPath="/Users/me/Desktop/research_test/10/ds10_results.csv";
          
        //(2)read csv
    	readCSV(csvPath);
    	System.out.println("finished read csv file.");
    	System.out.println();
    	
    	//(4)Calculate accuracy without algorithm
    	Map<String,Integer> map=new HashMap<>();
    	for(int i=0;i<allResults.size();i++) {
    		if(allResults.get(i).equals("TP")) {map.put("TP", map.getOrDefault("TP", 0)+1);}
    		if(allResults.get(i).equals("TN")) {map.put("TN", map.getOrDefault("TN", 0)+1);}
    		if(allResults.get(i).equals("FP")) {map.put("FP", map.getOrDefault("FP", 0)+1);}
    		if(allResults.get(i).equals("FN")) {map.put("FN", map.getOrDefault("FN", 0)+1);}
    	}
     	int TP=map.get("TP")!=null?map.get("TP"):15;
     	int TN=map.get("TN")!=null?map.get("TN"):0;
     	int FP=map.get("FP")!=null?map.get("FP"):0;
     	int FN=map.get("FN")!=null?map.get("FN"):0;
     //	System.out.println("Accuracy before algorithm: "+((double)(TP+TN)/(TP+TN+FP+FN)));
        System.out.println("TP | TN | FP | FN | ACCURACY | PRECISION | RECALL | F1");
        double PRECISION=(double)(TP)/(TP+FP);
        double RECALL=(double)(TP)/(TP+FN);
        double F1=2*(double)(PRECISION*RECALL)/(PRECISION+RECALL);
        System.out.println(TP+" | "+TN+" | "+FP+" | "+FN+" | "+(double)(TP+TN)/(TP+TN+FP+FN)+" | "+PRECISION+" | "+RECALL+" | "+F1);
    	 try {
             FileUtils.deleteDirectory(new File(targetLocation));
             new File(targetLocation).mkdirs();
             }catch(Exception e) {
             	//System.out.println(e);
             }
    	for(int i=0;i<allResults.size();i++) {
    		targetName=i;
    		
      	  if(allResults.get(i).equals("FN")||allResults.get(i).equals("FP")) {
      		System.out.println("*************************CASE "+i+" START**************************");
      		//(3)processing target image
         // 	driver.get(oldURL);
      		System.out.println("Current Case is "+allResults.get(i));
          	targetXpath=allXpaths.get(i);
          	String targetURL="";
          	if(isXpathExist(targetXpath,driver,oldURL)){
          		//System.out.println(isXpathExist(targetXpath,driver,oldURL));
          		targetURL=oldURL;
          	}else if(isXpathExist(targetXpath,driver,newURL)){//new node added in the new webpage
          		//System.out.println(isXpathExist(targetXpath,driver,newURL));
          		targetURL=newURL;
          	}else {
          		System.out.println("the xpath does not exist neither in old url nor new url");
          		continue;
          	}
          	System.out.println("++++++++++++++++++++++++++++++++++++++");
          	System.out.println("target xpath is "+targetXpath);
          	System.out.println("target url is "+targetURL);
          	try {
          	driver.get(targetURL);
          	}catch(TimeoutException e) {
          		continue;
          	}
           
            targetLocation="/Users/me/Desktop/research_test/target_img/";
            if(targetURL==oldURL) {
            	 processingTargetImage(driver,targetXpath,targetLocation,oldImage,i);
            }else {
            	 processingTargetImage(driver,targetXpath,targetLocation,newImage,i);
            }
           
            System.out.println("Saved target image in "+targetLocation);
            System.out.println("++++++++++++++++++++++++++++++++++++++");
            
            if(isPresent) {
            	 //(4)processing candidate images
            	String candidateURL="";
            	if(targetURL==oldURL) {
            		candidateURL=newURL;
            	}else {
            		candidateURL=oldURL;
            	}
            	//driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
            	try {
            		driver.get(candidateURL);
            	}catch(TimeoutException e) {
            		continue;
            		//System.out.println("adaptation system time outted!!");
            	}
                driver.manage().window().maximize();
                candidateLocation="/Users/me/Desktop/research_test/candidate_img/";
          
                new File(candidateLocation+"target"+i+"_folder").mkdirs();
                if(candidateURL==oldURL) {
                	processingCandidatesImage(driver,candidateLocation+"target"+i+"_folder/",oldImage);
                }else {
                	processingCandidatesImage(driver,candidateLocation+"target"+i+"_folder/",newImage);
                }
                
                if(finalCandidate.equals("")) {
                	System.out.println("no similar pic found");
                }
                
                System.out.println();
               
                //(5)Comparing target with candidates
//       	     System.out.println("starting comparing: ");
//       	     finalCandidate="";
//       	   //  compareImage();
       	     if(!finalCandidate.equals("")) {
       	    	 if(allResults.get(i).contentEquals("FN")) {
       	    		FnChange++;
       	    	 }else if(allResults.get(i).contentEquals("FP")) {
       	    		FpChange++;
       	    	 }
       	    	TotalChange++;
       	    	 System.out.println("Found Result for "+allXpaths.get(i)+"!");
       	     }else {
       	    	 System.out.println("No similar pic found!");
       	     }
       	     
       	     
            }
            //delete useless candidate folder
//       	 try {
//          FileUtils.deleteDirectory(new File(candidateLocation+"target"+i+"_folder"));
//          System.out.println("Deleted ");
//          }catch(Exception e) {
//          	System.out.println(e);
//          }
   	      System.out.println("*************************CASE "+i+" END**************************");
      	  }
      //	  System.out.println();
      	  
      	
        }
//    	System.out.println("FP/(TP+TN+FP+FN),FpChange,total after algorithm: "+((double)(FP+FpChange)/(TP+TN+FP+FN))+","+(double)FpChange+","+TotalChange);
//    	System.out.println("FN/(TP+TN+FP+FN),FnChange after algorithm: "+((double)(FN+FnChange)/(TP+TN+FP+FN))+","+(double)FnChange);
//    	System.out.println("Accuracy after algorithm: "+((double)(TP+TN+TotalChange)/(TP+TN+FP+FN)));
    	
    	
    	 System.out.println("TP | TN | FP | FN | ACCURACY | PRECISION | RECALL | F1");
         PRECISION=(double)(TP+FpChange)/(TP+FP);
         RECALL=(double)(TP+FpChange)/(TP+FN+FpChange-FnChange);
         F1=2*(double)(PRECISION*RECALL)/(PRECISION+RECALL);
         System.out.println((TP+FpChange)+" | "+(TN+FnChange)+" | "+(FP-FpChange)+" | "+(FN-FnChange)+" | "+(double)(TP+TN+TotalChange)/(TP+TN+FP+FN)+" | "+PRECISION+" | "+RECALL+" | "+F1);
    	 
         driver.quit();
       
    }
    
    
    public static void processingTargetImage(WebDriver driver,String xpath,String location,String image,int index) {
    	driver.manage().window().maximize();
    	 try {
//    	FileUtils.deleteDirectory(new File(location));
//        new File(location).mkdirs();
        isPresent = driver.findElements(By.xpath(xpath)).size() > 0;
        System.out.println("is current xpath exist?: "+isPresent);
    	 if(!isPresent) {
    		 return;
    	 }
    	 WebElement element=driver.findElement(By.xpath(xpath));
    	 
         driver.manage().window().maximize();
         targetText=element.getText();
         targetX=element.getLocation().x;
         targetY=element.getLocation().y;
         targetW=element.getSize().getWidth();
         targetH=element.getSize().getHeight();
         targetTag=element.getTagName();
      //   System.out.println(targetText+","+targetTag+","+xpath);
         
         System.out.println("starting saving target image...");
         System.out.println("Current target element: "+targetText+","+targetX+","+targetY+","+targetW+","+targetH);
         //saveTarget
	      
	       cutJPG(new FileInputStream(image),  
	                  new FileOutputStream(location+"target"+index+".png"), targetX*2,targetY*2,targetW*2,targetH*2); 
	       }catch(Exception e) {
	    	   System.out.println("errors when finding target image!");
	       }

    	 
      
   }
    
    public static void processingCandidatesImage(WebDriver driver,String location,String image) {
    	driver.manage().window().maximize();

        int count=1;
        
        //(1)save all the same tag imgs
        System.out.println("saving all element with same tag..."+ targetTag);
        List<WebElement> eletestTag=driver.findElements(By.tagName(targetTag));
        String targetPath="/Users/me/Desktop/research_test/target_img/target"+targetName+".png";
        try {
        		System.out.println("# of elements "+eletestTag.size());
    	       for(int i=0;i<eletestTag.size();i++) {
    	    	   finalCandidate="";
    	    	   int candidateX=eletestTag.get(i).getLocation().x;
    	           int candidateY=eletestTag.get(i).getLocation().y;
    	           int candidateW=eletestTag.get(i).getSize().getWidth();
    	           int candidateH=eletestTag.get(i).getSize().getHeight();
    	    	   String candidateText=eletestTag.get(i).getText();
    	    	   if(!candidateText.equals("")){
    	    	   System.out.println();
    	    	   System.out.println("Candidate "+count+": ");
    	    	   System.out.println("Candidiate tag: "+eletestTag.get(i).getTagName()+", "+eletestTag.get(i).getText());

        	   ScreenPNG.cutJPG(new FileInputStream(image),
        			   new FileOutputStream(location+"candidate"+(count)+".png"), candidateX*2,candidateY*2,candidateW*2,candidateH*2); 
        	   
        	   System.out.println("starting comparing: ");
        	   String tempTargetPath="/Users/me/Desktop/research_test/target_img/target"+targetName+"_"+count+".png";
        	   copyFile(targetPath,tempTargetPath);
        	  
        	  
        	   compareImage(tempTargetPath,location+"candidate"+(count)+".png");
        	   if(!finalCandidate.equals("")) {
               	return;
              }
        	   File file = new File(tempTargetPath); 
               
               file.delete();
               
        	   count++;
         	      
    	    	}
    	    	   
    	   }
            }catch(Exception e) {
            	//continue;
           	 System.out.println("errors when finding candidates images!");
         }
        
        //(2)look for same text
        if(!finalCandidate.equals("")) {
        	return;
        }
        System.out.println("starting looking for same text: ");
        System.out.println("target text:|"+targetText+"|");
        List<WebElement> eletest=null;
        try {
        eletest=driver.findElements(By.xpath("//*[text()='"+targetText+"']"));
        System.out.println(eletest.size()+" , "+targetText);
        }catch(InvalidSelectorException e) {
        	
        }
      
        System.out.println("target tag is :"+ targetTag);
        try {
        //  FileUtils.deleteDirectory(new File(location));
        //  new File(location).mkdirs();
	       for(int i=0;i<eletest.size();i++) {
	    	   System.out.println("candidate text:|"+eletest.get(i).getText()+"|");
	    	   if(eletest.get(i).getText().equals(targetText)) {            //same title
	    		   finalCandidate="same text";
	    		   System.out.println("found same text element1");
	    		   break;
	    		   
	    	   }
	    	
	       }
	        
        }catch(Exception e) {
       	 System.out.println("errors when finding candidates images!");
        }
	       System.out.println("got all candidates");
	       System.out.println("Saved all candidates images!");
    	
    }
   
   
    
    
    public static void compareImage(String targetPath,String candidatePath) {
    	if(!new File(candidatePath).canRead()||new File(candidatePath).length()==0){
	   		 return;
	   	}
    	System.out.println("target path: "+targetPath);
    	System.out.println("candidate path: "+candidatePath);
    	int totalCandidate=new File(candidateLocation).list().length;
	    try {
//	        File picture1 = new File(targetPath);
//	        File picture2 = new File(candidatePath);
//	        BufferedImage sourceImg1 =ImageIO.read(new FileInputStream(picture1));
//	        BufferedImage sourceImg2 =ImageIO.read(new FileInputStream(picture1));
//	        int newWidth=Math.min(sourceImg1.getWidth(), sourceImg2.getWidth());
//	        int newHeight=Math.min(sourceImg1.getHeight(), sourceImg2.getHeight());
	    	//resize target and candidate
//	        System.out.println("Resizing the target img and candidate img...");
//	        int t1=targetPath.lastIndexOf('/');
//	 	    int t2=targetPath.lastIndexOf('.');
//	        int c1=candidatePath.lastIndexOf('/');
//	 	    int c2=candidatePath.lastIndexOf('.');
//	 	    System.out.println(targetPath+" "+targetPath.substring(0,t1+1)+" "+targetPath.substring(t1+1,t2));
//	 	    System.out.println(candidatePath+" "+candidatePath.substring(0,c1+1)+" "+candidatePath.substring(c1+1,c2));
//	 	    UploadImg.uploadImg(targetPath,targetPath.substring(0,t1+1),targetPath.substring(t1+1,t2),newWidth,newHeight);
//	 	    UploadImg.uploadImg(candidatePath,candidatePath.substring(0,c1+1),candidatePath.substring(c1+1,c2),newWidth,newHeight);
//	        System.out.println("Done resizing!");
	        System.out.println("Comparing two pics...");
	        FingerPrint fp1 = new FingerPrint(ImageIO.read(new File(targetPath)));
	        FingerPrint fp2;
	        fp2 = new FingerPrint(ImageIO.read(new File(candidatePath)));
            	 
			  double fingerRes=fp1.compare(fp2);
			//  double cvRes=OpenCVImageCompare.CompareAndMarkDiff(targetPath, candidatePath);
              System.out.println("Compaing current two images...");
            //  double temp=fingerRes>cvRes?fingerRes:cvRes;;
              if(fingerRes>=0.85) {
        
            		//  System.out.println(temp);
            		  System.out.println("the most similiar picture is: "+"candidate "+candidatePath+": "+fingerRes);
            		  finalCandidate="candidate "+candidatePath;
            		  return;
              }
	        System.out.println("original images similaity: "+fingerRes);
	        //grey scale
	        System.out.println("Grey Scaling the target img and candidate img...");
	        converToGrey.convert(targetPath,targetPath);
	        converToGrey.convert(candidatePath,candidatePath);
	        System.out.println("Done Grey scale for target and candiate");
	        
	        
	        fp1 = new FingerPrint(ImageIO.read(new File(targetPath)));
	        fp2 = new FingerPrint(ImageIO.read(new File(candidatePath)));
            	 
			 fingerRes=fp1.compare(fp2);
		//	   cvRes=OpenCVImageCompare.CompareAndMarkDiff(targetPath, candidatePath);
              System.out.println("Compaing current two images...");
         //      temp=fingerRes>cvRes?fingerRes:cvRes;;
              if(fingerRes>=0.85) {
        
            		//  System.out.println(temp);
            		  System.out.println("the most similiar picture is: "+"candidate "+candidatePath+": "+fingerRes);
            		  finalCandidate="candidate "+candidatePath;
            		  return;
              }
              System.out.println("Current simility is "+fingerRes+", not consider as same pics");
              System.out.println("Trying reverse colors for candidate...");
              ImgInverse.imgeInverse(candidatePath, candidatePath);
              System.out.println("Done reverse color for candidate");
              
              //reverse color 
              System.out.println("Comparing two pics...");
              fp1 = new FingerPrint(ImageIO.read(new File(targetPath)));
  	          fp2 = new FingerPrint(ImageIO.read(new File(candidatePath)));
              fingerRes=fp1.compare(fp2);
		//	  cvRes=OpenCVImageCompare.CompareAndMarkDiff(targetPath, candidatePath);
		//	  temp=fingerRes>cvRes?fingerRes:cvRes;;
              if(fingerRes>=0.85) {
        
            		//  System.out.println(temp);
            		  System.out.println("the most similiar picture is: "+"candidate "+candidatePath+": "+fingerRes);
            		  finalCandidate="candidate "+candidatePath;
            		  return;
              }  
              System.out.println("reversed images similaity: "+fingerRes);
              
        } catch (IOException e) {
            e.printStackTrace();
        }catch(NullPointerException e1) {
        	System.out.println("can not read target image!");
        }
    }  
    public static void readCSV(String path) {
    	File csv = new File(path);   
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = "";
        String xpath = "";
        String type="";
        String res="";
       
        try {
            while ((line = br.readLine()) != null) 
            {
            	String[] temp=line.split(",");
                xpath=temp[0];
                type=temp[1];
                res=temp[6];
                
              //  System.out.println(xpath+","+type+","+res);
              
                allXpaths.add(xpath);
                allTypes.add(type);
                allResults.add(res);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    public static void cutJPG(InputStream input, OutputStream out, int x,  
            int y, int width, int height) throws IOException {  
        ImageInputStream imageStream = null;  
        try {  
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");  
            ImageReader reader = readers.next();  
            imageStream = ImageIO.createImageInputStream(input);  
            reader.setInput(imageStream, true);  
            ImageReadParam param = reader.getDefaultReadParam(); 
            Rectangle rect = new Rectangle(x, y, width, height);  
            param.setSourceRegion(rect);  
            BufferedImage bi = reader.read(0, param);  
            ImageIO.write(bi, "png", out);  
        } finally {  
            imageStream.close();  
        }  
    }  
    public static boolean isXpathExist(String xpath,WebDriver driver,String url) {
    	driver.manage().window().maximize();
    	
    	try {
    		driver.get(url);
    	}catch(TimeoutException e) {
    		return false;
    	}
    	
    	try {driver.findElements(By.xpath(xpath));}
    	catch(InvalidSelectorException e) {
    		return false;
    	}
    	
    	return driver.findElements(By.xpath(xpath)).size() > 0;
    }
    public static void copyFile(String src,String target)
	{	
		File srcFile = new File(src);  
		   File targetFile = new File(target);  
		   try {  
		       InputStream in = new FileInputStream(srcFile);   
		       OutputStream out = new FileOutputStream(targetFile);  
		       byte[] bytes = new byte[1024];  
		       int len = -1;  
		       while((len=in.read(bytes))!=-1)
		       {  
		           out.write(bytes, 0, len);  
		       }  
		       in.close();  
		       out.close();  
		   } catch (FileNotFoundException e) {  
		       e.printStackTrace();  
		   } catch (IOException e) {  
		       e.printStackTrace();  
		   }  
		   System.out.println("succeed copied pic"); 


	}
 
}


