package javagame;

public class Ninja {

	private float speed;
	private int health;

	public Ninja(float speed, int health) {
		this.speed = speed;
		this.health = health;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return this.speed;
	}

	public int getHealth() {
		return this.health;
	}
}
