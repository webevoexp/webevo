package edu.xxxxxx.webevo.test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class covertColor {
	 
	public static void main(String[] args) {
		String path="/Users/me/Desktop/1.png";
		convertColor(path);
 
	}
	public static void convertColor(String path) {
		try{
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			
			Mat src=Imgcodecs.imread(path);
			//
			if(src.empty()){
				throw new Exception("no file");
			}
			
			Mat dst = src.clone();
			//
			
			Core.bitwise_not(src,dst);
			//
			Imgcodecs.imwrite(path, dst);
		}catch(Exception e){
			System.out.println("exception：" + e);
		}
	}
 
}
