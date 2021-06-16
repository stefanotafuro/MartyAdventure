package edu.unibo.martyadventure.model.character;


import edu.unibo.martyadventure.model.weapon.Weapon;

/*
 * Abstract class used to declare common characteristics 
 * between enemycharacter and playercharacter
 * */

public abstract class Character {

    private String name;
    private int hp;
    private Weapon weapon;
    
    /**
     * pubblic constructor
     * @param name The name of the character
     * @param hp The health points of the character
     * @param set weapon the weapon of the character
    */ 

    public Character(String name, int hp, Weapon weapon) {
        setName(name);
        setHp(hp);
        setWeapon(weapon);

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

}
