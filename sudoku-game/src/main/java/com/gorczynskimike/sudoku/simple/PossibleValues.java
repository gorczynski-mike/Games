package com.gorczynskimike.sudoku.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PossibleValues {

    private List<Integer> possibleValues = new ArrayList<>();

    public PossibleValues() {
        possibleValues.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }
}
