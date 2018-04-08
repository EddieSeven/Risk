package edu.T10.Model;

public class Board {
    private Territory[] territories;
    private int nTerritories;

    public Board(Territory[] territories){
        this.territories = territories;
        nTerritories = territories.length;
    }

    public Territory getTerritory(int territoryID){
        return search(territoryID);
    }

    public Territory getTerritory(String name){
        return search(name); // todo we can delete one of these when we figure out which one will be more handy to use in practice.
    }

    public Player getTerritoryOwner(int territoryID){
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

    private Player searchOwner(int territoryID){
        return getTerritory(territoryID).getOwner();
    }
}
