package edu.T10.Model.Board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class Board {
    private Continent[] continents;
    private Territory[] territories;
    private int nTerritories;
    private final static String filename = "mapfile.txt";

    public Board(){
        initTerritories(filename);
    }

    private boolean initTerritories(String filename){
        try {
            String filepath = getClass().getClassLoader().getResource(filename).getFile();
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("line" + line);
                switch (line.trim()){
                    case "{continents}":
                        readContinentsBuffer(br);
                        break;
                    case "{countries}":
                        readTerritoriesBuffer(br);
                        break;
                    case "{adjacents}":
                        readAdjacentsBuffer(br);
                        break;
                    default:
                        break;
                }
            }
            br.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    private void readContinentsBuffer(BufferedReader br){
        Vector<Continent> vec = new Vector<>();
        try {
            while (true) {
                String line = br.readLine();
                if (line.equals(";;")) break;
                Continent continent = new Continent();
                String[] segs = line.split(" ");
                continent.id = Integer.parseInt(segs[0]);
                continent.name = segs[1];
                continent.members = new int[segs.length - 3];
                for (int i = 0; i < segs.length - 3; i++){
                    continent.members[i] = Integer.parseInt(segs[2+i]);
                }
                vec.add(continent);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        continents = vec.toArray(new Continent[vec.size()]);
    }

    private void readTerritoriesBuffer(BufferedReader br){
        Vector<Territory> vec = new Vector<>();
        try {
            while (true) {
                String line = br.readLine();
                Territory t = new Territory();
                if (t.readTerritoryFromLine(line)){
                    vec.add(t);
                } else{
                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        territories = vec.toArray(new Territory[vec.size()]);
        nTerritories = territories.length;
    }

    private void readAdjacentsBuffer(BufferedReader br){
        try {
            while (true) {
                String line = br.readLine();
                if (line.equals(";;")) break;
                Territory t = getTerritory(Integer.parseInt(line.split(" ")[0]));
                t.readAdjacentsFromLine(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public int getTerritoriesBonus(Territory[] territories){
        return (int) Math.floor((territories.length * 1.0) / 3.0);
    }

    public int getContinentsBonus(Continent[] continents){
        int bonus = 0;
        for (int i = 0; i < continents.length; i++)
            bonus += continents[i].bonus;
        return bonus;
    }

    public Territory[] getTerritories(int playerID){
        Vector<Territory> vec = new Vector<>();
        for (int i = 0; i < nTerritories; i++){
            if (territories[i].getOwner() == playerID)
                vec.add(territories[i]);
        }
        return vec.toArray(new Territory[vec.size()]);
    }

    public Continent[] getContinents(int playerID){
        Vector<Continent> vec = new Vector<>();
        for (int i = 0; i < continents.length; i++){
            Continent continent = continents[i];
            boolean occupied = true;
            for (int j = 0; j < continent.members.length; j++){
                Territory territory = search(continent.members[j]);
                if (territory.getOwner() !=  playerID)
                    occupied = false;
            }
            if (occupied)
                vec.add(continent);
        }
        return vec.toArray(new Continent[vec.size()]);
    }

    public void updateOwner(int territoryID, int playerID){
        Territory territory = getTerritory(territoryID);
        territory.assignOwner(playerID);
    }

    public Territory getTerritory(int territoryID){
        return search(territoryID);
    }

    public Territory getTerritory(String name){
        return search(name); // todo we can delete one of these when we figure out which one will be more handy to use in practice.
    }

    public int getTerritoryOwner(int territoryID){
        return searchOwner(territoryID);
    }

    public String getTerritoryName(int territoryID){
        return getTerritory(territoryID).getName();
    }

    public int getArmyStrength(int territoryID){
        return getTerritory(territoryID).getStrength();
    }

    private Territory search(int territoryID){
        Territory territory = null;

        for(int i = 0; i < nTerritories; i++){
            if (territories[i].getId() == territoryID){
                territory = territories[i];
                break;
            }
        }

        return territory;
    }

    private Territory search(String name){
        Territory territory = null;

        for(int i = 0; i < nTerritories; i++){
            if (territories[i].getName().equals(name)){
                territory = territories[i];
                break;
            }
        }

        return territory;
    }

    private int searchOwner(int territoryID){
        return getTerritory(territoryID).getOwner();
    }

    public static void main(String[] args) {
        // Test Code
        new Board();
    }
}
