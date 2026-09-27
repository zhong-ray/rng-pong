import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Pong implements ActionListener{
    JFrame frame = new JFrame();
    JButton button1 = new JButton("1 Player");
    JButton button2 = new JButton("2 Players");
    JButton button3 = new JButton("Don't Click"); 

    //Constructor
    public Pong() {
        //Set Buttons
        button1.setBounds(100, 100, 200, 50);
        button1.setFocusable(false);
        button1.addActionListener(this);

        button2.setBounds(400, 100, 200, 50);
        button2.setFocusable(false);
        button2.addActionListener(this);
        
        button3.setBounds(300, 250, 100, 30);
        button3.setFocusable(false);
        button3.addActionListener(this);

        //Visually customize buttons
        button1.setFont(new Font("Consolas", Font.PLAIN, 20));
        button2.setFont(new Font("Consolas", Font.PLAIN, 20));
        button3.setFont(new Font("Consolas", Font.PLAIN, 9));

        button1.setForeground(new Color(0x503C3C));
        button2.setForeground(new Color(0x503C3C));
        button3.setForeground(new Color(0x503C3C));

        button1.setBackground(new Color(0xDED0B6));
        button2.setBackground(new Color(0xDED0B6));
        button3.setBackground(new Color(0xDED0B6));

        button1.setBorder(BorderFactory.createEtchedBorder());
        button2.setBorder(BorderFactory.createEtchedBorder());
        button3.setBorder(BorderFactory.createEtchedBorder());

        //Change colour of button click effect
        UIManager.put("Button.select", new Color(0xBBAB8C));


        //Create panels
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();   

        //Customize panels
        panel1.setBackground(new Color(0xFDF7E4));
        panel2.setBackground(new Color(0xFAEED1));
        panel1.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(0xFDF7E4));
        p1.setBounds(0, 0, 700, 50);

        //Create and customize labels
        JLabel title = new JLabel();
        title.setText("Welcome to PONG!");
        title.setFont(new Font("Cambria", Font.BOLD, 40));
        title.setForeground(new Color(0x503C3C));

        JLabel label1 = new JLabel();
        label1.setText("Pick Gamemode: ");
        label1.setFont(new Font("Cambria", Font.PLAIN, 25));
        label1.setForeground(new Color(0x503C3C));

        JLabel label2 = new JLabel();
        label2.setText("Each hit has a 50% chance to increase the speed of the ball, good luck!");
        label2.setFont(new Font("Cambria", Font.PLAIN, 15));
        label2.setForeground(new Color(0x503C3C));
        label2.setBounds(125, 40, 600, 30);

        JLabel label3 = new JLabel();
        label3.setText("(First hit has a 100% chance of increasing the speed of the ball once, and 50% for twice)");
        label3.setFont(new Font("Cambria", Font.PLAIN, 15));
        label3.setForeground(new Color(0x503C3C));
        label3.setBounds(75, 60, 600, 30);

        //Set frame
        frame.setTitle("Pong");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //Images for buttons and icon   
        ImageIcon image = new ImageIcon("Bald Mario.jpg");
        frame.setIconImage(image.getImage());

        ImageIcon i1 = new ImageIcon("1-Player.png");
        Image icon1 = i1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        button1.setIcon(new ImageIcon(icon1));

        ImageIcon i2 = new ImageIcon("2-Player.png");
        Image icon2 = i2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        button2.setIcon(new ImageIcon(icon2));
        
        //Add everything to frame
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.NORTH);
        panel2.add(title);
        panel1.add(p1);
        p1.add(label1);
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(label2);
        panel1.add(button3);
        panel1.add(label3);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        //Create it
        new Pong();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //What buttons will do when clicked
        if (e.getSource() == button1) {
            //Create game and "delete" current frame
            new GameFrame(true);
            frame.setVisible(false);
        }
        if (e.getSource() == button2) {
            //Create game and "delete" current frame
            new GameFrame(false);
            frame.setVisible(false);
        }
        if (e.getSource() == button3) {
            //Stuff for fun
            for (int i = 0; i < 3; i++) {
                JOptionPane.showMessageDialog(null, "You're computer has a VIRUS!!!", "Pong", JOptionPane.WARNING_MESSAGE);
            }
            button3.setVisible(false);
        }
    }

}
