package edu.T10.Model;

import edu.T10.Controller.Player;

public class Territory {
    private Player owner;
    private int army; // todo simple int for now until we figure out how we want to represent army
    private int id;
    private int[] adjTerritories;
    private String name;

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
}
