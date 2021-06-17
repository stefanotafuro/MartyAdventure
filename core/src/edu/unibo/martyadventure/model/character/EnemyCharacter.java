package edu.unibo.martyadventure.model.character;

import edu.unibo.martyadventure.model.weapon.Weapon;

public class EnemyCharacter extends Character {

    public EnemyCharacter(Weapon dropitem, String name, int hp, Weapon weapon) {
        super(name, hp, weapon);
        setDropitem(weapon);
    }

    private Weapon dropitem;//weapon drop from enemy
    
    // Getter & Setter
    public Weapon getDropitem() {
        return dropitem;
    }

    public void setDropitem(Weapon dropitem) {
        this.dropitem = dropitem;
    }
}