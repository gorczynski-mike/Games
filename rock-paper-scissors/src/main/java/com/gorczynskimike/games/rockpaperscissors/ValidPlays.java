package com.gorczynskimike.games.rockpaperscissors;

public enum ValidPlays {
    ROCK, PAPER, SCISSORS;

    private ValidPlays beats;
    private ValidPlays beatenBy;

    static {
        ROCK.beats = SCISSORS;
        ROCK.beatenBy = PAPER;
        PAPER.beats = ROCK;
        PAPER.beatenBy = SCISSORS;
        SCISSORS.beats = PAPER;
        SCISSORS.beatenBy = ROCK;
    }

    public ValidPlays getBeats() {
        return beats;
    }

    public ValidPlays getBeatenBy() {
        return beatenBy;
    }

}
