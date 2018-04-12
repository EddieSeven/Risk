package edu.T10.Model;

public class InvasionResult {
    private int attackerLosses = 0;
    private int defenderLosses = 0;
    private Victor victor;

    public void incrementAttackerLosses(int losses){
        attackerLosses += losses;
    }

    public void incrementDefenderLosses(int losses){
        defenderLosses += losses;
    }

    public int getAttackerLosses(){
        return this.attackerLosses;
    }

    public int getDefenderLosses(){
        return this.defenderLosses;
    }

    public Victor getVictor(){
        return this.victor;
    }

    public void setVictor(Victor victor){
        this.victor = victor;
    }
}

enum Victor {
    ATTACKER, DEFENDER;
}