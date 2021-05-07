package edu.unibo.martyadventure.model;

import java.util.ArrayList;
import java.util.List;

public class Weapon {
    
    private String name;
    private String type;
    private double damageMultiplier;
    private List<Move> moveList = new ArrayList<>();
    
    public Weapon(String name, String type, double damageMultiplier, List<Move> moveList) {
        this.name = name;
        this.type = type;
        this.damageMultiplier = damageMultiplier;
        setMoveList(moveList);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public List<Move> getMoveList() {
        return moveList;
    }
    
    public void setMoveList(List<Move> moveList) {
        if(moveList.size()==4) 
            this.moveList = moveList;
        else 
            System.err.println("moveList ERROR");   
              
    }
    
}