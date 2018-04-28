package edu.T10.Model;
import edu.T10.Model.Deck.Card;
import edu.T10.Model.Deck.PlayerDeck;

import java.util.Vector;

public class Player {
    private String name;
    private Color color;
    private int freeArmies;
    private PlayerDeck deck;

    public Player(String name, int colorID){
        this.name = name;
        this.freeArmies = 0;
        this.deck = new PlayerDeck();
        assignColor(colorID);
    }

    public Vector<Integer> getCardTypes(){
        return deck.getCardTypes();
    }

    public int getFreeArmies() {
        return freeArmies;
    }

    public void assignColor(int colorId){
        this.color = Color.values()[colorId];
    }

    public void assignColor(Color color){
        this.color = color;
    }

    public void addCard(Card card){
        deck.addCard(card);
    }

    public int collectCardReward(){
        return deck.collectCardReward();
    }

    public boolean canCollect(){
        return deck.canCollectCards();
    }
    public void addNewArmies(int numOfArmies){
        this.freeArmies += numOfArmies;
    }

    @Override
    public String toString() {
        return this.name + " " + this.color.toString() + " " + String.valueOf(freeArmies);
    }

}

enum Color {
    GREEN, YELLOW, RED, BLUE, PINK, GRAY
}
