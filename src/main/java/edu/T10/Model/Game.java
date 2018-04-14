package edu.T10.Model;

import edu.T10.Model.Board.Board;
import edu.T10.Model.Board.Territory;

import java.util.Arrays;

import static edu.T10.Model.Victor.*;

public class Game {
    private Board board;
    private int numOfPlayers;
    private Player[] players;
    private Deck deck; // the deck the players take from, later on
    private boolean gameOver;
    private boolean currentWin;
    private int currentPlayer;
    private int round;

    public Game(String[] playerNames){
        this.board = new Board();
        this.numOfPlayers = playerNames.length;
        this.players = new Player[this.numOfPlayers];
        for (int i = 0; i < this.numOfPlayers; i++){
            players[i] = new Player(playerNames[i]);
        }
        this.gameOver = false;
        this.currentWin = false;
        this.round = 0; // human readable
    }

    public void startGame() {
        // Players are assignedcolor
        // Player choose territory
        new StartGameController(this.board, this.players);
        startTurn();
    }

    public int getCurrentPlayerID(){
        return currentPlayer;
    }

    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }

    public void startTurn(){
        getBonusArmy(this.currentPlayer);
    }

    public boolean finishTurn() {
        // check if game is over or not
        // eliminate players
        EndGameController egc = new EndGameController(this.board, this.players);
        gameOver = egc.checkGame();
        if(gameOver) return true;
        else {
            // Draw Card
            if (currentWin) {
                players[currentPlayer].addCard2Deck(deck.drawCards(1));
            }
            currentWin = false;

            // Swap Player
            currentPlayer = egc.swapPlayer(currentPlayer);
            startTurn();
            return false;
        }
    }

    public void playCards(int numOfCards) {
        if (players[currentPlayer].useCards(numOfCards))
            players[currentPlayer].addNewArmies(numOfCards/3);
        return;
    }

    public Territory[] getPlayerTerritories(int id){
        return board.getTerritories(id);
    }

    public Territory[] getAllTerritories(){
        return board.getAllTerritories();
    }

    public boolean conductReinforcement(int territoryID, int unitValue){
        if (players[currentPlayer].getFreeArmies() < unitValue)
            return false;
        else{
            players[currentPlayer].addNewArmies(-unitValue);
            updateTerritory(territoryID, unitValue);
            return true;
        }
    }

    private void getBonusArmy(int playerID) {
        int territoryBonus = board.getTerritoriesBonus(
                                board.getTerritories(playerID));
        int continentBonus = board.getContinentsBonus(
                                board.getContinents(playerID));
        players[playerID].addNewArmies(territoryBonus + continentBonus);
    }

    private void updateTerritory(int territoryID, int unitValue){
        board.getTerritory(territoryID).updateArmyStrength(unitValue);
    }

    public InvasionResult conductInvasion(int fromTerritoryID, int toTerritoryID, int attackerUnits, int attackerDice, int defenderDice) {
        int defenderUnits = board.getArmyStrength(toTerritoryID);
        InvasionResult invasionResult = new InvasionResult();

        while (defenderUnits != 0 && attackerUnits != 0){
            // attacker losses = results[0]
            // defender losses = results[1]
            int results[] = conductBattleRound(attackerDice, defenderDice, invasionResult);
            invasionResult.incrementAttackerLosses(results[0]);
            invasionResult.incrementDefenderLosses(results[1]);

            attackerUnits -= results[0];
            defenderUnits -= results[1];

            if (defenderUnits <= 0)
                invasionResult.setVictor(ATTACKER);
            else if (attackerUnits <= 0)
                invasionResult.setVictor(DEFENDER);
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

    public static void main(String[] args){
        // Test code
        Game game = new Game(new String[]{"a", "b", "c", "d"});
        game.startGame();
        /* ----- Test Board ----- */
        for (int i = 0; i < game.numOfPlayers; i++) {
            System.out.println(game.board.getTerritories(i).length);
            System.out.println(Arrays.toString(game.board.getTerritories(i)));
        }
        /* ------ Test Player ----- */
//        for (int i = 0; i < game.numOfPlayers; i++) {
//            System.out.println(game.players[i]);
//        }
        game.conductReinforcement(game.board.getTerritories(0)[0].getId(), 3);
        game.finishTurn();
        /* ------ Test Player ----- */
        for (int i = 0; i < game.numOfPlayers; i++) {
            System.out.println(game.players[i]);
        }
        System.out.println(game.currentPlayer);

    }

}
