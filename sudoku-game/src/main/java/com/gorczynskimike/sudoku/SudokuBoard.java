package com.gorczynskimike.sudoku;

import java.util.StringJoiner;

public class SudokuBoard {

    private static final int BOARD_X_SIZE = 9;
    private static final int BOARD_Y_SIZE = 9;
    private final SudokuElement[][] sudokuElements = new SudokuElement[BOARD_X_SIZE][BOARD_Y_SIZE];

    {
        for(int i=0; i<BOARD_X_SIZE; i++) {
            for(int j=0; j<BOARD_Y_SIZE; j++){
                sudokuElements[i][j] = new SudokuElement(i,j);
            }
        }
    }

    public void printBoard() {
        System.out.println(getRowSeparator());
        for(int i=0 ; i<BOARD_Y_SIZE; i++){
            System.out.println(getRowString(i));
            System.out.println(getRowSeparator());
        }
    }

    public void setElementValue(int xIndex, int yIndex, int value){
        if(xIndex < 0 || xIndex >= BOARD_X_SIZE) {
            throw new IllegalArgumentException("X index out of bounds: " + xIndex);
        }
        if(yIndex < 0 || yIndex >= BOARD_Y_SIZE) {
            throw new IllegalArgumentException("Y index out of bounds: " + yIndex);
        }
        if(value < 1 || value > 9) {
            throw new IllegalArgumentException("Value out of bounds: " + value);
        }
        sudokuElements[xIndex][yIndex].setValue(value);
    }

    private String getRowString(int yIndex) {
        StringJoiner sj = new StringJoiner("|", "|", "|");
        for(int i=0; i<BOARD_X_SIZE; i++) {
            int currentValue = sudokuElements[i][yIndex].getValue();
            String currectValueString = currentValue == -1 ? " " : String.valueOf(currentValue);
            sj.add(currectValueString);
        }
        return sj.toString();
    }

    private String getRowSeparator() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<BOARD_X_SIZE; i++) {
            sb.append("--");
        }
        sb.append("-");
        return sb.toString();
    }



}
