package javagame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class MainGame extends BasicGame {
	// resolution
	final static int resX = 1980;
	final static int resY = 1020;

	// IGU's
	final static int IGUS_HOR = 1000;
	final static int IGUS_VERT = 500;
	private static double conversion_factor = 1.02;
	private float[] p1_cen = new float[2];
	private float[] p2_cen = new float[2];

	// IGU dbug
	private Shape circle;
	private Shape circle2;

	// floor
	private Shape floor;
	private CollisionBox floor_collision;
	private Color floorColor;

	// walls
	private Shape wall;
	private Shape wall2;
	private CollisionBox wall_collision1;
	private CollisionBox wall_collision2;

	// 2 instances of ninja
	private Ninja p1_ninja;
	private Ninja p2_ninja;
	private float[] player1SpawnPos = new float[2];
	private float[] player2SpawnPos = new float[2];
	private CollisionBox player1_cBox;
	private CollisionBox player2_cBox;

	// Icons
	private SpriteSheet icons_atk;
	private SpriteSheet icons_select;
	private SpriteSheet icons_mvmt;

	// instance of Camera
	// private Camera camera;

	public MainGame(String title) {
		super(title);
	}

	public void init(GameContainer container) throws SlickException {
		// define the custom floor color
		floorColor = new Color(255, 100, 10);

		// import ninja sprite sheets
		p1_ninja = new Ninja(100, "res/Ninja.png");
		p2_ninja = new Ninja(100, "res/Ninja.png");
		// set floor bounds
		floor = new Rectangle(0, resY - resY / 4, resX, 1);
		floor_collision = new CollisionBox(0, resY - resY / 4 + 1, resX, 1);

		// set wall bounds
		wall = new Rectangle(1, 0, 1, resY);
		wall2 = new Rectangle(resX, 0, 1, resY);
		wall_collision1 = new CollisionBox(0, 0, 2, resY);
		wall_collision2 = new CollisionBox(resX - 1, 0, 2, resY);

		// define spawn position
		player1SpawnPos[0] = (float) (resX / 2) - 60;
		player1SpawnPos[1] = floor.getMaxY() - 80;
		player2SpawnPos[0] = (float) (resX / 2) + 60;
		player2SpawnPos[1] = floor.getMaxY() - 80;

		// define initial collision boxes (collision values) are arbitrary values and
		// probably
		// should be defined another way
		player1_cBox = new CollisionBox((int) player1SpawnPos[0] + 50, (int) player1SpawnPos[1] + 45, 35, 35);
		player2_cBox = new CollisionBox((int) player2SpawnPos[0] + 40, (int) player2SpawnPos[1] + 45, 35, 35);

		// define IGU'S
		p1_cen[0] = (player1_cBox.getX()) * (float) conversion_factor;
		p2_cen[0] = player2_cBox.getX() * (float) conversion_factor;
		p1_cen[1] = player1_cBox.getY() * (float) conversion_factor;
		p2_cen[1] = player2_cBox.getY() * (float) conversion_factor;

		// dbg igus
		circle = new Circle(p1_cen[0], p2_cen[1], 5);
		circle2 = new Circle(p2_cen[0], p2_cen[1], 5);

		// setup icons
		icons_atk = new SpriteSheet("res/Attack_Buttons.png", 128, 128);
		icons_select = new SpriteSheet("res/Lock-Hold.png", 128, 128);
		icons_mvmt = new SpriteSheet("res/Movement Buttons.png", 128, 128);

		// camera = new Camera()

	}

	public void update(GameContainer container, int frames) throws SlickException {
		// check if player is colliding with floor

	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		// draw floor
		g.setColor(floorColor);
		g.draw(floor);
		g.setColor(Color.red);
		g.draw(floor_collision.getCollisionBox());

		// draw walls
		g.setColor(floorColor);
		g.draw(wall);
		g.draw(wall2);

		g.setColor(Color.red);
		g.draw(wall_collision1.getCollisionBox());
		g.draw(wall_collision2.getCollisionBox());

		// draw initial sprite
		p1_ninja.getImage().draw(player1SpawnPos[0], player1SpawnPos[1]);
		p2_ninja.getImage().getFlippedCopy(true, false).draw(player2SpawnPos[0], player2SpawnPos[1]);

		// draw collision boxes
		g.setColor(Color.white);
		g.draw(player1_cBox.getCollisionBox());
		g.draw(player2_cBox.getCollisionBox());

		// draw dbug
		g.setColor(Color.blue);
		g.draw(circle);
		g.draw(circle2);

		// dbug
		g.drawString("p1", player1_cBox.getX(), player1_cBox.getY() - 20);

		// TODO: draw icons

	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer gameContainer = new AppGameContainer(new MainGame("YOMIH Remake"));

		gameContainer.setDisplayMode(resX, resY, false);

		gameContainer.start();
	}
}
