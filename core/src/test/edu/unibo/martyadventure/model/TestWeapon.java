package test.edu.unibo.martyadventure.model;

import java.util.ArrayList;
import java.util.List;

import edu.unibo.martyadventure.model.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestWeapon {

    String name = "Weapon1";
    String type = "Meele";
    int damageMultiplier = 10;
    List<Move> moveList = new ArrayList<>(List.of(Move.SHOOT, Move.HOOK, Move.SHOOT, Move.HOOK));
    Weapon weaponTest = new Weapon(name, type, damageMultiplier, moveList);

    void testLoadingWeapon() {
        assertEquals(name, weaponTest.getName());
        assertEquals(type, weaponTest.getType());
        assertEquals(damageMultiplier, weaponTest.getDamageMultiplier());
        assertEquals(moveList, weaponTest.getMoveList());
        // System.err.println("testLoadingWeapon ok");
    }

    void testSetMoveList() {
        List<Move> moveList2 = new ArrayList<>(List.of(Move.HOOK, Move.SHOOT, Move.HOOK, Move.HOOK));
        weaponTest.setMoveList(moveList2);
        assertEquals(moveList2, weaponTest.getMoveList());
        // System.err.println("testSetMoveList ok");
    }

    @Test
    void testWeapon() {
        testLoadingWeapon();
        testSetMoveList();
        // System.err.println("testWeapon ok");
    }
}
