package javagame;

public class Ninja {

	private float speed;
	private int health;
	private boolean active;

	public Ninja(float speed, int health, boolean active) {
		this.speed = speed;
		this.health = health;
		this.active = active;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getSpeed() {
		return this.speed;
	}

	public int getHealth() {
		return this.health;
	}

	public boolean isActive() {
		return this.active;
	}
}
