package com.gorczynskimike.sudoku;

import com.gorczynskimike.sudoku.simple.SimpleSudokuBoard;
import com.gorczynskimike.sudoku.swinggui.MainWindow;
import com.gorczynskimike.sudoku.userinterface.ConsoleUserInterface;
import com.gorczynskimike.sudoku.userinterface.UserChoiceHandler;

public class App {

    public static void main(String[] args) throws InterruptedException {

        ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface();
        UserChoiceHandler userChoiceHandler = new UserChoiceHandler();
        MainWindow mainWindow = new MainWindow();
        consoleUserInterface.setMessageService(mainWindow);
        consoleUserInterface.setUserInputService(mainWindow);
        userChoiceHandler.setMessageService(mainWindow);

        initialize();

        boolean keepPlaying = true;
        while (keepPlaying) {
            SimpleSudokuBoard simpleSudokuBoard = new SimpleSudokuBoard();
            simpleSudokuBoard.setMessageService(mainWindow);
            simpleSudokuBoard.setSudokuMessageService(text -> mainWindow.updateSudoku(text));
            boolean endThisGame = false;
            while (!endThisGame) {
                String sudokuText = simpleSudokuBoard.printBoard();
                mainWindow.updateSudoku(sudokuText);
                String userInput = consoleUserInterface.getUserInput();
                endThisGame = userChoiceHandler.handleUserInput(userInput, simpleSudokuBoard);
            }
            keepPlaying = consoleUserInterface.getNewGameDecision();
        }

        mainWindow.closeMainWindow();
    }

    private static void initialize() {
        for(int i=0; i<5; i++) {
            new SimpleSudokuBoard().solveSudoku(true);
        }
    }
}
