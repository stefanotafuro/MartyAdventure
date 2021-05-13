package edu.unibo.martyadventure.model.character;

import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.weapon.Weapon;

public class EnemyCharacter extends Character {

    public EnemyCharacter(Weapon dropitem, String name, int hp, Weapon weapon, Vector2 position, float speed, Vector2 velocity) {
        super(name, hp, weapon, position, speed, velocity);
        setDropitem(weapon);
    }

    private Weapon dropitem;

    public Weapon getDropitem() {
        return dropitem;
    }

    public void setDropitem(Weapon dropitem) {
        this.dropitem = dropitem;
    }
}