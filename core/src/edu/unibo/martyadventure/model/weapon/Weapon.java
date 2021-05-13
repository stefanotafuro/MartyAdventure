package edu.unibo.martyadventure.model.weapon;

import java.util.ArrayList;
import java.util.List;

public class Weapon {

    private String name;
    private String type;
    private int damageMultiplier;
    private List<Move> moveList = new ArrayList<>();

    Weapon(String name, String type, int damageMultiplier, List<Move> moveList) {
        this.name = name;
        this.type = type;
        this.damageMultiplier = damageMultiplier;
        setMoveList(moveList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(int damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<Move> moveList) {
        if (moveList.size() == 4)
            this.moveList = moveList;
        else
            System.err.println("moveList ERROR");

    }

    public void printWeapon() {
        System.out.println(getName());
        System.out.println(getType());
        System.out.println(getDamageMultiplier());
        System.out.println(getMoveList());

    }

}
