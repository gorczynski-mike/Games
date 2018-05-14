package com.gorczynskimike.games.rockpaperscissors;

public class GameDto {

    private final Playable player1;
    private final Playable player2;
    private final Playable winner;
    private final int winningNumberOfRounds;
    private final int player1Wins;
    private final int player2Wins;
    private final int totalRoundsPlayed;

    public GameDto(Playable player1, Playable player2, Playable winner, int winningNumberOfRounds, int player1Wins, int player2Wins, int totalRoundsPlayed) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.winningNumberOfRounds = winningNumberOfRounds;
        this.player1Wins = player1Wins;
        this.player2Wins = player2Wins;
        this.totalRoundsPlayed = totalRoundsPlayed;
    }

    public Playable getPlayer1() {
        return player1;
    }

    public Playable getPlayer2() {
        return player2;
    }

    public Playable getWinner() {
        return winner;
    }

    public int getWinningNumberOfRounds() {
        return winningNumberOfRounds;
    }

    public int getPlayer1Wins() {
        return player1Wins;
    }

    public int getPlayer2Wins() {
        return player2Wins;
    }

    public int getTotalRoundsPlayed() {
        return totalRoundsPlayed;
    }

    @Override
    public String toString() {
        return "GameDto{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", winner=" + winner +
                ", winningNumberOfRounds=" + winningNumberOfRounds +
                ", player1Wins=" + player1Wins +
                ", player2Wins=" + player2Wins +
                ", totalRoundsPlayed=" + totalRoundsPlayed +
                '}';
    }
}
