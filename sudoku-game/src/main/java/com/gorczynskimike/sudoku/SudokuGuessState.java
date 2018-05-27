package com.gorczynskimike.sudoku;

public class SudokuGuessState {

    private final SudokuBoard sudokuBoardDeepCopy;
    private final int xIndexOfGuessesElement;
    private final int yIndexOfGuessesElement;
    private final int valueBeingGuessed;

    public SudokuGuessState(SudokuBoard sudokuBoard, int xIndexOfGuessesElement, int yIndexOfGuessesElement, int valueBeingGuessed) {
        this.sudokuBoardDeepCopy = sudokuBoard.getDeepCopy();
        this.xIndexOfGuessesElement = xIndexOfGuessesElement;
        this.yIndexOfGuessesElement = yIndexOfGuessesElement;
        this.valueBeingGuessed = valueBeingGuessed;
    }

    public SudokuBoard getSudokuBoardDeepCopy() {
        return sudokuBoardDeepCopy;
    }

    public int getxIndexOfGuessesElement() {
        return xIndexOfGuessesElement;
    }

    public int getyIndexOfGuessesElement() {
        return yIndexOfGuessesElement;
    }

    public int getValueBeingGuessed() {
        return valueBeingGuessed;
    }
}
