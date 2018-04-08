package edu.T10.Model;

public class Player {
    private String name;
    private Color color;
    // private Units[] freeArmies // todo lets see if this is necessary
    private Deck deck;
    private Territory territories;

    public Player(String name){
        this.name = name;
    }

    public void assignColor(Color color){
        this.color = color;
    }
}

enum Color {
    GRAY, YELLOW, RED, BLACK, GREEN, BLUE // six players, six colors, from a quick google search
}