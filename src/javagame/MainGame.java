package javagame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class MainGame extends BasicGame {
	public MainGame(String title) {
		super(title);
	}

	public void init(GameContainer container) throws SlickException {

	}

	public void update(GameContainer container, int frames) {

	}

	public void render(GameContainer container, Graphics g) {
		g.drawString("Java time", 600, 800);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer gameContainer = new AppGameContainer(new MainGame("YOMIH Remake"));

		gameContainer.setDisplayMode(1920, 1080, false);

		gameContainer.start();
	}
}
