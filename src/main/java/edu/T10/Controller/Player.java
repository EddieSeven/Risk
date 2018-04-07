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
        from.updateArmyStrength(-unitValue);
        from.updateArmyStrength(unitValue);
    }

    public void invade(Territory from, Territory to, int unitValue){ // todo changed name from attack to invade

    }

    public void assignColor(Color color){
        this.color = color;
    }
}

enum Color {
    RED, BLUE, GREEN, PURPLE // todo add more as needed
}