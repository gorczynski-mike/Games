package com.gorczynskimike.sudoku.simple;

public final class SudokuArrayFactory {

    private SudokuArrayFactory() {
        //do nothing
    }

    public static SudokuElement[][] getEmptySudokuArray() {
        SudokuElement[][] emptyArray = new SudokuElement[9][9];
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                emptyArray[i][j] = new SudokuElement();
            }
        }
        return emptyArray;
    }

}
