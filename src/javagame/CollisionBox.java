package javagame;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class CollisionBox {

	float x;
	float y;
	int width;
	int height;
	private Shape collisionBox;

	public CollisionBox(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		collisionBox = new Rectangle(x, y, width, height);
	}

	public Shape getCollisionBox() {
		return this.collisionBox;
	}

	public void update() {
		this.collisionBox = new CollisionBox(x, y, width, height).getCollisionBox();
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(float width) {
		this.width = (int) width;
	}

	public void setHeight(float height) {
		this.height = (int) height;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

}
