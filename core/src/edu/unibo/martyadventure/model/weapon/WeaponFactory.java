package edu.unibo.martyadventure.model.weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import edu.unibo.martyadventure.model.weapon.Weapon.WeaponType;
import edu.unibo.martyadventure.view.MapManager;

/**
 * Factory class to create Weapon
 */

public class WeaponFactory {
    
    private static final int RANDOM_WEAPON_MAX_DAMAGE_MULTIPLIER = 10;
    public static final float LEVEL1 = 1;
    public static final float LEVEL2 = 2;
    public static final float LEVEL3 = 3;

    /**
     * Weapon public constructor
     * 
     * @return The new weapon
     */
    public static Weapon newWeapon(String name, Weapon.WeaponType type, double damageMultiplier, List<Move> moveList) {
        Weapon weapon = new Weapon(name, type, damageMultiplier, moveList);
        return weapon;

    }

    /**
     * Set an input moveList on a specific Weapon
     * 
     * @param weapon      The weapon that will be modify
     * @param move1/2/3/4 The moves that compose the new moveList
     * @return The new weapon
     */
    public static Weapon setWeaponMove(Weapon weapon, Move move1, Move move2, Move move3, Move move4) {
        List<Move> moveList = new ArrayList<>(List.of(move1, move2, move3, move4));
        weapon.setMoveList(moveList);
        return weapon;
    }

    /**
     * Create a new random weapon
     * 
     * @param name
     * @return
     */
    public static Weapon createRandomWeapon(String name, double damageMultiplier) {
        return createWeapon(name, damageMultiplier,
                new Random().nextBoolean() ? Weapon.WeaponType.MELEE : Weapon.WeaponType.RANGED);
    }

    /**
     * Used to create random weapon
     * 
     * @param name
     * @param type
     * @return
     */
    private static Weapon createWeapon(String name, double damageMultiplier, Weapon.WeaponType type) {
        Weapon weapon = new Weapon(name, new Random().nextInt() % RANDOM_WEAPON_MAX_DAMAGE_MULTIPLIER);
        List<Move> moveList = new ArrayList<>();
        int i = 0;
        Move move;
        do {
            if (type == WeaponType.MELEE) {
                move = Move.getRandomMeleeMove();
            } else {
                move = Move.getRandomRangedMove();
            }

            if (!moveList.contains(move)) {
                moveList.add(i, move);
                i++;
            }
        } while (i < Weapon.MOVE_LIST_SIZE);
        weapon.setDamageMultiplier(damageMultiplier);
        weapon.setMoveList(moveList);
        weapon.setType(type);
        return weapon;
    }

    /**
     * Set a random RANGED moveList on a specific Weapon composed of 4 ranged move
     * 
     * @param name The weapon name that will be created
     * @return The new weapon with random MELEE moveList
     */
    public static Weapon createRandomRangedWeapon(String name, double damageMultiplier) {
        return createWeapon(name, damageMultiplier, Weapon.WeaponType.RANGED);
    }

    /**
     * Set a random MELEE moveList on a specific Weapon composed of 4 melee move
     * 
     * @param name The weapon name that will be created
     * @return The new weapon with random MELEE moveList
     */
    public static Weapon createRandomMeleeWeapon(String name, double damageMultiplier) {
        return createWeapon(name, damageMultiplier, Weapon.WeaponType.MELEE);
    }

    /**
     * Create a new Weapon based on level
     * 
     * @param name  The weapon name that will be created
     * @param map The map in which the weapon will be created
     * @return The new random weapon based on level
     */
    public static Weapon randomWeaponLevel(String name, MapManager.Maps map) {
        switch (map) {
            case MAP1:
                return createRandomWeapon(name, (double)(randomDamageMultiplier() * LEVEL1));
            case MAP2:
                return createRandomWeapon(name, (double)(randomDamageMultiplier() * LEVEL2));
            case MAP3:
                return createRandomWeapon(name, (double)(randomDamageMultiplier() * LEVEL2));
            default:
                throw new IllegalArgumentException("Wrong Map");
        }  
    }

    /**
     * 
     * @return Random double between MIN and MAX
     */
    public static double randomDamageMultiplier() {
        double MIN = 1;
        double MAX = 3;
        return ThreadLocalRandom.current().nextDouble(MIN, MAX);
    }

    private WeaponFactory() {

    }

}
