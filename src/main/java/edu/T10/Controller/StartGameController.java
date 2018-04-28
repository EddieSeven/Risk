package edu.T10.Controller;

import edu.T10.Model.Board.Board;
import edu.T10.Model.Player;

import java.util.Collections;
import java.util.Vector;

public class StartGameController {
    public StartGameController(Board board, Vector<Player> players){
        // Player choose territory, randomly assigned for this time
        this.territoryAssignment(board, players.size());
    }

    private void territoryAssignment(Board board, int numOfPlayers) {
        int len = (board.getTerritorySize() / numOfPlayers) * numOfPlayers;
        Vector<Integer> assignments = intArrayShuffled(len);
        for (int i = 0; i < len; i++) {

            board.updateOwner(assignments.get(i) + 1, i % numOfPlayers);
            // TODO: Default set to 3
            board.getTerritory(assignments.get(i) + 1).updateArmyStrength(3);
        }
    }

    private Vector<Integer> intArrayShuffled(int size){
        Vector<Integer> solution = new Vector<Integer>();
        for (int i = 0; i < size; i++) {
            solution.add(i);
        }
        Collections.shuffle(solution);
        Vector<Integer> intVector = new Vector<Integer>();
        for (int i = 0; i < solution.size(); i++) {
            intVector.add(solution.get(i));
        }
        return intVector;
    }
}
