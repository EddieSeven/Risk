package edu.T10.Model;

import edu.T10.Model.Board.Board;

import java.util.Arrays;

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
        this.getBonusArmy();
    }

    public void finishTurn() {
        if (this.currentWin){
            this.players[currentPlayer].addCard2Deck(this.deck.drawCards(1));
        }
        this.currentWin = false;
        this.currentPlayer = (this.currentPlayer + 1) % players.length;
    }

    public void playCards(int numOfCards) {
        if (this.players[currentPlayer].useCards(numOfCards))
            this.players[currentPlayer].addNewArmies(numOfCards/3);
        return;
    }

    public void conductInvasion(int attackerStrength, int defenderStrength, int attackerDie, int defenderDie){

    }

    private void getBonusArmy() {
        // TODO
        // Determine how many armies current player can get in the beginning of a new turn
    }

    public boolean updateTerritory(int territoryID, int unitValue){
        board.getTerritory(territoryID).updateArmyStrength(unitValue);

        return true; // todo change when needed
    }

    public void battleController(int territoryID, int targetID, int unitValue) {
        int oppoUnits = board.getArmyStrength(targetID);
        InvasionResult invasionResult = battleRound(unitValue, oppoUnits);
        // TODO
        // Determine winner and update two Territory
        return;
    }

    public InvasionResult battleRound(int attackerDie, int defenderDie){
        int attackerRolls[] = rollDie(attackerDie); // todo 1 <= attacker die <= 3
        int defenderRolls[] = rollDie(defenderDie); // todo 1 <= defender die <= 2
        InvasionResult result = new InvasionResult();

        if (attackerDie > defenderDie)
            compareDie(defenderDie, result, attackerRolls, defenderRolls);
        else
            compareDie(attackerDie, result, attackerRolls, defenderRolls);

        return result;
    }

    private void compareDie(int lowestDieNumber, InvasionResult result, int[] attackerRolls, int[] defenderRolls){
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
