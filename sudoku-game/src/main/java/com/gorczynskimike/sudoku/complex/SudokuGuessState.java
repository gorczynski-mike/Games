package com.gorczynskimike.sudoku.complex;

public class SudokuGuessState {

    private final ComplexSudokuBoard complexSudokuBoardDeepCopy;
    private final int xIndexOfGuessesElement;
    private final int yIndexOfGuessesElement;
    private final int valueBeingGuessed;

    public SudokuGuessState(ComplexSudokuBoard complexSudokuBoard, int xIndexOfGuessesElement, int yIndexOfGuessesElement, int valueBeingGuessed) {
        this.complexSudokuBoardDeepCopy = complexSudokuBoard.getDeepCopy();
        this.xIndexOfGuessesElement = xIndexOfGuessesElement;
        this.yIndexOfGuessesElement = yIndexOfGuessesElement;
        this.valueBeingGuessed = valueBeingGuessed;
    }

    public ComplexSudokuBoard getComplexSudokuBoardDeepCopy() {
        return complexSudokuBoardDeepCopy;
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
