package edu.T10.Model;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.Vector;

public class Deck {
	
    private Vector<Card> cards;

    public Deck() {
    	cards = new Vector<Card>();
    }
	
	public Deck(Vector<Card> c) { 
		cards = new Vector<Card>(c);
	}
    
    public Vector<Card> drawCards(int numOfCards){
        if (numOfCards > this.cards.size())
            return new Vector<Card>();
        Vector<Card> pile = new Vector<Card>(numOfCards);
        for (int i = 0; i < numOfCards; i++){
            pile.add(cards.get(0));
			cards.remove(0);
        }
        return pile;
    }

    public void addCards(Vector<Card> pile){
        for (Card c:pile) {
			this.cards.add(c);
		}
    }
}
