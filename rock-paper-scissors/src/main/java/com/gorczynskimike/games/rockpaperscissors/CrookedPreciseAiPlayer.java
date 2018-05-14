package com.gorczynskimike.games.rockpaperscissors;

import java.util.Random;

/**
 * CrookedPreciseAiPlayer lets you choose its chance for a win and for a loss - int values correspond to percentage values,
 * must be in range 0 to 100, their sum cannot exceed 100.
 */
public class CrookedPreciseAiPlayer extends CrookedAiPlayer {

    private final int winChance;
    private final int lossChance;
    private final Random random = new Random();
    private final String name = "Crooked Precise AI player";

    public CrookedPreciseAiPlayer(int winChance, int lossChance){
        if(winChance < 0 || lossChance < 0 || winChance + lossChance > 100) {
            throw new IllegalArgumentException("Win chance and loss chance must not be negative and their sum must be" +
                    " smaller or equal to 100");
        }
        this.winChance = winChance;
        this.lossChance = lossChance;
    }

    @Override
    public ValidPlays cheat(ValidPlays otherPlayerPlay) {
        int generatedNumber = random.nextInt(100) + 1;
        if (generatedNumber <= winChance) {
            return otherPlayerPlay.getBeatenBy();
        } else if (generatedNumber <= winChance + lossChance) {
            return otherPlayerPlay.getBeats();
        } else {
            return otherPlayerPlay;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }
}
