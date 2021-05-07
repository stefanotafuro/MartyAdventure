package edu.unibo.martyadventure.model;

import java.util.ArrayList;
import java.util.List;

public class WeaponFactory {
    
    
    Weapon createWeapon(Weapon weapon, Move move1, Move move2, Move move3, Move move4) {
        List<Move> moveList = new ArrayList<>(List.of(move1, move2, move3, move4));
        weapon.setMoveList(moveList);
        return weapon;        
    }
    
    Weapon createRandomWeapon(Weapon weapon) {
        List<Move> moveList = new ArrayList<>(List.of(Move.getRandomMove(), Move.getRandomMove(),
                                                    Move.getRandomMove(), Move.getRandomMove()));
        weapon.setMoveList(moveList);
        return weapon;        
    }
    
    

    /*Weapon createMeleeWeapon(Weapon weapon) {
        
    }

    Weapon createRangedWeapon(Weapon weapon) {
        
    }*/

}
