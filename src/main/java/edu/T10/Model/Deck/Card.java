package edu.T10.Model.Deck;

public class Card {
    private CardType type;
    
    public Card() {
    	// Default type is DEFAULT
    	this.type = CardType.DEFAULT;
    }
    
    public Card(CardType type) {
    	this.type = type;
    }
    
    public CardType getType() {
    	return type;
    }

    public enum CardType {
        INFANTRY, CAVALRY, ARTILLERY, WILDCARD, DEFAULT
    }

}
