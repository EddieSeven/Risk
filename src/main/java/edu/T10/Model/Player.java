package edu.T10.Model;

public class Player {
    private String name;
    private Color color;
    private int freeArmies;
    private Deck deck;

    public Player(String name){
        this.name = name;
        this.freeArmies = 0;
        this.deck = new Deck();
        this.color = Color.DEFAULT;
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

    public void addCard2Deck(Card[] pile){
        this.deck.addCards(pile);
    }

    public boolean useCards(int numOfCards){
        if (this.deck.drawCards(numOfCards).length != numOfCards)
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
    GRAY, YELLOW, RED, BLACK, GREEN, BLUE, DEFAULT // six players, six colors, from a quick google search
}