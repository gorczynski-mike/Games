package com.gorczynskimike.sudoku.complex;

import java.util.LinkedList;
import java.util.Queue;

public class SudokuStack {

    private SudokuStack() {
        //do nothing
    }

    private static final Queue<SudokuGuessState> sudokuStack = new LinkedList<>();

    public static void pushSudokuState(SudokuGuessState sudokuGuessState) {
        sudokuStack.offer(sudokuGuessState);
    }

    public static SudokuGuessState popSudokuState() {
        return sudokuStack.poll();
    }

}
