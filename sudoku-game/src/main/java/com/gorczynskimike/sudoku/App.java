package com.gorczynskimike.sudoku;

import com.gorczynskimike.sudoku.simple.SimpleSudokuBoard;
import com.gorczynskimike.sudoku.userinterface.ConsoleUserInterface;
import com.gorczynskimike.sudoku.userinterface.UserInterface;

public class App {

    public static void main(String[] args) {

        UserInterface userInterface = new ConsoleUserInterface();
        UserChoiceHandler userChoiceHandler = new UserChoiceHandler();

        boolean keepPlaying = true;
        while (keepPlaying) {
            SimpleSudokuBoard simpleSudokuBoard = new SimpleSudokuBoard();
            simpleSudokuBoard.printBoard();
            boolean endThisGame = false;
            while (!endThisGame) {
                String userInput = userInterface.getUserInput();
                endThisGame = userChoiceHandler.handleUserInput(userInput, simpleSudokuBoard);
            }
            keepPlaying = userInterface.getNewGameDecision();
        }
    }
}
