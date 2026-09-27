import java.awt.*;
import java.util.*;

public class Ball extends Rectangle{
    int xVelocity;
    int yVelocity;
    int speedMultiplier = 3;
    Random random;
    
    //Constructor
    Ball(int x, int y, int width, int height) {
        //Set variables
        super(x, y, width, height);
        random = new Random();
        //Random starting horizontal direction
        int randomX = random.nextInt(2);
        if (randomX == 0) randomX--;
        setX(randomX*speedMultiplier++);
        //Random starting vertical direction
        int randomY = random.nextInt(2);
        if (randomY == 0) randomY--;
        setY(randomY*(speedMultiplier-1));
    }

    public void setX(int xV) {
        //Set the xVelocity
        xVelocity = xV;
    }

    public void setY(int yV) {
        //Set the yVelocity
        yVelocity = yV;
    }

    public void move() {
        //Add x/y to make ball move horizontally/vertically
        if (Math.abs(xVelocity) < (Math.abs(yVelocity)-1) && xVelocity > 0) xVelocity++;
        if (Math.abs(xVelocity) < (Math.abs(yVelocity)-1) && xVelocity < 0) xVelocity--;
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        //Draw the ball
        g.setColor(new Color(0x503C3C));
        g.fillOval(x, y, width, height);
    }
}

