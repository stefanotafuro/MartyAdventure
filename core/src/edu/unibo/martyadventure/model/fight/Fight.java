package edu.unibo.martyadventure.model.fight;

import java.util.Random;

import edu.unibo.martyadventure.model.character.Character;
import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.model.character.PlayerCharacter;
import edu.unibo.martyadventure.model.weapon.Move;
import edu.unibo.martyadventure.model.weapon.Weapon;

/*
 *  Main fight class (damage = weapon.damageMoltiplier * move.getDamage)
 */

public class Fight {
    private PlayerCharacter player;
    private EnemyCharacter enemy;
    private int turnCount;

    /**
     * The public constructor start a fight
     * @param player The PlayerCharacter  
     * @param enemy The EnemyCharacter 
     */
    public Fight(PlayerCharacter player, EnemyCharacter enemy) {
        this.player = player;
        this.enemy = enemy;
        this.turnCount = 1;
    }

    //Getter & Setter
    public PlayerCharacter getPlayer() {
        return player;
    }

    public EnemyCharacter getEnemy() {
        return enemy;
    }

    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Call attack function with enemy weapon, random move and player character
     */
    public void enemyAttack() {
        attack(enemy.getWeapon(), enemyMove(), player);
    }
    
    /**
     * @return The random Move choosen from the enemy MoveList
     */
    public Move enemyMove() {
        Random rand = new Random();
        return enemy.getWeapon().getMoveList().get(rand.nextInt(enemy.getWeapon().getMoveList().size()));

    }
    
    /**
     * Call attack function with input Move
     * @param inputMove The Move that the player wants to use
    */
    public void playerAttack(Move inputMove) {
        attack(player.getWeapon(), inputMove, enemy);

    }

    /**
     *  
     * @param weapon The striker's weapon
     * @param move The striker's move
     * @param character The opponent character
     */
    public void attack(Weapon weapon, Move move, Character character) {
        
        //check if the move is usable
        if (!move.isUsable(turnCount)) {
            //System.out.println("Unusable Move");

        }
        //check if the move fail
        else if (!move.testFailure()) {
            //System.out.println("Move Fail");
        }
        //ATTACK
        else {
            //check if the damage will kill the opponent using isDead function
            if (isDead( (int) (weapon.getDamageMultiplier() * move.getDamage()), character.getHp())) {
                //opponent is DEAD
                character.setHp(0); 
                endFight();

            } else {
                //inflict attack on the opponent 
                character.setHp((int) (character.getHp() - (weapon.getDamageMultiplier() * move.getDamage())));
            }
        }
        
        turnCount++;
    }

    /**
     * Check if the damage will kill the character
     * @param damage The attack damage
     * @param characterHP The Character HP
     * @return TRUE if the character is Dead, FALSE in the other case
     */
    public boolean isDead(int damage, int characterHP) {
        return damage >= characterHP;
    }
    
    /**
     * The fight is ended
     * @return The winner
     */
    public Character endFight() {
        if (player.getHp() == 0) {
            return enemy;
        }
        return player;
    }

}
