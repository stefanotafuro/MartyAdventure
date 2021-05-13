package edu.unibo.martyadventure.model.fight;

import java.util.Random;

import edu.unibo.martyadventure.model.character.Character;
import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.model.character.PlayerCharacter;
import edu.unibo.martyadventure.model.weapon.Move;
import edu.unibo.martyadventure.model.weapon.Weapon;

public class Fight {
    private PlayerCharacter player;
    private EnemyCharacter enemy;
    private int turnCount;

    public Fight(PlayerCharacter player, EnemyCharacter enemy) {
        this.player = player;
        this.enemy = enemy;
        this.turnCount = 1;
    }

    public PlayerCharacter getPlayer() {
        return player;
    }

    public EnemyCharacter getEnemy() {
        return enemy;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void enemyAttack() {
        attack(enemy.getWeapon(), enemyMove(), player);
    }

    public Move enemyMove() {
        Random rand = new Random();
        return enemy.getWeapon().getMoveList().get(rand.nextInt(enemy.getWeapon().getMoveList().size()));

    }

    public void playerAttack(Move inputMove) {
        attack(player.getWeapon(), inputMove, enemy);

    }

    public void attack(Weapon weapon, Move move, Character character) {
        if (move.testFailure() && move.isUsable(turnCount)) {
            int damage = weapon.getDamageMultiplier() * move.getDamage();
            if (isDead(damage, character.getHp())) {
                character.setHp(0);
                endFight();

            } else {
                character.setHp(character.getHp() - damage);
            }

        }
        turnCount++;
    }

    public boolean isDead(int damage, int characterHP) {
        return damage >= characterHP;
    }

    public Character endFight() {
        if (player.getHp() == 0) {
            return enemy;
        }
        return player;
    }

}
