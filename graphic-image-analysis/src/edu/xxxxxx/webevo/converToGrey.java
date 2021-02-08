package edu.xxxxxx.webevo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class converToGrey {

    public static void main(String... args) {
        converToGrey cc=new converToGrey();
        String path="/Users/me/Desktop/resize1.png";
        String outputpath="/Users/me/Desktop/grey1.png";
       
        cc.convert(path,outputpath);

    }
   
    public static void convert(String path,String outputPath) {
    	try {

            File input = new File(path);
            BufferedImage image = ImageIO.read(input);
            BufferedImage result=null;
            Graphics2D graphic=null;
            try {
            result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

           
            graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
           
            

            for (int i = 0; i < result.getHeight(); i++) {
                for (int j = 0; j < result.getWidth(); j++) {
                    Color c = new Color(result.getRGB(j, i));
                    int red = (int) (c.getRed() * 0.299);
                    int green = (int) (c.getGreen() * 0.587);
                    int blue = (int) (c.getBlue() * 0.114);
                    Color newColor = new Color(
                            red + green + blue,
                            red + green + blue,
                            red + green + blue);
                    result.setRGB(j, i, newColor.getRGB());
                }
            }

            File output = new File(outputPath);
            ImageIO.write(result, "png", output);
 }catch(NullPointerException e) {
            	
            }

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

}