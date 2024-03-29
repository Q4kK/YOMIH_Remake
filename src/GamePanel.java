package src;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

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
}
