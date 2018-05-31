package com.gorczynskimike.sudoku.simple;

import java.util.*;

public class SudokuElement {

    private int value = 0;
    private final List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement() {
        possibleValues.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    public SudokuElement getCopy() {
        SudokuElement copy = new SudokuElement();
        copy.value = this.value;
        copy.getPossibleValuesCopy().retainAll(this.possibleValues);
        return copy;
    }

    public List<Integer> getPossibleValuesCopy() {
        return new ArrayList<>(possibleValues);
    }

    public boolean removePossibleValue(Integer value) {
        return this.possibleValues.remove(value);
    }

    public boolean addPossibleValue(Integer value) {
        if(this.possibleValues.contains(value)) {
            return false;
        } else {
            return this.possibleValues.add(value);
        }
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        if(!possibleValues.contains(value)) {
            throw new IllegalArgumentException("Can't set value, value is not possible here.");
        }
        this.value = value;
    }

    public void clearValue(){
        this.value = 0;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
