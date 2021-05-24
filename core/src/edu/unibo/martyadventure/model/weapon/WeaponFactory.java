package edu.unibo.martyadventure.model.weapon;

import java.util.ArrayList;
import java.util.List;

/*
 * Factory class to create Weapon 
 */
public class WeaponFactory {

    public Weapon newWeapon(String name, String type, int damageMultiplier, List<Move> moveList) {
        Weapon weapon = new Weapon(name, type, damageMultiplier, moveList);
        return weapon;

    }

    // Set an input moveList on a specific Weapon
    public Weapon createWeapon(Weapon weapon, Move move1, Move move2, Move move3, Move move4) {
        List<Move> moveList = new ArrayList<>(List.of(move1, move2, move3, move4));
        weapon.setMoveList(moveList);
        return weapon;
    }

    // Set a random moveList on a specific Weapon
    public Weapon createRandomWeapon(Weapon weapon) {
        List<Move> moveList = new ArrayList<>();
        int i = 0;
        Move move;
        do {
            move = Move.getRandomMove();
            if (!moveList.contains(move)) {
                moveList.add(i, move);
                i++;
            }
        } while (i < 4);
        weapon.setMoveList(moveList);
        return weapon;
    }

    // Set a random MELEE moveList on a specific Weapon
    public Weapon createRandomMeleeWeapon(Weapon weapon) {
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
        return weapon;
    }

    // Set a random moveList on a specific Weapon.
    // composed of 3 ranged move and 1 melee move
    public Weapon createRandomRangedWeapon(Weapon weapon) {
        List<Move> moveList = new ArrayList<>(List.of(Move.getRandomMeleeMove()));
        int i = 1;
        Move move;
        do {
            move = Move.getRandomRangedMove();
            if (!moveList.contains(move)) {
                moveList.add(i, move);
                i++;
            }
        } while (i < 4);
        weapon.setMoveList(moveList);
        return weapon;
    }

}
