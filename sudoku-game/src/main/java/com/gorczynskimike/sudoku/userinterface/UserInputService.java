package com.gorczynskimike.sudoku.userinterface;

/**
 * Service that gets input from the user.
 */
public interface UserInputService {

    /**
     * Gets input from the user.
     * @return Command sent by the user.
     * @throws InterruptedException Exception might be thrown by GUI waiting for user input / action.
     */
    String getUserInput() throws InterruptedException;

    /**
     * Gets a decision from the user whether to start a new game or not.
     * @return "y" if user wants to start new game, "n" otherwise.
     * @throws InterruptedException Exception might be thrown by GUI waiting for user input / action.
     */
    String getNewGameDecision() throws InterruptedException;

}
