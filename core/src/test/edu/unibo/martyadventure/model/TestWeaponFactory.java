package test.edu.unibo.martyadventure.model;

import java.util.ArrayList;
import java.util.List;

import edu.unibo.martyadventure.model.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestWeaponFactory {
    String name = "Weapon1";
    String type = "Meele";
    int damageMultiplier = 10;
    List<Move> moveList = new ArrayList<>(List.of(Move.SHOOT, Move.HOOK, Move.SHOOT, Move.HOOK));
    Weapon weaponTest = new Weapon(name, type, damageMultiplier, moveList);

    void testCreateWeapon() {
        weaponTest = new WeaponFactory().createWeapon(weaponTest, Move.SHOOT, Move.HOOK, Move.SHOOT, Move.HOOK);
        assertEquals(moveList, weaponTest.getMoveList());

        // System.err.println("testCreateWeapon ok");
    }

    void testCreateRandomWeapon() {
        weaponTest = new WeaponFactory().createRandomWeapon(weaponTest);
        weaponTest.printWeapon();

        // System.err.println("testCreateRandomWeapon ok");
    }

    @Test

    void testWeaponFactory() {
        testCreateWeapon();
        testCreateRandomWeapon();

        // System.err.println("testWeaponFactory ok");
    }

}
