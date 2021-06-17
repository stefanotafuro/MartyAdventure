package edu.unibo.martyadventure.model.character;

import edu.unibo.martyadventure.model.weapon.Weapon;

public class PlayerCharacter extends Character {

    private Shoes shoes;


    public PlayerCharacter(Shoes shoes, String name, int hp, Weapon weapon) {
        super(name, hp, weapon);
        setShoes(shoes);
    }

    // Getter & Setter

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }
}
