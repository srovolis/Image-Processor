package ce325.hw2;
import java.io.*;
import java.util.*;

public class PPMImageStacker {
    private List<PPMImage> imgList;
    private RGBImage rgbImage;
    
    public PPMImageStacker(File dir) {
        if(!dir.exists()){
            System.err.println("[ERROR] Directory "+dir.getName()+" does not exist!");
        }else if(!dir.isDirectory()){
            System.err.println("[ERROR] "+dir.getName()+" is not a directory!");
        }
        File []files = dir.listFiles();
        imgList = new ArrayList<>(); 
        for(File f: files) {
            try {
                imgList.add(new PPMImage(f));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (UnsupportedFileFormatException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void stack(){
        short avgRed = 0, avgGreen = 0, avgBlue = 0;
        int width=imgList.get(0).getWidth();
        int height=imgList.get(0).getHeight();
        int listSize = imgList.size();
        
        rgbImage = new RGBImage(width, height, 255);
        
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                for(int k=0; k<listSize; k++){ //gia kathe photo
                    avgRed += imgList.get(k).image[i][j].getRed();
                    avgGreen += imgList.get(k).image[i][j].getGreen();
                    avgBlue += imgList.get(k).image[i][j].getBlue();
                }
                
                avgRed = (short)(avgRed / listSize);
                avgGreen = (short)(avgGreen / listSize);
                avgBlue = (short)(avgBlue / listSize);
                
                rgbImage.image[i][j] = new RGBPixel(avgRed, avgGreen, avgBlue);
                
                avgRed = avgGreen = avgBlue = 0;
            }
        }
    }
        
    public PPMImage getStackedImage(){
        PPMImage stackedImage = new PPMImage(rgbImage);
        return stackedImage;
    }
    
}
