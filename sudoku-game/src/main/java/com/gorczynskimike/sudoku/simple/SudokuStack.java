package com.gorczynskimike.sudoku.simple;

import java.util.LinkedList;
import java.util.Queue;

public class SudokuStack {

    private static Queue<SudokuState> sudokuStack = new LinkedList<>();

    public static void pushSudokuState(SudokuState sudokuState) {
        sudokuStack.offer(sudokuState);
    }

    public static SudokuState popSudokuState() {
        return sudokuStack.poll();
    }

    public static int getStackSize() {
        return sudokuStack.size();
    }

    public static void printStackSize() {
        System.out.println("Stack size: " + sudokuStack.size());
    }

}
