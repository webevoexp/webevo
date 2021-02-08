package edu.xxxxxx.webevo;

import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

public class OpenCVImageCompare {
	public static void main(String[] args) {
		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
         System.out.println("mat = " + mat.dump());
	}
	
//	public static void main(String[] args) 
//    {
//        //ISO-8859-1
//       
//        String imagePath1 = "/Users/me/Desktop/target22.png";
//        String imagePath2 = "/Users/me/Desktop/candidate2.png";
//        OpenCVImageCompare imageCompare = new OpenCVImageCompare();
//        double res=imageCompare.CompareAndMarkDiff(imagePath1, imagePath2);
//        System.out.println(res);
//        
//        
//    }
	


    private static boolean compareResult = false;
    private static String mark = "_compareResult";
   
    public static double CompareAndMarkDiff(String imagePath1, String imagePath2)
    { 
    	 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat1 = readMat(imagePath1);
        Mat mat2 = readMat(imagePath2);
        mat1 = Imgcodecs.imdecode(mat1, Imgcodecs.IMREAD_UNCHANGED);
        mat2 = Imgcodecs.imdecode(mat2, Imgcodecs.IMREAD_UNCHANGED);
        /*Mat mat1 = Imgcodecs.imread(imagePath1, Imgcodecs.IMREAD_UNCHANGED);
        Mat mat2 = Imgcodecs.imread(imagePath2, Imgcodecs.IMREAD_UNCHANGED);*/
        if(mat1.cols() == 0 || mat2.cols() == 0 || mat1.rows() == 0 || mat2.rows() == 0)
        {
           System.out.println("wrong path");
           return 0;
        }
        if(mat1.cols() != mat2.cols() || mat1.rows() != mat2.rows())
        {
            System.out.println("the size of two pic are different, can't compare!");
            return 0;
        }
        mat1.convertTo(mat1, CvType.CV_8UC1);
        mat2.convertTo(mat2, CvType.CV_8UC1);
        Mat mat1_gray = new Mat();
        Imgproc.cvtColor(mat1, mat1_gray, Imgproc.COLOR_BGR2GRAY);
        Mat mat2_gray = new Mat();
        Imgproc.cvtColor(mat2, mat2_gray, Imgproc.COLOR_BGR2GRAY);
        mat1_gray.convertTo(mat1_gray, CvType.CV_32F); 
        mat2_gray.convertTo(mat2_gray, CvType.CV_32F); 
        double result = Imgproc.compareHist(mat1_gray, mat2_gray, Imgproc.CV_COMP_CORREL);
//        if(result == 1)
//        {
//        	System.out.println("same pics");
//            compareResult = true;
//            return;
//        }
        double similarity=result;
      //  System.out.println("Similarity is :"+result);
        Mat mat_result = new Mat();
        //calculate gray scale
        Core.absdiff(mat1_gray, mat2_gray, mat_result);
        //convert grey pic to absoluate value
        mat_result.convertTo(mat_result, CvType.CV_8UC1);
        List<MatOfPoint> mat2_list = new ArrayList<MatOfPoint>();
        Mat mat2_hi = new Mat();
        //find skeleton map
        Imgproc.findContours(mat_result, mat2_list, mat2_hi, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat mat_result1 = mat1;
        Mat mat_result2 = mat2;
        //red marker
     //   System.out.println("mat2 list size:"+mat2_list.size());
        for (MatOfPoint matOfPoint : mat2_list) 
        {
            Rect rect = Imgproc.boundingRect(matOfPoint);
            Imgproc.rectangle(mat_result1, rect.tl(), rect.br(), new Scalar(0, 0, 255),2);
            Imgproc.rectangle(mat_result2, rect.tl(), rect.br(), new Scalar(0, 0, 255),2);
        }
        String fileName1 = getFileName(imagePath1);
        String targetPath1 = getParentDir(imagePath2)+File.separator+fileName1.replace(".", mark+".");
        String fileName2 = getFileName(imagePath2);
        String targetPath2 = getParentDir(imagePath2)+File.separator+fileName2.replace(".", mark+".");
//        System.out.println(targetPath1);
//        System.out.println(targetPath2);
        //output file for pic1
//        Imgcodecs.imwrite(targetPath1, mat_result1);
        //output file for pic2
//        Imgcodecs.imwrite(targetPath2, mat_result2);
        writeImage(mat_result1, targetPath1);
        writeImage(mat_result2, targetPath2);
        return similarity;
    }

    private static void writeImage(Mat mat, String outPutFile) 
    {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            ImageIO.write(bufImage, "png", new File(outPutFile));
        } catch (IOException | HeadlessException e) 
        {
            e.printStackTrace();
        }
    }

    private static String getFileName(String filePath) 
    {
        File f = new File(filePath);
        return f.getName();
    }

    private static String getParentDir(String filePath) 
    {
        File f = new File(filePath);
        return f.getParent();
    }

    private static Mat readMat(String filePath) 
    {
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(filePath);
            byte[] byt = new byte[(int) file.length()];
            int read = inputStream.read(byt);
            List<Byte> bs = convert(byt);
            Mat mat1 = Converters.vector_char_to_Mat(bs);
            return mat1;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Mat();
    }

    private static List<Byte> convert(byte[] byt)
    {
        List<Byte> bs = new ArrayList<Byte>();
        for (int i = 0; i < byt.length; i++) 
        {
            bs.add(i, byt[i]);
        }
        return bs;
    }
}

