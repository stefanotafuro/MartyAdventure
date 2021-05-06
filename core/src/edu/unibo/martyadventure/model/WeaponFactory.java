package edu.unibo.martyadventure.model;

public interface WeaponFactory {
    Weapon createWeapon(Weapon weapon, Move m1, Move m2, Move m3, Move m4);
    Weapon createRandomWeapon(Weapon weapon);
}
