package edu.T10.Model;

import java.util.Random;

public class Dice {
    public int roll() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
