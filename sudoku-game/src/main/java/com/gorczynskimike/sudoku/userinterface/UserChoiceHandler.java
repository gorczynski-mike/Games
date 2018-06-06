package com.gorczynskimike.sudoku.userinterface;

import com.gorczynskimike.sudoku.board.SimpleSudokuBoard;
import com.gorczynskimike.sudoku.board.SudokuGenerator;

/**
 * Depending on the user input passed it invokes related methods on given sudoku board or other utility classes.
 */
public class UserChoiceHandler {

    private static final String ERROR_PATTERN = "error";
    private static final String SOLVE_SUDOKU_PATTERN = "sudoku";
    private static final String SET_ELEMENT_PATTERN = "\\d,\\d,\\d";
    private static final String UNSET_ELEMENT_PATTERN = "\\d,\\d,unset";
    private static final String GENERATE_ONE_RANDOM_NUMBER_PATTERN = "random";
    private static final String GENERATE_N_RANDOM_NUMBERS_PATTERN = "random,\\d+";
    private static final String GENERATE_N_NUMBERS_SOLVABLE_PATTERN = "solvable,\\d+";
    private static final String REMOVE_N_NUMBERS = "remove,\\d+";
    private static final String GENERATE_EASY_SUDOKU_PATTERN = "easy";
    private static final String GENERATE_MEDIUM_SUDOKU_PATTERN = "medium";
    private static final String GENERATE_HARD_SUDOKU_PATTERN = "hard";
    private static final String CLEAR_BOARD_PATTERN = "clear";

    private MessageService messageService = (text) -> {
        System.out.println(text);
    };

    /**
     * It changes message service used by this class to send messages. Default message service is a standard sysout.
     * @param messageService Message service that will be used to send messages.
     */
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Main method used to handle user input. Depending on the input it invokes corresponding methods.
     * @param userInput Command sent by the user.
     * @param simpleSudokuBoard The board that will be modified by the commands.
     * @return True if game has ended, False otherwise.
     */
    public boolean handleUserInput(String userInput, SimpleSudokuBoard simpleSudokuBoard) {

        userInput = userInput.toLowerCase();
        boolean finishGame = false;

        if (userInput.equalsIgnoreCase(ERROR_PATTERN)) {
                messageService.sendMessage("Invalid format, try again.");

        } else if (userInput.equalsIgnoreCase(SOLVE_SUDOKU_PATTERN)) {
            messageService.sendMessage("Trying to solve sudoku");
            finishGame = true;
            simpleSudokuBoard.solveSudoku();

        } else if (userInput.matches(UNSET_ELEMENT_PATTERN)) {
            String[] inputParts = userInput.split(",");
            messageService.sendMessage("Trying to clear element.");
            try {
                //minus 1 to coordinates so the user can select row/column in range 1-9, not 0-8
                simpleSudokuBoard.unsetElement(
                        Integer.parseInt(inputParts[1])-1,
                        Integer.parseInt(inputParts[0])-1
                );
            } catch (IllegalArgumentException e) {
                messageService.sendMessage("Clearing element failed: ");
                messageService.sendMessage(e.getMessage());
                return finishGame;
            }
            messageService.sendMessage("Element cleared successfully.");

        } else if (userInput.matches(GENERATE_ONE_RANDOM_NUMBER_PATTERN)) {
            SudokuGenerator.generateOneRandomNumber(simpleSudokuBoard);

        } else if (userInput.matches(CLEAR_BOARD_PATTERN)) {
            messageService.sendMessage("Clearing sudoku board.");
            simpleSudokuBoard.clearTheBoard();
            messageService.sendMessage("Sudoku board cleared.");

        } else if (userInput.matches(GENERATE_N_RANDOM_NUMBERS_PATTERN)) {
            String[] inputParts = userInput.split(",");
            int howManyToGenerate = Integer.parseInt(inputParts[1]);
            messageService.sendMessage("Trying to generate " + howManyToGenerate + " random numbers.");
            try {
                SudokuGenerator.generateRandomNumbers(howManyToGenerate, simpleSudokuBoard);
            } catch (IllegalArgumentException | IllegalStateException e) {
                messageService.sendMessage("Impossible to generate numbers: ");
                messageService.sendMessage(e.getMessage());
                return finishGame;
            }
            messageService.sendMessage("Numbers were generated successfully.");

        } else if (userInput.matches(GENERATE_N_NUMBERS_SOLVABLE_PATTERN)) {
            String[] inputParts = userInput.split(",");
            int howManyToGenerate = Integer.parseInt(inputParts[1]);
            messageService.sendMessage("Trying to generate " + howManyToGenerate + " random numbers (solvable).");
            try {
                SudokuGenerator.generateRandomNumbersSolvable(howManyToGenerate, simpleSudokuBoard);
            } catch (IllegalStateException | IllegalArgumentException e) {
                messageService.sendMessage("Impossible to generate numbers: ");
                messageService.sendMessage(e.getMessage());
                return finishGame;
            }
            messageService.sendMessage("Numbers were generated successfully.");

        } else if (userInput.matches(REMOVE_N_NUMBERS)) {
            String[] inputParts = userInput.split(",");
            int howManyToRemove = Integer.parseInt(inputParts[1]);
            messageService.sendMessage("Trying to remove " + howManyToRemove + " random numbers.");
            try {
                SudokuGenerator.removeRandomNumbers(howManyToRemove, simpleSudokuBoard);
            } catch (IllegalStateException | IllegalArgumentException e) {
                messageService.sendMessage("Impossible to remove numbers: ");
                messageService.sendMessage(e.getMessage());
                return finishGame;
            }
            messageService.sendMessage("Numbers were removed successfully.");

        } else if (userInput.matches(GENERATE_EASY_SUDOKU_PATTERN)) {
            messageService.sendMessage("Generating easy sudoku board");
            SudokuGenerator.generateEasySudoku(simpleSudokuBoard);
            messageService.sendMessage("Easy sudoku board generated");

        } else if (userInput.matches(GENERATE_MEDIUM_SUDOKU_PATTERN)) {
            messageService.sendMessage("Generating medium sudoku board");
            SudokuGenerator.generateMediumSudoku(simpleSudokuBoard);
            messageService.sendMessage("Medium sudoku board generated");

        } else if (userInput.matches(GENERATE_HARD_SUDOKU_PATTERN)) {
            messageService.sendMessage("Generating hard sudoku board");
            SudokuGenerator.generateHardSudoku(simpleSudokuBoard);
            messageService.sendMessage("Hard sudoku board generated");

        } else if(userInput.matches(SET_ELEMENT_PATTERN)) {
            String[] inputParts = userInput.split(",");
            messageService.sendMessage("Trying to set element.");
            try {
                //minus 1 to coordinates, so the user can select row/column in range 1-9, not 0-8
                simpleSudokuBoard.setElement(
                        Integer.parseInt(inputParts[1])-1,
                        Integer.parseInt(inputParts[0])-1,
                        Integer.parseInt(inputParts[2])
                );
            } catch (IllegalArgumentException | IllegalStateException e) {
                messageService.sendMessage("Setting element failed: ");
                messageService.sendMessage(e.getMessage());
                return finishGame;
            }
            messageService.sendMessage("Element set successfully.");

        } else {
            messageService.sendMessage("Sorry, choice not recognized, try again.");
        }

        return finishGame;
    }
}