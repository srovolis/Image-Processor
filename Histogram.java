package ce325.hw2;

import java.io.*;

public class Histogram {
    private int [][]histogram;
    private int []equalizedY;
    
    public Histogram(YUVImage img){
        int Yvalues = 16;
        histogram = new int[2][220]; // 16<Y<235
        
        for(int i=0; i<histogram[0].length; i++)
            histogram[0][i] = Yvalues++;
        
        for(int i=0; i<img.getHeight(); i++){
            for (int j=0; j<img.getWidth(); j++){
                histogram[1][img.image[i][j].getY()-16]++; 
            }
        }
    }
    
    public String toString(){
        StringBuffer str= new StringBuffer("");
        int stars;
        int max=0;
        
        for (int i=0; i<histogram[0].length; i++){
            if (histogram[1][i]>max) {
                max=histogram[1][i];
            }
        }
        
        for(int i=0; i<histogram[0].length; i++){
            str.append(histogram[0][i]+":");
            stars = (int)Math.ceil((float)(histogram[1][i]*80)/max);
            for(int j=0; j<stars; j++) {
                str.append("*");
            }
            str.append("\n");
        }
        return str.toString();
    }
    
    public void toFile(File file){
        PrintWriter writer = null;
        
        try{
            while(!file.createNewFile()){
                if(file.exists()){
                    System.out.println("Deleting old File...");
                    file.delete();
                }
            }
            System.out.println("New file: "+file.getName());
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        try{
            writer = new PrintWriter(file);
            writer.println(toString());
        }catch (IOException ex){
            ex.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            writer.close();
        }
    }
    
    public void equalize(){
        double []probability = new double[220];
        double []cumulative = new double[220];
        equalizedY = new int[220];
        int pixelSum=0, maxY=0;
        int result;
        
        for(int i: histogram[1])
            pixelSum += i;
        
        for(int i=0; i<histogram[1].length; i++){
            probability[i] = (double)(histogram[1][i])/(double)(pixelSum);
        }

        cumulative[0] = probability[0];
        for(int i=1; i<histogram[1].length; i++){
            cumulative[i] = cumulative[i-1] + probability[i];
        }
       
        for(int i=histogram[1].length-1; i>=0; i--){ //find maxY
            if(histogram[1][i]!=0){
                maxY = i+16;
                break;
            }
        }
        
        for(int i=0; i<histogram[1].length; i++){
            result = (int)Math.floor(maxY*  cumulative[i]);
            if (result<16) {
                equalizedY[i]=16;
            }
            else if (result>235) {
                equalizedY[i]=235;
            }else {
                equalizedY[i] = result;
            }
        } 
    }
    
    public short getEqualizedLuminocity(int luminocity){
        return (short)equalizedY[luminocity-16];
    }
    

}
