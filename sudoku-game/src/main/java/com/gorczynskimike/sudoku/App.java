package com.gorczynskimike.sudoku;

import com.gorczynskimike.sudoku.simple.SimpleSudokuBoard;

public class App {

    public static void main(String[] args) {
        UserInterface userInterface = new ConsoleUserInterface();

        SimpleSudokuBoard simpleSudokuBoard = new SimpleSudokuBoard();
        simpleSudokuBoard.printBoard();


        boolean finishLoopFlag = false;
        while(!finishLoopFlag) {
            String userInput = userInterface.getUserInput();
            if(userInput.equalsIgnoreCase("error")) {
                System.out.println("Invalid format, try again.");
                continue;
            }
            if(userInput.equalsIgnoreCase("sudoku")) {
                finishLoopFlag = true;
                simpleSudokuBoard.solveSudoku();
            } else
//                if (userInput.contains("unset")) {
//                String[] inputParts = userInput.split(",");
//                try {
//                    simpleSudokuBoard.unsetElement(
//                            Integer.parseInt(inputParts[0]),
//                            Integer.parseInt(inputParts[1])
//                    );
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Sorry, one of the values is out of bounds: ");
//                    System.out.println(e.getMessage());
//                }
//            }
//            else
                {
                String[] inputParts = userInput.split(",");
                try {
                    simpleSudokuBoard.setElement(
                            Integer.parseInt(inputParts[0]),
                            Integer.parseInt(inputParts[1]),
                            Integer.parseInt(inputParts[2])
                    );
                    simpleSudokuBoard.printBoard();
                } catch (IllegalArgumentException e) {
                    System.out.println("Sorry, one of the values is out of bounds: ");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
