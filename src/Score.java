import java.awt.*;

public class Score extends Rectangle{
    int speed = 1;
    int hit1 = 0;
    int hit2 = 0;
    int gameWidth;
    int gameHeight;
    boolean singleplayer;
    
    //Constructor
    Score(int gameWidth, int gameHeight, boolean singleplayer) {
        //Set variables
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.singleplayer = singleplayer;
    }

    public void draw(Graphics g) {
        //Change graphics to 2D graphics(2d can do more things) and set colour
        Graphics2D g2d = (Graphics2D) g;
		g.setColor(new Color(0xBBAB8C));
		
        //Draw dashed line in the middle
        Stroke dashed = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{25}, 30);
        g2d.setStroke(dashed);
        g2d.drawLine(gameWidth/2, 0, gameWidth/2, gameHeight);

        //Draw the hit counters, the speed counter and the player names
        g2d.setFont(new Font("Consolas", Font.PLAIN, 20));
        g2d.drawString(String.valueOf(hit1/10) + String.valueOf(hit1%10), (gameWidth/2)-40, 50);
        g2d.drawString(String.valueOf(hit2/10) + String.valueOf(hit2%10), (gameWidth/2)+20, 50);
        g2d.drawString("Speed:", (gameWidth/2)-80, 525);
        g2d.drawString(String.valueOf(speed), (gameWidth/2)+20, 525);
        g2d.setFont(new Font("Consolas", Font.PLAIN, 30));
        g2d.drawString("Player 1", 175, 50);
        if (singleplayer) g2d.drawString("Player 2 (CPU)", gameWidth-350, 50);
        else g2d.drawString("Player 2", gameWidth-300, 50);
    }
}
