package edu.T10.Model;

import java.util.Arrays;
import java.util.stream.Stream;

public class Deck {
	
    private Card[] cards; //todo any array for now, though an arraylist will probably be better.

    public Deck() {
    	cards = new Card[0];
    }
	
	public Deck(Card[] c) { 
		cards = c;
	}
    
    public Card[] drawCards(int numOfCards){
        if (numOfCards > this.cards.length)
            return new Card[0];
        Card[] pile = new Card[numOfCards];
        for (int i = 0; i < numOfCards; i++){
            pile[i] = cards[i];
        }
        this.cards = Arrays.copyOfRange(this.cards, numOfCards, this.cards.length);
        return pile;
    }

    public void addCards(Card[] pile){
        this.cards = Stream.of(this.cards, pile).flatMap(Stream::of).toArray(Card[]::new);
    }
}
