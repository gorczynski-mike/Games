package com.gorczynskimike.sudoku;

public class App {

    public static void main(String[] args) {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.setElementValue(2,2,7);
        sudokuBoard.setElementValue(1,5,2);
        sudokuBoard.setElementValue(6,7,3);
        sudokuBoard.printBoard();

        UserInterface userInterface = new ConsoleUserInterface();
        boolean finishLoopFlag = false;
        while(!finishLoopFlag) {
            String userInput = userInterface.getUserInput();
            if(userInput.equalsIgnoreCase("error")) {
                System.out.println("Invalid format, try again.");
                continue;
            }
            if(userInput.equalsIgnoreCase("sudoku")) {
                finishLoopFlag = true;
            } else {
                String[] inputParts = userInput.split(",");
                try {
                    sudokuBoard.setElementValue(
                            Integer.parseInt(inputParts[0]),
                            Integer.parseInt(inputParts[1]),
                            Integer.parseInt(inputParts[2])
                    );
                    sudokuBoard.printBoard();
                } catch (IllegalArgumentException e) {
                    System.out.println("Sorry, one of the values is out of bounds: ");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
