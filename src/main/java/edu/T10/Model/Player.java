package edu.T10.Model;
import java.util.Vector;

public class Player {
    private String name;
    private Color color;
    private int freeArmies;
    private Deck deck;

    public Player(String name){
        this.name = name;
        this.freeArmies = 0;
        this.deck = new Deck();
        this.color = Color.RED;
    }

    public int getFreeArmies() {
        return freeArmies;
    }

    public void assignColor(int colorIdx){
        this.color = Color.values()[colorIdx];
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
    GRAY, YELLOW, RED, BLACK, GREEN, BLUE // six players, six colors, from a quick google search
}
