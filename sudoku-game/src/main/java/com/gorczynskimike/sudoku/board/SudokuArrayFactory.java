package com.gorczynskimike.sudoku.board;

/**
 * Utility class that can create or copy arrays of SudokuElements
 */
public final class SudokuArrayFactory {

    /**
     * This utility class shouldn't be instantiated, thus one private constructor
     */
    private SudokuArrayFactory() {
        //do nothing
    }

    /**
     * Create new 9x9 array of SudokuElements
     * @return 9x9 array of not set SudokuElements
     */
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
     * Method that clones passed array of SudokuElements
     * @param original Array of SudokuElements that will be copied, must be a square.
     * @return Copy of array passed as an argument.
     */
    public static final SudokuElement[][] copySudokuArray(SudokuElement[][] original) {

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
