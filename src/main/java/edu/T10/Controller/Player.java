package edu.T10.Controller;

import edu.T10.Model.Deck;
import edu.T10.Model.Territory;

public class Player {
    private String id;
    private Color color;
    // private Units[] freeArmies // todo lets see if this is necessary
    private Deck deck;
    private Territory territories;

    public Player(){
        // todo constructor
    }

    public void playCards(){

    }

    public void placeUnit(Territory territory){

    }

    public void reinforce(){

    }

    public void fortify(){

    }

    public void invade(){ // todo changed name from attack to invade

    }
}

enum Color {
    RED, BLUE, GREEN, PURPLE // todo add more as needed
}