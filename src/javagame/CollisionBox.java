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

}
