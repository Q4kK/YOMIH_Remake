package javagame;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class CollisionBox {

	int x;
	int y;
	int width;
	int height;
	private Shape collisionBox;

	public CollisionBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		collisionBox = new Rectangle(x, y, width, height);
	}

	public Shape getCollisionBox() {
		return this.collisionBox;
	}

	public void setX(float x) {
		this.x = (int) x;
	}

	public void setY(float y) {
		this.y = (int) y;
	}

	public void setWidth(float width) {
		this.width = (int) width;
	}

	public void setHeight(float height) {
		this.height = (int) height;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

}
