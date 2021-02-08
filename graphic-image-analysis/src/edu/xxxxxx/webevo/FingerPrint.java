package edu.xxxxxx.webevo;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
 
import javax.imageio.ImageIO;
 
/**
 * 
 * Perceptual hash algorithm, generates a 'fingerprint' for each pictures, and then compare the fingerprint
 * 
 */
public final class FingerPrint {
 
    public static void main(String[] args) {
    	//original:0.33
    	//resize:0.25
    	//grey scale:0.25
    	//reverse 0.78
    	 
    	String target="/Users/me/Desktop/target.png";
    	String candidate="/Users/me/Desktop/candidate.png";
    	System.out.println(" "+compare(target,candidate));
        
    }
    public  static double compare(String targetPath,String candidatePath) {
    	double res=0;
    	try {
            FingerPrint fp1 = new FingerPrint(ImageIO.read(new File(targetPath)));
            FingerPrint fp2 = new FingerPrint(ImageIO.read(new File(candidatePath)));
            double fingerRes=fp1.compare(fp2);
            System.out.println("Comparing current two images...");
            //  double temp=fingerRes>cvRes?fingerRes:cvRes;;
              if(fingerRes>=0.98) {
        
            		//  System.out.println(temp);
            		  System.out.println("the most similiar picture is: "+"candidate "+candidatePath+": "+fingerRes);
            		//  finalCandidate="candidate "+candidatePath;
            		  return 0;
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
              if(fingerRes>=0.98) {
        
            		//  System.out.println(temp);
            		  System.out.println("the most similiar picture is: "+"candidate "+candidatePath+": "+fingerRes);
            		//  finalCandidate="candidate "+candidatePath;
            		  return 0;
              }
              System.out.println("Current simility is "+fingerRes+", not consider as same pics");
              System.out.println("Trying reverse colors for candidate...");
          //    ImgInverse.imgeInverse(candidatePath, candidatePath);
              System.out.println("Done reverse color for candidate");
              
              //reverse color 
              System.out.println("Comparing two pics...");
              fp1 = new FingerPrint(ImageIO.read(new File(targetPath)));
  	          fp2 = new FingerPrint(ImageIO.read(new File(candidatePath)));
              fingerRes=fp1.compare(fp2);
		//	  cvRes=OpenCVImageCompare.CompareAndMarkDiff(targetPath, candidatePath);
		//	  temp=fingerRes>cvRes?fingerRes:cvRes;;
              if(fingerRes>=0.98) {
        
            		//  System.out.println(temp);
            		  System.out.println("the most similiar picture is: "+"candidate "+candidatePath+": "+fingerRes);
            		//  finalCandidate="candidate "+candidatePath;
            		  return 0;
              }  
              System.out.println("reversed images similaity: "+fingerRes);
        	  
            
          } catch (IOException e) {
              e.printStackTrace();
          }
    	//System.out.print(res);
    	return res;
    }
 
    /**
     * 
     */
    private static final int HASH_SIZE = 16;
    /**
     * 
     */
    private final byte[] binaryzationMatrix;
 
    public FingerPrint(byte[] hashValue) {
        if (hashValue.length != HASH_SIZE * HASH_SIZE)
            throw new IllegalArgumentException(String.format(
                    "length of hashValue must be %d", HASH_SIZE * HASH_SIZE));
        this.binaryzationMatrix = hashValue;
    }
 
    public FingerPrint(String hashValue) {
        this(toBytes(hashValue));
    }
 
    public FingerPrint(BufferedImage src) {
        this(hashValue(src));
    }
 
    private static byte[] hashValue(BufferedImage src) {
        BufferedImage hashImage = resize(src, HASH_SIZE, HASH_SIZE);
        byte[] matrixGray = (byte[]) toGray(hashImage).getData()
                .getDataElements(0, 0, HASH_SIZE, HASH_SIZE, null);
        return binaryzation(matrixGray);
    }
 
    /**
     * 
     * 
     * @param compactValue
     * @return
     */
    public static FingerPrint createFromCompact(byte[] compactValue) {
        return new FingerPrint(uncompact(compactValue));
    }
 
    public static boolean validHashValue(byte[] hashValue) {
        if (hashValue.length != HASH_SIZE)
            return false;
        for (byte b : hashValue) {
            if (0 != b && 1 != b)
                return false;
        }
        return true;
    }
 
    public static boolean validHashValue(String hashValue) {
        if (hashValue.length() != HASH_SIZE)
            return false;
        for (int i = 0; i < hashValue.length(); ++i) {
            if ('0' != hashValue.charAt(i) && '1' != hashValue.charAt(i))
                return false;
        }
        return true;
    }
 
    public byte[] compact() {
        return compact(binaryzationMatrix);
    }
 
    /**
     * 
     * 
     * @param hashValue
     * @return
     */
    private static byte[] compact(byte[] hashValue) {
        byte[] result = new byte[(hashValue.length + 7) >> 3];
        byte b = 0;
        for (int i = 0; i < hashValue.length; ++i) {
            if (0 == (i & 7)) {
                b = 0;
            }
            if (1 == hashValue[i]) {
                b |= 1 << (i & 7);
            } else if (hashValue[i] != 0)
                throw new IllegalArgumentException(
                        "invalid hashValue,every element must be 0 or 1");
            if (7 == (i & 7) || i == hashValue.length - 1) {
                result[i >> 3] = b;
            }
        }
        return result;
    }
 
    /**
     *
     * 
     * @param compactValue
     * @return
     */
    private static byte[] uncompact(byte[] compactValue) {
        byte[] result = new byte[compactValue.length << 3];
        for (int i = 0; i < result.length; ++i) {
            if ((compactValue[i >> 3] & (1 << (i & 7))) == 0)
                result[i] = 0;
            else
                result[i] = 1;
        }
        return result;
    }
 
    /**
     * 
     * 
     * @param hashValue
     * @return
     */
    private static byte[] toBytes(String hashValue) {
        hashValue = hashValue.replaceAll("\\s", "");
        byte[] result = new byte[hashValue.length()];
        for (int i = 0; i < result.length; ++i) {
            char c = hashValue.charAt(i);
            if ('0' == c)
                result[i] = 0;
            else if ('1' == c)
                result[i] = 1;
            else
                throw new IllegalArgumentException("invalid hashValue String");
        }
        return result;
    }
 
    /**
     * 
     * 
     * @param src
     * @param width
     * @param height
     * @return
     */
    private static BufferedImage resize(Image src, int width, int height) {
        BufferedImage result = new BufferedImage(width, height,
                BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = result.getGraphics();
        try {
            g.drawImage(
                    src.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                    0, 0, null);
        } finally {
            g.dispose();
        }
        return result;
    }
 
    /**
     * 
     * 
     * @param src
     * @return
     */
    private static int mean(byte[] src) {
        long sum = 0;
        // 
        for (byte b : src)
            sum += (long) b & 0xff;
        return (int) (Math.round((float) sum / src.length));
    }
 
    /**
     * 
     * 
     * @param src
     * @return
     */
    private static byte[] binaryzation(byte[] src) {
        byte[] dst = src.clone();
        int mean = mean(src);
        for (int i = 0; i < dst.length; ++i) {
            //
            dst[i] = (byte) (((int) dst[i] & 0xff) >= mean ? 1 : 0);
        }
        return dst;
 
    }
 
    /**
     * 
     * 
     * @param src
     * @return
     */
    private static BufferedImage toGray(BufferedImage src) {
        if (src.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            return src;
        } else {
            // 
            BufferedImage grayImage = new BufferedImage(src.getWidth(),
                    src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null)
                    .filter(src, grayImage);
            return grayImage;
        }
    }
 
    @Override
    public String toString() {
        return toString(true);
    }
 
    /**
     * @param multiLine
     *          
     * @return
     */
    public String toString(boolean multiLine) {
        StringBuffer buffer = new StringBuffer();
        int count = 0;
        for (byte b : this.binaryzationMatrix) {
            buffer.append(0 == b ? '0' : '1');
            if (multiLine && ++count % HASH_SIZE == 0)
                buffer.append('\n');
        }
        return buffer.toString();
    }
 
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FingerPrint) {
            return Arrays.equals(this.binaryzationMatrix,
                    ((FingerPrint) obj).binaryzationMatrix);
        } else
            return super.equals(obj);
    }
 
