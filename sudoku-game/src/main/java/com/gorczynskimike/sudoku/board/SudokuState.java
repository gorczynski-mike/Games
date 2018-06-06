package com.gorczynskimike.sudoku.board;

public class SudokuState {

    private final SudokuElement[][] sudokuElementsArray;
    private final int xIndex;
    private final int yIndex;
    private final int guessedNumber;

    public SudokuState(SudokuElement[][] sudokuElementsArray, int xIndex, int yIndex, int guessedNumber) {
        this.sudokuElementsArray = SudokuArrayFactory.copySudokuArray(sudokuElementsArray);
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.guessedNumber = guessedNumber;
    }

    public final SudokuElement[][] getSudokuElementsArray() {
        return this.sudokuElementsArray;
    }

    public final int getXIndex() {
        return this.xIndex;
    }

    public final int getYIndex() {
        return this.yIndex;
    }

    public final int getGuessedNumber() {
        return this.guessedNumber;
    }
}
