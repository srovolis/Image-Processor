package ce325.hw2;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YUVImage {
    YUVPixel [][]image;
    File f;
    
    public YUVImage(int width, int height){
        image = new YUVPixel[height][width];
        
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                image[i][j] = new YUVPixel((short)16, (short)128, (short)128);
            }
        }
    }
    
    public YUVImage(YUVImage copyImg){
        this(copyImg.getWidth(), copyImg.getHeight());
        
        for(int i=0; i<getHeight(); i++){
            for(int j=0; j<getWidth(); j++){
                image[i][j].setΥ(copyImg.image[i][j].getY());
                image[i][j].setU(copyImg.image[i][j].getU());
                image[i][j].setV(copyImg.image[i][j].getV());
            }
        }
    }
    
    public YUVImage(RGBImage RGBImg){
        image = new YUVPixel[RGBImg.getHeight()][RGBImg.getWidth()];        
        
        for(int i=0; i<getHeight(); i++){
            for(int j=0; j<getWidth(); j++){
                image[i][j] = new YUVPixel(RGBImg.image[i][j]);
            }
        }
    }
    
    public YUVImage(File file) throws FileNotFoundException,UnsupportedFileFormatException{
        f=file;
        Scanner sc = new Scanner(file);
        short Y=0, U=0, V=0;
        String str = sc.next();
        
        try{
            if(!file.exists()){
                throw new java.io.FileNotFoundException("File Not Found!");
            }
            if(!file.getName().substring(file.getName().length()-4).equals(".yuv") | !str.equals("YUV3")){
                throw new ce325.hw2.UnsupportedFileFormatException(file.getName()+" Error not .yuv"); 
                
            }
        }

        finally {}
        
        int width = sc.nextInt();
        int height=sc.nextInt();
        image = new YUVPixel[height][width];
        
        for (int i=0; i<height; i++){
            for(int j=0; j<width; j++) {
                Y=sc.nextShort();
                U=sc.nextShort();
                V=sc.nextShort();
                image[i][j] = new YUVPixel(Y,U,V);
            }
        }
    }
    
    public int getWidth(){
        return image[0].length;
    }
    
     public int getHeight(){
        return image.length;
    }
     
    public String toString(){
        String str = "";
        try{
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                str += sc.nextLine()+"\n";
            }
            sc.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return str;
    }
    public String toString2(){
        StringBuffer str = new StringBuffer("");
        for (int i=0; i<getHeight();i++) {
            for(int j=0; j<getWidth();j++) {
                str.append(image[i][j].toString());
            }
        }
        return str.toString();
    }
    public void toFile(File file){
        String str = "";
        PrintWriter writer = null;
        
        str = "YUV3\n"+getWidth()+" "+getHeight()+"\n"+toString2();
        
        try {
            while(!file.createNewFile()){
                if(file.exists()){
                    file.delete();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try{
            writer = new PrintWriter(file);
            writer.println(str);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            writer.close();
        }
    }
    
    
    public void equalize(){
        Histogram hist = new Histogram(this);
        hist.equalize();
        short newY;
        short oldY;
        
         for(int i=0; i<getHeight(); i++){
                for(int j=0; j<getWidth(); j++){
                    oldY = image[i][j].getY();
                    newY = hist.getEqualizedLuminocity(oldY);
                    image[i][j].setΥ(newY);
                }
            }
           
    }
}
