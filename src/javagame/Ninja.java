package javagame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Ninja {

	private int health;
	private boolean active = true;
	private SpriteSheet sheet = null;
	private Image image = null;

	public Ninja(int health, String image) throws SlickException {
		this.health = health;
		this.image = new Image(image);
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
}
