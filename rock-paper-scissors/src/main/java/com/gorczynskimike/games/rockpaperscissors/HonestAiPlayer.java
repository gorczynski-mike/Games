package com.gorczynskimike.games.rockpaperscissors;

import java.util.Random;

public class HonestAiPlayer extends Player{

    Random random = new Random();
    private final String name = "Honest AI player";

    @Override
    public ValidPlays play() {
        int generatedNumber = random.nextInt(3);
        return ValidPlays.values()[generatedNumber];
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "HonestAiPlayer{" +
                "name='" + name + '\'' +
                '}';
    }
}
