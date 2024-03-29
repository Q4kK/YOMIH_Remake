package src;

import javax.swing.*;

public class GameFrame {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Your Only Move is HUSTLE (remake)");

        GamePanel gpanel = new GamePanel();

        //add menu here

        window.add(gpanel);

        window.pack();
    
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
