package com.gorczynskimike.sudoku.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimpleSudokuBoard {

    private SudokuElement[][] sudokuElementsArray;

    public SimpleSudokuBoard() {
        this.sudokuElementsArray = SudokuArrayFactory.getEmptySudokuArray();
    }

    public boolean solveSudoku() {
        return solveSudoku(false);
    }

    public boolean solveSudoku(boolean silentModeOn) {

        //setup
        long startTime = System.currentTimeMillis();
        long startTimeNano = System.nanoTime();
        boolean isSolved = false;
        boolean result = false;
        int mainLoopCounter = 0;
        int timesProgramGuessedValue = 0;

        mainLoop:
        while (!isSolved) {
            mainLoopCounter++;
            int unsetElements = 0;
            int modifiedElements = 0;
            //if there is an element with 0 possible values it's impossible to solve the board
            if(!isSolvable()) {
                if(SudokuStack.getStackSize() == 0) {
                    if(!silentModeOn) {
                        System.out.println("Sorry, it's impossible to solve this sudoku.");
                    }
                    break mainLoop;
                }
                restoreLastSudokuStateAndRemoveGuessedValueFromPossibilities();
                continue mainLoop;
            }

            //check how many elements are not set and set every element with only one possible value
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    SudokuElement current = sudokuElementsArray[i][j];
                    int currentValue = current.getValue();
                    if(currentValue != 0) {
                        continue;
                    } else {
                        unsetElements++;
                        if(current.getPossibleValues().size() == 1) {
                            setElement(i,j,current.getPossibleValues().get(0));
                            modifiedElements++;
                            unsetElements--;
                        }
                    }
                }
            }

            if(unsetElements == 0) {
                if(!silentModeOn) {
                    System.out.println("Solved");
                    printBoard();
                    System.out.println("Number of loops: " + mainLoopCounter);
                    SudokuStack.printStackSize();
                }
                SudokuStack.clearStack();
                result = true;
                break mainLoop;
            }

            if(modifiedElements > 0) {
                //modifying at least one element on the board could change the situation on the board, it's necessary to
                //check the board again
                continue mainLoop;
            } else {
                //there were no elements with one possible value on the board, it's necessary to guess one element
                CoordinatePair bestGuessCoordinates = findBestElementToGuess();
                if(!(bestGuessCoordinates == null)) {
                    guessNumber(bestGuessCoordinates);
                    timesProgramGuessedValue++;
                }
                continue mainLoop;
            }
        }

        //end of algorithm, print info
        long endTime = System.currentTimeMillis();
        long endTimeNano = System.nanoTime();
        if(!silentModeOn) {
            System.out.println("Solving sudoku procedure took " + (endTime - startTime) + " milliseconds. " +
                    "( " + (endTimeNano - startTimeNano) + " nano seconds)");
            System.out.println("Program had to guess " + timesProgramGuessedValue + " times.");
        }
        return result;
    }

    private void guessNumber(CoordinatePair coordinatePair) {
        if(coordinatePair == null) {
            throw new IllegalArgumentException("Argument to this method must not be null.");
        }
        int bestXIndex = coordinatePair.getX();
        int bestYIndex = coordinatePair.getY();
        if(sudokuElementsArray[bestXIndex][bestYIndex].getPossibleValues().size() == 0) {
            throw new IllegalStateException("Can't guess value for given element, possible values = 0");
        }
        int guessedNumber = sudokuElementsArray[bestXIndex][bestYIndex].getPossibleValues().get(0);
        SudokuState savedState = new SudokuState(this.sudokuElementsArray, bestXIndex, bestYIndex, guessedNumber);
        SudokuStack.pushSudokuState(savedState);
        setElement(bestXIndex,bestYIndex,guessedNumber);
    }

    private void restoreLastSudokuStateAndRemoveGuessedValueFromPossibilities() {
        SudokuState lastState = SudokuStack.popSudokuState();
        sudokuElementsArray = lastState.getSudokuElementsArray();
        sudokuElementsArray[lastState.getxIndex()][lastState.getyIndex()].getPossibleValues().remove((Integer) lastState.getGuessedNumber());
    }

    private CoordinatePair findBestElementToGuess() {
        int bestXIndex = 0;
        int bestYIndex = 0;
        int minPossibilities = 10;
        SudokuElement current;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                current = sudokuElementsArray[i][j];
                if(current.getValue() == 0 && current.getPossibleValues().size() < minPossibilities) {
                    bestXIndex = i;
                    bestYIndex = j;
                    minPossibilities = current.getPossibleValues().size();
                }
            }
        }
        if(minPossibilities == 10) {
            return null;
        } else {
            return new CoordinatePair(bestXIndex, bestYIndex);
        }
    }

    private boolean isSolvable() {
        int minPossibilities = 10;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuElement currentElement = sudokuElementsArray[i][j];
                if(currentElement.getValue() == 0) {
                    int currentPossibilities = currentElement.getPossibleValues().size();
                    if(currentPossibilities < minPossibilities) {
                        minPossibilities = currentPossibilities;
                    }
                }
            }
        }
        return minPossibilities > 0;
    }

    public void printBoard() {
        for(int i=0; i<9; i++) {
            System.out.println(Arrays.toString(sudokuElementsArray[i]));
        }
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
            System.out.println("Sorry, can't set this element, the element had been already set.");
            System.out.println("You can use command 'x,y,unset' to unset this element first and then you can assign new value.");
            return;
        }
        sudokuElementsArray[xIndex][yIndex].setValue(value);

        //row
        for(int i=0; i<9; i++) {
            sudokuElementsArray[i][yIndex].getPossibleValues().remove((Integer)value);
        }

        //column
        for(int i=0; i<9; i++) {
            sudokuElementsArray[xIndex][i].getPossibleValues().remove((Integer)value);
        }

        //3x3 section
        int xStartIndex = xIndex - xIndex%3;
        int yStartIndex = yIndex - yIndex%3;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                sudokuElementsArray[xStartIndex + i][yStartIndex + j].getPossibleValues().remove((Integer)value);
            }
        }
    }

    public void unsetElement(int xIndex, int yIndex) {

        //initial checks on range
        if(
               (xIndex < 0 || xIndex > 8)
            || (yIndex < 0 || yIndex > 8)) {
        throw new IllegalArgumentException("X or Y coordinate out of bounds.");
        }
        int oldValue = sudokuElementsArray[xIndex][yIndex].getValue();
        if(oldValue == 0) {
            System.out.println("Can't unset this element, the element had not been set.");
            return;
        }

        sudokuElementsArray[xIndex][yIndex].clearValue();

        //row
        for(int i=0; i<9; i++) {
            if(!sudokuElementsArray[i][yIndex].getPossibleValues().contains((Integer)oldValue))
            sudokuElementsArray[i][yIndex].getPossibleValues().add((Integer)oldValue);
        }

        //column
        for(int i=0; i<9; i++) {
            if(!sudokuElementsArray[xIndex][i].getPossibleValues().contains((Integer)oldValue))
            sudokuElementsArray[xIndex][i].getPossibleValues().add((Integer)oldValue);
        }

        //3x3 section
        int xStartIndex = xIndex - xIndex%3;
        int yStartIndex = yIndex - yIndex%3;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(!sudokuElementsArray[xStartIndex + i][yStartIndex + j].getPossibleValues().contains((Integer)oldValue))
                sudokuElementsArray[xStartIndex + i][yStartIndex + j].getPossibleValues().add((Integer)oldValue);
            }
        }
    }

    public void generateRandomNumbers(int howMany) {
        //initial check of range
        if(howMany < 1 || howMany > 81) {
            System.out.println("Sorry, can't generate " + howMany + " numbers, valid range is: 1 - 81");
            return;
        }

        //check if there are enough not set sudokuElementsArray to fill
        int numberOfEmptyElements = getNumberOfEmptyElements();
        if(numberOfEmptyElements < howMany) {
            System.out.println("Sorry, can't generate that many numbers: " + howMany + " there is only: " +
                    + numberOfEmptyElements + " empty sudokuElementsArray left.");
            return;
        }

        //generate
        int succesfullyGeneratedNumbers = 0;
        while(howMany > 0) {
            boolean wasNumberGenerated = generateOneNumber();
            if(!wasNumberGenerated) {
                System.out.println("Sorry, it's impossible to generate more numbers without breaking sudoku rules.");
                break;
            } else {
                succesfullyGeneratedNumbers++;
            }
            howMany--;
        }
        System.out.println(succesfullyGeneratedNumbers + " numbers were generated successfully.");
        this.printBoard();
    }

    private boolean generateOneNumber() {
        int possibleNumbers = 0;
        List<CoordinatePair> listOfEmptyFields = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudokuElementsArray[i][j].getValue() == 0 && sudokuElementsArray[i][j].getPossibleValues().size() > 0) {
                    possibleNumbers++;
                    listOfEmptyFields.add(new CoordinatePair(i,j));
                }
            }
        }
        if(possibleNumbers == 0) {
            return false;
        }
        Random random = new Random();
        CoordinatePair chosenFieldCoordinates = listOfEmptyFields.get(random.nextInt(listOfEmptyFields.size()));
        SudokuElement chosenElement = sudokuElementsArray[chosenFieldCoordinates.getX()][chosenFieldCoordinates.getY()];
        int chosenValue = chosenElement.getPossibleValues().get(random.nextInt(chosenElement.getPossibleValues().size()));
        setElement(chosenFieldCoordinates.getX(), chosenFieldCoordinates.getY(), chosenValue);
        return true;
    }

    private int getNumberOfEmptyElements() {
        int numberOfEmptyElements = 0;
        for(int i = 0; i< sudokuElementsArray.length; i++) {
            for(int j = 0; j< sudokuElementsArray.length; j++) {
                if(sudokuElementsArray[i][j].getValue() == 0) {
                    numberOfEmptyElements++;
                }
            }
        }
        return numberOfEmptyElements;
    }

    public void generateSolvableBoard(int howManyNumbers) {
        //initial check of range
        if(howManyNumbers < 0 || howManyNumbers > 81) {
            throw new IllegalArgumentException("Valid range is 0-81. Passed value: " + howManyNumbers);
        }

        //check if the board is solvable before any modifications
        SudokuElement[][] boardCopy = SudokuArrayFactory.copySudokuBoard(this.sudokuElementsArray);
        if(!solveSudoku(true)) {
            System.out.println("Sorry but the board is not solvable at the moment, no numbers generated. You can remove " +
                    "some numbers and try again.");
            this.sudokuElementsArray = boardCopy;
            return;
        }
        this.sudokuElementsArray = boardCopy;

        //check if there are enough not set sudokuElementsArray to fill
        int numberOfEmptyElements = getNumberOfEmptyElements();
        if(numberOfEmptyElements < howManyNumbers) {
            System.out.println("Sorry, can't generate that many numbers: " + howManyNumbers + " there is only: " +
                    + numberOfEmptyElements + " empty sudokuElementsArray left.");
            return;
        }

        //generate numbers
        while(howManyNumbers > 0) {
            SudokuElement[][] boardCopyBefore = SudokuArrayFactory.copySudokuBoard(this.sudokuElementsArray);
            generateOneNumber();
            SudokuElement[][] boardCopyAfter = SudokuArrayFactory.copySudokuBoard(this.sudokuElementsArray);
            if(solveSudoku(true)) {
                howManyNumbers--;
                this.sudokuElementsArray = boardCopyAfter;
                continue;
            } else {
                this.sudokuElementsArray = boardCopyBefore;
            }
        }

        printBoard();
    }

    public void clearTheBoard() {
        this.sudokuElementsArray = SudokuArrayFactory.getEmptySudokuArray();
    }

}
