package edu.T10.Controller;

import edu.T10.Model.Dice;
import edu.T10.Model.InvasionResult;

import java.util.Arrays;

public class BattleController {
    private final Game game;

    public BattleController(Game game) {
        this.game = game;
    }

    public InvasionResult conductInvasion(int fromTerritoryID, int toTerritoryID, int attackerUnits, int attackerDice, int defenderDice) {
        int defenderUnits = game.getBoard().getArmyStrength(toTerritoryID);
        int attackingPlayerID = game.getBoard().getTerritory(fromTerritoryID).getOwner();
        InvasionResult invasionResult = new InvasionResult();
        int originalAttackerStrength = attackerUnits;
        int originalDefenderStrength = defenderUnits;

        if (defenderUnits == 0) {
            attackerWins(fromTerritoryID, toTerritoryID, attackerUnits, invasionResult, attackingPlayerID);
        }

        while (defenderUnits != 0 && attackerUnits != 0) {
            // attacker losses = results[0]
            // defender losses = results[1]
            int results[] = conductBattleRound(attackerDice, defenderDice, invasionResult);
            invasionResult.incrementAttackerLosses(results[0]);
            invasionResult.incrementDefenderLosses(results[1]);

            attackerUnits = updateUnits(attackerUnits, results);
            defenderUnits = updateUnits(defenderUnits, results);

            if (defenderUnits <= 0) {
                checkLosses(invasionResult, originalAttackerStrength, true);
                checkLosses(invasionResult, originalDefenderStrength, false);
                attackerWins(fromTerritoryID, toTerritoryID, attackerUnits, invasionResult, attackingPlayerID);
            } else if (attackerUnits <= 0) {
                checkLosses(invasionResult, originalAttackerStrength, true);
                checkLosses(invasionResult, originalDefenderStrength, false);
                defenderWins(fromTerritoryID, toTerritoryID, invasionResult);
            }
        }

        return invasionResult;
    }

    private void checkLosses(InvasionResult invasionResult, int originalStrength, boolean isAttacker) {
        if (isAttacker) {
            if (invasionResult.getAttackerLosses() > originalStrength)
                invasionResult.setAttackerLosses(originalStrength);
        } else {
            if (invasionResult.getDefenderLosses() > originalStrength)
                invasionResult.setDefenderLosses(originalStrength);
        }
    }

    private void defenderWins(int fromTerritoryID, int toTerritoryID, InvasionResult invasionResult) {
        invasionResult.setDefenderVictor();
        game.getBoard().updateTerritoryStrength(toTerritoryID, -invasionResult.getDefenderLosses());
        game.getBoard().updateTerritoryStrength(fromTerritoryID, -invasionResult.getAttackerLosses());
    }

    private void attackerWins(int fromTerritoryID, int toTerritoryID, int attackerUnits, InvasionResult invasionResult, int attackingPlayerID) {
        int remainingAttackerStrength = attackerUnits - invasionResult.getAttackerLosses();
        System.out.println("remainingAttackerStrength" + remainingAttackerStrength);
        invasionResult.setAttackerVictor();
        game.getBoard().updateTerritoryStrength(fromTerritoryID, -attackerUnits); // all attacking units are sent to new province
        game.getBoard().updateTerritoryStrength(toTerritoryID, remainingAttackerStrength);
        game.getBoard().updateOwner(toTerritoryID, attackingPlayerID);
    }

    private int[] conductBattleRound(int attackerDice, int defenderDice, InvasionResult runningResult) {
        int attackerRolls[] = rollDie(attackerDice);
        int defenderRolls[] = rollDie(defenderDice);
        int results[];

        if (attackerDice > defenderDice)
            results = compareDie(defenderDice, attackerRolls, defenderRolls);
        else
            results = compareDie(attackerDice, attackerRolls, defenderRolls);

        return results;
    }

    private int[] compareDie(int lowestDieNumber, int[] attackerRolls, int[] defenderRolls) {
        int results[] = new int[2];

        for (int i = 0; i < lowestDieNumber; i++) {
            if (attackerRolls[i] > defenderRolls[i])
                results[0] += 1;
            else // in the event of a tie the defend wins
                results[1] += 1;
        }

        return results;
    }

    private int[] rollDie(int numberOfDie) {
        int result[] = new int[numberOfDie];
        Dice dice = new Dice();

        for (int i = 0; i < numberOfDie; i++) {
            result[i] = dice.roll();
        }

        Arrays.sort(result);
        reverse(result);

        return result;
    }

    private void reverse(int[] array) {
        int k = array.length - 1;
        for (int i = 0; i < k; i++, k--) {
            int temp = array[i];
            array[i] = array[k];
            array[k] = temp;
        }
    }

    private int updateUnits(int units, int[] results) {
        units -= results[0];
        if (units < 0)
            units = 0;

        return units;
    }
}