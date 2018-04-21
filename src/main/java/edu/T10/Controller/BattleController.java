package edu.T10.Controller;

import edu.T10.Model.Dice;
import edu.T10.Model.InvasionResult;

import java.util.Vector;
import java.util.Collections;

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
            Vector<Integer> results = conductBattleRound(attackerDice, defenderDice, invasionResult);
            invasionResult.incrementAttackerLosses(results.get(0));
            invasionResult.incrementDefenderLosses(results.get(1));

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

    private Vector<Integer> conductBattleRound(int attackerDice, int defenderDice, InvasionResult runningResult) {
        Vector<Integer> attackerRolls = rollDie(attackerDice);
        Vector<Integer> defenderRolls = rollDie(defenderDice);
        Vector<Integer> results;

        if (attackerDice > defenderDice)
            results = compareDie(defenderDice, attackerRolls, defenderRolls);
        else
            results = compareDie(attackerDice, attackerRolls, defenderRolls);

        return results;
    }

    private Vector<Integer> compareDie(int lowestDieNumber, Vector<Integer> attackerRolls, Vector<Integer> defenderRolls) {
        Vector<Integer> results = new Vector<Integer>();
		results.add(0);
		results.add(0);

        for (int i = 0; i < lowestDieNumber; i++) {
            if (attackerRolls.get(i) > defenderRolls.get(i))
                results.set(0, results.get(0)+1);
            else // in the event of a tie the defend wins
                results.set(1, results.get(1)+1);
        }

        return results;
    }

    private Vector<Integer> rollDie(int numberOfDie) {
        Vector<Integer> result = new Vector<Integer>();
        Dice dice = new Dice();

        for (int i = 0; i < numberOfDie; i++) {
            result.add(dice.roll());
        }

        Collections.sort(result);
        reverse(result);

        return result;
    }

    private void reverse(Vector<Integer> vector) {
        int k = vector.size() - 1;
        for (int i = 0; i < k; i++, k--) {
            int temp = vector.get(i);
			vector.set(i, vector.get(k));
            vector.set(k, temp);
        }
    }

    private int updateUnits(int units, Vector<Integer> results) {
        units -= results.get(0);
        if (units < 0)
            units = 0;

        return units;
    }
}