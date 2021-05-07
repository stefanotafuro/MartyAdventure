package edu.unibo.martyadventure.model;

import com.badlogic.gdx.math.Vector2;

public class PlayerCharacter extends Character {
    private Shoes shoes;

    public PlayerCharacter(Shoes shoes, String name, int hp, Weapon weapon, Vector2 position, float speed,
            Vector2 velocity) {
        super(name, hp, weapon, position, speed, velocity);
        setShoes(shoes);
    }

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }

}
