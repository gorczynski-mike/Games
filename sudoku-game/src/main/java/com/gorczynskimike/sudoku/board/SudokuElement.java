package com.gorczynskimike.sudoku.board;

import java.util.*;

/**
 * Class representing a single element on the sudoku board.
 */
public class SudokuElement {

    private int value = 0;
    private final List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement() {
        possibleValues.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    /**
     * Creates a copy of this element.
     * @return New SudokuElement with the same value and possible values.
     */
    public SudokuElement getCopy() {
        SudokuElement copy = new SudokuElement();
        copy.value = this.value;
        copy.possibleValues.retainAll(this.possibleValues);
        return copy;
    }

    /**
     * Returns a copy of the list of possible values.
     * @return A copy of possible values list.
     */
    public List<Integer> getPossibleValuesCopy() {
        return new ArrayList<>(possibleValues);
    }

    /**
     * Removes possible value from this element's list.
     * @param value Value to be removed.
     * @return True if operation was successful, False otherwise.
     */
    public boolean removePossibleValue(Integer value) {
        return this.possibleValues.remove(value);
    }

    /**
     * It adds a value to possible values list of this element. It doesn't allow duplicates.
     * @param value Value to be added.
     * @return True if value was added, False otherwise.
     */
    public boolean addPossibleValue(Integer value) {
        if(this.possibleValues.contains(value)) {
            return false;
        } else {
            return this.possibleValues.add(value);
        }
    }

    /**
     * It adds a collection of possible values to this element's possible values list. Only 1-9 values are allowed.
     * It doesn't allow duplicates.
     * @param inputValues
     */
    public void addPossibleValues(Collection<Integer> inputValues) {
        List<Integer> validValues = new ArrayList<>(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9}));
        if(!validValues.containsAll(inputValues)) {
            throw new IllegalArgumentException("Illegal input values. Valid values are integers in range 1-9");
        }
        for(Integer value : inputValues) {
            addPossibleValue(value);
        }
    }

    /**
     * Get value of this element.
     * @return Value of this element.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * It sets value for this element.
     * @param value New value for this element.
     * @throws IllegalArgumentException if the value is not possible for this element, exception is thrown.
     */
    public void setValue(int value) {
        if(!possibleValues.contains(value)) {
            throw new IllegalArgumentException("Can't set value, value is not possible here (already present in row/column/section). Value: " + value +
            " Number of possible values: " + possibleValues.size());
        }
        this.value = value;
    }

    /**
     * It clears the value held by this element. The possible values are not modified.
     */
    public void clearValue(){
        this.value = 0;
    }

    @Override
    public String toString() {
        return this.value == 0 ? " " : String.valueOf(value);
    }
}
