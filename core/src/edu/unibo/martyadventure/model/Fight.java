package edu.unibo.martyadventure.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fight {
    private PlayerCharacter player;
    private EnemyCharacter enemy;
    private int turnCount;

    public void startFight(PlayerCharacter player, EnemyCharacter enemy) {
        this.player = player;
        this.enemy = enemy;
        this.turnCount = 1;
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
        if (move.testFailure(move.getFailRatio()) && move.isUsable(turnCount)) {
            int damage = weapon.getDamageMultiplier() * move.getDamage();
            if (isDead(damage, character.getHp())) {
                character.setHp(0);
                endFight();

            } else {
                character.setHp(damage);
            }

        } else {
            // ATTACCO NON HA AVUTO SUCCESSO
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
