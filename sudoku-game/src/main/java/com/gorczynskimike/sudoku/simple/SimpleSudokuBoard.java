package com.gorczynskimike.sudoku.simple;

import com.gorczynskimike.sudoku.userinterface.MessageService;

import java.util.Arrays;
import java.util.Random;

/**
 * Sudoku main class. It holds an array of SudokuElements. It provides the interface to manipulate the board, makes
 * sure that the board is in valid state and it can solve the board.
 */
public class SimpleSudokuBoard {

    private SudokuElement[][] sudokuElementsArray;
    private SudokuStack sudokuStack;

    private int howManyGuesses = 0;

    private MessageService messageService = (text) -> {
        System.out.println(text);
    };
    private MessageService sudokuMessageService = (text) -> {
        System.out.println(text);
    };

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setSudokuMessageService(MessageService sudokuMessageService) {
        this.sudokuMessageService = sudokuMessageService;
    }

    public SimpleSudokuBoard() {
        this.sudokuElementsArray = SudokuArrayFactory.getEmptySudokuArray();
        this.sudokuStack = new SudokuStack();
    }

    public SimpleSudokuBoard(SimpleSudokuBoard other) {
        this.sudokuElementsArray = other.getSudokuElementsArrayCopy();
        this.sudokuStack = new SudokuStack();
    }

    public boolean solveSudoku() {
        return solveSudoku(false, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public boolean solveSudoku(boolean silentModeOn) {
        return solveSudoku(silentModeOn, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public boolean solveSudoku(boolean silentModeOn, int guessesLimit, int mainLoopLimit) {

        //setup
        long startTimeNano = System.nanoTime();
        int mainLoopCounter = 0;
        this.howManyGuesses = 0;
        this.sudokuStack.clearStack();

        mainLoop:
        while (true) {
            if(this.howManyGuesses > guessesLimit) {
                System.out.println("Guesses limit reached");
                return false;
            }
            if(mainLoopCounter > mainLoopLimit) {
                System.out.println("Main loop limit reached");
                return false;
            }
            mainLoopCounter++;
            int unsetElements = 0;
            int modifiedElements = 0;

            //if there is an element with 0 possible values it's impossible to solve the board and it's necessary to
            //restore last saved state (if present). if there is no saved states then it's not possible to solve the
            //board at all
            if(checkIfAnyFieldWithNoPossibilities()) {
                if(this.sudokuStack.getStackSize() == 0) {
                    if(!silentModeOn) {
                        messageService.sendMessage("");
                        messageService.sendMessage("*** Sorry, it's impossible to solve this sudoku. ***");
                        messageService.sendMessage("");
                        printBoard();
                        printSolvingSummaryInfo(startTimeNano, mainLoopCounter);
                    }
                    this.sudokuStack.clearStack();
                    return false;
                }
                restoreLastSudokuStateAndRemoveGuessedValueFromPossibilities();
                continue mainLoop;
            }

            //check how many elements are not set and set every element with only one possible value
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    SudokuElement current = sudokuElementsArray[i][j];
                    if(current.getValue() == 0) {
                        unsetElements++;
                        if(current.getPossibleValuesCopy().size() == 1) {
                            setElement(i,j,current.getPossibleValuesCopy().get(0));
                            modifiedElements++;
                            unsetElements--;
                        }
                    }
                }
            }

            //no empty fields on the board = board is solved
            if(unsetElements == 0) {
                if(!silentModeOn) {
                    messageService.sendMessage("");
                    messageService.sendMessage("*** Solved ***");
                    messageService.sendMessage("");
                    printBoard();
                    printSolvingSummaryInfo(startTimeNano, mainLoopCounter);
                }
                sudokuStack.clearStack();
                return true;
            }

            //no modified elements on the board = it's necessary
            if(modifiedElements == 0) {
                //there are no elements with one possible value on the board, it's necessary to guess one element
                CoordinatePair bestGuessCoordinates = findBestElementToGuess();
                int guessedValue = getGuessedValueForElement(bestGuessCoordinates);
                saveSudokuStateToStack(bestGuessCoordinates, guessedValue);
                setElement(bestGuessCoordinates.getX(), bestGuessCoordinates.getY(), guessedValue);
                howManyGuesses++;
            }
        }
    }

//    public boolean solveSudokuGuessesLimit(int guessesLimit) {
//        return solveSudoku(true, guessesLimit);
//    }

    public String printBoard() {
        StringBuilder resultBuilder = new StringBuilder();
        for(int i=0; i<9; i++) {
            StringBuilder lineBuilder = new StringBuilder();
            for(int j=0; j<9; j++) {
                lineBuilder.append(sudokuElementsArray[i][j]);
                if(j == 2 || j == 5) {
                    lineBuilder.append("| ");
                } else {
                    if(j != 8)
                    lineBuilder.append(", ");
                }
            }
            resultBuilder.append(" " + lineBuilder.toString() + System.lineSeparator());
            if(i == 2 || i == 5) {
                resultBuilder.append(" " + lineBuilder.toString().replaceAll(".", "-") + System.lineSeparator());
            }
        }
        sudokuMessageService.sendMessage(resultBuilder.toString());
        return resultBuilder.toString();
    }

    public void setElement(int xIndex, int yIndex, int value) {

        //range checks on arguments
        if( (xIndex < 0 || xIndex > 8) || (yIndex < 0 || yIndex > 8)) {
            throw new IllegalArgumentException("X or Y coordinate out of bounds.");
        }
        if ( value < 1 || value > 9) {
            throw new IllegalArgumentException("Value out of bounds.");
        }

        //check if element is not set
        int oldValue = sudokuElementsArray[xIndex][yIndex].getValue();
        if(oldValue != 0) {
            messageService.sendMessage("Sorry, can't set this element, the element had been already set.");
            messageService.sendMessage("You can use command 'x,y,unset' to unset this element first and then you can assign new value.");
            return;
        }
        sudokuElementsArray[xIndex][yIndex].setValue(value);

        //row
        for(int i=0; i<9; i++) {
            sudokuElementsArray[i][yIndex].removePossibleValue(value);
        }

        //column
        for(int i=0; i<9; i++) {
            sudokuElementsArray[xIndex][i].removePossibleValue(value);
        }

        //3x3 section
        int xStartIndex = xIndex - xIndex%3;
        int yStartIndex = yIndex - yIndex%3;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                sudokuElementsArray[xStartIndex + i][yStartIndex + j].removePossibleValue(value);
            }
        }
    }

