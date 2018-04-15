package edu.T10.Model;

public class InvasionResult {
    private int attackerLosses = 0;
    private int defenderLosses = 0;
    private Victor victor;
    
    public InvasionResult() {
    	this.victor = Victor.DEFAULT;
    }

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

    private String victorString(Victor victor){
        if (victor == Victor.ATTACKER)
            return "attacker";
        else
            return "defender";
    }

    @Override
    public String toString(){
        return victorString(victor) + " wins. Defender lost " + defenderLosses + ", attacker lost " + attackerLosses + ".";

    public void setAttackerLosses(int attackerLosses) {
        this.attackerLosses = attackerLosses;
    }

    public void setDefenderLosses(int defenderLosses) {
        this.defenderLosses = defenderLosses;
    }
}

enum Victor {
    ATTACKER, DEFENDER, DEFAULT;
}