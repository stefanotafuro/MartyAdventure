package test.edu.unibo.martyadventure.model.fight;

import edu.unibo.martyadventure.model.character.*;
import edu.unibo.martyadventure.model.fight.Fight;
import edu.unibo.martyadventure.model.weapon.Move;
import test.edu.unibo.martyadventure.model.character.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestFight {

    PlayerCharacter playerCharacter = new TestPlayerCharacter().getPlayerCharacter();
    EnemyCharacter enemyCharacter = new TestEnemyCharacter().getEnemyCharacter();

    Fight testFight = new Fight(playerCharacter, enemyCharacter);

    @Test
    void testStartFight() {
        assertEquals(playerCharacter, testFight.getPlayer());
        assertEquals(enemyCharacter, testFight.getEnemy());
        assertEquals(1, testFight.getTurnCount());
        // System.err.println("testStartFight ok");
    }

    @Test
    void testIsDead() {
        assertFalse(testFight.isDead(10, playerCharacter.getHp()));
        assertTrue(testFight.isDead(playerCharacter.getHp(), playerCharacter.getHp()));
        // System.err.println("testIsDead ok");
    }

    @Test
    void testPlayerAttack() {
        int turnCount = testFight.getTurnCount() + 1;
        int enemyHp = testFight.getEnemy().getHp();
        int damage = testFight.getPlayer().getWeapon().getDamageMultiplier() * Move.HOOK.getDamage();
        testFight.playerAttack(Move.HOOK);
        if (enemyHp != enemyCharacter.getHp()) {
            assertEquals(enemyHp - damage, enemyCharacter.getHp());
        }
        assertEquals(turnCount, testFight.getTurnCount());
        // System.err.println("testPlayerAttack ok");

    }

    @Test
    void testAttack() {
        int enemyHp = testFight.getEnemy().getHp();
        int damage = testFight.getPlayer().getWeapon().getDamageMultiplier() * Move.UPPERCUT.getDamage();
        testFight.attack(testFight.getPlayer().getWeapon(), Move.UPPERCUT, testFight.getEnemy());
        if (enemyHp != enemyCharacter.getHp()) {
            assertEquals(enemyHp - damage, enemyCharacter.getHp());
        }
        // System.err.println("testAttack ok");
    }

}
