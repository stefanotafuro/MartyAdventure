package edu.unibo.martyadventure.model;

import com.badlogic.gdx.math.Vector2;

public abstract class Character {

	private String name;
	private int hp;
	private Weapon weapon;
	private Vector2 position;
	private float speed;
	private Vector2 velocity;

	public Character(String name, int hp, Weapon weapon, Vector2 position, float speed, Vector2 velocity) {
		setName(name);
		setHp(hp);
		setWeapon(weapon);
		setPosition(position);
		setSpeed(speed);
		setVelocity(velocity);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

}
