package edu.T10.Controller;

import edu.T10.Model.Game;
import edu.T10.Model.Territory;

public class Controller {
    Game game;

    public void doCommand(String command){
        switch (command){
            case "cards":
                this.playCards();
                break;

        }
    }

    public void playCards(){

    }

    public void placeUnit(int territoryID, int unitValue){
        game.updateTerritory(territoryID, unitValue);
    }

    public void fortify(Territory from, Territory to, int unitValue){
        from.updateArmyStrength(-unitValue); // todo add condition to check that from territory has 1) enough unitValue to transfer and 2) that at least one unitValue is left
        to.updateArmyStrength(unitValue);
    }

    public void invade(Territory from, Territory to, int unitValue){
    }

}
