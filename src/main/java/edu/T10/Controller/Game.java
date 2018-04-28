package edu.T10.Controller;

import edu.T10.Model.*;
import edu.T10.Model.Board.Board;
import edu.T10.Model.Board.Territory;

import java.util.Vector;

import edu.T10.Model.Deck.Card;
import edu.T10.Model.Deck.GameDeck;
import edu.T10.Model.Exceptions.*;

public class Game {
    private final BattleController battleController = new BattleController(this);
    private Board board;
    private int numOfPlayers;
    private Vector<Player> players;
    private GameDeck deck;
    private boolean gameOver;
    private boolean currentWin;
    private int currentPlayer;

    public Game(Player[] players){
        this.board = new Board();
        this.numOfPlayers = players.length;
        this.players = new Vector<Player>();
        this.deck = new GameDeck();

        for (int i = 0; i < this.numOfPlayers; i++){
            this.players.add(players[i]);
        }

        this.gameOver = false;
        this.currentWin = false;
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
            if (currentWin && deck.getDeckSize() > 0) {
                Card drawnCard = deck.draw();
                players.get(currentPlayer).addCard(drawnCard);
            }
            currentWin = false;

            // Swap Player
            currentPlayer = egc.swapPlayer(currentPlayer);
            startTurn();
            return false;
        }
    }

    public void setCurrentPlayerGetsCard(){
        this.currentWin = true;
    }

    public boolean canCollectPlayerCards(){
        Player player = players.get(currentPlayer);

        return player.canCollect();
    }

    public void playCards() throws DeckCompositionException {
        Player player = players.get(currentPlayer);

        if (!player.canCollect())
            throw new DeckCompositionException("Invalid move: You do not have a set of cards available to play.");

        int reward = player.collectCardReward();
        player.addNewArmies(reward);
    }

    public Vector<Integer> getCardTypes(){
        Player player = players.get(currentPlayer);

        return player.getCardTypes();
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
