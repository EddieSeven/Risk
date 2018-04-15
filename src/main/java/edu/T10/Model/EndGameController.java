package edu.T10.Model;

import edu.T10.Model.Board.Board;

public class EndGameController {

    private final int endingTerritories = 42;
    private final int endingPlayers = 1;
    private Board board;
    private Player[] players;

    public EndGameController(Board board, Player[] players) {
        this.board = board;
        this.players = players;

        eliminatePlayers();
    }

    private void eliminatePlayers(){
        for (int i = 0; i < players.length; i++) {
            if (board.getTerritories(i).length == 0)
                players[i] = null;
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
        int numOfPlayers = players.length;
        int next = currentPlayer;
        while(true) {
            next = (next + 1) % numOfPlayers;
            if (players[next].equals(null)) continue;
            return next;
        }
    }
}
