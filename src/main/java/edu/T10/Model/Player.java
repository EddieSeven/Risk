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
}

enum Color {
    GRAY, YELLOW, RED, BLACK, GREEN, BLUE // six players, six colors, from a quick google search
}