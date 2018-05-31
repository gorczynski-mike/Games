package com.gorczynskimike.sudoku.simple;

public final class SudokuArrayFactory {

    private SudokuArrayFactory() {
        //do nothing
    }

    public static final SudokuElement[][] getEmptySudokuArray() {
        SudokuElement[][] emptyArray = new SudokuElement[9][9];
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                emptyArray[i][j] = new SudokuElement();
            }
        }
        return emptyArray;
    }

    /**
     * Method assumes that original board is a square, eg. 9x9
     * @param original
     * @return
     */
    public static final SudokuElement[][] copySudokuBoard(SudokuElement[][] original) {

        //check if original is a square
        int originalSide = original.length;
        for(int i=0; i<original.length; i++) {
            if(original[i].length != originalSide) {
                throw new IllegalArgumentException("Array passed to this method must be a square.");
            }
        }

        //create a copy
        SudokuElement[][] copy = new SudokuElement[original.length][original.length];
        for(int i=0; i<original.length; i++) {
            for(int j=0; j<original.length; j++) {
                copy[i][j] = original[i][j].getCopy();
            }
        }

        return copy;
    }

}
