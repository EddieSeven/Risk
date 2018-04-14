package edu.T10.Model.Board;

import edu.T10.Model.Player;

import java.util.Arrays;

public class Territory {
    private int owner = -1;
    private String name = null;
    private int armyStrength = 0; // todo simple int for now until we figure out how we want to represent army
    private int id = -1;
    private int continentID = 0;
    private int[] adjTerritories = new int[0];

    public Territory(){}

    public void assignOwner(int playerID){
        this.owner = playerID;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getOwner(){
        return this.owner;
    }

    public int getStrength(){
        return this.armyStrength;
    }
  
    public void updateArmyStrength(int unitValue){
        this.armyStrength += unitValue; // todo add condition that armyStrength doesn't go below zero
    }

    public void setArmyStrength(int unitValue){
        this.armyStrength = unitValue;
    }

    /*
        String line: Format:    id, name, continentID, ... ;
                         ex:    1 Alaska 1 47 76 ;
     */
    public boolean readTerritoryFromLine(String line){
        String[] segs = line.split(" ");
        if (segs.length < 4)
            return false;
        else{
            id = Integer.parseInt(segs[0]);
            name = segs[1];
            continentID = Integer.parseInt(segs[2]);
            return true;
        }
    }

    /*
        String line: Format:    id, adjId, adjId ... ;
                         ex:    1 2 4 32 ;
     */
    public boolean readAdjacentsFromLine(String line){
        String[] segs = line.split(" ");
        if (segs.length < 2)
            return false;
        else{
            this.adjTerritories = new int[segs.length - 2];
            for (int i = 0; i < segs.length - 2; i++){
                this.adjTerritories[i] = Integer.parseInt(segs[1+i]);
            }
            return true;
        }
    }

    @Override
    public String toString() {
        return id + " " + name + " " + continentID
                + " " + armyStrength + " " + Arrays.toString(adjTerritories);
    }
}
