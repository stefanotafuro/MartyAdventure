package edu.unibo.martyadventure.model;


public class Fight {
    Character player;
    int playerHp;
    Character enemy;
    int enemyHp;
    int turnCount;
    
    public void startFight(Character player, int playerHp, Character enemy, int enemyHp ) {
        this.player = player;
        this.playerHp = playerHp;
        this.enemy = enemy;
        this.enemyHp = enemyHp;
        this.turnCount = 1;
    }
    
    public void enemyAttack() {
        
    }
    
    public Move enemyMove() {
    
    }
    
    public void playerAttack(Move inputMove) {
        
    }
    
    public void attack(Weapon w, Move m, Character c) {
        
    }
    
    
}
