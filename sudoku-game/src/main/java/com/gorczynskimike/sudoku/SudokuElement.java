package com.gorczynskimike.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuElement {

    int value = -1;
    int xIndex;
    int yIndex;
    List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        possibleValues.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        possibleValues.clear();
        possibleValues.add(value);
    }

    public void removePossibleValue(Integer value) {
        possibleValues.remove(value);
    }

    public List<Integer> getPossibleValues() {
        return new ArrayList<>(possibleValues);
    }

    @Override
    public String toString() {
        return "SudokuElement{" +
                "value=" + value +
                ", xIndex=" + xIndex +
                ", yIndex=" + yIndex +
                '}';
    }
}
