package com.gorczynskimike.sudoku.simple;

public class SudokuState {

    private SudokuElement[][] sudokuElementsArray;
    private int xIndex;
    private int yIndex;
    private int guessedNumber;

    public SudokuState(SudokuElement[][] sudokuElementsArray, int xIndex, int yIndex, int guessedNumber) {
        this.sudokuElementsArray = new SudokuElement[9][9];
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                this.sudokuElementsArray[i][j] = sudokuElementsArray[i][j].getCopy();
            }
        }
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
