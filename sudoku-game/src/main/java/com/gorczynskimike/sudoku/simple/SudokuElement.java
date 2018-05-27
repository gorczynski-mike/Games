package com.gorczynskimike.sudoku.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuElement {

    private int value = 0;
    private List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement() {
        possibleValues.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    public SudokuElement getCopy() {
        SudokuElement copy = new SudokuElement();
        copy.value = this.value;
        copy.getPossibleValues().retainAll(this.possibleValues);
        return copy;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if(!possibleValues.contains(value)) {
            throw new IllegalArgumentException("Can't set value, value is not possible here.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
