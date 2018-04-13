package edu.T10.Model.Board;

import java.util.Arrays;

public class Continent {
    public String name;
    public int id;
    public int[] members;
    public int bonus;


    @Override
    public String toString() {
        return id + " " + name + " " + Arrays.toString(members);
    }
}
