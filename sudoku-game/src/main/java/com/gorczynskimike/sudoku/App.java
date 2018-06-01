package com.gorczynskimike.sudoku;

import com.gorczynskimike.sudoku.simple.SimpleSudokuBoard;
import com.gorczynskimike.sudoku.userinterface.ConsoleUserInterface;
import com.gorczynskimike.sudoku.userinterface.UserChoiceHandler;
import com.gorczynskimike.sudoku.userinterface.UserInterface;

public class App {

    public static void main(String[] args) {

        UserInterface userInterface = new ConsoleUserInterface();
        UserChoiceHandler userChoiceHandler = new UserChoiceHandler();

        initialize();

        boolean keepPlaying = true;
        while (keepPlaying) {
            SimpleSudokuBoard simpleSudokuBoard = new SimpleSudokuBoard();
            boolean endThisGame = false;
            while (!endThisGame) {
                simpleSudokuBoard.printBoard();
                String userInput = userInterface.getUserInput();
                endThisGame = userChoiceHandler.handleUserInput(userInput, simpleSudokuBoard);
            }
            keepPlaying = userInterface.getNewGameDecision();
        }
    }

    private static void initialize() {
        for(int i=0; i<5; i++) {
            new SimpleSudokuBoard().solveSudoku(true);
        }
    }
}
