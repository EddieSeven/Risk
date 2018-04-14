package edu.T10.Model;

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
    
}

enum CardType {
    INFANTRY, CAVALRY, ARTILLERY, WILDCARD, DEFAULT
}
