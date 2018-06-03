package com.gorczynskimike.sudoku;

import com.gorczynskimike.sudoku.simple.SimpleSudokuBoard;
import com.gorczynskimike.sudoku.swinggui.MainWindow;
import com.gorczynskimike.sudoku.userinterface.ConsoleUserInterface;
import com.gorczynskimike.sudoku.userinterface.UserChoiceHandler;
import com.gorczynskimike.sudoku.userinterface.UserInterface;

public class App {

    public static void main(String[] args) throws InterruptedException {

        UserInterface userInterface = new ConsoleUserInterface();
        UserChoiceHandler userChoiceHandler = new UserChoiceHandler();
        MainWindow mainWindow = new MainWindow();
        ((ConsoleUserInterface) userInterface).setMessageService(mainWindow);
        ((ConsoleUserInterface) userInterface).setUserInputService(mainWindow);
        userChoiceHandler.setMessageService(mainWindow);

        initialize();

        boolean keepPlaying = true;
        while (keepPlaying) {
            SimpleSudokuBoard simpleSudokuBoard = new SimpleSudokuBoard();
            simpleSudokuBoard.setMessageService(mainWindow);
            simpleSudokuBoard.setSudokuMessageService(text -> mainWindow.setSudoku(text));
            boolean endThisGame = false;
            while (!endThisGame) {
                String sudokuText = simpleSudokuBoard.printBoard();
                mainWindow.setSudoku(sudokuText);
                String userInput = userInterface.getUserInput();
                endThisGame = userChoiceHandler.handleUserInput(userInput, simpleSudokuBoard);
            }
            keepPlaying = userInterface.getNewGameDecision();
        }

        mainWindow.closeMainWindow();
    }

    private static void initialize() {
        for(int i=0; i<5; i++) {
            new SimpleSudokuBoard().solveSudoku(true);
        }
    }
}
