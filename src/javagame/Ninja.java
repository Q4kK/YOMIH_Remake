package javagame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Ninja {

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

	public int getX() {
		return this.x;
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

	public void draw() {
		image.draw(x, y);
	}

	public void flip() {
		this.image = image.getFlippedCopy(true, false);
	}

	public void update() {
		draw();
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void move(int amount) {
		if (direction == 0)
			x -= amount;
		else if (direction == 1)
			x += amount;
	}

	public void setX(float pos) {
		this.x = (int) pos;
	}

	public void setY(float pos) {
		this.y = (int) pos;
	}

	public int getY() {
		return this.y;
	}

}
