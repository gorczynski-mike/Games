package com.gorczynskimike.sudoku.board;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Stack of sudoku states.
 */
public class SudokuStack {

    private Deque<SudokuState> sudokuStack = new LinkedList<>();

    public SudokuStack() {
        //do nothing
    }

    public void pushSudokuState(SudokuState sudokuState) {
        this.sudokuStack.offerFirst(sudokuState);
    }

    public SudokuState popSudokuState() {
        return this.sudokuStack.pollFirst();
    }

    public int getStackSize() {
        return this.sudokuStack.size();
    }

    public void printStackSize() {
        System.out.println("Stack size: " + this.sudokuStack.size());
    }

    public void clearStack() {
        this.sudokuStack.clear();
    }

}
