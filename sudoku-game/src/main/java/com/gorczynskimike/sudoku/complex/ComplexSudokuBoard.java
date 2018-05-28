package com.gorczynskimike.sudoku.complex;

import java.util.*;
import java.util.stream.Collectors;

public class ComplexSudokuBoard {

    private static final int BOARD_X_SIZE = 9;
    private static final int BOARD_Y_SIZE = 9;
    private final ComplexSudokuElement[][] complexSudokuElements = new ComplexSudokuElement[BOARD_X_SIZE][BOARD_Y_SIZE];

    public ComplexSudokuBoard() {
        for(int i=0; i<BOARD_X_SIZE; i++) {
            for(int j=0; j<BOARD_Y_SIZE; j++){
                complexSudokuElements[i][j] = new ComplexSudokuElement(i,j);
            }
        }
    }

    public ComplexSudokuBoard getDeepCopy() {
        ComplexSudokuBoard complexSudokuBoardDeepCopy = new ComplexSudokuBoard();
        for(int i=0; i<BOARD_X_SIZE; i++) {
            for(int j=0; j<BOARD_Y_SIZE; j++) {
                complexSudokuBoardDeepCopy.complexSudokuElements[i][j] = this.complexSudokuElements[i][j].getDeepCopy();
            }
        }
        return complexSudokuBoardDeepCopy;
    }

    private void restoreFromDeepCopy(ComplexSudokuBoard deepCopy) {
        for(int i=0; i<BOARD_X_SIZE; i++) {
            for(int j=0; j<BOARD_Y_SIZE; j++) {
                this.complexSudokuElements[i][j] = deepCopy.complexSudokuElements[i][j].getDeepCopy();
            }
        }
    }

    public void solveBoard() {
        List<ComplexSudokuElement> unsetElements;
        int counter = 0;
        mainLoop:
        while(!(unsetElements = getAllUnsetElements()).isEmpty()) {
            counter++;
            if(counter % 10000 == 0) {
                printBoard();
                System.out.println(counter + " : " + unsetElements.size());
            }
            for(ComplexSudokuElement element : unsetElements) {
                if(element.getPossibleValues().size() == 1) {
                    setElementValue(element.getxIndex(), element.getyIndex(), element.getPossibleValues().get(0));
                    unsetElements.remove(element);
                    continue mainLoop;
                }
            }
            if(!checkIfSolvable()) {
                SudokuGuessState lastGuessedState = SudokuStack.popSudokuState();
                restoreFromDeepCopy(lastGuessedState.getComplexSudokuBoardDeepCopy());
                complexSudokuElements[lastGuessedState.getxIndexOfGuessesElement()][lastGuessedState.getyIndexOfGuessesElement()]
                        .getPossibleValues().remove((Integer)lastGuessedState.getValueBeingGuessed());
//                unsetElements = getAllUnsetElements();
                continue mainLoop;
            } else {
                ComplexSudokuElement elementBeingGuessed = unsetElements.get(0);
                int valueBeingGuessed = elementBeingGuessed.getPossibleValues().get(0);
                SudokuStack.pushSudokuState(new SudokuGuessState(
                        this.getDeepCopy(),
                        elementBeingGuessed.getxIndex(),
                        elementBeingGuessed.getyIndex(),
                        valueBeingGuessed)
                );
                this.setElementValue(elementBeingGuessed.getxIndex(), elementBeingGuessed.getyIndex(), valueBeingGuessed);
                unsetElements.remove(elementBeingGuessed);
                continue mainLoop;
            }
        }
        System.out.println("Solved!");
        printBoard();
    }

    public void printBoard() {
        System.out.println(getBoardStringRepresentation());
    }

