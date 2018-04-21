package edu.T10.Model;
import java.util.Vector;

public class Player {
    private String name;
    private Color color;
    private int freeArmies;
    private Deck deck;

    public Player(String name, int colorID){
        this.name = name;
        this.freeArmies = 0;
        this.deck = new Deck();
        assignColor(colorID);
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

    public void addCard2Deck(Vector<Card> pile){
        this.deck.addCards(pile);
    }

    public boolean useCards(int numOfCards){
        if (this.deck.drawCards(numOfCards).size() != numOfCards)
            return false;
        return true;
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
