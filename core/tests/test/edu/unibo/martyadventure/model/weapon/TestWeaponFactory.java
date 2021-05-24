package test.edu.unibo.martyadventure.model.weapon;

import java.util.ArrayList;
import java.util.List;
import edu.unibo.martyadventure.model.weapon.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestWeaponFactory {
    String name = "Weapon1";
    String type = "Meele";
    int damageMultiplier = 10;
    List<Move> moveList = new ArrayList<>(List.of(Move.UPPERCUT, Move.HOOK, Move.JAB, Move.SUPERMANPUNCH));
    Weapon weaponTest = new WeaponFactory().newWeapon(name, type, damageMultiplier, moveList);

    @Test
    void testNewWeapon() {
        weaponTest = new WeaponFactory().newWeapon(name, type, damageMultiplier, moveList);
        assertEquals(name, weaponTest.getName());
        assertEquals(type, weaponTest.getType());
        assertEquals(damageMultiplier, weaponTest.getDamageMultiplier());
        assertEquals(moveList, weaponTest.getMoveList());

        // System.err.println("testLoadingWeapon ok");
    }

    @Test
    void testCreateWeapon() {
        weaponTest = new WeaponFactory().createWeapon(weaponTest, Move.UPPERCUT, Move.HOOK, Move.JAB, Move.SUPERMANPUNCH);
        assertEquals(moveList, weaponTest.getMoveList());

        // System.err.println("testCreateWeapon ok");
    }
    
    @Test
    void testCreateRandomWeapon() {
        weaponTest = new WeaponFactory().createRandomWeapon(weaponTest);
        checkDuplicateItemsInMoveList(weaponTest);
        // weaponTest.printWeapon();
        // System.err.println("testCreateRandomWeapon ok");
    }

    @Test
    void testCreateRandomMeleeWeapon() {
        weaponTest = new WeaponFactory().createRandomMeleeWeapon(weaponTest);
        checkDuplicateItemsInMoveList(weaponTest);
        // check if the items of moveList are all melee MOVE
        for (int i = 0; i < 4; i++) {
            assertEquals(weaponTest.getMoveList().get(i).getMeleeOrRanged(), 'M');
        }

        // weaponTest.printWeapon();
        // System.err.println("testCreateRandomWeapon ok");
    }

    @Test
    void testCreateRandomRangedWeapon() {
        weaponTest = new WeaponFactory().createRandomRangedWeapon(weaponTest);
        checkDuplicateItemsInMoveList(weaponTest);
        // check if the items of moveList are all ranged MOVE
        for (int i = 1; i < 4; i++) {
            assertEquals(weaponTest.getMoveList().get(i).getMeleeOrRanged(), 'R');
        }

        // weaponTest.printWeapon();
        // System.err.println("testCreateRandomWeapon ok");
    }

    // Function to check if there is a duplicate items in a moveList
    void checkDuplicateItemsInMoveList(Weapon weaponTest) {
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                assertNotEquals(weaponTest.getMoveList().get(i), weaponTest.getMoveList().get(j));
            }
        }
    }

}
