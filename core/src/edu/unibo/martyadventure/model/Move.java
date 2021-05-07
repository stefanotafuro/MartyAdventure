package edu.unibo.martyadventure.model;

public enum Move{
    //name(dmg, failRatio, reloadTime, Melee or Ranged)
    GANCIO(50, 10, 2, 'R'),
    LANCIO(50, 10, 2, 'R');


    private final int damage;   
    private final int failRatio;
    private final int reloadTime;
    private final char MeleeOrRanged;
    
    Move(int damage, int failRatio, int reloadTime, char MeleeOrRanged) {
        this.damage = damage;
        this.failRatio = failRatio;
        this.reloadTime = reloadTime;
        this.MeleeOrRanged = MeleeOrRanged;
    }

    public int getDamage() {
        return damage;
    }

    public int getFailRatio() {
        return failRatio;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public char getMeleeOrRanged() {
        return MeleeOrRanged;
    }
    

    public static Move getRandomMove() {
        return values()[(int) (Math.random() * values().length)];
    }
    
    
    /*
    public static Move getRandomMeleeMove() {
        
    }
    
    public static Move getRandomRangedMove() {
    
    }*/
    
}