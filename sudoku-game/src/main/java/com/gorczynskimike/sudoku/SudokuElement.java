package com.gorczynskimike.sudoku;

import java.util.*;

public class SudokuElement {

    private int value = -1;
    private boolean valueSet = false;
    private int xIndex;
    private int yIndex;
    private Set<Integer> possibleValues = new HashSet<>();

    public SudokuElement(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.possibleValues.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if(!this.possibleValues.contains(value)) {
            throw new IllegalStateException("Invalid value: the value is either out of bounds or already taken by " +
                    "an element in the same row, column or section. Value: " + value);
        }
        this.value = value;
        valueSet = true;
    }

    public void unset(Collection<Integer> possibleValues) {
        this.value = -1;
        this.possibleValues.clear();
        this.possibleValues.addAll(possibleValues);
        valueSet = false;
    }

    public boolean isValueSet() {
        return valueSet;
    }

    public int getxIndex() {
        return xIndex;
    }

    public int getyIndex() {
        return yIndex;
    }

    public void removePossibleValue(Integer value) {
        possibleValues.remove(value);
    }

    public void addPossibleValue(Integer value) {
        this.possibleValues.add(value);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuElement that = (SudokuElement) o;
        return xIndex == that.xIndex &&
                yIndex == that.yIndex;
    }

    @Override
    public int hashCode() {

        return Objects.hash(xIndex, yIndex);
    }
}
