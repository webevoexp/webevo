package edu.xxxxxx.webevo;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImgInverse {
	
	public static void main(String[] args) {
        //
        String input="/Users/me/Desktop/grey1.png";  //
       
        String output="/Users/me/Desktop/reverse1.png";  //
        imgeInverse(input,output);
    }
 
	public static void imgeInverse(String input,String output) {
		 BufferedImage bi=file2img(input);  //
	        BufferedImage bii=img_inverse(bi);
	        img2file(bii,"png",output);  //
	}

	public static BufferedImage img_inverse(BufferedImage imgsrc) {
        try {
            //
            BufferedImage back=new BufferedImage(imgsrc.getWidth(), imgsrc.getHeight(),BufferedImage.TYPE_INT_RGB);
            int width = imgsrc.getWidth();  
            int height = imgsrc.getHeight();  
            for (int i = 0; i < height; i++) { 
                for (int j = 0; j < width; j++) {  
                    int pixel = imgsrc.getRGB(j, i); 
                    back.setRGB(j,i,0xFFFFFF-pixel);
                }
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	//
    public static BufferedImage file2img(String imgpath) {
        try {
            BufferedImage bufferedImage=ImageIO.read(new File(imgpath));
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //
    public static void img2file(BufferedImage img,String extent,String newfile) {
        try {
            ImageIO.write(img, extent, new File(newfile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 
}
