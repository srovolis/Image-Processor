package ce325.hw2;

import java.awt.*;
import static java.awt.Color.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JLabel;

public class ImageProcessing extends JFrame{
    private JTextField helpTextField;
    JPanel p;
    JFrame  frame;
    BufferedImage img;
    PPMImage newPPMImage=null;
    YUVImage newYUVImage=null;
    PPMImageStacker stackedImg;
    int fileType = 0; // 0 = no file selected , 1 = ppm , 2 = yuv
   

    public ImageProcessing(){
        initUI();
    }

    private void initUI() {
        createMenuBar();

        setTitle("Image Processor");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
  
    private void createMenuBar(){
        
        
        //Create Text Field ,used as a dialog box
        helpTextField = new JTextField("This is a simple image editor, open a .ppm , or .yuv image");
        helpTextField.setEditable(false);
        helpTextField.setBackground(WHITE);
        p = new JPanel(); 
        Container cp = getContentPane();
        p.setLayout(new GridLayout(2, 1));
        p.add(helpTextField);
        cp.add(p, BorderLayout.NORTH);
      
        JMenuBar menubar = new JMenuBar();  
        
        //Create menus
        JMenu fileMenu = new JMenu("File");
        JMenu openMenu = new JMenu("Open");
        JMenu saveMenu = new JMenu("Save");
        JMenu actionMenu = new JMenu("Actions");
        JMenu stackingAlgorithm = new JMenu("Stacking Algorithm");

        //Create Menu Items for Open , Save 
        JMenuItem openPPM = new JMenuItem("PPM File");
        JMenuItem openYUV= new JMenuItem("YUV FIle");
        JMenuItem savePPM= new JMenuItem("PPM File");
        JMenuItem saveYUV = new JMenuItem("YUV File");
        JMenuItem directory = new JMenuItem("Select Directory");

        //Add Menu items in Open , and Save menu 
        openMenu.add(openPPM);
        openMenu.add(openYUV);

        saveMenu.add(savePPM);
        saveMenu.add(saveYUV);

        stackingAlgorithm.add(directory);

        // Create menu items for Action menu
        JMenuItem grayscale = new JMenuItem("Grayscale");//,iconGrayscale);
        JMenuItem increaseSize = new JMenuItem("Increase Size");//,iconIncreaseSize);
        JMenuItem decreaseSize = new JMenuItem("Decrease Size");//,iconDecreaseSize);
        JMenuItem rotateClockwise = new JMenuItem("Rotate Clockwise");//,iconRotateClockwise);
        JMenuItem equalizeHistogram = new JMenuItem("Equalise Histogram");//,iconEqualiseHistogram);

        //Add Open,Save menus to File menu
        fileMenu.add(openMenu);
        fileMenu.add(saveMenu);

        //Add menu items in Action menu
        actionMenu.add(grayscale);
        actionMenu.add(increaseSize);
        actionMenu.add(decreaseSize);
        actionMenu.add(rotateClockwise);
        actionMenu.add(equalizeHistogram);
        actionMenu.add(stackingAlgorithm);

        //Add File , Action menu in Menu Bar
        menubar.add(fileMenu);
        menubar.add(actionMenu);

        //Action Listeners 
        openPPM.addActionListener(new OpenPPMListener());
        openYUV.addActionListener(new OpenYUVListener());
        
        savePPM.addActionListener(new SavePPMListener());
        saveYUV.addActionListener(new SaveYUVListener());

        //Grayscale
        grayscale.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent event){
                if(fileType>0){
                    newPPMImage.grayscale();
                    int height = newPPMImage.getHeight();
                    int width  = newPPMImage.getWidth();
                    
                    for(int i=0; i<height; i++){
                        for(int j=0; j<width; j++){ 
                            int col = newPPMImage.image[i][j].getPixel();
                            img.setRGB(j, i, col);
                        }
                    }
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                    frame.revalidate();
                    frame.repaint();
                }
                else{
                    helpTextField.setText("You have to select an Image First");
                }
            }
        });

        //IncreaseSize
        increaseSize.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent event){
                if(fileType>0){
                    newPPMImage.doublesize();
                    int height = newPPMImage.getHeight();
                    int width  = newPPMImage.getWidth();
                    
                    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    for(int i=0; i<height; i++){
                        for(int j=0; j<width; j++){ 
                            int col = newPPMImage.image[i][j].getPixel();
                            img.setRGB(j, i, col);
                        }
                    }
                    frame.getContentPane().removeAll();
                    frame.setPreferredSize(new Dimension(width, height));
                    frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                    frame.pack();
                    frame.revalidate();
                    frame.repaint();

                }
                else{
                    helpTextField.setText("You have to select an Image First");
                }
            }
        });


        //DecreaseSize
        decreaseSize.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent event){
                if(fileType>0){
                    newPPMImage.halfsize();
                    int height = newPPMImage.getHeight();
                    int width = newPPMImage.getWidth();
                    
                    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    for(int i=0; i<height; i++){
                        for(int j=0; j<width; j++){ 
                            int col = newPPMImage.image[i][j].getPixel();
                            img.setRGB(j, i, col);
                        }
                    }
                    frame.getContentPane().removeAll();
                    frame.setPreferredSize(new Dimension(width, height));
                    frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                    frame.pack();
                    frame.revalidate();
                    frame.repaint();
                    
               
                    
                }else{
                    helpTextField.setText("You have to select an Image First");
                }
            }
        });

        //RotateClockwise
        rotateClockwise.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent event){
                if(fileType>0){
                    newPPMImage.rotateClockwise();
                    int height = newPPMImage.getHeight();
                    int width  = newPPMImage.getWidth();
                    
                    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    for (int i=0; i<height; i++){
                        for (int j=0; j<width; j++){ 
                            int col = newPPMImage.image[i][j].getPixel();
                            img.setRGB(j, i, col);
                        }
                    }
                    frame.getContentPane().removeAll();
                    frame.setPreferredSize(new Dimension(width, height));
                    frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                    frame.pack();
                    frame.revalidate();
                    frame.repaint();
                }else{
                    helpTextField.setText("You have to select an Image First");
                }
            }
        });


        //Equalize Histogram
        equalizeHistogram.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent event){
                if(fileType==2){
                    newYUVImage.equalize();
                    newPPMImage = new PPMImage(newYUVImage);
                    
                    int height = newYUVImage.getHeight();
                    int width  = newYUVImage.getWidth();
                    
                    //Transform PPM to BufferedImage
                    
                    img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
                    for (int i=0; i<height; i++){
                        for (int j=0; j<width; j++){ 
                            int col =newPPMImage.image[i][j].getPixel();
                            img.setRGB(j, i, col);
                        }
                    }
                    frame.getContentPane().removeAll();
                    frame.setPreferredSize(new Dimension(width, height));
                    frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                    frame.revalidate();
                    frame.repaint();
                }else{
                    helpTextField.setText("You have to select a YUV Image");
                }
            }
        });
        
        //ImageStacker
        directory.addActionListener(new StackListener());

        setJMenuBar(menubar);    
    }


  
    public class OpenPPMListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            JFileChooser c = new JFileChooser(); 
            
            int rVal = c.showOpenDialog(ImageProcessing.this); //Demonstrate "Open" dialog
            if(fileType>0) {
                helpTextField.setText("Close the image before opening another.");
            }
            else if(rVal == JFileChooser.CANCEL_OPTION){
                helpTextField.setText("You pressed cancel");
            }
            else if(rVal == JFileChooser.APPROVE_OPTION ){
                helpTextField.setText(c.getSelectedFile().getPath());
                fileType = 1;
                try{
                    newPPMImage=new PPMImage(new File(c.getSelectedFile().getPath()));
                    int width = newPPMImage.getWidth();
                    int height= newPPMImage.getHeight();
                    
                    //Transform PPMImage to BufferedImage
                    img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
                    for(int i=0; i<height; i++){
                        for(int j=0; j<width; j++){ 
                            int col = newPPMImage.image[i][j].getPixel();
                            img.setRGB(j, i, col);
                        }
                    }
                    
                    frame = new JFrame(c.getSelectedFile().getName()); //############## new frame h apo katw?
                    frame.setVisible(true);
                    frame.setPreferredSize(new Dimension(width, height));
                    frame.add(new JLabel(new ImageIcon(img)));
                    frame.pack();
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            fileType = 0;
                            frame.dispose();
                        }
                    });
                    
                }catch (FileNotFoundException ex){
                    helpTextField.setText("File Not found , try again");
                }catch (UnsupportedFileFormatException ex){
                    helpTextField.setText("Please select a PPM file");
                }
            }
        }
    }
    
    public class OpenYUVListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            JFileChooser c = new JFileChooser();
            
            int rVal = c.showOpenDialog(ImageProcessing.this);//Demonstrate "Open" dialog
            if(fileType>0) {
                helpTextField.setText("Close the image before opening another.");
            }
            else if(rVal == JFileChooser.CANCEL_OPTION){
                helpTextField.setText("You pressed cancel");
            }
            else if(rVal == JFileChooser.APPROVE_OPTION){
                helpTextField.setText(c.getSelectedFile().getPath());
                fileType = 2;
                
                try{
                    newYUVImage=new YUVImage(new File(c.getSelectedFile().getPath()));
                    int width = newYUVImage.getWidth();
                    int height= newYUVImage.getHeight();
                    
                    //Transform YUV to RGB and then to PPM
                    newPPMImage= new PPMImage(newYUVImage);
                    
                    //Transform PPM to BufferedImage
                    img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
                    for(int i=0; i<height; i++){
                        for(int j=0; j<width; j++){ 
                            int col = newPPMImage.image[i][j].getPixel();
                            img.setRGB(j, i, col);
                        }
                    }
                    
                    frame = new JFrame(c.getSelectedFile().getName()); //########## new frame h apo katw?
                    frame.setVisible(true);
                    frame.setPreferredSize(new Dimension(width, height));
                    frame.add(new JLabel(new ImageIcon(img)));
                    frame.pack();
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    
                    frame.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            fileType = 0;
                            frame.dispose();
                        }
                    });
                    
                }catch (FileNotFoundException ex) {
                    helpTextField.setText("File Not found , try again");
                }catch (UnsupportedFileFormatException ex) {
                    helpTextField.setText("Please select a YUV file");
                }
            }
        }
    }
    
    
    public class StackListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            JFileChooser c = new JFileChooser();
            c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int rVal = c.showOpenDialog(ImageProcessing.this); //Demonstrate "Open" dialog
            if(rVal == JFileChooser.CANCEL_OPTION){
                helpTextField.setText("No image selected");
            }
            else if(rVal == JFileChooser.APPROVE_OPTION){
                fileType=1;
                helpTextField.setText(c.getSelectedFile().getName());
                stackedImg = new PPMImageStacker(new File(c.getSelectedFile().getPath()));
                stackedImg.stack();
                newPPMImage = stackedImg.getStackedImage();
                int width = newPPMImage.getWidth();
                int height= newPPMImage.getHeight();
                
                img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                for (int i=0; i<height; i++){
                    for (int j=0; j<width; j++){ 
                        int col = newPPMImage.image[i][j].getPixel();
                        img.setRGB(j, i, col);
                    }
                }
                frame = new JFrame(c.getSelectedFile().getName());
                frame.setVisible(true);
                frame.setPreferredSize(new Dimension(width, height));
                frame.add(new JLabel(new ImageIcon(img)));
                frame.pack();
            }
        }
    }
  
    public class SavePPMListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            JFileChooser c = new JFileChooser();
            
            if (fileType>0){
                int rVal = c.showSaveDialog(ImageProcessing.this);//Demonstrate "Save" dialog
                if(rVal == JFileChooser.CANCEL_OPTION) {
                  helpTextField.setText("You pressed cancel");
                }
                else if(rVal == JFileChooser.APPROVE_OPTION) {
                  helpTextField.setText(c.getSelectedFile().getName());
                  newPPMImage.toFile(new File(c.getSelectedFile().getPath()));
                }
            }
            else {
                helpTextField.setText("You need to open an Image first");
            }
        }
    }
    
    public class SaveYUVListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            JFileChooser c = new JFileChooser();
            
            if (fileType>0){
                int rVal = c.showSaveDialog(ImageProcessing.this); //Demonstrate "Save" dialog
                if(rVal == JFileChooser.CANCEL_OPTION) {
                  helpTextField.setText("You pressed cancel");
                }
                else if(rVal == JFileChooser.APPROVE_OPTION) {
                  helpTextField.setText(c.getSelectedFile().getName());

                  //Transform from PPM to YUV before save
                  newYUVImage = new YUVImage(newPPMImage);
                  newYUVImage.toFile(new File(c.getSelectedFile().getPath()));
                }
            }else {
                 helpTextField.setText("You need to open an Image first");
            }
        }
    }
}