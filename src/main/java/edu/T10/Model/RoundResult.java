package edu.T10.Model;

public class RoundResult {
    public int attackerLosses;
    public int defenderLosses;

    public void incrementAttackerLosses(){
        attackerLosses += 1;
    }

    public void incrementDefenderLosses(){
        defenderLosses += 1;
    }
}