    /**
     * 
     * 
     * @param compactValue
     * @return
     * @see #compare(FingerPrint)
     */
    public float compareCompact(byte[] compactValue) {
        return compare(createFromCompact(compactValue));
    }
 
    /**
     * @param hashValue
     * @return
     * @see #compare(FingerPrint)
     */
    public float compare(String hashValue) {
        return compare(new FingerPrint(hashValue));
    }
 
    /**
     *
     * 
     * @param hashValue
     * @return
     * @see #compare(FingerPrint)
     */
    public float compare(byte[] hashValue) {
        return compare(new FingerPrint(hashValue));
    }
 
    /**
     * 
     * 
     * @param image2
     * @return
     * @see #compare(FingerPrint)
     */
    public float compare(BufferedImage image2) {
        return compare(new FingerPrint(image2));
    }
 
    /**
     * 
     * 
     * @param src
     * @return
     * @see #compare(byte[], byte[])
     */
    public float compare(FingerPrint src) {
        if (src.binaryzationMatrix.length != this.binaryzationMatrix.length)
            throw new IllegalArgumentException(
                    "length of hashValue is mismatch");
        return compare(binaryzationMatrix, src.binaryzationMatrix);
    }
 
    /**
     * 
     * 
     * @param f1
     * @param f2
     * @return
     */
    private static float compare(byte[] f1, byte[] f2) {
        if (f1.length != f2.length)
            throw new IllegalArgumentException("mismatch FingerPrint length");
        int sameCount = 0;
        for (int i = 0; i < f1.length; ++i) {
            if (f1[i] == f2[i])
                ++sameCount;
        }
        return (float) sameCount / f1.length;
    }
 
    public static float compareCompact(byte[] f1, byte[] f2) {
        return compare(uncompact(f1), uncompact(f2));
    }
 
    public static float compare(BufferedImage image1, BufferedImage image2) {
        return new FingerPrint(image1).compare(new FingerPrint(image2));
    }
}
