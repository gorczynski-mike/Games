package com.gorczynskimike.sudoku.simple;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class SudokuStack {

    private static Deque<SudokuState> sudokuStack = new LinkedList<>();

    public static void pushSudokuState(SudokuState sudokuState) {
        sudokuStack.offerFirst(sudokuState);
    }

    public static SudokuState popSudokuState() {
        return sudokuStack.pollFirst();
    }

    public static int getStackSize() {
        return sudokuStack.size();
    }

    public static void printStackSize() {
        System.out.println("Stack size: " + sudokuStack.size());
    }

    public static void clearStack() {
        sudokuStack.clear();
    }

}
