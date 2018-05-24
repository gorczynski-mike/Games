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
    private String playerName = "";

    private StringBuilder messagesEngHistory = new StringBuilder();
    private StringBuilder messagesPlHistory = new StringBuilder();

    private ResourceBundle englishMessages = ResourceBundle.getBundle("gameMessages", new Locale("en", "US"));
    private ResourceBundle polishMessages = ResourceBundle.getBundle("gameMessages", new Locale("pl", "PL"));
    private ResourceBundle resourceBundle = englishMessages;

    private ActionListener sendMessageListener;
    private ActionListener playerChangeListener;
    private ActionListener gameWonListener;

    public void setGameWonListener(ActionListener gameWonListener) {
        this.gameWonListener = gameWonListener;
    }

    public void setPlayer(String playerName) {
        this.playerName = playerName;
        playerChangeListener.performAction(playerName);
    }

    public void setPlayerChangeListener(ActionListener playerChangeListener) {
        this.playerChangeListener = playerChangeListener;
    }

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
        messagesEngHistory.append(englishMessages.getString(key) + System.lineSeparator());
        messagesPlHistory.append(polishMessages.getString(key) + System.lineSeparator());
    }

    private void generateMessage(String key, Integer... ints){
        sendMessage(String.format(resourceBundle.getString(key), ints));
        messagesEngHistory.append(String.format(englishMessages.getString(key), ints) + System.lineSeparator());
        messagesPlHistory.append(String.format(polishMessages.getString(key), ints) + System.lineSeparator());
    }

    private void sendMessage(String message) {
        sendMessageListener.performAction(message);
    }

    public void checkUserNumber(String userAnswer) {
        if(!isRunning) {
            generateMessage("startGameFirst");
            return;
        }
        int userAnswerInt;
        if(!(userAnswer.matches(VALID_USER_NUMBER))) {
            generateMessage("invalidFormat");
            return;
        } else {
            userAnswerInt = Integer.parseInt(userAnswer);
        }
        if(userAnswerInt < 0 || userAnswerInt > 100) {
            generateMessage("numberNotInRange", userAnswerInt);
            return;
        }
        this.counter++;
        difference = Math.abs(goal - userAnswerInt);
        generateMessage("yourGuess", userAnswerInt);
        if (difference == 0) {
            generateMessage("bullseye");
            userWonTheGame();
        } else if(difference < 5) {
            generateMessage("hot");
        } else if (difference < 20) {
            generateMessage("veryWarm");
        } else if (difference < 50) {
            generateMessage("warm");
        } else if (difference < 70) {
            generateMessage("cold");
        } else if (difference < 95) {
            generateMessage("veryCold");
        } else {
            generateMessage("freezing");
        }
    }

    private void userWonTheGame(){
        generateMessage("win1", goal);
        generateMessage("win2", counter);
        generateMessage("win3");
        gameWonListener.performAction(this.playerName + "&&" + this.counter);
        endGame();
    }

    public void userEndsTheGame() {
        if(!isRunning) {
            generateMessage("cantEndGame");
        } else {
            generateMessage("loss1");
            generateMessage("loss2", goal, counter);
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

    public void setSendMessageListener(ActionListener sendMessageListener) {
        this.sendMessageListener = sendMessageListener;
    }

    public void setLanguage(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String getHistory(String language) {
        switch(language.toUpperCase()){
            case "PL": return messagesPlHistory.toString();
            case "ENG": return messagesEngHistory.toString();
            default: return "Language not found";
        }
    }
}
