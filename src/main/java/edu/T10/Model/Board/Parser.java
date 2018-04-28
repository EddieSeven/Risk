package edu.T10.Model.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

public class Parser {
    public final static String filename = "mapfile.txt";
    private Board board;

    public Parser(Board board){
        this.board = board;
    }

    public Continent[] readContinentsBuffer(BufferedReader br) {

        Vector<Continent> vec = new Vector<Continent>();
        try {
            while (true) {
                String line = br.readLine();
                if (line.equals(";;")) break;
                vec.add(getContinent(line));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return vec.toArray(new Continent[vec.size()]);
    }
    
    private Continent getContinent(String line) {
    	Continent continent = new Continent();
        String[] segs = line.split(" ");
        continent.id = Integer.parseInt(segs[0]);
        continent.name = segs[1];
        continent.members = new int[segs.length - 3];
        for (int i = 0; i < segs.length - 3; i++) {
            continent.members[i] = Integer.parseInt(segs[2 + i]);
        }
        return continent;
    }

    public Territory[] readTerritoriesBuffer(BufferedReader br) {
        Vector<Territory> vec = new Vector<Territory>();
        try {
            while (true) {
                String line = br.readLine();
                Territory territory = new Territory();
                if (readTerritoryFromLine(territory, line)) {
                    vec.add(territory);
                } else {
                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        return vec.toArray(new Territory[vec.size()]);
    }

    public void readAdjacentsBuffer(BufferedReader br) {
        try {
            while (true) {
                String line = br.readLine();
                if (line.equals(";;")) break;
                Territory territory = board.getTerritory(Integer.parseInt(line.split(" ")[0]));
                readAdjacentsFromLine(territory, line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*
        String line: Format:    id, name, continentID, ... ;
                         ex:    1 Alaska 1 47 76 ;
     */
    private boolean readTerritoryFromLine(Territory territory, String line){
        String[] segs = line.split(" ");
        if (segs.length < 4)
            return false;
        else{
            territory.setId(Integer.parseInt(segs[0]));
            territory.setName(segs[1]);
            territory.setContinentID(Integer.parseInt(segs[2]));
            return true;
        }
    }

    /*
        String line: Format:    id, adjId, adjId ... ;
                         ex:    1 2 4 32 ;
     */
    private boolean readAdjacentsFromLine(Territory territory, String line){
        String[] segs = line.split(" ");
        if (segs.length < 2)
            return false;

        else{
            Vector<Integer> adjTerritories = new Vector<Integer>();
            for (int i = 0; i < segs.length - 2; i++){
                adjTerritories.add(Integer.parseInt(segs[1+i]));
            }

            territory.setAdjTerritories(adjTerritories);

            return true;
        }
    }
}