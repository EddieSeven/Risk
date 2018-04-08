package edu.T10.Model;

import java.util.Arrays;

public class Game {
    private Board board;
    private Player[] players;
    // todo not sure if we should have decks be handled by players or by game

    public Game(){
        // todo constructor
    }

    public boolean updateTerritory(int territoryID, int unitValue){
        board.getTerritory(territoryID).updateArmyStrength(unitValue);

        return true; // todo change when needed
    }

    public RoundResult battleRound(int attackerDie, int defenderDie){
        int attackerRolls[] = rollDie(attackerDie); // todo 1 <= attacker die <= 3
        int defenderRolls[] = rollDie(defenderDie); // todo 1 <= defender die <= 2
        RoundResult result = new RoundResult();

        if (attackerDie > defenderDie)
            compareDie(defenderDie, result, attackerRolls, defenderRolls);
        else
            compareDie(attackerDie, result, attackerRolls, defenderRolls);

        return result;
    }

    private void compareDie(int lowestDieNumber, RoundResult result, int[] attackerRolls, int[] defenderRolls){
        for (int i = 0; i < lowestDieNumber; i++){
            if (attackerRolls[i] > defenderRolls[i])
                result.incrementDefenderLosses();
            else // in the event of a tie the defend wins
                result.incrementAttackerLosses();
        }
    }

    private int[] rollDie(int numberOfDie){
        int result[] = new int[numberOfDie];
        Dice dice = new Dice();

        for (int i = 0; i < numberOfDie; i++){
            result[i] = dice.roll();
        }

        Arrays.sort(result);
        reverse(result);

        return result;
    }

    private void reverse(int[] array){
        int k = array.length - 1;
        for (int i = 0; i < k; i++, k--){
            int temp = array[i];
            array[i] = array[k];
            array[k] = temp;
        }
    }
}
