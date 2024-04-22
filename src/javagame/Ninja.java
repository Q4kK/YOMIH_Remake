package javagame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Ninja {

	private final int Left = 0;
	private final int Right = 1;
	private int x, y;

	private int health;
	private boolean active = true;
	private SpriteSheet sheet = null;
	private Image image = null;
	private int direction;

	public Ninja(int health, String image, int x, int y) throws SlickException {
		this.health = health;
		this.image = new Image(image);
		this.x = x;
		this.y = y;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getHealth() {
		return this.health;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setSheet(SpriteSheet sheet) {
		this.sheet = sheet;
	}

	public SpriteSheet getSheet() {
		return this.sheet;
	}

	public void setImage(String image) throws SlickException {
		this.image = new Image(image);
	}

	public Image getImage() {
		return this.image;
	}

	public Image getImage(int x, int y) throws SlickException {
		return new SpriteSheet(this.image, 128, 128).getSubImage(x, y);
	}

	public Image getImage(String image, int x, int y) throws SlickException {
		return new SpriteSheet(image, 128, 128).getSubImage(x, y);
	}

	public void draw() {
		image.draw(x, y);
	}

	public void update(int delta) {
		move();
	}

	public void move() {
		if (direction == Left)
			x -= 1;
		else if (direction == Right)
			x += 1;
	}

}
