package javagame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Fighting extends BasicGameState {

	private static int resX = 1920;
	private static int resY = 1080;
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
	final float atk_locX = resX / 2;
	final float atk_locY = resY - resY / 4 + 100;
	final float mvmt_locX = resX / 2;
	final float mvmt_locY = resY - resY / 4 + 50;
	final float select_locX = resX / 2;
	final float select_locY = resY - resY / 4 - 30;
	final float icon_scale = 1.35f;
	private CollisionBox mv_jump;
	private CollisionBox mv_left;
	private CollisionBox mv_right;
	private CollisionBox mv_sJump;
	private CollisionBox atk_punch;
	private CollisionBox atk_kick;
	private CollisionBox atk_nChuck;
	private CollisionBox atk_grab;
	private CollisionBox select_lock;
	private CollisionBox select_hold;

	// turn for p1 and p2
	private boolean turn;
	private int turnCount;
	private int moveId;
	private int p2MoveId;

	// debug stuff
	private String selectedMove;
	private String playerTurn;

	// input
	Input input;
	private boolean mousePressed;

	private boolean collides;
	private boolean atk_collides = false;
	private boolean attacking = false;
	private int knockback;
	private int player_knocked; // 0 for p1, 1 for p2
	private Circle mouse;

	public void init(GameContainer container, StateBasedGame sbd) throws SlickException {
		collides = false;
		player_knocked = 0;
		knockback = 0;
		container.setTargetFrameRate(60);

		// define the custom floor color
		floorColor = new Color(255, 100, 10);

		// init the turn order
		turnCount = 0;
		turn = true;
		playerTurn = new String("P1 Turn");
		selectedMove = new String("Selected move: ");

		// import ninja sprite sheets
		p1_ninja = new Ninja(100, "res/Ninja.png", (int) player1SpawnPos[0], (int) player1SpawnPos[1]);
		p2_ninja = new Ninja(100, "res/Ninja.png", (int) player2SpawnPos[0], (int) player2SpawnPos[1]);
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

		p1_ninja.setX(player1SpawnPos[0]);
		p1_ninja.setY(player1SpawnPos[1]);

		p2_ninja.setX(player2SpawnPos[0]);
		p2_ninja.setY(player2SpawnPos[1]);
		p2_ninja.flip();

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
		int icon_box_param = (int) (30 * icon_scale);
		int select_param = (int) (128 * icon_scale);
		icons_atk = new SpriteSheet("res/Attack_Buttons.png", 128, 128);
		icons_select = new SpriteSheet("res/Lock-Hold.png", 128, 128);
		icons_mvmt = new SpriteSheet("res/Movement Buttons.png", 128, 128);

		mv_jump = new CollisionBox((int) mvmt_locX, (int) mvmt_locY + 87, icon_box_param, icon_box_param);
		mv_left = new CollisionBox((int) mvmt_locX + 45, (int) mvmt_locY + 87, icon_box_param, icon_box_param);
		mv_right = new CollisionBox((int) mvmt_locX + 90, (int) mvmt_locY + 87, icon_box_param, icon_box_param);
		mv_sJump = new CollisionBox((int) mvmt_locX + 130, (int) mvmt_locY + 87, icon_box_param, icon_box_param);

		atk_punch = new CollisionBox((int) atk_locX, (int) atk_locY + 87, icon_box_param, icon_box_param);
		atk_kick = new CollisionBox((int) atk_locX + 45, (int) atk_locY + 87, icon_box_param, icon_box_param);
		atk_nChuck = new CollisionBox((int) atk_locX + 90, (int) atk_locY + 87, icon_box_param, icon_box_param);
		atk_grab = new CollisionBox((int) atk_locX + 130, (int) atk_locY + 87, icon_box_param, icon_box_param);

		select_lock = new CollisionBox((int) select_locX, (int) select_locY + 65, select_param, icon_box_param);
		select_hold = new CollisionBox((int) select_locX, (int) select_locY + 110, select_param, icon_box_param);

		mouse = new Circle(0, 0, 0);
		input = container.getInput();

	}

	public void update(GameContainer container, StateBasedGame sbg, int frames) throws SlickException {

		if (p1_ninja.getHealth() <= 0 || p2_ninja.getHealth() <= 0) {
			sbg.enterState(3);
		}

		if (player1_cBox.getY() > 776) {
			player1_cBox.setY(player1_cBox.getY() - 1);
			player1_cBox.update();
		}
		if (player2_cBox.getY() > 776) {
			player2_cBox.setY(player2_cBox.getY() - 1);
			player2_cBox.update();
		}

		if (p1_ninja.getY() > 731) {
			p1_ninja.setY(p1_ninja.getY() - 1);
			p1_ninja.update();
		}

		if (p2_ninja.getY() > 731) {
			p2_ninja.setY(p2_ninja.getY() - 1);
			p2_ninja.update();
		}

		// gravity
		if (player1_cBox.getY() < floor.getMinY()) {
			player1_cBox.setY(player1_cBox.getY() + 1);
			p1_ninja.setY(p1_ninja.getY() + 1);
			player1_cBox.update();
			p1_ninja.update();
		}
		if (player2_cBox.getY() < floor.getMinY()) {
			player2_cBox.setY(player2_cBox.getY() + 1);
			p2_ninja.setY(p2_ninja.getY() + 1);
			player2_cBox.update();
			p2_ninja.update();
		}
		mousePressed = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		mouse.setCenterX(container.getInput().getMouseX());
		mouse.setCenterY(container.getInput().getMouseY());

		atk_collides = player1_cBox.getCollisionBox().intersects(player2_cBox.getCollisionBox());

		collides = mouse.intersects(circle) || mouse.intersects(circle2) || mouse.intersects(mv_jump.getCollisionBox())
				|| mouse.intersects(mv_left.getCollisionBox()) || mouse.intersects(mv_right.getCollisionBox())
				|| mouse.intersects(mv_sJump.getCollisionBox()) || mouse.intersects(atk_punch.getCollisionBox())
				|| mouse.intersects(atk_kick.getCollisionBox()) || mouse.intersects(atk_nChuck.getCollisionBox())
				|| mouse.intersects(atk_grab.getCollisionBox()) || mouse.intersects(select_lock.getCollisionBox())
				|| mouse.intersects(select_hold.getCollisionBox());
		if (!attacking) {
			if (turnCount >= 2) {

				// TODO: play the animation of the attack
				// Check if the collision boxes are intersecting
				// TODO: play the animation of the hit

				// Send knockback
				// health bar
				switch (moveId) {
				case 1:
					// punch
					player1_cBox.setX(player1_cBox.getX() + 15);
					player1_cBox.update();
					p1_ninja.setX(p1_ninja.getX() + 15);
					p1_ninja.update();

					if (atk_collides) {
						p2_ninja.setHealth(p2_ninja.getHealth() - 10);
						player_knocked = 0;
						attacking = true;
						knockback = 15;
					}
					moveId = 0;
					turnCount = 0;
					break;
				case 2:
					// kick
					player1_cBox.setX(player1_cBox.getX() + 20);
					player1_cBox.update();
					p1_ninja.setX(p1_ninja.getX() + 20);
					p1_ninja.update();

					if (atk_collides) {
						p2_ninja.setHealth(p2_ninja.getHealth() - 15);
						player_knocked = 0;
						attacking = true;
						knockback = 20;
					}
					moveId = 0;
					turnCount = 0;
					break;
				case 3:
					// nunchuck
					player1_cBox.setX(player1_cBox.getX() + 25);
					player1_cBox.update();
					p1_ninja.setX(p1_ninja.getX() + 25);
					p1_ninja.update();

					if (atk_collides) {
						p2_ninja.setHealth(p2_ninja.getHealth() - 30);
						player_knocked = 0;
						attacking = true;
						knockback = 30;
					}
					moveId = 0;
					turnCount = 0;
					break;
				case 4:
					// throw
					player1_cBox.setX(player1_cBox.getX() + 5);
					player1_cBox.update();
					p1_ninja.setX(p1_ninja.getX() + 5);
					p1_ninja.update();

					if (atk_collides) {
						p2_ninja.setHealth(p2_ninja.getHealth() - 5);
						player_knocked = 0;
						attacking = true;
						knockback = 50;
					}
					moveId = 0;
					turnCount = 0;
					break;
				case 5:
					player1_cBox.setY(player1_cBox.getY() - 50);
					player1_cBox.update();
					p1_ninja.setY(p1_ninja.getY() - 50);
					p1_ninja.update();
					moveId = 0;
					turnCount = 0;
					break;
				case 6:
					player1_cBox.setX(player1_cBox.getX() - 50);
					player1_cBox.update();
					p1_ninja.setX(p1_ninja.getX() - 50);
					p1_ninja.update();
					moveId = 0;
					turnCount = 0;
					break;
				case 7:
					player1_cBox.setX(player1_cBox.getX() + 50);
					player1_cBox.update();
					p1_ninja.setX(p1_ninja.getX() + 50);
					p1_ninja.update();
					moveId = 0;
					turnCount = 0;
					break;
				case 8:
					player1_cBox.setY(player1_cBox.getY() - 100);
					player1_cBox.update();
					p1_ninja.setY(p1_ninja.getY() - 100);
					p1_ninja.update();
					moveId = 0;
					turnCount = 0;
					break;
				default:
					moveId = 0;
					turnCount = 0;

				}
				switch (p2MoveId) {
				case 1:
					player2_cBox.setX(player2_cBox.getX() - 15);
					player2_cBox.update();
					p2_ninja.setX(p2_ninja.getX() - 15);
					p2_ninja.update();
					if (atk_collides) {
						p1_ninja.setHealth(p1_ninja.getHealth() - 10);
						player_knocked = 1;
						attacking = true;
						knockback = 15;
					}
					p2MoveId = 0;
					turnCount = 0;
					break;
				case 2:
					player2_cBox.setX(player2_cBox.getX() - 20);
					player2_cBox.update();
					p2_ninja.setX(p2_ninja.getX() - 20);
					p2_ninja.update();
					if (atk_collides) {
						p1_ninja.setHealth(p1_ninja.getHealth() - 15);
						player_knocked = 1;
						attacking = true;
						knockback = 20;
					}
					p2MoveId = 0;
					turnCount = 0;
					break;
				case 3:
					player2_cBox.setX(player2_cBox.getX() - 25);
					player2_cBox.update();
					p2_ninja.setX(p2_ninja.getX() - 25);
					p2_ninja.update();
					if (atk_collides) {
						p1_ninja.setHealth(p1_ninja.getHealth() - 30);
						player_knocked = 1;
						attacking = true;
						knockback = 30;
					}
					p2MoveId = 0;
					turnCount = 0;
					break;
				case 4:
					player2_cBox.setX(player2_cBox.getX() - 5);
					player2_cBox.update();
					p2_ninja.setX(p2_ninja.getX() - 5);
					p2_ninja.update();
					if (atk_collides) {
						p2_ninja.setHealth(p2_ninja.getHealth() - 5);
						player_knocked = 1;
						attacking = true;
						knockback = 50;
					}
					p2MoveId = 0;
					turnCount = 0;
					break;
				case 5:
					player2_cBox.setY(player2_cBox.getY() - 50);
					player2_cBox.update();
					p2_ninja.setY(p2_ninja.getY() - 50);
					p2_ninja.update();
					p2MoveId = 0;
					turnCount = 0;
					break;
				case 6:
					player2_cBox.setX(player2_cBox.getX() - 50);
					player2_cBox.update();
					p2_ninja.setX(p2_ninja.getX() - 50);
					p2_ninja.update();
					p2MoveId = 0;
					turnCount = 0;
					break;
				case 7:
					player2_cBox.setX(player2_cBox.getX() + 50);
					player2_cBox.update();
					p2_ninja.setX(p2_ninja.getX() + 50);
					p2_ninja.update();
					p2MoveId = 0;
					turnCount = 0;
					break;
				case 8:
					player2_cBox.setY(player2_cBox.getY() - 100);
					player2_cBox.update();
					p2_ninja.setY(p2_ninja.getY() - 100);
					p2_ninja.update();
					p2MoveId = 0;
					turnCount = 0;
					break;
				default:
					p2MoveId = 0;
					turnCount = 0;

				}
			}
		}

		if (attacking) {
			if (knockback <= 0)
				attacking = false;
			else if (attacking && player_knocked == 0 && atk_collides) {
				player2_cBox.setX(player2_cBox.getX() + 3 * knockback);
				p2_ninja.setX(p2_ninja.getX() + 3 * knockback);
				player2_cBox.setY(player2_cBox.getY() - 2 * knockback);
				p2_ninja.setY(p2_ninja.getY() - 2 * knockback);
				player2_cBox.update();
				p2_ninja.update();
				knockback--;
			} else if (attacking && player_knocked == 1 && atk_collides) {
				player1_cBox.setX(player1_cBox.getX() - 3 * knockback);
				p1_ninja.setX(p1_ninja.getX() - 3 * knockback);
				player1_cBox.setY(player1_cBox.getY() - 2 * knockback);
				p1_ninja.setY(p1_ninja.getY() - 2 * knockback);
				p1_ninja.update();
				player1_cBox.update();
				knockback--;
			} else
				knockback--;

		}

		if (mouse.intersects(select_lock.getCollisionBox()) && mousePressed)
			if (turn)

			{
				selectedMove = "Selected move: ";
				playerTurn = new String("P2 Turn");
				turnCount++;
				turn = !turn;
			} else {
				selectedMove = "Selected move: ";
				playerTurn = new String("P1 Turn");
				turnCount++;
				turn = !turn;
			}

		if (mouse.intersects(atk_punch.getCollisionBox()) && mousePressed)
			if (!selectedMove.contains("Punch")) {
				selectedMove = "Selected move: ";
				selectedMove += "Punch";
				if (turn)
					moveId = 1;
				else
					p2MoveId = 1;
			}

		if (mouse.intersects(atk_kick.getCollisionBox()) && mousePressed)
			if (!selectedMove.contains("Kick")) {
				selectedMove = "Selected move: ";
				selectedMove += "Kick";
				if (turn)
					moveId = 2;
				else
					p2MoveId = 2;
			}

		if (mouse.intersects(atk_nChuck.getCollisionBox()) && mousePressed)
			if (!selectedMove.contains("NunChuck")) {
				selectedMove = "Selected move: ";
				selectedMove += "NunChuck";
				if (turn)
					moveId = 3;
				else
					p2MoveId = 3;
			}

		if (mouse.intersects(atk_grab.getCollisionBox()) && mousePressed)
			if (!selectedMove.contains("Grab")) {
				selectedMove = "Selected move: ";
				selectedMove += "Grab";
				if (turn)
					moveId = 4;
				else
					p2MoveId = 4;
			}

		if (mouse.intersects(mv_jump.getCollisionBox()) && mousePressed)
			if (!selectedMove.contains("Jump")) {
				selectedMove = "Selected move: ";
				selectedMove += "Jump";
				if (turn)
					moveId = 5;
				else
					p2MoveId = 5;
			}
		if (mouse.intersects(mv_left.getCollisionBox()) && mousePressed)
			if (!selectedMove.contains("Left")) {
				selectedMove = "Selected move: ";
				selectedMove += "Left";
				if (turn)
					moveId = 6;
				else
					p2MoveId = 6;
			}
		if (mouse.intersects(mv_right.getCollisionBox()) && mousePressed)
			if (!selectedMove.contains("Right")) {
				selectedMove = "Selected move: ";
				selectedMove += "Right";
				if (turn)
					moveId = 7;
				else
					p2MoveId = 7;
			}
		if (mouse.intersects(mv_sJump.getCollisionBox()) && mousePressed)
			if (!selectedMove.contains("Super Jump")) {
				selectedMove = "Selected move: ";
				selectedMove += "Super Jump";
				if (turn)
					moveId = 8;
				else
					p2MoveId = 8;
			}

		// TODO: check if player is colliding with floor
		// TODO: check if player is colliding with walls
		// TODO: check if both players are locked in

	}

	public void render(GameContainer container, StateBasedGame sbd, Graphics g) throws SlickException {

		g.drawString("Player 1 Health: " + p1_ninja.getHealth(), 500, 10);
		g.drawString("Player 2 Health: " + p2_ninja.getHealth(), 1500, 10);

		g.drawString("Attack collides: " + atk_collides, 10, 50);
		g.drawString("Attacking: " + attacking, 10, 70);

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
		p1_ninja.draw();
		p2_ninja.draw();

		// draw collision boxes
		g.setColor(Color.white);
		g.draw(player1_cBox.getCollisionBox());
		g.draw(player2_cBox.getCollisionBox());

		// All the icons
		icons_atk.getSprite(0, 0).draw(atk_locX, atk_locY, icon_scale);
		icons_atk.getSprite(2, 0).draw(atk_locX, atk_locY, icon_scale);
		icons_atk.getSprite(4, 0).draw(atk_locX, atk_locY, icon_scale);
		icons_atk.getSprite(6, 0).draw(atk_locX, atk_locY, icon_scale);
		icons_mvmt.getSprite(0, 0).draw(mvmt_locX, mvmt_locY, icon_scale);
		icons_mvmt.getSprite(2, 0).draw(mvmt_locX, mvmt_locY, icon_scale);
		icons_mvmt.getSprite(4, 0).draw(mvmt_locX, mvmt_locY, icon_scale);
		icons_mvmt.getSprite(6, 0).draw(mvmt_locX, mvmt_locY, icon_scale);
		icons_select.getSprite(0, 0).draw(select_locX, select_locY, icon_scale);
		icons_select.getSprite(2, 0).draw(select_locX, select_locY - 20, icon_scale);

		// where to click
		g.setColor(Color.green);
		g.draw(mv_jump.getCollisionBox());
		g.draw(mv_left.getCollisionBox());
		g.draw(mv_right.getCollisionBox());
		g.draw(mv_sJump.getCollisionBox());
		g.draw(atk_punch.getCollisionBox());
		g.draw(atk_kick.getCollisionBox());
		g.draw(atk_nChuck.getCollisionBox());
		g.draw(atk_grab.getCollisionBox());
		g.draw(select_lock.getCollisionBox());
		g.draw(select_hold.getCollisionBox());

		g.drawString(playerTurn, resX / 2, 20);
		g.drawString(selectedMove, resX / 2, 40);
	}

	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

}
