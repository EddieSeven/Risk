package edu.T10.Controller;

import edu.T10.Model.Board.Board;
import edu.T10.Model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartGameController {
    public StartGameController(Board board, Player[] players){
        // Player choose color, randomly assigned for this time
        this.colorAssignment(players);
        // Player choose territory, randomly assigned for this time
        this.territoryAssignment(board, players.length);
    }

    private void colorAssignment(Player[] players){
        int len = players.length;
        int[] assignments = intArrayShuffled(len);
        for (int i = 0; i < len; i++){
            players[i].assignColor(assignments[i]);
        }
    }

    private void territoryAssignment(Board board, int numOfPlayers) {
        int len = (board.getTerritorySize() / numOfPlayers) * numOfPlayers;
        int[] assignments = intArrayShuffled(len);
        for (int i = 0; i < len; i++) {
            board.updateOwner(assignments[i] + 1, i % numOfPlayers);
            // TODO: Default set to 3
            board.getTerritory(assignments[i] + 1).updateArmyStrength(3);
        }
    }

    private int[] intArrayShuffled(int size){
        List<Integer> solution = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            solution.add(i);
        }
        Collections.shuffle(solution);
        int[] intArray = new int[solution.size()];
        for (int i = 0; i < solution.size(); i++) {
            intArray[i] = solution.get(i);
        }
        return intArray;
    }
}
