package test.edu.unibo.martyadventure.model.fight;

import edu.unibo.martyadventure.model.character.*;
import edu.unibo.martyadventure.model.fight.Fight;
import edu.unibo.martyadventure.model.weapon.Move;
import test.edu.unibo.martyadventure.model.TestCharacterFactory;
import org.javatuples.Triplet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestFight {

    private Triplet<Fight, PlayerCharacter, EnemyCharacter> getFight() {

    }

    @Test
    void testStartFight() {
        final PlayerCharacter player = TestCharacterFactory.getPlayerCharacter();
        final EnemyCharacter enemy = TestCharacterFactory.getEnemyCharacter();
        final Fight testFight = new Fight(player, enemy);
        assertEquals(player, testFight.getPlayer());
        assertEquals(enemy, testFight.getEnemy());
        assertEquals(1, testFight.getTurnCount());
    }

    @Test
    void testIsPlayerDead() {
        final PlayerCharacter player = TestCharacterFactory.getPlayerCharacter();
        final EnemyCharacter enemy = TestCharacterFactory.getEnemyCharacter();
        final Fight testFight = new Fight(player, enemy);

        assertFalse(testFight.isDead(10, player.getHp()));
        assertTrue(testFight.isDead(player.getHp(), player.getHp()));
    }

    @Test
    void testPlayerAttack() {
        final PlayerCharacter player = TestCharacterFactory.getPlayerCharacter();
        final EnemyCharacter enemy = TestCharacterFactory.getEnemyCharacter();
        final Fight testFight = new Fight(player, enemy);

        int turnCount = testFight.getTurnCount() + 1;
        int enemyHp = testFight.getEnemy().getHp();
        int damage = (int) (testFight.getPlayer().getWeapon().getDamageMultiplier() * Move.HOOK.getDamage());
        testFight.playerAttack(Move.HOOK);
        if (enemyHp != enemy.getHp()) {
            assertEquals(enemyHp - damage, enemy.getHp());
        }
        assertEquals(turnCount, testFight.getTurnCount());
    }

    @Test
    void testEnemyAttack() {
        final PlayerCharacter player = TestCharacterFactory.getPlayerCharacter();
        final EnemyCharacter enemy = TestCharacterFactory.getEnemyCharacter();
        final Fight testFight = new Fight(player, enemy);

        int enemyHp = testFight.getEnemy().getHp();
        int damage = (int) (testFight.getPlayer().getWeapon().getDamageMultiplier() * Move.UPPERCUT.getDamage());
        testFight.attack(testFight.getPlayer().getWeapon(), Move.UPPERCUT, testFight.getEnemy());
        if (enemyHp != enemy.getHp()) {
            assertEquals(enemyHp - damage, enemy.getHp());
        }
    }
}
