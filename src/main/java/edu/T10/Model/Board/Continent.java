package edu.T10.Model.Board;

import java.util.Arrays;

/**
 * Created by Jim on 4/9/18.
 */
public class Continent {
    String name;
    int id;
    int[] members;


    @Override
    public String toString() {
        return id + " " + name + " " + Arrays.toString(members);
    }
}
