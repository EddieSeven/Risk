package edu.T10.Model;

public class RoundResult {
    public int attackerLosses = 0;
    public int defenderLosses = 0;

    public void incrementAttackerLosses(){
        attackerLosses += 1;
    }

    public void incrementDefenderLosses(){
        defenderLosses += 1;
    }
}
