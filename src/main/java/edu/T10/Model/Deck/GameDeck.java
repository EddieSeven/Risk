package edu.T10.Model.Deck;


import java.util.Collections;
import java.util.Vector;

public class GameDeck implements Deck {
    private Vector<Card> deck;

    public GameDeck(){
        deck = new Vector<>();
        addCardsOfType(CardType.INFANTRY, 14);
        addCardsOfType(CardType.CAVALRY, 14);
        addCardsOfType(CardType.ARTILLERY, 14);
        addCardsOfType(CardType.WILDCARD, 2);
        shuffle();
    }

    @Override
    public void addCard(Card card) {
        deck.add(card);
    }

    public int getDeckSize(){
        return this.deck.size();
    }

    public Card draw(){ //todo check no cards left
        Card card = deck.get(deck.size()-1);
        deck.remove(deck.size()-1);

        return card;
    }

    private void addCardsOfType(CardType cardType, int number){
        for (int i = 0; i < number; i++){
            deck.add(new Card(cardType));
        }
    }

    private void shuffle(){
        Collections.shuffle(deck);
    }
}
