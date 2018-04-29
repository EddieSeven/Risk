package edu.T10.Model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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

    public void setDefenderVictor(){
        this.victor = Victor.DEFENDER;
    }

    public void setAttackerVictor(){
        this.victor = Victor.ATTACKER;
    }

    private String victorString(Victor victor){
        if (victor == Victor.ATTACKER)
            return "attacker";
        else
            return "defender";
    }

    @Override
    public String toString() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("action", "result")
                .add("result", victorString(victor))
                .add("attacker", attackerLosses)
                .add("defender", defenderLosses).build();
        return jsonObject.toString();
    }

    public void setAttackerLosses(int attackerLosses) {
        this.attackerLosses = attackerLosses;
    }

    public void setDefenderLosses(int defenderLosses) {
        this.defenderLosses = defenderLosses;
    }
}

enum Victor {
    ATTACKER, DEFENDER, DEFAULT
}