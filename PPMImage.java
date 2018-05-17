package ce325.hw2;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PPMImage extends RGBImage{
    File f;
    
    public PPMImage(File file) throws  FileNotFoundException, UnsupportedFileFormatException{
        super(getWidth(file),getHeight(file),getColordepth(file));
        f = file;
        Scanner sc = new Scanner(f);
        short red=0;
        short blue=0;
        short green=0;
        sc.next();
        sc.nextInt();
        sc.nextInt();
        sc.nextInt();

        //System.out.println("prin");
        for (int i=0; i<super.getHeight(); i++){
            for(int j=0; j<super.getWidth(); j++) {
                
                red=sc.nextShort(); //############
                green=sc.nextShort();
                blue=sc.nextShort();
                
                image[i][j] = new RGBPixel(red,green,blue);
                
            }
        }
        //System.out.println("meta");
    }
    
    
    public PPMImage(RGBImage img){
       super(img.getWidth(),img.getHeight(),img.getColordepth());
       for(int i=0 ;i<img.getHeight();i++){
            for(int j=0; j<img.getWidth();j++) {
                    image[i][j] = new RGBPixel(img.image[i][j]);
                  
            }
       }
    }
   
    public PPMImage(YUVImage img){
        super(img);
        for(int i=0 ;i<img.getHeight();i++){
            for(int j=0; j<img.getWidth();j++) {
                    image[i][j] = new RGBPixel(super.image[i][j]);
            }
       }
        
    }
    
    public static int getWidth(File file) throws FileNotFoundException ,UnsupportedFileFormatException {
        int width=1;
        try{
            Scanner sc = new Scanner(file);
            String str = sc.next();
            if(!file.exists()){
                throw new java.io.FileNotFoundException();
            }
            else if(!file.getName().substring(file.getName().length()-4).equals(".ppm") | !str.equals("P3")){
                throw new ce325.hw2.UnsupportedFileFormatException(file.getName()+" Error not .ppm"); 
            }
        }
        finally {}
        
        try {
            Scanner sc = new Scanner(file);
            String str = sc.next();
            width=sc.nextInt();
            sc.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return width;
    }
    
    public static int getHeight(File file) {
        int height=1;
        try {
            Scanner sc = new Scanner(file);
            sc.next();
            sc.nextInt();
            height=sc.nextInt(); 
            sc.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return height;
    }
    
    
    public static int getColordepth(File file) {
        int colordepth=1;
        try {
            Scanner sc = new Scanner(file);
            sc.next();
            sc.nextInt();
            sc.nextInt();
            colordepth=sc.nextInt();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return colordepth;
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
   
    
    public void toFile(File file){
        String str = ""; //Bufferstring
        PrintWriter writer = null;
        
        str = "P3\n"+getWidth()+" "+getHeight()+"\n"+getColordepth()+"\n"+toString2();
        
        try {
            while(!file.createNewFile()){
                if(file.exists()){
                    file.delete();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try {
            writer = new PrintWriter(file);
            writer.println(str);
        }catch (IOException ex){
            ex.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            writer.close();
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
