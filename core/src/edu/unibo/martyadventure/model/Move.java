package edu.unibo.martyadventure.model;

import java.util.Random;

public enum Move {
    // name(dmg, failRatio, reloadTime, Melee or Ranged)
    HOOK("Gancio", 50, 10, 2, 'M', 0), SHOOT("Lancio", 50, 10, 2, 'R', 0);

    private final String name;
    private final int damage;
    private final int failRatio; // 0 success 100 fail
    private final int reloadTime;
    private final char MeleeOrRanged;
    private int lastUse; // last turn of use
    private Random rand = new Random();

    Move(String name, int damage, int failRatio, int reloadTime, char MeleeOrRanged, int lastUse) {
        this.name = name;
        this.damage = damage;
        this.failRatio = failRatio;
        this.reloadTime = reloadTime;
        this.MeleeOrRanged = MeleeOrRanged;
        setLastUse(lastUse);
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

    public int getLastUse() {
        return lastUse;
    }

    public String getName() {
        return name;
    }

    public void setLastUse(int lastUse) {
        this.lastUse = lastUse;
    }

    public static Move getRandomMove() {
        return values()[(int) (Math.random() * values().length)];
    }

    public boolean isUsable(int fightTurn) {
        if (lastUse + reloadTime < fightTurn) {
            this.lastUse = fightTurn;
            return true;
        } else
            return false;
    }

    public boolean testFailure(int failRatio) {
        // random number if it's <= failRatio success, else fail
        return rand.nextInt(101) >= failRatio;
    }

    /*
     * public static Move getRandomMeleeMove() {
     * 
     * }
     * 
     * public static Move getRandomRangedMove() {
     * 
     * }
     */

}