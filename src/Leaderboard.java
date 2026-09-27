import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class Leaderboard extends JFrame implements ActionListener{
    JButton restart;
    JButton quit;

    //Constructor
    Leaderboard(boolean singleplayer, int winner, int hit, int speed) {
        //Set Frame
        this.setTitle("Leaderboard");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(0,0,700,550);    
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        //Set Button
        restart = new JButton("Play Again");
        restart.setFocusable(false);
        restart.addActionListener(this);
        restart.setFont((new Font("Consolas", Font.PLAIN, 20)));
        restart.setBorder(BorderFactory.createEtchedBorder());
        restart.setForeground(new Color(0x503C3C));
        restart.setBackground(new Color(0xDED0B6));
        restart.setBounds(250,0,200,50);

        quit = new JButton("Quit");
        quit.setFocusable(false);
        quit.addActionListener(this);
        quit.setFont((new Font("Consolas", Font.PLAIN, 20)));
        quit.setBorder(BorderFactory.createEtchedBorder());
        quit.setForeground(new Color(0x503C3C));
        quit.setBackground(new Color(0xDED0B6));
        quit.setBounds(300,50,100,50);

        //Set Label
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 35));
        titleLabel.setForeground(new Color(0x503C3C));

        //Arrays to be used later by labels
        int[] hits = new int[5];
        int[] speeds = new int[5];
        //Singleplayer leaderboard
        if (singleplayer) {
            titleLabel.setText("Personal Singleplayer Leaderboard");
            try {
                FileReader fr = new FileReader(GamePanel.getSaveFile("SingleplayerPong.txt"));
                Scanner s = new Scanner(fr);

                int i = 0;
                int j = 0;
                int k = 0;
                //Read contents of file into the arrays
                while (s.hasNext()) {
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
            } catch (Exception e) {}
        }
        //Multiplayer leaderboard
        else {
            titleLabel.setText("Personal Multiplayer Leaderboard");
            try {
                FileReader fr = new FileReader(GamePanel.getSaveFile("MultiplayerPong.txt"));
                Scanner s = new Scanner(fr);

                int i = 0;
                int j = 0;
                int k = 0;
                //Read contents of file into arrays
                while (s.hasNext()) {
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
            } catch (Exception e) {}
        }

        //Set panels
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(0xDED0B6));
        p1.setBounds(0, 80, 350, 40);

        JPanel p2 = new JPanel();
        p2.setBackground(new Color(0xDED0B6));
        p2.setBounds(350, 80, 350, 40);

        JPanel p3 = new JPanel();
        p3.setBackground(new Color(0xFDF7E4));
        p3.setBounds(0, 120, 350, 130);

        JPanel p4 = new JPanel();
        p4.setBackground(new Color(0xFDF7E4));
        p4.setBounds(350, 120, 350, 130);

        JPanel p5 = new JPanel();
        p5.setBackground(new Color(0xBBAB8C));
        p5.setBounds(0, 250, 700, 50);

        JPanel p6 = new JPanel();
        p6.setBackground(new Color(0xDED0B6));
        p6.setBounds(0, 300, 700, 40);

        JPanel p7 = new JPanel();
        p7.setBackground(new Color(0xFDF7E4));
        p7.setBounds(0, 340, 700, 60);

        JPanel p8 = new JPanel();
        p8.setBackground(new Color(0xFDF7E4));
        p8.setBounds(0, 400, 700, 200);
        p8.setLayout(null);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0xBBAB8C));
        titlePanel.setBounds(0, 0, 700, 80);
        
        //Labels
        JLabel l1 = new JLabel();
        l1.setText("MOST # of HITS");
        l1.setFont(new Font("Consolas", Font.BOLD, 20));
        l1.setForeground(new Color(0x503C3C));

        JLabel l2 = new JLabel();
        l2.setText("TOP SPEEDS");
        l2.setFont(new Font("Consolas", Font.BOLD, 20));
        l2.setForeground(new Color(0x503C3C));

        JLabel l3 = new JLabel();
        l3.setText("Previous Game:");
        l3.setFont(new Font("Consolas", Font.BOLD, 30));
        l3.setForeground(new Color(0x503C3C));
        

        JLabel l4 = new JLabel();
        l4.setFont(new Font("Consolas", Font.PLAIN, 20));
        l4.setForeground(new Color(0x503C3C));
        if (winner == 1) l4.setText("Player 1 Wins!");
        if (winner == 2) l4.setText("Player 2 Wins!");
        if (winner == 3) l4.setText("Player 2(CPU) Wins!");

        JLabel l5 = new JLabel();
        l5.setText("Hits:" + hit + "               Speed:" + speed);
        l5.setFont(new Font("Consolas", Font.PLAIN, 20));
        l5.setForeground(new Color(0x503C3C));

        JLabel topHits = new JLabel();
        topHits.setText("<html>#1: " + hits[0] + "<br/>#2: " + hits[1] + "<br/>#3: " + hits[2] + "<br/>#4: " + hits[3] + "<br/>#5: " + hits[4] + "</html>");
        topHits.setFont(new Font("Consolas", Font.PLAIN, 20));
        topHits.setForeground(new Color(0x503C3C));

        JLabel topSpeeds = new JLabel();
        topSpeeds.setText("<html>#1: " + speeds[0] + "<br/>#2: " + speeds[1] + "<br/>#3: " + speeds[2] + "<br/>#4: " + speeds[3] + "<br/>#5: " + speeds[4] + "</html>");
        topSpeeds.setFont(new Font("Consolas", Font.PLAIN, 20));
        topSpeeds.setForeground(new Color(0x503C3C));

        //Add everything to frame
        titlePanel.add(titleLabel);
        p1.add(l1);
        p2.add(l2);
        p3.add(topHits);
        p4.add(topSpeeds);
        p5.add(l3); 
        p6.add(l4);
        p7.add(l5);
        p8.add(restart);
        p8.add(quit);
        this.add(titlePanel);
        this.add(p1);
        this.add(p2);
        this.add(p3);
        this.add(p4);
        this.add(p5);
        this.add(p6);
        this.add(p7);
        this.add(p8);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Open menu and close leaderboard
        if (e.getSource() == restart) {
            SwingUtilities.getWindowAncestor((Component)e.getSource()).dispose();
            new Pong();
        }
        //Close game
        if (e.getSource() == quit) {
            System.exit(0);
        }
    }
    
}
