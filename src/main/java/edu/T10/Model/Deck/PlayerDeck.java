package edu.T10.Model.Deck;

import java.util.Vector;

public class PlayerDeck implements Deck {
    private Vector<Card> deck;
    private int deckSize;
    private int numOfInfantry;
    private int numOfCavalry;
    private int numOfArtillery;
    private int numOfWildCards;
    private int collectionRewardTier = 1;
    private int collectionReward = 4;

    public PlayerDeck(){
        deck = new Vector<>();
    }

    public boolean canCollectCards() {
        return !canCollect().equals(CollectionType.NONE);
    }

    public Vector<Integer> getCardTypes(){
        Vector<Integer> cardTypes = new Vector<>();
        cardTypes.add(numOfInfantry);
        cardTypes.add(numOfCavalry);
        cardTypes.add(numOfArtillery);
        cardTypes.add(numOfWildCards);

        return cardTypes;
    }

    @Override
    public void addCard(Card card) {
        CardType cardType = card.getType();
        deck.add(card);
        deckSize++;

        switch(cardType){
            case INFANTRY:
                numOfInfantry++;
                break;
            case CAVALRY:
                numOfCavalry++;
                break;
            case ARTILLERY:
                numOfArtillery++;
                break;
            case WILDCARD:
                numOfWildCards++;
                break;
            case DEFAULT:
                break;
        }
    }

    public int collectCardReward(){
        CollectionType collectionType = canCollect();
        int reward = collect(collectionType);

        return reward;
    }

    private int collect(CollectionType collectionType){
        int reward = this.collectionReward;
        updateTier();
        removeCardsOfCollectionType(collectionType);
        return reward;
    }

    private void removeCardsOfCollectionType(CollectionType collectionType){
        switch (collectionType){
            case INF3:
                removeCardOfType(CardType.INFANTRY);
                removeCardOfType(CardType.INFANTRY);
                removeCardOfType(CardType.INFANTRY);

                numOfInfantry -= 3;
                assert(numOfInfantry < 0);
                break;
            case CAV3:
                removeCardOfType(CardType.CAVALRY);
                removeCardOfType(CardType.CAVALRY);
                removeCardOfType(CardType.CAVALRY);

                numOfCavalry -= 3;
                assert(numOfCavalry < 0);
                break;
            case ART3:
                removeCardOfType(CardType.ARTILLERY);
                removeCardOfType(CardType.ARTILLERY);
                removeCardOfType(CardType.ARTILLERY);

                numOfArtillery -= 3;
                assert(numOfArtillery < 0);
                break;
            case FULL:
                removeCardOfType(CardType.ARTILLERY);
                removeCardOfType(CardType.CAVALRY);
                removeCardOfType(CardType.INFANTRY);

                numOfInfantry -= 1;
                numOfCavalry -= 1;
                numOfArtillery -= 1;
                assert(numOfInfantry < 0);
                assert(numOfCavalry < 0);
                assert(numOfArtillery < 0);

                break;
            case WCA:
                removeCardOfType(CardType.ARTILLERY);
                removeCardOfType(CardType.CAVALRY);
                removeCardOfType(CardType.WILDCARD);

                numOfWildCards -= 1;
                numOfCavalry -= 1;
                numOfArtillery -= 1;
                assert(numOfWildCards < 0);
                assert(numOfCavalry < 0);
                assert(numOfArtillery < 0);

                break;
            case WIA:
                removeCardOfType(CardType.ARTILLERY);
                removeCardOfType(CardType.WILDCARD);
                removeCardOfType(CardType.INFANTRY);

                numOfInfantry -= 1;
                numOfWildCards -= 1;
                numOfArtillery -= 1;
                assert(numOfInfantry < 0);
                assert(numOfWildCards < 0);
                assert(numOfArtillery < 0);

                break;
            case WIC:
                removeCardOfType(CardType.WILDCARD);
                removeCardOfType(CardType.CAVALRY);
                removeCardOfType(CardType.INFANTRY);

                numOfInfantry -= 1;
                numOfCavalry -= 1;
                numOfWildCards -= 1;
                assert(numOfInfantry < 0);
                assert(numOfCavalry < 0);
                assert(numOfWildCards < 0);

                break;

        }

        deckSize -= 3;
    }

    private void removeCardOfType(CardType cardType){
        for (int i = 0; i < deckSize; i++){
            if (deck.get(i).getType().equals(cardType)) {
                deck.remove(i);
                break;
            }
        }
    }

    private CollectionType canCollect(){
        if (numOfInfantry == 3){
            return CollectionType.INF3;
        } else if (numOfCavalry == 3){
            return CollectionType.CAV3;
        } else if (numOfArtillery == 3){
            return CollectionType.ART3;
        } else if (numOfInfantry >= 1 && numOfCavalry >= 1 && numOfArtillery >= 1){
            return CollectionType.FULL;
        } else if (numOfWildCards >= 1 && numOfCavalry >= 1 && numOfArtillery >= 1){
            return CollectionType.WCA;
        } else if (numOfWildCards >= 1 && numOfCavalry >= 1 && numOfInfantry >= 1){
            return CollectionType.WIC;
        } else if (numOfWildCards >= 1 && numOfInfantry >= 1 && numOfArtillery >= 1){
            return CollectionType.WIA;
        } else
            return CollectionType.NONE;
    }

    private void updateTier(){
        switch (collectionRewardTier){
            case 1:
                collectionRewardTier = 2;
                collectionReward = 6;
                break;
            case 2:
                collectionRewardTier = 3;
                collectionReward = 8;
                break;
            case 3:
                collectionRewardTier = 4;
                collectionReward = 10;
                break;
            case 4:
                collectionRewardTier = 5;
                collectionReward = 12;
                break;
            case 5:
                collectionRewardTier = 6;
                collectionReward = 15;
                break;
            default:
                break;
        }
    }
}

enum CollectionType {
    INF3, CAV3, ART3, FULL, WCA, WIC, WIA, NONE
}
