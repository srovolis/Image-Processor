
package ce325.hw2;
import java.awt.EventQueue;

public class Main {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                ImageProcessing ex = new ImageProcessing();
                ex.setVisible(true);
            }
        });
    }

}
