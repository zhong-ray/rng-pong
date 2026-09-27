import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{
    GamePanel panel;
    boolean singleplayer;

    //Constructor
    GameFrame(boolean singleplayer) {
        //Set variables
        this.singleplayer = singleplayer;

        //Customize frame
        this.setTitle("Pong");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBackground(new Color(0xFAEED1));

        //Change icon
        ImageIcon image = new ImageIcon("Bald Mario.jpg");
        this.setIconImage(image.getImage());
        
        //Create panel and add to frame
        panel = new GamePanel(singleplayer, this);
        this.add(panel);

        //Set size, location, and visibility of frame
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
