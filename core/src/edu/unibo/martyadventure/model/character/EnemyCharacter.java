package edu.unibo.martyadventure.model.character;

import edu.unibo.martyadventure.model.weapon.Weapon;

public class EnemyCharacter extends Character {
    
    private Weapon dropitem;//weapon drop from enemy

    // Weapon drop from enemy.
    private Weapon dropWeapon;


    public EnemyCharacter(final Weapon dropWeapon, final String name, final int hp, final Weapon weapon) {
        super(name, hp, weapon);
        this.dropWeapon = dropWeapon;
    }

    public Weapon getDropitem() {
        return this.dropWeapon;
    }

    public void setDropitem(final Weapon dropWeapon) {
        this.dropWeapon = dropWeapon;
    }
}