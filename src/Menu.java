package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
    
    public void render(Graphics g) {
        Font font = new Font("Tahoma", Font.BOLD, 100);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("YOUR ONLY JAVA IS HUSTlE", GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2);
    
    }
}
