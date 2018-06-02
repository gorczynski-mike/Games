package com.gorczynskimike.sudoku.userinterface;

public interface UserInterface {

    String getUserInput();

    boolean getNewGameDecision() throws InterruptedException;

    void printInstructions();

}
