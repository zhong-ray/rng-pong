import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    //Set some variables
    int gameWidth = 1000;
    int gameHeight = (int)(gameWidth*(5.0/9.0)); //The approximate ratio of 5/9 used for pong
    int paddleWidth = 15;
    int paddleHeight = 100;
    int ballSize = 20;
    Random random = new Random();
    Image image;
    Thread thread;
    Graphics graphics;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    boolean singleplayer;
    boolean p1 = true;
    boolean p2 = true;
    boolean loop = true;
    boolean paused = false;
    GameFrame frame;

    //Constructor
    GamePanel(boolean singleplayer, GameFrame frame) {
        //Create the panel
        this.setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        score = new Score(gameWidth, gameHeight, singleplayer);
        this.setPreferredSize(new Dimension(gameWidth,gameHeight));
        this.singleplayer = singleplayer;
        this.frame = frame;
        
        //Create paddles and ball
        newPaddle();
        newBall();

        //Execute another thread
        thread = new Thread(this);
        thread.start();
    }

    public void newBall() {
        //Create ball at a random y value (within range)
        ball = new Ball((gameWidth/2)-(ballSize/2), (int)(((gameHeight-10)-(ballSize+10)+1) * Math.random() + ballSize), ballSize, ballSize);
        if (singleplayer) paddle2.y = ball.y; //Set the CPU's paddle to the random height of ball
    }

    public void newPaddle() {
        //Create paddles for 1 player
        if (singleplayer) {
            paddle1 = new Paddle(1, (gameHeight/2)-(paddleHeight/2), paddleWidth, paddleHeight, 1, singleplayer);
            paddle2 = new Paddle(gameWidth-paddleWidth-1, (gameHeight/2)-(paddleHeight/2), paddleWidth, paddleHeight, 2, singleplayer);
        }
        //Create paddles for 2 players
        else {
            paddle1 = new Paddle(1, (gameHeight/2)-(paddleHeight/2), paddleWidth, paddleHeight, 1, singleplayer);
            paddle2 = new Paddle(gameWidth-paddleWidth-1, (gameHeight/2)-(paddleHeight/2), paddleWidth, paddleHeight, 2, singleplayer);
        }
        
    }

    public void paint(Graphics g) {
        //Paints panel and can be called with repaint();
        Graphics2D g2d = (Graphics2D) g;
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g2d.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        //Draw paddles, ball and score
        Graphics2D g2d = (Graphics2D) g;
        paddle1.draw(g2d);
        paddle2.draw(g2d);
        ball.draw(g2d);
        score.draw(g2d);
        //Draw "Paused" text over everything while paused
        if (paused) {
            g2d.setFont(new Font("Consolas", Font.PLAIN, 40));
            g2d.drawString("Paused", (gameWidth/2)-90, (gameHeight/2));
        }
    }

    public void move() {
        //The run method can call this 60 times a second to move paddles as long as the key is pressed, making movement smoother rather than whenever key is entered
        paddle1.move();
        paddle2.move();
        ball.move();
        //For CPU movement
        if (singleplayer) {
            paddle2.y += ball.yVelocity;
        }
    }

    public void checkCollision() {
        //Stops paddles from going off screen
        if (paddle1.y <= 0) paddle1.y = 0;
        if (paddle1.y >= (gameHeight-paddleHeight)) paddle1.y = (gameHeight-paddleHeight);
        if (paddle2.y <= 0) paddle2.y = 0;
        if (paddle2.y >= (gameHeight-paddleHeight)) paddle2.y = (gameHeight-paddleHeight);

        //Check ball collision - Top/Bottom
        if (ball.y <= 0) ball.setY(-ball.yVelocity);
        if (ball.y >= gameHeight-ballSize) ball.setY(-ball.yVelocity);

        //Paddle1 collision
        if ((ball.x+ballSize) > (paddleWidth+1) && ball.x <= (paddleWidth+1) && ball.y < (paddle1.y+paddleHeight) && (ball.y+ballSize) > paddle1.y && p1) {
            //First hit of the game
            if (p1 && p2) {
                ball.xVelocity++;
                score.speed++;
            }
            //New velocities to turn the ball around
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.setX(ball.xVelocity);
            ball.setY(ball.yVelocity);
            score.hit1++;

            //P variables ensure only 1 hit is registered per paddle hit, while letting the other paddle register hits again
            p1 = false;
            p2 = true;
            //Random chance to increase speed
            int rand = random.nextInt(2);
            if (rand == 1) {
                ball.xVelocity++;
                score.speed++;
                //Cap the vertical speed, otherwise increase it once
                if (ball.yVelocity > 15 || ball.yVelocity < -15);
                else if (ball.yVelocity > 0) ball.yVelocity++;
                else ball.yVelocity--;
            }
        }
        //Paddle2 collision
        if (ball.x < (gameWidth-paddleWidth-1) && (ball.x+ballSize) >= (gameWidth-paddleWidth-1) && ball.y < (paddle2.y+paddleHeight) && (ball.y+ballSize) > paddle2.y && p2) {
            //First hit of the game
            if (p1 && p2) {
                ball.xVelocity++;
                score.speed++;
            }
            //New velocities to turn the ball around
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.setX(-ball.xVelocity);
            ball.setY(ball.yVelocity);
            score.hit2++;

            //P variables ensure only 1 hit is registered per paddle hit, while letting the other paddle register hits again
            p2 = false;
            p1 = true;
            //Random chance to increase speed
            int rand = random.nextInt(2);
            if (rand == 1) {
                ball.xVelocity++;
                score.speed++;
                //Cap the vertical speed, otherwise increase it once
                if (ball.yVelocity > 15 || ball.yVelocity < -15);
                else if (ball.yVelocity > 0) ball.yVelocity++;
                else ball.yVelocity--;
            }
        }
        
        //Check win
        //Singleplayer
        if (singleplayer) {
            //Ball hit left side
            if (ball.x <= 0) {
                loop = false;
                endGame(3);
            }
            //Ball hit right side
            if ((ball.x+ballSize) >= gameWidth) {
                loop = false;
                endGame(1);
            }
        }
        //Multiplayer
        else {
            //Ball hit left side
            if (ball.x <= 0) {
                loop = false;
                endGame(2);
            }
            //Ball hit right side
            if ((ball.x+ballSize) >= gameWidth) {
                loop = false;
                endGame(1);
            }
        }
    }

    //Resolve save files to a fixed location so they don't fork based on the working directory the game is launched from
    static File getSaveFile(String name) {
        File dir = new File(System.getProperty("user.home"), ".pong");
        dir.mkdirs();
        return new File(dir, name);
    }

    //Game ended
    public void endGame(int winner) {
        //Create/Print inside files for leaderboard
        //Array length 5 for previous, 6 for adding new score
        int[] hits = new int[6];
        int[] speeds = new int[6];
        //Singleplayer leaderboard
        if (singleplayer) {
            File file = getSaveFile("SingleplayerPong.txt");
            if (file.exists()){
                try {
                    //File Order: (Line1: Hits, Line2: Speed)x3
                    FileReader fr = new FileReader(file);
                    Scanner s = new Scanner(fr);
                    int k = 0;
                    int i = 0;
                    int j = 0;
                    //Put all existing information into arrays
                    while (s.hasNextLine() && i < 10) {
                        if (i%2 == 0) {
                            hits[j] = Integer.parseInt(s.nextLine());
                            j++;
                        }
                        else {
                            speeds[k] = Integer.parseInt(s.nextLine());
                            k++;
                        }
                        i++;
                    }
                    s.close();
                    //Add the scores of the previously played game and sort it
                    hits[j] = score.hit1;
                    selectionSort(hits);
                    speeds[k] = score.speed;
                    selectionSort(speeds);
                    //Create new 2D array with all of the information
                    int leaderboard[][] = {hits, speeds};

                    //Print everything back out, with the new game's information (if it exceeds previous scores)
                    FileWriter fw = new FileWriter(file);
                    PrintWriter pw = new PrintWriter(fw);

                    k = 0;
                    int n = 0;
                    //Print top 5 for both scores, 6th element is ignored
                    for (i = 0; i < 5; i++) {
                        for (j = 0; j < 2; j++) {
                            if (j%2 == 0) {
                            pw.println(leaderboard[j][k]);
                            k++;
                            }
                            else {
                            pw.println(leaderboard[j][n]);
                            n++;
                            }
                        }
                    }
                    pw.close();
                } catch (Exception e) {
                    System.out.println("1");
                }
            }
            //File doesn't exist, create it and print score
            else {
                try {
                    FileWriter fw = new FileWriter(file);
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println(score.hit1);
                    pw.println(score.speed);
                    pw.close();
                } catch (IOException e) {
                    System.out.println("IO EXCEPTION");
                }
            }
        }
        //Multiplayer leaderboard
        else {
            File file = getSaveFile("MultiplayerPong.txt");
            if (file.exists()){
                try {
                    //File Order: (Line1: Hits, Line2: Speed)x3
                    FileReader fr = new FileReader(file);
                    Scanner s = new Scanner(fr);
                    int k = 0;
                    int i = 0;
                    int j = 0;
                    //Put all existing information into arrays
                    while (s.hasNextLine() && i < 10) {
                        if (i%2 == 0) {
                            hits[j] = Integer.parseInt(s.nextLine());
                            j++;
                        }
                        else {
                            speeds[k] = Integer.parseInt(s.nextLine());
                            k++;
                        }
                        i++;
                    }
                    s.close();
                    //Add the scores of the previously played game and sort it
                    hits[j] = (score.hit1 + score.hit2);
                    selectionSort(hits);
                    speeds[k] = score.speed;
                    selectionSort(speeds);
                    //Create new 2D array with all of the information
                    int leaderboard[][] = {hits, speeds};

                    //Print everything back out, with the new game's information (if it exceeds previous scores)
                    FileWriter fw = new FileWriter(file);
                    PrintWriter pw = new PrintWriter(fw);

                    k = 0;
                    int n = 0;
                    //Print top 5 for both scores, 6th element is ignored
                    for (i = 0; i < 5; i++) {
                        for (j = 0; j < 2; j++) {
                            if (j%2 == 0) {
                            pw.println(leaderboard[j][k]);
                            k++;
                            }
                            else {
                            pw.println(leaderboard[j][n]);
                            n++;
                            }
                        }
                    }
                    pw.close();
                } catch (Exception e) {
                    System.out.println("IO EXCEPTION");
                }
            }
            //File doesn't exist, create it and print score
            else {
                try {
                    FileWriter fw = new FileWriter(file);
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println(score.hit1+score.hit2);
                    pw.println(score.speed);
                    pw.close();
                } catch (Exception e) {}
            }
        }
        //"Delete" current frame    
        frame.setVisible(false);
        //Create a leaderboard
        if (singleplayer) new Leaderboard(singleplayer, winner, score.hit1, score.speed);
        else new Leaderboard(singleplayer, winner, score.hit1+score.hit2, score.speed);
    }

    //Selection sort in descending order
    public void selectionSort(int[] list) {
        for (int top = list.length-1; top > 0; top--) {
                int smallest = 0;
                //Find smallest value in array
                for (int i = 0; i <= top; i++) {
                    if (list[i] < list[smallest]) {
                        //Save element of smallest value
                        smallest = i;
                    }
                }
                //Switcheroo
                int temp = list[top];
                list[top] = list[smallest];
                list[smallest] = temp;
            }
    }

    public void run() {
        //Gameloop 60 times/sec
        long lastTime = System.nanoTime();
        double ticks = 60.0;
        double nanoseconds = 1000000000/ticks;
        double delta = 0;
        //Repeat until game ends
        while (loop) {
            long now = System.nanoTime();
            delta += (now-lastTime)/nanoseconds;
            lastTime = now;
            if (delta >= 1) {
                //Skip movement and collision while paused
                if (!paused) {
                    move();
                    checkCollision();
                }
                repaint();
                delta--;
            }
        }
    }

    //Action Listener for keys
    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            //Sends key pressed to method inside Paddle class
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased (KeyEvent e) {
            //Sends key released to method inside Paddle class
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
            //P key toggles pause
            if (e.getKeyCode() == KeyEvent.VK_P) paused = !paused;
        }
    }

}
