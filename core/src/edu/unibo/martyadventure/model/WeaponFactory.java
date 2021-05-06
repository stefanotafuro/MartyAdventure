package edu.unibo.martyadventure.model;

import java.util.ArrayList;
import java.util.List;

public class WeaponFactory {
    
    
    Weapon createWeapon(Weapon weapon, Move move1, Move move2, Move move3, Move move4) {
        List<Move> moveList = new ArrayList<>();
        moveList.add(move1);
        moveList.add(move2);
        moveList.add(move3);
        moveList.add(move4);
        weapon.setMoveList(moveList);
        return weapon;        
    }
    
    Weapon createRandomWeapon(Weapon weapon) {
        List<Move> moveList = new ArrayList<>();
        moveList.add(Move.getRandomMove());
        moveList.add(Move.getRandomMove());
        moveList.add(Move.getRandomMove());
        moveList.add(Move.getRandomMove());
        weapon.setMoveList(moveList);
        return weapon;        
    }

    /*Weapon createMeleeWeapon(Weapon weapon) {
        
    }

    Weapon createRangedWeapon(Weapon weapon) {
        
    }*/

}