    public void unsetElement(int xIndex, int yIndex) {

        //initial checks on range
        if((xIndex < 0 || xIndex > 8) || (yIndex < 0 || yIndex > 8)) {
            throw new IllegalArgumentException("X or Y coordinate out of bounds.");
        }
        int oldValue = sudokuElementsArray[xIndex][yIndex].getValue();
        if(oldValue == 0) {
            System.out.println("Can't unset this element, the element had not been set.");
            return;
        }

        SudokuElement theElement = sudokuElementsArray[xIndex][yIndex];
        sudokuElementsArray[xIndex][yIndex].clearValue();

        //row
        for(int i=0; i<9; i++) {
            sudokuElementsArray[i][yIndex].addPossibleValue(oldValue);
        }

        //column
        for(int i=0; i<9; i++) {
            sudokuElementsArray[xIndex][i].addPossibleValue(oldValue);
        }

        //3x3 section
        int xStartIndex = xIndex - xIndex%3;
        int yStartIndex = yIndex - yIndex%3;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                sudokuElementsArray[xStartIndex + i][yStartIndex + j].addPossibleValue(oldValue);
            }
        }

        removeAllSetValuesFromPossibleOnes();
    }

    public void clearTheBoard() {
        this.sudokuElementsArray = SudokuArrayFactory.getEmptySudokuArray();
    }

    public SudokuElement[][] getSudokuElementsArrayCopy() {
        return SudokuArrayFactory.copySudokuArray(this.sudokuElementsArray);
    }

    public boolean checkIfSolvable() {
        return checkIfSolvableWithLimit(Integer.MAX_VALUE);
    }

    public boolean checkIfSolvableWithLimit(int mainLoopLimit) {
        SudokuElement[][] copy = SudokuArrayFactory.copySudokuArray(this.sudokuElementsArray);
        boolean isSolvable = this.solveSudoku(true, Integer.MAX_VALUE, 3000);
        this.sudokuElementsArray = copy;
        return isSolvable;
    }

    public int howManyGuessesNeededToSolve() {
        SudokuElement[][] arrayCopy = SudokuArrayFactory.copySudokuArray(this.sudokuElementsArray);
        int result = -1;
        if(solveSudoku(true, 500, 100)) {
            result = this.howManyGuesses;
        }
        this.sudokuElementsArray = arrayCopy;
        return result;
    }

    public void recalculateBoard() {
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                sudokuElementsArray[i][j].addPossibleValues(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9}));
            }
        }
        removeAllSetValuesFromPossibleOnes();
    }

    private void printSolvingSummaryInfo(long startTimeNano, int mainLoopCounter) {
        long endTimeNano = System.nanoTime();
        this.sudokuStack.printStackSize();
        messageService.sendMessage("Number of loops: " + mainLoopCounter);
        messageService.sendMessage("Solving sudoku procedure took " + ((double)(endTimeNano - startTimeNano)) / 1000000 + " milliseconds. ");
        messageService.sendMessage("Program had to guess " + howManyGuesses + " times.");
    }

    private int getGuessedValueForElement(CoordinatePair coordinatePair) {
        if(coordinatePair == null) {
            throw new IllegalArgumentException("Argument to this method must not be null.");
        }
        int bestXIndex = coordinatePair.getX();
        int bestYIndex = coordinatePair.getY();
        if(sudokuElementsArray[bestXIndex][bestYIndex].getPossibleValuesCopy().size() == 0) {
            throw new IllegalStateException("Can't guess value for given element, possible values = 0");
        }
        Random random = new Random();
        int guessedNumber = sudokuElementsArray[bestXIndex][bestYIndex].getPossibleValuesCopy().get(
                random.nextInt(sudokuElementsArray[bestXIndex][bestYIndex].getPossibleValuesCopy().size())
//            0
        );
        return guessedNumber;
    }

    private void saveSudokuStateToStack(CoordinatePair coordinates, int guessedValue) {
        SudokuState savedState = new SudokuState(this.sudokuElementsArray, coordinates.getX(), coordinates.getY(), guessedValue);
        this.sudokuStack.pushSudokuState(savedState);
    }

    private void restoreLastSudokuStateAndRemoveGuessedValueFromPossibilities() {
        SudokuState lastState = this.sudokuStack.popSudokuState();
        sudokuElementsArray = lastState.getSudokuElementsArray();
        sudokuElementsArray[lastState.getXIndex()][lastState.getYIndex()].removePossibleValue(lastState.getGuessedNumber());
    }

    private CoordinatePair findBestElementToGuess() {
        int bestXIndex = 0;
        int bestYIndex = 0;
        int minPossibilities = 10;
        SudokuElement current;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                current = sudokuElementsArray[i][j];
                if(current.getValue() == 0 && current.getPossibleValuesCopy().size() < minPossibilities) {
                    bestXIndex = i;
                    bestYIndex = j;
                    minPossibilities = current.getPossibleValuesCopy().size();
                }
            }
        }
        if(minPossibilities == 10) {
            return null;
        } else {
            return new CoordinatePair(bestXIndex, bestYIndex);
        }
    }

    private boolean checkIfAnyFieldWithNoPossibilities() {
        int minPossibilities = 10;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuElement currentElement = sudokuElementsArray[i][j];
                if(currentElement.getValue() == 0) {
                    int currentPossibilities = currentElement.getPossibleValuesCopy().size();
                    if(currentPossibilities < minPossibilities) {
                        minPossibilities = currentPossibilities;
                    }
                }
            }
        }
        return minPossibilities == 0;
    }

    private void removeAllSetValuesFromPossibleOnes() {
        for(int xIndex=0; xIndex<9; xIndex++) {
            for(int yIndex=0; yIndex<9; yIndex++) {

                SudokuElement theElement = this.sudokuElementsArray[xIndex][yIndex];

                //row
                for(int i=0; i<9; i++) {
                    theElement.removePossibleValue(sudokuElementsArray[i][yIndex].getValue());
                }

                //column
                for(int i=0; i<9; i++) {
                    theElement.removePossibleValue(sudokuElementsArray[xIndex][i].getValue());
                }

                //3x3 section
                int xStartIndex = xIndex - xIndex%3;
                int yStartIndex = yIndex - yIndex%3;
                for(int i=0; i<3; i++) {
                    for(int j=0; j<3; j++) {
                        theElement.removePossibleValue(sudokuElementsArray[xStartIndex + i][yStartIndex + j].getValue());
                    }
                }

            }
        }
    }

}
