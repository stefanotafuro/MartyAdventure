package edu.unibo.martyadventure.model;


public class Weapon {
    
    String name;
    String type;
    double damageMultiplier;
    Move moveSet;
    
    public Weapon(String name, String type, double damageMultiplier, Move moveSet) {
        this.name = name;
        this.type = type;
        this.damageMultiplier = damageMultiplier;
        this.moveSet = moveSet;
    }

    @Override
    public Weapon createWeapon(Weapon weapon, Move m1, Move m2, Move m3, Move m4) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Weapon createRandomWeapon(Weapon weapon) {
        // TODO Auto-generated method stub
        return null;
    }
    
}