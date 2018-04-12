package edu.T10.Model.Board;

import edu.T10.Model.Player;

import java.util.Arrays;

public class Territory {
    private int owner;
    private String name;
    private int armyStrength; // todo simple int for now until we figure out how we want to represent army
    private int id;
    private int continentID;
    private int[] adjTerritories;

    public Territory(){}

    public Territory(String name, int id, int[] adjTerritories){
        this.name = name;
        this.id = id;
        this.adjTerritories = adjTerritories;
    }

    public void assignOwner(int player){
        this.owner = player;
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
