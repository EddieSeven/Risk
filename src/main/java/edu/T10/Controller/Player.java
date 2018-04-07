package edu.T10.Controller;

import edu.T10.Model.Deck;
import edu.T10.Model.Territory;

public class Player {
    private String name;
    private Color color;
    // private Units[] freeArmies // todo lets see if this is necessary
    private Deck deck;
    private Territory territories;

    public Player(String name){
        this.name = name;
    }

    public void playCards(){

    }

    public void placeUnit(Territory territory, int unitValue){

    }

    public void reinforce(Territory territory, int unitValue){
        territory.updateArmyStrength(unitValue);
    }

    public void fortify(Territory from, Territory to, int unitValue){
        from.updateArmyStrength(-unitValue); // todo add condition to check that from territory has 1) enough unitValue to transfer and 2) that at least one unitValue is left
        to.updateArmyStrength(unitValue);
    }

    public void invade(Territory from, Territory to, int unitValue){
    }

    public void assignColor(Color color){
        this.color = color;
    }
}

enum Color {
    GRAY, YELLOW, RED, BLACK, GREEN, BLUE // six players, six colors, from a quick google search
}