package edu.T10.Model.Deck;

public interface Deck {
    public void addCard(Card card);
    
    public enum CardType {
        INFANTRY, CAVALRY, ARTILLERY, WILDCARD, DEFAULT
    }
}
