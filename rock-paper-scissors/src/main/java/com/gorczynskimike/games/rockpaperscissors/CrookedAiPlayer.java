package com.gorczynskimike.games.rockpaperscissors;

import java.util.Random;

/**
 * Crooked AI player has 25% chance for a loss, 25% chance for a draw and 50% chance for a win
 */
public class CrookedAiPlayer extends Player {

    private Random random = new Random();
    private final String name = "Crooked AI player";
    private ValidPlays otherPlayerPlay;
    private Game.CrookedPlayerHelper crookedPlayerHelper = null;

    @Override
    public ValidPlays play() {
        otherPlayerPlay = crookedPlayerHelper.getOtherPlayerPlay(this);
        return cheat(otherPlayerPlay);
    }

    public ValidPlays cheat(ValidPlays otherPlayerPlay) {
        int generatedNumber = random.nextInt(4);
        if(generatedNumber == 3) {
            System.out.println("Cheating!!!");
            return otherPlayerPlay.getBeatenBy();
        } else {
            return ValidPlays.values()[generatedNumber];
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CrookedAiPlayer{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setCrookedPlayerHelper(Game.CrookedPlayerHelper crookedPlayerHelper) {
        if(crookedPlayerHelper == null) {
            throw new IllegalArgumentException("Argument to this method cannot be null.");
        }
        this.crookedPlayerHelper = crookedPlayerHelper;
    }
}
