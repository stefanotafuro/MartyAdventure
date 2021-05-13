package edu.unibo.martyadventure.model.weapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponFactory {
    
    public Weapon newWeapon(String name, String type, int damageMultiplier, List<Move> moveList) {
        Weapon weapon = new Weapon(name, type, damageMultiplier, moveList);
        return weapon;
        
    }

    public Weapon createWeapon(Weapon weapon, Move move1, Move move2, Move move3, Move move4) {
        List<Move> moveList = new ArrayList<>(List.of(move1, move2, move3, move4));
        weapon.setMoveList(moveList);
        return weapon;
    }

    public Weapon createRandomWeapon(Weapon weapon) {
        List<Move> moveList = new ArrayList<>(
                List.of(Move.getRandomMove(), Move.getRandomMove(), Move.getRandomMove(), Move.getRandomMove()));
        weapon.setMoveList(moveList);
        return weapon;
    }

    /*
     * Weapon createMeleeWeapon(Weapon weapon) {
     * 
     * }
     * 
     * Weapon createRangedWeapon(Weapon weapon) {
     * 
     * }
     */

}
