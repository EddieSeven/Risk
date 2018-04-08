package edu.T10.Model;

import edu.T10.Controller.Player;

public class Territory {
    private Player owner;
    private String name;
    private int armyStrength; // todo simple int for now until we figure out how we want to represent army
    private int id;
    private int continentID;
    private int[] adjTerritories;
    private String name;

    public Territory(String name, int id, int[] adjTerritories){
        this.name = name;
        this.id = id;
        this.adjTerritories = adjTerritories;
    }

    public void assignOwner(Player player){
        this.owner = player;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public Player getOwner(){
        return this.owner;
    }

    public int getStrength(){
        return this.army;
    }
  
    public void updateArmyStrength(int unitValue){
        this.armyStrength += unitValue; // todo add condition that armyStrength doesn't go below zero
    }
}
