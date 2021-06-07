package edu.unibo.martyadventure.model.weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory class to create Weapon 
*/

public class WeaponFactory {

    private static final int RANDOM_WEAPON_MAX_DAMAGE_MULTIPLIER = 10;

    /**
     * Weapon public constructor 
     * @return The new weapon
     */
    public static Weapon newWeapon(String name, Weapon.WeaponType type, double damageMultiplier, List<Move> moveList) {
        Weapon weapon = new Weapon(name, type, damageMultiplier, moveList);
        return weapon;

    }

    /**
     * Set an input moveList on a specific Weapon
     * @param weapon The weapon that will be modify
     * @param move1/2/3/4 The moves that compose the new moveList 
     * @return The new weapon
     */
    public static Weapon setWeaponMove(Weapon weapon,Move move1, Move move2, Move move3, Move move4) {
        List<Move> moveList = new ArrayList<>(List.of(move1, move2, move3, move4));
        weapon.setMoveList(moveList);
        return weapon;
    }

    /**
     * Create a new random weapon
     * @param name
     * @return
     */
    public static Weapon createRandomWeapon(String name) {
        return createWeapon(name, new Random().nextBoolean() ? Weapon.WeaponType.MELEE : Weapon.WeaponType.RANGED);
    }

    /**
     * Used to create random weapon
     * @param name
     * @param type
     * @return
     */
    private static Weapon createWeapon(String name, Weapon.WeaponType type) {
        Weapon weapon = newWeapon(name, null, new Random().nextInt() % RANDOM_WEAPON_MAX_DAMAGE_MULTIPLIER , new ArrayList<>());
        List<Move> moveList = new ArrayList<>();
        int i = 0;
        Move move;
        do {
            move = Move.getRandomMeleeMove();
            if (!moveList.contains(move)) {
                moveList.add(i, move);
                i++;
            }
        } while (i < 4);
        weapon.setMoveList(moveList);
        weapon.setType(type);
        return weapon;
    }
    
    /**
     * Set a random RANGED moveList on a specific Weapon
     * composed of 3 ranged move and 1 melee move
     * @param name The weapon name that will be created
     * @return The new weapon with random MELEE moveList
     */
    public static Weapon createRandomRangedWeapon(String name) {
        return createWeapon(name, Weapon.WeaponType.RANGED);
    }
    
    /**
     * Set a random MELEE moveList on a specific Weapon
     * composed of 3 ranged move and 1 melee move
     * @param name The weapon name that will be created
     * @return The new weapon with random MELEE moveList
     */
    public static Weapon createRandomMeleeWeapon(String name) {
        return createWeapon(name, Weapon.WeaponType.MELEE);
    }
    
    private WeaponFactory() {
        
    }

}
