import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{
    int yVelocity;
    int id;
    int speed = 5;
    boolean singleplayer;

    //Constructor
    Paddle(int x, int y, int width, int height, int id, boolean singleplayer) {
        //Set variables
        super(x, y, width, height);
        this.id = id;
        this.singleplayer = singleplayer;
    }

    public void keyPressed(KeyEvent e) {
        //Paddle 1
        if (id == 1) {
            //W key pressed
            if (e.getKeyCode() == KeyEvent.VK_W) {
                setY(-speed);
                move();
            }
            //S key pressed
            if (e.getKeyCode() == KeyEvent.VK_S) {
                setY(speed);
                move();
            }
        }
        //Paddle 2
        if (id == 2 && singleplayer == false) {
            //Up arrow key pressed
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                setY(-speed);
                move();
            }
            //Down arrow key pressed
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                setY(speed);
                move();
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        //Paddle 1
        if (id == 1) {
            //W key released
            if (e.getKeyCode() == KeyEvent.VK_W) {
                setY(0);
                move();
            }
            //S key released
            if (e.getKeyCode() == KeyEvent.VK_S) {
                setY(0);
                move();
            }
        }
        //Paddle 2
        if (id == 2 && singleplayer == false) {
            //Up arrow key released
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                setY(0);
                move();
            }
            //Down arrow key released
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                setY(0);
                move();
            }
        }
    }
    public void setY(int speed) {
        //Sets velocity
        yVelocity = speed;
    }
    public void move() {
        //Move paddle
        y += yVelocity;
    }
    public void draw(Graphics g) {
        //Paint the paddle
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0x503C3C));
        g2d.fillRect(x, y, width, height);
    }
}
