package edu.T10.View;

import edu.T10.Model.Territory;

public class Board {
    private Territory[] territories;
    private int nTerritories;

    public Board(Territory[] territories){
        this.territories = territories;
        nTerritories = territories.length;
    }

    public Territory getTerritory(int id){
        return search(id);
    }

    public Territory getTerritory(String name){
        return search(name); // todo we can delete one of these when we figure out which one will be more handy to use in practice.
    }

    private Territory search(int id){
        Territory territory = null;

        for(int i = 0; i < nTerritories; i++){
            if (territories[i].getId() == id)
                territory = territories[i];
            break;
        }

        return territory;
    }

    private Territory search(String name){
        Territory territory = null;

        for(int i = 0; i < nTerritories; i++){
            if (territories[i].getName().equals(name))
                territory = territories[i];
            break;
        }

        return territory;
    }

}
