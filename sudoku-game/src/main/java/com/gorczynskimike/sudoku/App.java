package com.gorczynskimike.sudoku;

public class App {

    public static void main(String[] args) {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.setElementValue(2,2,7);
        sudokuBoard.setElementValue(1,5,2);
        sudokuBoard.setElementValue(6,7,3);
        sudokuBoard.printBoard();
    }

}
