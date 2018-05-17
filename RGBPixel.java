package ce325.hw2;

public class RGBPixel{
    private int pixel;
   
    //Constructors
    public RGBPixel(short red, short green, short blue){ //Creates a pixel with r,g,b values
        pixel = pixel + red;
        pixel = (pixel<<8) + green;
        pixel = (pixel<<8) + blue;
    }
    
    public RGBPixel(RGBPixel pixel){ // creates a new object copy of "pixel"
        this(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }
    
    public RGBPixel(YUVPixel pixel) { //creates RGB pixel from YUV pixel
        this((short)0,(short)0,(short)0);
        
        short C = (short)(pixel.getY() - 16);
        short D = (short)(pixel.getU() - 128);
        short E = (short)(pixel.getV() - 128);
        
        short R = clip(( 298 * C + 409 * E + 128) >> 8);
        short G = clip(( 298 * C - 100 * D - 208 * E + 128) >> 8);
        short B = clip(( 298 * C + 516 * D + 128) >> 8);
                
        setRed(R);
        setGreen(G);
        setBlue(B);
    }
    
    //Getters
    public short getRed(){ // returns red value of pixel
        return (short)((pixel>>16) & 0xFF);
    }
    public short getGreen(){ // returns green value of pixel
        return (short)((pixel>>8) & 0xFF);
    }
    public short getBlue(){ //returns blue value of pixel
        return (short)(pixel & 0xFF);
    }
    
    //Setters
    public void setRed(short red){ // sets red value of pixel       
        pixel = pixel & 0xFF00FFFF;
        pixel = pixel | (red<<16);
    }
    public void setGreen(short green){ // sets green value of pixel
        pixel = pixel & 0xFFFF00FF;
        pixel = pixel | green<<8;
    }
    public void setBlue(short blue){ // sets blue value of pixel
        pixel = pixel & 0xFFFFFF00;
        pixel = pixel | blue;
    }   
    public int getPixel(){
        return pixel;
    }
    public String toString(){
        return getRed()+"\n"+getGreen()+"\n"+getBlue()+"\n";
    }
    
    
    public static short clip(int c){
        if(c < 0)
            return 0;
        
        if(c > 255)
            return 255;
        
        return (short)c;
    }
    
}