package com.gorczynskimike.sudoku.board;

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

    /**
     * It sets message service for the board. Standard sysout is the default MessageService.
     * @param messageService New MessageService.
     */
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Sudoku message service is used to print the String representation of the board.
     * @param sudokuMessageService New MessageService to print the board state.
     */
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

    /**
     * It solves this board with following defaults:
     * silent mode = false
     * guesses limit = Integer.MAX_VALUE
     * main loop limit = Integer.MAX_VALUE
     * That means the method will send messages to message service and (effectively) there are no guesses or main loop
     * limits.
     * @return true if board was solved, false otherwise.
     */
    public boolean solveSudoku() {
        return solveSudoku(false, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * It solves this board with following defaults:
     * guesses limit = Integer.MAX_VALUE
     * main loop limit = Integer.MAX_VALUE
     * That means that (effectively) there are no guesses or main loop limits.
     * @param silentModeOn if true, the board will not print messages to message services
     * @return true if board was solved, false otherwise.
     */
    public boolean solveSudoku(boolean silentModeOn) {
        return solveSudoku(silentModeOn, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * It solves this board. The limits are useful when generating new boards to avoid generations that need a lot of
     * time to solve.
     * @param silentModeOn if true, the board will not print messages to message services
     * @param guessesLimit how many times the board can guess one number. If the limit is reached then method returns
     *                    false and exits.
     * @param mainLoopLimit how many times the board can start main loop of the algorithm. If the limit is reached
     *                     then method returns false and exits.
     * @return true if board was solved, false otherwise.
     */
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

    /**
     * It sends String representation of the board to sudoku message service
     * @return String representation of the board.
     */
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

    /**
     * Set given element. It also removes this value from possible values in the row, column and 3x3 section of this element.
     * @param xIndex x index of the element
     * @param yIndex y index of the element
     * @param value new value for the element
     */
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
            throw new IllegalStateException("Sorry, can't set this element, the element had been already set." +
            System.lineSeparator() + "You can use command 'x,y,unset' to unset this element first and then you can assign new value.");
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

    /**
     * Clear given element. It also adds value removed to possible values in row, column and 3x3 section (where applicable).
     * @param xIndex x index of the element
     * @param yIndex y index of the element
     */
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

    /**
     * Clears the board.
     */
    public void clearTheBoard() {
        this.sudokuElementsArray = SudokuArrayFactory.getEmptySudokuArray();
    }

    /**
     * Creates a copy of the array of sudoku elements of this board.
     * @return Copy of array of suoku elements.
     */
    public SudokuElement[][] getSudokuElementsArrayCopy() {
        return SudokuArrayFactory.copySudokuArray(this.sudokuElementsArray);
    }

    /**
     * It checks if the board is solvable with no main loop limits.
     * @return true if the board is solvable.
     */
    public boolean checkIfSolvable() {
        return checkIfSolvableWithLimit(Integer.MAX_VALUE);
    }

    /**
     * It checks if the board is solvable with given main loop limit.
     * @param mainLoopLimit limit of the main loops of the algorithm
     * @return false if the board is not solvable or the limit is reached
     */
    public boolean checkIfSolvableWithLimit(int mainLoopLimit) {
        SudokuElement[][] copy = SudokuArrayFactory.copySudokuArray(this.sudokuElementsArray);
        boolean isSolvable = this.solveSudoku(true, Integer.MAX_VALUE, mainLoopLimit);
        this.sudokuElementsArray = copy;
        return isSolvable;
    }

    /**
     * Check how many guesses it takes to solve the board in current state. It
     * @return How many guesses the algorithm needed to solve the board
     */
    public int howManyGuessesNeededToSolve() {
        SudokuElement[][] arrayCopy = SudokuArrayFactory.copySudokuArray(this.sudokuElementsArray);
        int result = -1;
        if(solveSudoku(true, 500, 100)) {
            result = this.howManyGuesses;
        }
        this.sudokuElementsArray = arrayCopy;
        return result;
    }

    /**
     * It resets the possible values for every element on the board and then removes all impossible values
     */
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
        messageService.sendMessage("Sudoku states stack: " + this.sudokuStack.getStackSize());
        messageService.sendMessage("Number of loops: " + mainLoopCounter);
        messageService.sendMessage("Solving sudoku procedure took " + ((double)(endTimeNano - startTimeNano)) / 1000000 + " milliseconds. ");
        messageService.sendMessage("Program had to guess " + howManyGuesses + " times.");
    }

    /**
     * Get the best value for given element to make a guess.
     * @param coordinatePair Element being guessed.
     * @return best guess value for this element.
     */
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

    /**
     * Find the best element to make a guess.
     * @return Coordinates of best element to guess
     */
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

    /**
     * It checks if there is on the board at least one sudoku element with empty possible values list.
     * @return true if on theboard there is an element with no possible values
     */
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

    /**
     * For every set element on the board it's value is removed from possibilities in it's row, column and 3x3 section.
     */
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
