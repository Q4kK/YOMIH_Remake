package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    Thread gamethread;

    final int baseTileSize = 128; // 128x128 tile, characters are 128x128
    final int scale = 16;

    final int tileSize = baseTileSize * scale; // 2048 tiles
    // set width and height
    final int screenWidth = 1920;
    final int screenHeight = 1080;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);


    }

    public void startGameThread() {
        gamethread = new Thread(this);
        gamethread.start();
    }

    public void run() {

        while (gamethread != null) {

            System.out.println("Game looping ");

            update();

            repaint();
        }
    }

    public void update() {

    }

    public void paintObject(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2.fillRect(1000, 1000, tileSize, tileSize);
        g2.dispose();
    }

}
