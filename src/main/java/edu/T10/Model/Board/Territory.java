package edu.T10.Model.Board;

import edu.T10.Model.*;

import java.util.Vector;

public class Territory {
    private int owner = -1;
    private String name = null;
    private int armyStrength = 0;
    private int id = -1;
    private int continentID = 0;
    private Vector<Integer> adjTerritories = new Vector<Integer>();

    public Territory(){}
    
    public Territory(int owner, String name, int armyStrength, int id, int continentID, int adj) {
    	this.owner = owner;
    	this.name = name;
    	this.armyStrength = armyStrength;
    	this.id = id;
    	this.continentID = continentID;
    	this.adjTerritories.add(adj);
    }

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

    public boolean isNotAdjacent(int adjTerritoryID){

        for (int adjTerritory : adjTerritories) {
            if (adjTerritory == adjTerritoryID)
                return false;
        }

        return true;
    }

    public String getAdjTerritoriesString() {
        String adjList = "";

        for (int adjTerritory : adjTerritories) {
            adjList = adjList + " " + adjTerritory;
        }

        return adjList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContinentID(int continentID) {
        this.continentID = continentID;
    }

    public void setAdjTerritories(Vector<Integer> adjTerritories) {
        this.adjTerritories = new Vector<Integer> (adjTerritories);
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
