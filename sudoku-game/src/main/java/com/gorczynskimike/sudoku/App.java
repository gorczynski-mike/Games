package com.gorczynskimike.sudoku;

import com.gorczynskimike.sudoku.board.SimpleSudokuBoard;
import com.gorczynskimike.sudoku.swinggui.MainWindow;
import com.gorczynskimike.sudoku.userinterface.UserInputService;
import com.gorczynskimike.sudoku.userinterface.UserInputValidator;
import com.gorczynskimike.sudoku.userinterface.UserChoiceHandler;

public class App {

    public static void main(String[] args) throws InterruptedException {

        UserInputValidator userInputValidator = new UserInputValidator();
        UserChoiceHandler userChoiceHandler = new UserChoiceHandler();
        MainWindow mainWindow = new MainWindow();

        UserInputService userInputService = mainWindow;
        userInputValidator.setMessageService(mainWindow);
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
                String userInput = userInputService.getUserInput();
                userInput = userInputValidator.validateUserInput(userInput);
                endThisGame = userChoiceHandler.handleUserInput(userInput, simpleSudokuBoard);
            }
            mainWindow.sendMessage("Do you want to start new game? Y - yes, N - exit application");
            String newGameDecision = userInputService.getNewGameDecision();
            while(!userInputValidator.validateNewGameDecision(newGameDecision)) {
                newGameDecision = userInputService.getNewGameDecision();
            }
            keepPlaying = newGameDecision.matches("[yY]");
        }

        mainWindow.closeMainWindow();
    }

    /**
     * It creates a couple of empty boards and then solves them. It makes solving boards in the future quicker.
     */
    private static void initialize() {
        for(int i=0; i<5; i++) {
            new SimpleSudokuBoard().solveSudoku(true);
        }
    }
}
