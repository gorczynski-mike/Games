package com.gorczynskimike.sudoku.simple;

public class SudokuState {

    private SudokuElement[][] sudokuElementsArray;
    private int xIndex;
    private int yIndex;
    private int guessedNumber;

    public SudokuState(SudokuElement[][] sudokuElementsArray, int xIndex, int yIndex, int guessedNumber) {
        this.sudokuElementsArray = SudokuArrayFactory.copySudokuBoard(sudokuElementsArray);
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.guessedNumber = guessedNumber;
    }

    public SudokuElement[][] getSudokuElementsArray() {
        return sudokuElementsArray;
    }

    public int getxIndex() {
        return xIndex;
    }

    public int getyIndex() {
        return yIndex;
    }

    public int getGuessedNumber() {
        return guessedNumber;
    }
}