    public String getBoardStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append(getRowSeparator());
        for(int i=0 ; i<BOARD_Y_SIZE; i++){
            sb.append(System.lineSeparator());
            sb.append(getRowString(i));
            sb.append(System.lineSeparator());
            sb.append(getRowSeparator());
        }
        return sb.toString();
    }

    public boolean setElementValue(int xIndex, int yIndex, int value){
        if(xIndex < 0 || xIndex >= BOARD_X_SIZE) {
            throw new IllegalArgumentException("X index out of bounds: " + xIndex);
        }
        if(yIndex < 0 || yIndex >= BOARD_Y_SIZE) {
            throw new IllegalArgumentException("Y index out of bounds: " + yIndex);
        }
        if(value < 1 || value > 9) {
            throw new IllegalArgumentException("Value out of bounds: " + value);
        }
        ComplexSudokuElement theElement = complexSudokuElements[xIndex][yIndex];
        if(!theElement.getPossibleValues().contains(value)) {
            System.out.println("Can't set given value for given element. Possibly the number is already assigned in " +
                    "the row, column or 3x3 section of this element");
            return false;
        }
        complexSudokuElements[xIndex][yIndex].setValue(value);
        getAllLinkedElementsToElement(xIndex,yIndex).forEach(complexSudokuElement -> complexSudokuElement.removePossibleValue((Integer)value));
//        getRow(yIndex).forEach(sudokuElement -> sudokuElement.removePossibleValue(value));
//        getColumn(xIndex).forEach(sudokuElement -> sudokuElement.removePossibleValue(value));
//        getSection(xIndex, yIndex).forEach(sudokuElement -> sudokuElement.removePossibleValue(value));
        return true;
    }

    public boolean unsetElement(int xIndex, int yIndex) {
        if(xIndex < 0 || xIndex >= BOARD_X_SIZE) {
            throw new IllegalArgumentException("X index out of bounds: " + xIndex);
        }
        if(yIndex < 0 || yIndex >= BOARD_Y_SIZE) {
            throw new IllegalArgumentException("Y index out of bounds: " + yIndex);
        }
        ComplexSudokuElement theElement = complexSudokuElements[xIndex][yIndex];
        if(theElement.getValue() == -1) {
            System.out.println("Can't unset element, the element had not been set.");
            return false;
        }
        int oldValue = theElement.getValue();
        getAllLinkedElementsToElement(xIndex, yIndex).stream()
                .forEach(element -> element.addPossibleValue(oldValue));
        theElement.unset(getAllPossibleValuesForElement(xIndex, yIndex));
        printBoard();
        return true;
    }

    private String getRowString(int yIndex) {
        StringJoiner sj = new StringJoiner("|", "|", "|");
        for(int i=0; i<BOARD_X_SIZE; i++) {
            int currentValue = complexSudokuElements[i][yIndex].getValue();
            String currectValueString = currentValue == -1 ? " " : String.valueOf(currentValue);
            sj.add(currectValueString);
        }
        return sj.toString();
    }

    private String getRowSeparator() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<BOARD_X_SIZE; i++) {
            sb.append("--");
        }
        sb.append("-");
        return sb.toString();
    }

    private List<ComplexSudokuElement> getRow(int rowIndex) {
        List<ComplexSudokuElement> resultList = new ArrayList<>();
        for(int i=0 ; i<BOARD_X_SIZE; i++) {
            resultList.add(complexSudokuElements[i][rowIndex]);
        }
        return resultList;
    }

    private List<ComplexSudokuElement> getColumn(int columnIndex) {
        List<ComplexSudokuElement> resultList = new ArrayList<>();
        for(int i=0 ; i<BOARD_Y_SIZE; i++) {
            resultList.add(complexSudokuElements[columnIndex][i]);
        }
        return resultList;
    }

    private List<ComplexSudokuElement> getSection(int xIndex, int yIndex) {
        List<ComplexSudokuElement> resultList = new ArrayList<>();
        int xStartIndex = xIndex - xIndex%3;
        int yStartIndex = yIndex - yIndex%3;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                resultList.add(complexSudokuElements[xStartIndex + i][yStartIndex + j]);
            }
        }
        return resultList;
    }

    private Collection<Integer> getAllPossibleValuesForElement(int xIndex, int yIndex) {
        Set<ComplexSudokuElement> otherElements = getAllLinkedElementsToElement(xIndex, yIndex);
        Set<Integer> impossibleValues = otherElements.stream()
                .map(element -> element.getValue())
                .collect(Collectors.toSet());
        Set<Integer> resultSet = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        resultSet.removeAll(impossibleValues);
        return resultSet;
    }

    /**
     *
     * @param xIndex
     * @param yIndex
     * @return a Set of all elements in the same row, column or 3x3 section
     */
    private Set<ComplexSudokuElement> getAllLinkedElementsToElement(int xIndex, int yIndex) {
        Set<ComplexSudokuElement> otherElements = new HashSet<>();
        otherElements.addAll(getRow(yIndex));
        otherElements.addAll(getColumn(xIndex));
        otherElements.addAll(getSection(xIndex, yIndex));
        otherElements.remove(complexSudokuElements[xIndex][yIndex]);
        return otherElements;
    }

    private List<ComplexSudokuElement> getAllElements() {
        List<ComplexSudokuElement> resultList = new ArrayList<>();
        for(int i=0 ;i<BOARD_Y_SIZE; i++) {
            resultList.addAll(getRow(i));
        }
        return resultList;
    }

    private List<ComplexSudokuElement> getAllUnsetElements() {
        List<ComplexSudokuElement> resultList = getAllElements();
        resultList.removeIf(complexSudokuElement -> complexSudokuElement.isValueSet());
        return resultList;
    }

    private boolean checkIfSolvable() {
        Collection<ComplexSudokuElement> unsetElements = getAllUnsetElements();
        if(unsetElements.size() == 0) { return true; }
        OptionalInt minPossibilities = getAllUnsetElements().stream()
                .mapToInt(element -> element.getPossibleValues().size())
                .min();
        return minPossibilities.getAsInt() != 0;
    }

}
