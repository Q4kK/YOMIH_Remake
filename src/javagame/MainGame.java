package javagame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class MainGame extends BasicGame {
	// resolution
	final static int resX = 1980;
	final static int resY = 1020;

	// sprite sheets
	private SpriteSheet player1Ninja = null;
	private SpriteSheet player2Ninja = null;

	// floor
	private Shape floor;
	private CollisionBox floor_collision;
	private Color floorColor;

	// 2 instances of ninja
	private float[] player1SpawnPos = new float[2];
	private float[] player2SpawnPos = new float[2];
	private CollisionBox player1_cBox;
	private CollisionBox player2_cBox;

	public MainGame(String title) {
		super(title);
	}

	public void init(GameContainer container) throws SlickException {
		// define the custom floor color
		floorColor = new Color(255, 100, 10);

		// import ninja sprite sheets
		player1Ninja = new SpriteSheet("res/Ninja-Sprite.png", 128, 128);
		player2Ninja = new SpriteSheet("res/Ninja-Sprite.png", 128, 128);
		player2Ninja.rotate(180);

		// set floor bounds
		floor = new Rectangle(0, resY - resY / 4, resX, 1);
		floor_collision = new CollisionBox(0, resY - resY / 4 + 1, resX, 1);

		// define spawn position
		player1SpawnPos[0] = (float) (resX / 2) + 60;
		player1SpawnPos[1] = floor.getMaxY() - 80;
		player2SpawnPos[0] = (float) (resX / 2) - 60;
		player2SpawnPos[1] = floor.getMaxY() - 80;

		// define initial collision boxes (70, 70) are arbitrary values and probably
		// should be defined another way
		player1_cBox = new CollisionBox((int) player1SpawnPos[0] + 50, (int) player1SpawnPos[1] + 45, 35, 35);
		player2_cBox = new CollisionBox((int) player2SpawnPos[0] + 50, (int) player2SpawnPos[1] + 45, 35, 35);

	}

	public void update(GameContainer container, int frames) throws SlickException {

	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		// draw floor
		g.setColor(floorColor);
		g.draw(floor);
		g.setColor(Color.red);
		g.draw(floor_collision.getCollisionBox());

		// draw initial sprite
		player1Ninja.getSubImage(0, 0).draw(player1SpawnPos[0], player1SpawnPos[1]);
		g.drawString("1", player1SpawnPos[0], player1SpawnPos[1]);
		player2Ninja.getSubImage(0, 0).draw(player2SpawnPos[0], player2SpawnPos[1]);

		// draw collision boxes
		g.setColor(Color.white);
		g.draw(player1_cBox.getCollisionBox());
		g.draw(player2_cBox.getCollisionBox());

	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer gameContainer = new AppGameContainer(new MainGame("YOMIH Remake"));

		gameContainer.setDisplayMode(resX, resY, false);

		gameContainer.start();
	}
}
