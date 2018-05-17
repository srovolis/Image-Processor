package ce325.hw2;

public class RGBImage implements Image {
    private int colordepth;
    RGBPixel [][]image;
    
    public RGBImage(int width, int height, int colordepth) { // creates RGB image with dimensions width x height and max colordepth
        image = new RGBPixel[height][width];
        this.colordepth = colordepth;
    }
    public RGBImage(RGBImage copyImg) { // Creates a new RGBImage copy of copyImg
        this(copyImg.getWidth(), copyImg.getHeight(), copyImg.getColordepth());
    }
    
    public RGBImage(YUVImage YUVImg) { // Creates RGB Image from YUV Image
        this(YUVImg.getWidth(), YUVImg.getHeight(), 255);
        
        for(int i=0; i<YUVImg.getHeight(); i++){
            for(int j=0; j<YUVImg.getWidth(); j++){
                image[i][j] = new RGBPixel(YUVImg.image[i][j]);
            }
        }
    }    

    
    public int getWidth(){
        return image[0].length;
    }
    
     public int getHeight(){
        return image.length;
    }
    
    public int getColordepth(){
        return colordepth;
    }
    
    
    public void grayscale(){
        short gray;
        for(int i=0; i<getHeight(); i++){
            for(int j=0; j<getWidth(); j++){
                gray = (short)(Math.round(image[i][j].getRed()*0.3+image[i][j].getGreen()*0.59+image[i][j].getBlue()*0.11));
                image[i][j].setRed(gray);
                image[i][j].setGreen(gray);
                image[i][j].setBlue(gray);
            }
        }
    }

    public void doublesize(){
        RGBPixel [][] doubleImage = new RGBPixel[getHeight()*2][getWidth()*2];
        for(int i=0; i<getHeight(); i++){
            for(int j=0; j<getWidth(); j++){
                doubleImage[2*i][2*j] = new RGBPixel(image[i][j]);
                doubleImage[2*i+1][2*j]=new RGBPixel(image[i][j]);
                doubleImage[2*i][2*j+1]=new RGBPixel(image[i][j]);
                doubleImage[2*i+1][2*j+1]=new RGBPixel(image[i][j]);
            }
        }
        image = doubleImage;
    }

    public void halfsize(){
        RGBPixel [][] halfImage = new RGBPixel[getHeight()/2][getWidth()/2];
        for(int i=0; i<getHeight()/2; i++){
            for(int j=0; j<getWidth()/2; j++){ 
                short avgRed = (short)((image[2*i][2*j].getRed()+image[2*i+1][2*j].getRed()+image[2*i][2*j+1].getRed()+image[2*i+1][2*j+1].getRed())/4);
                short avgGreen = (short)((image[2*i][2*j].getGreen()+image[2*i+1][2*j].getGreen()+image[2*i][2*j+1].getGreen()+image[2*i+1][2*j+1].getGreen())/4);
                short avgBlue = (short)((image[2*i][2*j].getBlue()+image[2*i+1][2*j].getBlue()+image[2*i][2*j+1].getBlue()+image[2*i+1][2*j+1].getBlue())/4);
                halfImage[i][j]= new RGBPixel(avgRed,avgGreen,avgBlue);
            }
        }
        image = halfImage;
    }

    public void rotateClockwise(){
        RGBPixel [][]rotateImage = new RGBPixel[getWidth()][getHeight()];
        for(int i=0; i<getHeight(); i++){
            for(int j=0; j<getWidth(); j++){
                rotateImage[j][getHeight()-1-i] = new RGBPixel(image[i][j]);
            }
        }
        image = rotateImage;
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
    
}
