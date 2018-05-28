package com.gorczynskimike.sudoku;

import com.gorczynskimike.sudoku.simple.SimpleSudokuBoard;

public class App {

    public static void main(String[] args) {
        UserInterface userInterface = new ConsoleUserInterface();
        boolean keepPlaying = true;

        while (keepPlaying) {
            SimpleSudokuBoard simpleSudokuBoard = new SimpleSudokuBoard();
            simpleSudokuBoard.printBoard();
            boolean finishLoopFlag = false;
            while (!finishLoopFlag) {
                String userInput = userInterface.getUserInput();
                if (userInput.equalsIgnoreCase("error")) {
                    System.out.println("Invalid format, try again.");
                    continue;
                }
                if (userInput.equalsIgnoreCase("sudoku")) {
                    finishLoopFlag = true;
                    simpleSudokuBoard.solveSudoku();
                } else if (userInput.contains("unset")) {
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
                    } finally {
                        simpleSudokuBoard.printBoard();
                    }
                } else if (userInput.matches("random")) {
                    simpleSudokuBoard.generateRandomNumbers(1);
                } else if (userInput.matches("random,\\d+")) {
                    String[] inputParts = userInput.split(",");
                    int howManyToGuess = Integer.parseInt(inputParts[1]);
                    simpleSudokuBoard.generateRandomNumbers(howManyToGuess);
                } else {
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
                    } finally {
                        simpleSudokuBoard.printBoard();
                    }
                }
            }
            keepPlaying = userInterface.getNewGameDecision();
        }
    }
}
