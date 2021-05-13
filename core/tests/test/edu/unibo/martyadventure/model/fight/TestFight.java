package test.edu.unibo.martyadventure.model.fight;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.model.character.PlayerCharacter;
import edu.unibo.martyadventure.model.fight.Fight;
import edu.unibo.martyadventure.model.weapon.Move;
import test.edu.unibo.martyadventure.model.character.TestEnemyCharacter;
import test.edu.unibo.martyadventure.model.character.TestPlayerCharacter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestFight {

    PlayerCharacter playerCharacter = new TestPlayerCharacter().getPlayerCharacter();
    EnemyCharacter enemyCharacter = new TestEnemyCharacter().getEnemyCharacter();

    Fight testFight = new Fight(playerCharacter, enemyCharacter);

    void testStartFight() {
        assertEquals(playerCharacter, testFight.getPlayer());
        assertEquals(enemyCharacter, testFight.getEnemy());
        assertEquals(1, testFight.getTurnCount());
        // System.err.println("testStartFight ok");
    }

    void testIsDead() {
        assertFalse(testFight.isDead(10, playerCharacter.getHp()));
        assertTrue(testFight.isDead(playerCharacter.getHp(), playerCharacter.getHp()));
        // System.err.println("testIsDead ok");
    }

    void testPlayerAttack() {
        int enemyHp = testFight.getEnemy().getHp();
        int damage = testFight.getPlayer().getWeapon().getDamageMultiplier() * Move.HOOK.getDamage();
        testFight.playerAttack(Move.HOOK);
        assertEquals(enemyHp - damage, enemyCharacter.getHp());
        assertEquals(2, testFight.getTurnCount());
        // System.err.println("testPlayerAttack ok");
    }

    void testAttack() {
        int enemyHp = testFight.getEnemy().getHp();
        int damage = testFight.getPlayer().getWeapon().getDamageMultiplier() * Move.SHOOT.getDamage();
        testFight.attack(testFight.getPlayer().getWeapon(), Move.SHOOT, testFight.getEnemy());
        assertEquals(enemyHp - damage, enemyCharacter.getHp());
        // System.err.println("testAttack ok");
    }

    void testEnemyMove() {
        System.out.println(testFight.enemyMove());
    }

    @Test

    void testFight() {
        testStartFight();
        testIsDead();
        testPlayerAttack();
        testAttack();
        testEnemyMove();
        // System.err.println("testFight ok");
    }

}
