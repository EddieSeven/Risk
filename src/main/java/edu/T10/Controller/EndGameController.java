package edu.T10.Controller;

import edu.T10.Model.Board.Board;
import edu.T10.Model.Player;

import java.util.Vector;

public class EndGameController {
    private final int endingTerritories = 42;
    private final int endingPlayers = 1;
    private Game game;
    private Vector<Player> players;


    public EndGameController(Game game, Vector<Player> players) {
        this.game = game;
        this.players = new Vector<Player> (players);
        eliminatePlayers();
    }

    private void eliminatePlayers(){
        for (int i = 0; i < players.size(); i++) {
            if (game.getPlayerTerritories(i).length == 0)
                players.set(i, null);
        }
    }

    public boolean checkGame(){
        int playersLeft = 0;
        for (Player player : players) {
            if (player != null)
                playersLeft += 1;
        }
        return (playersLeft == endingPlayers);
    }

    public int swapPlayer(int currentPlayer){
        int numOfPlayers = players.size();
        int next = currentPlayer;
        while(true) {
            next = (next + 1) % numOfPlayers;
            if (players.get(next).equals(null)) continue;
            return next;
        }
    }
}
