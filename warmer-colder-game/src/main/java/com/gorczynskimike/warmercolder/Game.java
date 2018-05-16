package com.gorczynskimike.warmercolder;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class Game {
    private static final String VALID_USER_NUMBER = "\\d+";

    private int goal;
    private int counter;
    private boolean isRunning;
    private int difference = -1;

    private StringBuilder messagesEngHistory = new StringBuilder();
    private StringBuilder messagesPlHistory = new StringBuilder();

    private ResourceBundle englishMessages = ResourceBundle.getBundle("gameMessages", new Locale("en", "US"));
    private ResourceBundle polishMessages = ResourceBundle.getBundle("gameMessages", new Locale("pl", "PL"));
    private ResourceBundle resourceBundle = englishMessages;

    private ActionListener gameActionListener;

    public void startNewGame() {
        messagesEngHistory.delete(0, messagesEngHistory.length());
        messagesPlHistory.delete(0, messagesPlHistory.length());
        this.isRunning = true;
        this.counter = 0;
        generateMessage("gameStart");
        generateMessage("greeting");
        this.goal = new Random().nextInt(101);
    }

    private void generateMessage(String key){
        sendMessage(resourceBundle.getString(key));
        messagesEngHistory.append(englishMessages.getString(key));
        messagesPlHistory.append(polishMessages.getString(key));
    }

    private void generateMessage(String key, Integer... ints){
        sendMessage(String.format(resourceBundle.getString(key), ints));
        messagesEngHistory.append(String.format(englishMessages.getString(key), ints));
        messagesPlHistory.append(String.format(polishMessages.getString(key), ints));
    }

    private void sendMessage(String message) {
        gameActionListener.performAction(message);
    }

    public void checkUserNumber(String userAnswer) {
        if(!isRunning) {
//            sendMessage("Sorry but you have to start the game before you try to guess the number.");
            generateMessage("startGameFirst");
            return;
        }
        int userAnswerInt;
        if(!(userAnswer.matches(VALID_USER_NUMBER))) {
//            sendMessage("Sorry, the format is invalid. Try again. (valid input are digits)");
            generateMessage("invalidFormat");
            return;
        } else {
            userAnswerInt = Integer.parseInt(userAnswer);
        }
        if(userAnswerInt < 0 || userAnswerInt > 100) {
//            sendMessage(String.format("Sorry, the number %d is too large. Try again in range 0 - 100 inclusive.", userAnswerInt));
            generateMessage("numberNotInRange", userAnswerInt);
            return;
        }
        this.counter++;
        difference = Math.abs(goal - userAnswerInt);
        generateMessage("yourGuess", userAnswerInt);
//        sendMessage(String.format("Your guess: %d", userAnswerInt));
        if (difference == 0) {
//            sendMessage("Bullseye!");
            generateMessage("bullseye");
            userWonTheGame();
        } else if(difference < 5) {
//            sendMessage("Hot!!!");
            generateMessage("hot");
        } else if (difference < 20) {
//            sendMessage("Very warm.");
            generateMessage("veryWarm");
        } else if (difference < 50) {
//            sendMessage("Warm.");
            generateMessage("warm");
        } else if (difference < 70) {
//            sendMessage("Cold.");
            generateMessage("cold");
        } else if (difference < 95) {
//            sendMessage("Very cold.");
            generateMessage("veryCold");
        } else {
//            sendMessage("Freezing!!!");
            generateMessage("freezing");
        }
    }

    private void userWonTheGame(){
        generateMessage("win1", goal);
        generateMessage("win2", counter);
        generateMessage("win3");
//        sendMessage(String.format("Congratulations! You have won the game. The number was: %d", goal));
//        sendMessage(String.format("It took you %d attempts to guess the number.", counter));
//        sendMessage("If you want to start a new game please click on the button \"Start game\"");
        endGame();
    }

    public void userEndsTheGame() {
        if(!isRunning) {
            generateMessage("cantEndGame");
//            sendMessage("You cannot end the game, the game is not running. You can start a new game.");
        } else {
            generateMessage("loss1");
            generateMessage("loss2", goal, counter);
//            sendMessage("You decided to end the game. You have not guessed the number.");
//            sendMessage(String.format("The number was: %d. You tried %d times to guess it.", goal, counter));
            endGame();
        }
    }

    private void endGame() {
        generateMessage("gameEnd1");
        generateMessage("gameEnd2");
//        sendMessage("The game has ended.");
//        sendMessage("*** GAME END ***");
        this.isRunning = false;
        this.counter = 0;
        this.goal = -1;
    }

    public void setGameActionListener(ActionListener gameActionListener) {
        this.gameActionListener = gameActionListener;
    }

    public void setLanguage(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
