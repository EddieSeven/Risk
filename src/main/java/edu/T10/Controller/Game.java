package edu.T10.Controller;

import edu.T10.Model.*;
import edu.T10.Model.Board.Board;
import edu.T10.Model.Board.Territory;

import java.util.Vector;
import edu.T10.Model.Exceptions.MoveException;
import edu.T10.Model.Exceptions.NumberOfDiceException;
import edu.T10.Model.Exceptions.NumberOfUnitsException;
import edu.T10.Model.Exceptions.PlayerException;

public class Game {
    private final BattleController battleController = new BattleController(this);
    private Board board;
    private int numOfPlayers;
    private Vector<Player> players;
    private Deck deck; // the deck the players take from, later on
    private boolean gameOver;
    private boolean currentWin;
    private int currentPlayer;
    private int round;

    public Game(String[] playerNames){
        this.board = new Board();
        this.numOfPlayers = playerNames.length;
        this.players = new Vector<Player>();
        for (int i = 0; i < this.numOfPlayers; i++){
            players.set(i, new Player(playerNames[i]));
        }
        this.gameOver = false;
        this.currentWin = false;
        this.round = 0;
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
        return players.get(currentPlayer);
    }

    public void startTurn(){
        getBonusArmy(this.currentPlayer);
    }

    public int getNumberOfTerritories(){
        return this.board.getTerritorySize();
    }

    public boolean finishTurn() {
        // check if game is over or not
        // eliminate players
        EndGameController egc = new EndGameController(this, this.players);
        gameOver = egc.checkGame();
        if(gameOver) return true;
        else {
            // Draw Card
            if (currentWin) {
                players.get(currentPlayer).addCard2Deck(deck.drawCards(1));
            }
            currentWin = false;

            // Swap Player
            currentPlayer = egc.swapPlayer(currentPlayer);
            startTurn();
            return false;
        }
    }

    public void playCards(int numOfCards) {
        if (players.get(currentPlayer).useCards(numOfCards))
            players.get(currentPlayer).addNewArmies(numOfCards/3);
        return;
    }


    public Territory[] getPlayerTerritories(int playerID){
        return board.getTerritories(playerID);
    }

    public Territory[] getAllTerritories(){
        return board.getAllTerritories();
    }

    public Territory getTerritory(int territoryID){
        return board.getTerritory(territoryID);
    }

    public void conductReinforcement(int territoryID, int unitValue) throws NumberOfUnitsException, PlayerException {
        if (players.get(currentPlayer).getFreeArmies() < unitValue)
             throw new NumberOfUnitsException("Invalid move: You have " + players.get(currentPlayer).getFreeArmies() +
                     " available, and you are trying to add "+ unitValue + ".");
        else if(currentPlayer != getTerritory(territoryID).getOwner())
            throw new PlayerException("Invalid move: You can't place units in territory you do not own.");
        else {
            players.get(currentPlayer).addNewArmies(-unitValue);
            updateTerritory(territoryID, unitValue);
        }
    }

    public InvasionResult conductInvasion(int fromTerritoryID, int toTerritoryID, int attackerUnits, int attackerDice, int defenderDice) throws MoveException, NumberOfUnitsException, NumberOfDiceException {
        return battleController.conductInvasion(fromTerritoryID, toTerritoryID, attackerUnits, attackerDice, defenderDice);
    }

    public void conductFortification(int fromTerritoryID, int toTerritoryID, int unitValue) throws MoveException, NumberOfUnitsException {
        Territory from = getTerritory(fromTerritoryID);
        Territory to = getTerritory(toTerritoryID);

        if (from.isNotAdjacent(toTerritoryID))
            throw new MoveException("Invalid move: Cannot move from " + from.getName() + " to " + to.getName() + ".");
        else if (from.getStrength() < unitValue)
            throw new NumberOfUnitsException("Invalid move: " + from.getName() + " has only " + from.getStrength() + " and " +
                    "you want to move " + unitValue + ". Make sure you leave at least one unit in the territory you " +
                    "are attacking from and that you don't enter in more units than you have.");

        updateTerritory(toTerritoryID, unitValue);
        updateTerritory(fromTerritoryID, -unitValue);
    }

    private void getBonusArmy(int playerID) {
        int territoryBonus = board.getTerritoriesBonus(
                                board.getTerritories(playerID));
        int continentBonus = board.getContinentsBonus(
                                board.getContinents(playerID));
        players.get(playerID).addNewArmies(territoryBonus + continentBonus);
    }

    private void updateTerritory(int territoryID, int unitValue){
        board.getTerritory(territoryID).updateArmyStrength(unitValue);
    }

    public Board getBoard() {
        return board;
    }
}
