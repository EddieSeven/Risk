package edu.T10.Model.Board;

import edu.T10.Model.*;

import java.util.Arrays;

public class Territory {
    private int owner = -1;
    private String name = null;
    private int armyStrength = 0;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContinentID(int continentID) {
        this.continentID = continentID;
    }

    public void setAdjTerritories(int[] adjTerritories) {
        this.adjTerritories = adjTerritories;
    }

    public void setArmyStrength(int unitValue){
        this.armyStrength = unitValue;
    }

    public void updateArmyStrength(int unitValue){
        this.armyStrength += unitValue; // todo add condition that armyStrength doesn't go below zero
    }
    @Override
    public String toString() {
        return id + " " + armyStrength;
    }
}
