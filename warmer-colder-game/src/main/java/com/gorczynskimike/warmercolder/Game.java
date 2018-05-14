package com.gorczynskimike.warmercolder;

import java.util.Random;

public class Game {
    private static final String VALID_USER_NUMBER = "\\d+";

    private int goal;
    private int counter;
    private boolean isRunning;
    private int difference = -1;

    private ActionListener gameActionListener;

    public void startNewGame() {
        this.isRunning = true;
        this.counter = 0;
        sendMessage("*** GAME START ***");
        sendMessage("Hello user, the goal of this game is to guess a number in range of 0 - 100 (inclusive).");
        this.goal = new Random().nextInt(101);
    }

    private void sendMessage(String message) {
        gameActionListener.performAction(message);
    }

    public void checkUserNumber(String userAnswer) {
        if(!isRunning) {
            sendMessage("Sorry but you have to start the game before you try to guess the number.");
            return;
        }
        int userAnswerInt;
        if(!(userAnswer.matches(VALID_USER_NUMBER))) {
            sendMessage("Sorry, the format is invalid. Try again. (valid input are digits)");
            return;
        } else {
            userAnswerInt = Integer.parseInt(userAnswer);
        }
        if(userAnswerInt < 0 || userAnswerInt > 100) {
            sendMessage("Sorry, the number " + userAnswerInt + " is too large. Try again in range 0 - 100 inclusive.");
            return;
        }
        this.counter++;
        difference = Math.abs(goal - userAnswerInt);
        sendMessage("Your guess: " + userAnswerInt);
        if (difference == 0) {
            sendMessage("Bullseye!");
            userWonTheGame();
        } else if(difference < 5) {
            sendMessage("Hot!!!");
        } else if (difference < 20) {
            sendMessage("Very warm.");
        } else if (difference < 50) {
            sendMessage("Warm.");
        } else if (difference < 70) {
            sendMessage("Cold.");
        } else if (difference < 95) {
            sendMessage("Very cold.");
        } else {
            sendMessage("Freezing!!!");
        }
    }

    private void userWonTheGame(){
        sendMessage("Congratulations! You have won the game. The number was: " + goal);
        sendMessage("It took you " + counter + " attempts to guess the number.");
        sendMessage("If you want to start a new game please click on the button \"Start game\"");
        endGame();
    }

    public void userEndsTheGame() {
        if(!isRunning) {
            sendMessage("You cannot end the game, the game is not running. You can start a new game.");
        } else {
            sendMessage("You decided to end the game. You have not guessed the number.");
            sendMessage("The number was: " + goal + ". You tried " + counter + " times to guess it.");
            endGame();
        }
    }

    private void endGame() {
        sendMessage("The game has ended.");
        sendMessage("*** GAME END ***");
        this.isRunning = false;
        this.counter = 0;
        this.goal = -1;
    }

    public void setGameActionListener(ActionListener gameActionListener) {
        this.gameActionListener = gameActionListener;
    }
}
