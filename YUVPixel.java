package ce325.hw2;

public class YUVPixel {
    private int Y;
    private int U;
    private int V;
    
    public YUVPixel(short Y, short U, short V){
        this.Y = Y;
        this.U = U;
        this.V = V;
    }
    
    public YUVPixel(YUVPixel pixel){
        this(pixel.getY(), pixel.getU(), pixel.getV());
    }
    
    public YUVPixel(RGBPixel pixel){
        Y = ((66 * pixel.getRed() + 129 * pixel.getGreen() + 25 * pixel.getBlue() + 128) >> 8) + 16;
        U = ( ( -38 * pixel.getRed() -  74 * pixel.getGreen() + 112 * pixel.getBlue() + 128) >> 8) + 128;
        V = ( ( 112 * pixel.getRed() -  94 * pixel.getGreen() -  18 * pixel.getBlue() + 128) >> 8) + 128;
    }
    
    public short getY(){
        return (short)Y;
    }
    public short getU(){
        return (short)U; 
    }
    public short getV(){
        return (short)V;
    }
    
    public void setΥ(short newY){
        this.Y = (int)newY;
    }
    public void setU(short newU){
        this.U = (int)newU;
    }
    public void setV(short newV){
        this.V = (int)newV;
    }
     public String toString(){
        return getY()+" "+getU()+" "+getV()+"\n";
    }
}
