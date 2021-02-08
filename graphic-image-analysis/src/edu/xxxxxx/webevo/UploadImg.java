package edu.xxxxxx.webevo;
import java.awt.geom.AffineTransform;  
import java.awt.image.AffineTransformOp;  
import java.awt.image.BufferedImage;  
import java.io.File;  
  
import javax.imageio.ImageIO;  
public class UploadImg {  
    String fromFileStr;  
    String saveToFileStr;  
    String sysimgfile;  
    int width;  
    int height;  
    String suffix;  
    /** 
     * @param fromFileStr 
     *            
     * @param saveToFileStr 
     *            
     * @param sysimgfilenNow 
     *           
     *  
     */  
    public UploadImg(String fromFileStr, String saveToFileStr, String sysimgfile,String suffix,int width,int height) {  
        this.fromFileStr = fromFileStr;  
        this.saveToFileStr = saveToFileStr;  
        this.sysimgfile = sysimgfile;  
        this.width=width;  
        this.height=height;  
        this.suffix=suffix;  
    }  
    public boolean createThumbnail() throws Exception {  
    	
        //  
        // String fileExtNmae="";  
        File F = new File(fromFileStr);  
        if (!F.isFile())  
            throw new Exception(F  
                    + " is not image file error in CreateThumbnail!");  
        File ThF = new File(saveToFileStr, sysimgfile +"."+suffix);  
        BufferedImage buffer = ImageIO.read(F);  
        /* 
         * 
         */  
        int w= buffer.getWidth();  
        int h=buffer.getHeight();  
        double ratiox = 1.0d;  
        double ratioy = 1.0d;  
          
        ratiox= w * ratiox / width;  
        ratioy= h * ratioy / height;  
          
        if( ratiox >= 1){  
            if(ratioy < 1){  
                ratiox = height * 1.0 / h;  
            }else{  
                if(ratiox > ratioy){  
                    ratiox = height * 1.0 / h;  
                }else{  
                    ratiox = width * 1.0 / w;  
                }  
            }  
        }else{  
            if(ratioy < 1){  
                if(ratiox > ratioy){  
                    ratiox = height * 1.0 / h;  
                }else{  
                    ratiox = width * 1.0 / w;  
                }  
            }else{  
                ratiox = width * 1.0 / w;  
            }  
        }  
        /* 
         * 
         */  
        AffineTransformOp op = new AffineTransformOp(AffineTransform  
                .getScaleInstance(ratiox, ratiox), null);  
        buffer = op.filter(buffer, null);  
        // 
        buffer = buffer.getSubimage((buffer.getWidth()-width)/2, (buffer.getHeight() - height) / 2, width, height);  
        try {  
            ImageIO.write(buffer, suffix, ThF);  
        } catch (Exception ex) {  
            throw new Exception(" ImageIo.write error in CreatThum.: "  
                    + ex.getMessage());  
        }  
        return (true);  
    }  
    public static void uploadImg(String input,String outputPath,String outputName,int w,int h) {
    	UploadImg UI;  
        boolean ss = false;  
        try {  
            UI = new UploadImg(input, outputPath,outputName,"png",w,h);  
            ss = UI.createThumbnail();  
            if (ss) {  
                System.out.println("Success");  
            } else {  
                System.out.println("Error");  
            }  
        } catch (Exception e) {  
            System.out.print(e.toString());  
        }  
    }
    public static void main(String[] args) {  
        String input="/Users/me/Desktop/1.png";
        String output="/Users/me/Desktop/";
        String outputName="resize1";
       // uploadImg(input,output,outputName);
    }
	 
} 