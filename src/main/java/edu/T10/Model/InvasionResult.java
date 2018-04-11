package edu.T10.Model;

public class InvasionResult {
    private int attackerLosses = 0;
    private int defenderLosses = 0;
    private boolean attackerWon = false;

    public void incrementAttackerLosses(int loss){
        attackerLosses += loss;
    }

    public void incrementDefenderLosses(int loss){
        defenderLosses += loss;
    }

    public int getAttackerLosses(){
        return this.attackerLosses;
    }

    public int getDefenderLosses(){
        return this.defenderLosses;
    }

    public boolean attackerWon(){
        return this.attackerWon;
    }

    public void setAttackWon(){
        this.attackerWon = true;
    }
}
