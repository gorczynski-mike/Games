package com.gorczynskimike.games.rockpaperscissors;

public class Game {

    private Playable player1;
    private ValidPlays player1Play;
    private Playable player2;
    private ValidPlays player2Play;
    private Playable winner;
    private int winningNumberOfRounds;
    private int roundsPlayed;
    private int player1Wins;
    private int player2Wins;
    private int draws;
    private final UserInterface userInterface;
    private boolean stopTheGame;
    private final CrookedPlayerHelper crookedPlayerHelper = new CrookedPlayerHelper();

    public Game(Playable player1, Playable player2, UserInterface userInterface) {
        this.player1 = player1;
        this.player2 = player2;
        if(player1 instanceof CrookedAiPlayer) {
            ((CrookedAiPlayer) player1).setCrookedPlayerHelper(crookedPlayerHelper);
        }
        if(player2 instanceof CrookedAiPlayer) {
            ((CrookedAiPlayer) player2).setCrookedPlayerHelper(crookedPlayerHelper);
        }
        this.userInterface = userInterface;
    }

    public GameDto startNewGame() {
        userInterface.printInstructions();
        stopTheGame = false;
        roundsPlayed = 0;
        player1Wins = 0;
        player2Wins = 0;
        draws = 0;
        winner = null;
        winningNumberOfRounds = userInterface.getWinningNumberOfRounds();
        while(player1Wins < winningNumberOfRounds && player2Wins < winningNumberOfRounds) {
            playRound();
            if(stopTheGame) {
                return null;
            }
        }
        if(player1Wins == winningNumberOfRounds) {
            System.out.println(player1 + " won the game!");
            winner = player1;
        } else {
            System.out.println(player2 + " won the game!");
            winner = player2;
        }
        return new GameDto(player1, player2, winner, winningNumberOfRounds, player1Wins, player2Wins, roundsPlayed);
    }

    private void playRound() {
        player1Play = player1.play();
        if(stopTheGame) {
            return;
        }
        player2Play = player2.play();
        System.out.println(player1 + " played: " + player1Play);
        System.out.println(player2 + " played: " + player2Play);
        boolean isDraw = player1Play.equals(player2Play);
        boolean player1WinsRound = player2Play.getBeatenBy().equals(player1Play);

        if(isDraw) {
            System.out.println("It's a draw!");
            roundsPlayed++;
            draws++;
        } else if(player1WinsRound) {
            System.out.println(player1 + " wins round!");
            roundsPlayed++;
            player1Wins++;
        } else {
            System.out.println(player2 + " wins round!");
            roundsPlayed++;
            player2Wins++;
        }

        printGameState();
    }

    private void printGameState() {
        System.out.printf("%s won %d times. %s won %d times. %d draws. Rounds played so far: %d. Winning number of rounds: %d.%n",
                player1, player1Wins, player2, player2Wins, draws, roundsPlayed, winningNumberOfRounds);
    }

    public void stopTheGame() {
        stopTheGame = true;
    }

    class CrookedPlayerHelper {

        private CrookedPlayerHelper(){

        }

        public ValidPlays getOtherPlayerPlay(Playable playable){
            if (playable == Game.this.player1) {
                return Game.this.player2Play;
            } else if (playable == Game.this.player2) {
                return Game.this.player1Play;
            } else {
                throw new IllegalArgumentException("Player not found");
            }
        }
    }

}
