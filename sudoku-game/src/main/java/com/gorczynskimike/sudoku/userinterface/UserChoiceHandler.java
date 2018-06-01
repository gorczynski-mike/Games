package com.gorczynskimike.sudoku.userinterface;

import com.gorczynskimike.sudoku.simple.SimpleSudokuBoard;
import com.gorczynskimike.sudoku.simple.SudokuGenerator;

public class UserChoiceHandler {

    private static final String ERROR_PATTERN = "error";
    private static final String SOLVE_SUDOKU_PATTERN = "sudoku";
    private static final String SET_ELEMENT_PATTERN = "\\d,\\d,\\d";
    private static final String UNSET_ELEMENT_PATTERN = "\\d,\\d,unset";
    private static final String GENERATE_ONE_RANDOM_NUMBER_PATTERN = "random";
    private static final String GENERATE_N_RANDOM_NUMBERS_PATTERN = "random,\\d+";
    private static final String GENERATE_N_NUMBERS_SOLVABLE_PATTERN = "solvable,\\d+";
    private static final String GENERATE_EASY_SUDOKU_PATTERN = "easy";
    private static final String GENERATE_MEDIUM_SUDOKU_PATTERN = "medium";
    private static final String GENERATE_HARD_SUDOKU_PATTERN = "hard";
    private static final String CLEAR_BOARD_PATTERN = "clear";

    public boolean handleUserInput(String userInput, SimpleSudokuBoard simpleSudokuBoard) {

        userInput = userInput.toLowerCase();
        boolean finishGame = false;

        if (userInput.equalsIgnoreCase(ERROR_PATTERN)) {
                System.out.println("Invalid format, try again.");

        } else if (userInput.equalsIgnoreCase(SOLVE_SUDOKU_PATTERN)) {
            finishGame = true;
            simpleSudokuBoard.solveSudoku();

        } else if (userInput.matches(UNSET_ELEMENT_PATTERN)) {
            String[] inputParts = userInput.split(",");
            try {
                //minus 1 to coordinates so the user can select row/column in range 1-9, not 0-8
                simpleSudokuBoard.unsetElement(
                        Integer.parseInt(inputParts[1])-1,
                        Integer.parseInt(inputParts[0])-1
                );
            } catch (IllegalArgumentException e) {
                System.out.println("Unsetting element failed: ");
                System.out.println(e.getMessage());
            }

        } else if (userInput.matches(GENERATE_ONE_RANDOM_NUMBER_PATTERN)) {
//            simpleSudokuBoard.generateRandomNumbers(1);
            SudokuGenerator.generateOneRandomNumber(simpleSudokuBoard);

        } else if (userInput.matches(CLEAR_BOARD_PATTERN)) {
            simpleSudokuBoard.clearTheBoard();

        } else if (userInput.matches(GENERATE_N_RANDOM_NUMBERS_PATTERN)) {
            String[] inputParts = userInput.split(",");
            int howManyToGenerate = Integer.parseInt(inputParts[1]);
//            simpleSudokuBoard.generateRandomNumbers(howManyToGenerate);
            SudokuGenerator.generateRandomNumbers(howManyToGenerate, simpleSudokuBoard);

        } else if (userInput.matches(GENERATE_N_NUMBERS_SOLVABLE_PATTERN)) {
            String[] inputParts = userInput.split(",");
            int howManyToGenerate = Integer.parseInt(inputParts[1]);
            SudokuGenerator.generateRandomNumbersSolvable(howManyToGenerate, simpleSudokuBoard);

        } else if (userInput.matches(GENERATE_EASY_SUDOKU_PATTERN)) {
            SudokuGenerator.generateEasySudoku(simpleSudokuBoard);

        } else if (userInput.matches(GENERATE_MEDIUM_SUDOKU_PATTERN)) {
            SudokuGenerator.generateMediumSudoku(simpleSudokuBoard);

        } else if (userInput.matches(GENERATE_HARD_SUDOKU_PATTERN)) {
            SudokuGenerator.generateHardSudoku(simpleSudokuBoard);

        } else if(userInput.matches(SET_ELEMENT_PATTERN)) {
            String[] inputParts = userInput.split(",");
            try {
                //minus 1 to coordinates, so the user can select row/column in range 1-9, not 0-8
                simpleSudokuBoard.setElement(
                        Integer.parseInt(inputParts[1])-1,
                        Integer.parseInt(inputParts[0])-1,
                        Integer.parseInt(inputParts[2])
                );
            } catch (IllegalArgumentException e) {
                System.out.println("Setting element failed: ");
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("Sorry, choice not recognized, try again.");
        }

        return finishGame;
    }
}