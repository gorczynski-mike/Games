package com.gorczynskimike.sudoku.simple;

import java.util.Arrays;

public class SudokuState {

    private SudokuElement[][] board;
    private int xIndex;
    private int yIndex;
    private int guessedNumber;

    public SudokuState(SudokuElement[][] board, int xIndex, int yIndex, int guessedNumber) {
        this.board = new SudokuElement[9][9];
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                this.board[i][j] = board[i][j].getCopy();
            }
        }
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.guessedNumber = guessedNumber;
    }

    public SudokuElement[][] getBoard() {
        return board;
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
