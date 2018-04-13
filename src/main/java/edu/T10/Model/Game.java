package edu.T10.Model;

import edu.T10.Model.Board.Board;

import java.util.Arrays;

import static edu.T10.Model.Victor.*;

public class Game {
    private Board board;
    private Player[] players;
    private Deck deck; // the deck the players take from, later on
    private boolean gameOver;
    private boolean currentWin;
    private int currentPlayer;
    private int round;

    public Game(int numOfPlayer){
        this.board = new Board();
        this.players = new Player[numOfPlayer];
        this.gameOver = false;
        this.currentWin = false;
        this.round = 0; // human readable
    }

    public void startGame() {
        // TODO
        // Player choose color
        // Player claim territories
    }

    public void startTurn(){
        getBonusArmy();
    }

    public void finishTurn() {
        if (currentWin){
            players[currentPlayer].addCard2Deck(deck.drawCards(1));
        }
        currentWin = false;
        currentPlayer = (currentPlayer + 1) % players.length;
    }

    public void playCards(int numOfCards) {
        if (players[currentPlayer].useCards(numOfCards))
            players[currentPlayer].addNewArmies(numOfCards/3);
        return;
    }

    private void getBonusArmy() {
        int territoryBonus = board.getTerritoriesBonus(
                                board.getTerritories(currentPlayer));
        int continentBonus = board.getContinentsBonus(
                                board.getContinents(currentPlayer));
        players[currentPlayer].addNewArmies(territoryBonus + continentBonus);
    }

    public InvasionResult conductInvasion(int fromTerritoryID, int toTerritoryID, int attackerUnits, int attackerDice, int defenderDice) {
        int defenderUnits = board.getArmyStrength(toTerritoryID);
        InvasionResult invasionResult = new InvasionResult();
        int attackingPlayerID = board.getTerritory(fromTerritoryID).getOwner();

        while (defenderUnits != 0 && attackerUnits != 0){
            // attacker losses = results[0]
            // defender losses = results[1]
            int results[] = conductBattleRound(attackerDice, defenderDice, invasionResult);
            invasionResult.incrementAttackerLosses(results[0]);
            invasionResult.incrementDefenderLosses(results[1]);

            attackerUnits -= results[0];
            defenderUnits -= results[1];

            if (defenderUnits <= 0) {
                int remainingAttackerStrength = attackerUnits - invasionResult.getAttackerLosses();
                invasionResult.setVictor(ATTACKER);
                board.updateTerritoryStrength(fromTerritoryID, -attackerUnits); // all attacking units are sent to new province
                board.updateTerritoryStrength(toTerritoryID, remainingAttackerStrength);
                board.updateOwner(toTerritoryID, attackingPlayerID);

            }
            else if (attackerUnits <= 0){
                invasionResult.setVictor(DEFENDER);
                board.updateTerritoryStrength(toTerritoryID, -invasionResult.getDefenderLosses());
                board.updateTerritoryStrength(fromTerritoryID, invasionResult.getAttackerLosses());
            }

        }

        return invasionResult;
    }

    private int[] conductBattleRound(int attackerDice, int defenderDice, InvasionResult runningResult){
        int attackerRolls[] = rollDie(attackerDice); // todo 1 <= attacker die <= 3
        int defenderRolls[] = rollDie(defenderDice); // todo 1 <= defender die <= 2
        int results[];

        if (attackerDice > defenderDice)
            results = compareDie(defenderDice, attackerRolls, defenderRolls);
        else
            results = compareDie(attackerDice, attackerRolls, defenderRolls);

        return results;
    }

    private int[] compareDie(int lowestDieNumber, int[] attackerRolls, int[] defenderRolls){
        int results[] = new int[2];

        for (int i = 0; i < lowestDieNumber; i++){
            if (attackerRolls[i] > defenderRolls[i])
                results[0] += 1;
            else // in the event of a tie the defend wins
                results[1] += 1;
        }

        return results;
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
