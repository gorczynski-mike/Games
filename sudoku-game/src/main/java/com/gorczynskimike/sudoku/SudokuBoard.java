package com.gorczynskimike.sudoku;

import java.util.*;
import java.util.stream.Collectors;

public class SudokuBoard {

    private static final int BOARD_X_SIZE = 9;
    private static final int BOARD_Y_SIZE = 9;
    private final SudokuElement[][] sudokuElements = new SudokuElement[BOARD_X_SIZE][BOARD_Y_SIZE];

    public SudokuBoard() {
        for(int i=0; i<BOARD_X_SIZE; i++) {
            for(int j=0; j<BOARD_Y_SIZE; j++){
                sudokuElements[i][j] = new SudokuElement(i,j);
            }
        }
    }

    public SudokuBoard getDeepCopy() {
        SudokuBoard sudokuBoardDeepCopy = new SudokuBoard();
        for(int i=0; i<BOARD_X_SIZE; i++) {
            for(int j=0; j<BOARD_Y_SIZE; j++) {
                sudokuBoardDeepCopy.sudokuElements[i][j] = this.sudokuElements[i][j].getDeepCopy();
            }
        }
        return sudokuBoardDeepCopy;
    }

    private void restoreFromDeepCopy(SudokuBoard deepCopy) {
        for(int i=0; i<BOARD_X_SIZE; i++) {
            for(int j=0; j<BOARD_Y_SIZE; j++) {
                this.sudokuElements[i][j] = deepCopy.sudokuElements[i][j].getDeepCopy();
            }
        }
    }

    public void solveBoard() {
        List<SudokuElement> unsetElements;
        int counter = 0;
        mainLoop:
        while(!(unsetElements = getAllUnsetElements()).isEmpty()) {
            counter++;
            if(counter % 10000 == 0) {
                printBoard();
                System.out.println(counter + " : " + unsetElements.size());
            }
            for(SudokuElement element : unsetElements) {
                if(element.getPossibleValues().size() == 1) {
                    setElementValue(element.getxIndex(), element.getyIndex(), element.getPossibleValues().get(0));
                    unsetElements.remove(element);
                    continue mainLoop;
                }
            }
            if(!checkIfSolvable()) {
                SudokuGuessState lastGuessedState = SudokuStack.popSudokuState();
                restoreFromDeepCopy(lastGuessedState.getSudokuBoardDeepCopy());
                sudokuElements[lastGuessedState.getxIndexOfGuessesElement()][lastGuessedState.getyIndexOfGuessesElement()]
                        .getPossibleValues().remove((Integer)lastGuessedState.getValueBeingGuessed());
//                unsetElements = getAllUnsetElements();
                continue mainLoop;
            } else {
                SudokuElement elementBeingGuessed = unsetElements.get(0);
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
        SudokuElement theElement = sudokuElements[xIndex][yIndex];
        if(!theElement.getPossibleValues().contains(value)) {
            System.out.println("Can't set given value for given element. Possibly the number is already assigned in " +
                    "the row, column or 3x3 section of this element");
            return false;
        }
        sudokuElements[xIndex][yIndex].setValue(value);
        getAllLinkedElementsToElement(xIndex,yIndex).forEach(sudokuElement -> sudokuElement.removePossibleValue((Integer)value));
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
        SudokuElement theElement = sudokuElements[xIndex][yIndex];
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
            int currentValue = sudokuElements[i][yIndex].getValue();
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

    private List<SudokuElement> getRow(int rowIndex) {
        List<SudokuElement> resultList = new ArrayList<>();
        for(int i=0 ; i<BOARD_X_SIZE; i++) {
            resultList.add(sudokuElements[i][rowIndex]);
        }
        return resultList;
    }

    private List<SudokuElement> getColumn(int columnIndex) {
        List<SudokuElement> resultList = new ArrayList<>();
        for(int i=0 ; i<BOARD_Y_SIZE; i++) {
            resultList.add(sudokuElements[columnIndex][i]);
        }
        return resultList;
    }

    private List<SudokuElement> getSection(int xIndex, int yIndex) {
        List<SudokuElement> resultList = new ArrayList<>();
        int xStartIndex = xIndex - xIndex%3;
        int yStartIndex = yIndex - yIndex%3;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                resultList.add(sudokuElements[xStartIndex + i][yStartIndex + j]);
            }
        }
        return resultList;
    }

    private Collection<Integer> getAllPossibleValuesForElement(int xIndex, int yIndex) {
        Set<SudokuElement> otherElements = getAllLinkedElementsToElement(xIndex, yIndex);
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
    private Set<SudokuElement> getAllLinkedElementsToElement(int xIndex, int yIndex) {
        Set<SudokuElement> otherElements = new HashSet<>();
        otherElements.addAll(getRow(yIndex));
        otherElements.addAll(getColumn(xIndex));
        otherElements.addAll(getSection(xIndex, yIndex));
        otherElements.remove(sudokuElements[xIndex][yIndex]);
        return otherElements;
    }

    private List<SudokuElement> getAllElements() {
        List<SudokuElement> resultList = new ArrayList<>();
        for(int i=0 ;i<BOARD_Y_SIZE; i++) {
            resultList.addAll(getRow(i));
        }
        return resultList;
    }

    private List<SudokuElement> getAllUnsetElements() {
        List<SudokuElement> resultList = getAllElements();
        resultList.removeIf(sudokuElement -> sudokuElement.isValueSet());
        return resultList;
    }

    private boolean checkIfSolvable() {
        Collection<SudokuElement> unsetElements = getAllUnsetElements();
        if(unsetElements.size() == 0) { return true; }
        OptionalInt minPossibilities = getAllUnsetElements().stream()
                .mapToInt(element -> element.getPossibleValues().size())
                .min();
        return minPossibilities.getAsInt() != 0;
    }

}
