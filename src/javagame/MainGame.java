package javagame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame {
	// resolution
	static int resX = 1920;
	static int resY = 1080;

	public MainGame(String title) {
		super(title);
	}

	public void init(GameContainer container, StateBasedGame sbd) {

	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer gameContainer = new AppGameContainer(new MainGame("YOMIH Remake"));

		gameContainer.setDisplayMode(resX, resY, false);

		gameContainer.start();
	}

	public int getID() {
		return 0;
	}

	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new Fighting());
		this.addState(new GameOver());
	}

}
