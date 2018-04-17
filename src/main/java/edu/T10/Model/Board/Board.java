package edu.T10.Model.Board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Board {
    private Continent[] continents;
    private Territory[] territories;
    private int nTerritories;

    public Board(){
        String filepath = getClass().getClassLoader().getResource(Parser.filename).getFile();
        initTerritories(filepath);
    }

    private boolean initTerritories(String filepath){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            Parser parser = new Parser(this);

            while ((line = br.readLine()) != null) {
                switch (line.trim()){
                    case "{continents}":
                        continents = parser.readContinentsBuffer(br);
                        break;
                    case "{countries}":
                        territories = parser.readTerritoriesBuffer(br);
                        nTerritories = territories.length;
                        break;
                    case "{adjacents}":
                        parser.readAdjacentsBuffer(br);
                        break;
                    default:
                        break;
                }
            }
            br.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    public Territory[] getAllTerritories(){
        return this.territories;
    }

    public int getTerritorySize(){
        return territories.length;
    }

    public int getTerritoriesBonus(Territory[] territories){
        return (int) Math.floor((territories.length * 1.0) / 3.0);
    }

    public void updateTerritoryStrength(int territoryID, int unitValue){
        getTerritory(territoryID).updateArmyStrength(unitValue);
    }

    public void setTerritoryStrength(int territoryID, int unitValue){
        getTerritory(territoryID).setArmyStrength(unitValue);
    }

    public int getContinentsBonus(Continent[] continents){
        int bonus = 0;
        for (int i = 0; i < continents.length; i++)
            bonus += continents[i].bonus;
        return bonus;
    }

    public Territory[] getTerritories(int playerID){
        Vector<Territory> vec = new Vector<>();
        for (int i = 0; i < nTerritories; i++){
            if (territories[i].getOwner() == playerID)
                vec.add(territories[i]);
        }
        return vec.toArray(new Territory[vec.size()]);
    }

    public Continent[] getContinents(int playerID){
        Vector<Continent> vec = new Vector<>();
        for (int i = 0; i < continents.length; i++){
            Continent continent = continents[i];
            boolean occupied = true;
            for (int j = 0; j < continent.members.length; j++){
                Territory territory = search(continent.members[j]);
                if (territory.getOwner() !=  playerID)
                    occupied = false;
            }
            if (occupied)
                vec.add(continent);
        }
        return vec.toArray(new Continent[vec.size()]);
    }

    public void updateOwner(int territoryID, int playerID){
        Territory territory = getTerritory(territoryID);
        territory.assignOwner(playerID);
    }

    public Territory getTerritory(int territoryID){
        return search(territoryID);
    }

    public Territory getTerritory(String name){
        return search(name); // todo we can delete one of these when we figure out which one will be more handy to use in practice.
    }

    public int getTerritoryOwner(int territoryID){
        return searchOwner(territoryID);
    }

    public String getTerritoryName(int territoryID){
        return getTerritory(territoryID).getName();
    }

    public int getArmyStrength(int territoryID){
        return getTerritory(territoryID).getStrength();
    }

    private Territory search(int territoryID){
        Territory territory = null;

        for(int i = 0; i < nTerritories; i++){
            if (territories[i].getId() == territoryID){
                territory = territories[i];
                break;
            }
        }

        return territory;
    }

    private Territory search(String name){
        Territory territory = null;

        for(int i = 0; i < nTerritories; i++){
            if (territories[i].getName().equals(name)){
                territory = territories[i];
                break;
            }
        }

        return territory;
    }

    private int searchOwner(int territoryID){
        return getTerritory(territoryID).getOwner();
    }

    public static void main(String[] args) {
        // Test Code
        new Board();
    }

    public Territory[] getTerritories() {
        return territories;
    }

    public Continent[] getContinents() {
        return continents;
    }

    public int getnTerritories() {
        return nTerritories;
    }

    public void setTerritories(Territory[] territories) {
        this.territories = territories;
    }

    public void setContinents(Continent[] continents) {
        this.continents = continents;
    }

    public void setnTerritories(int nTerritories) {
        this.nTerritories = nTerritories;
    }
}
