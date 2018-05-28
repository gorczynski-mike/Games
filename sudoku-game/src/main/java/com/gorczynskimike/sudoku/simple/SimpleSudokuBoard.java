package com.gorczynskimike.sudoku.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimpleSudokuBoard {

    public static void main(String[] args) {
//        SimpleSudokuBoard simpleSudokuBoard = new SimpleSudokuBoard();
//        simpleSudokuBoard.printBoard();
//        simpleSudokuBoard.generateRandomNumbers(21);
//        simpleSudokuBoard.printBoard();
//        long startTime = System.currentTimeMillis();
//        simpleSudokuBoard.solveSudoku();
//        long endTime = System.currentTimeMillis();
//        System.out.println("It took " + (endTime - startTime) + " milliseconds to solve empty sudoku.");
//
//        SimpleSudokuBoard simpleSudokuBoard2 = new SimpleSudokuBoard();
//        simpleSudokuBoard2.setElement(0,0,5);
//        simpleSudokuBoard2.setElement(0,1,6);
//        simpleSudokuBoard2.setElement(0,2,7);
//        simpleSudokuBoard2.printBoard();
//        long startTime2 = System.currentTimeMillis();
//        simpleSudokuBoard2.solveSudoku();
//        long endTime2 = System.currentTimeMillis();
//        System.out.println("It took " + (endTime2 - startTime2) + " milliseconds to solve sudoku.");

        SimpleSudokuBoard simpleSudokuBoard3 = new SimpleSudokuBoard();
        simpleSudokuBoard3.generateSolvableBoard(1);
        simpleSudokuBoard3.solveSudoku(false);
    }

    private SudokuElement[][] elements = new SudokuElement[9][9];

    public SimpleSudokuBoard() {
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                elements[i][j] = new SudokuElement();
            }
        }
    }

    public boolean solveSudoku(boolean silentMode) {
        long startTime = System.currentTimeMillis();
        long startTimeNano = System.nanoTime();
        boolean isSolved = false;
        boolean result = false;
        int counter = 0;
        mainLoop:
        while (!isSolved) {
//            System.out.println("main loop");
            counter++;
//            if(counter % 5000 == 0) {
//                System.out.println(counter);
//                printBoard();
//                SudokuStack.printStackSize();
//            }
            int unsetElements = 0;
            int modifiedElements = 0;
            if(!isSolvable()) {
                if(SudokuStack.getStackSize() == 0) {
                    if(!silentMode) {
                        System.out.println("Sorry, it's impossible to solve this sudoku.");
                    }
                    break mainLoop;
                }
                SudokuState lastState = SudokuStack.popSudokuState();
                elements = lastState.getBoard();
                elements[lastState.getxIndex()][lastState.getyIndex()].getPossibleValues().remove((Integer) lastState.getGuessedNumber());
                continue mainLoop;
            }
            allElementsLoop:
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    SudokuElement current = elements[i][j];
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
                if(!silentMode) {
                    System.out.println("Solved");
                    printBoard();
                    System.out.println("Number of loops: " + counter);
                    SudokuStack.printStackSize();
                }
                SudokuStack.clearStack();
                isSolved = true;
                result = true;
                break mainLoop;
            }
            if(modifiedElements > 0) {
                continue mainLoop;
            } else {
                int bestXIndex = 0;
                int bestYIndex = 0;
                int minPossibilities = 10;
                SudokuElement current = null;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        current = elements[i][j];
                        if(current.getValue() == 0 && current.getPossibleValues().size() < minPossibilities) {
//                            System.out.println("===============");
//                            printBoard();
                            bestXIndex = i;
                            bestYIndex = j;
                            minPossibilities = current.getPossibleValues().size();
                        }
                    }
                }
                int guessedNumber = elements[bestXIndex][bestYIndex].getPossibleValues().get(0);
                SudokuStack.pushSudokuState(new SudokuState(this.elements, bestXIndex, bestYIndex, guessedNumber));
                setElement(bestXIndex,bestYIndex,guessedNumber);
                continue mainLoop;
            }
        }
        long endTime = System.currentTimeMillis();
        long endTimeNano = System.nanoTime();
        if(!silentMode) {
            System.out.println("Solving sudoku procedure took " + (endTime - startTime) + " milliseconds. " +
                    "( " + (endTimeNano - startTimeNano) + " nano seconds)");
        }
        return result;
    }

    private boolean isSolvable() {
        int minPossibilities = 10;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuElement currentElement = elements[i][j];
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
            System.out.println(Arrays.toString(elements[i]));
        }
    }

    public void setElement(int xIndex, int yIndex, int value) {

        if(
                   (xIndex < 0 || xIndex > 8)
                || (yIndex < 0 || yIndex > 8)) {
            throw new IllegalArgumentException("X or Y coordinate out of bounds.");
        }
        if ( value < 1 || value > 9) {
            throw new IllegalArgumentException("Value out of bounds.");
        }

        int oldValue = elements[xIndex][yIndex].getValue();
        if(oldValue != 0) {
            System.out.println("Sorry, can't set this element, the element had been already set.");
            System.out.println("You can use command 'x,y,unset' to unset this element first and then you can assign new value.");
            return;
        }
        elements[xIndex][yIndex].setValue(value);

        //row
        for(int i=0; i<9; i++) {
            elements[i][yIndex].getPossibleValues().remove((Integer)value);
        }

        //column
        for(int i=0; i<9; i++) {
            elements[xIndex][i].getPossibleValues().remove((Integer)value);
        }

        //3x3 section
        int xStartIndex = xIndex - xIndex%3;
        int yStartIndex = yIndex - yIndex%3;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                elements[xStartIndex + i][yStartIndex + j].getPossibleValues().remove((Integer)value);
            }
        }
    }

    public void unsetElement(int xIndex, int yIndex) {

        if(
               (xIndex < 0 || xIndex > 8)
            || (yIndex < 0 || yIndex > 8)) {
        throw new IllegalArgumentException("X or Y coordinate out of bounds.");
        }
        int oldValue = elements[xIndex][yIndex].getValue();
        if(oldValue == 0) {
            System.out.println("Can't unset this element, the element had not been set.");
            return;
        }
        elements[xIndex][yIndex].clearValue();

        //row
        for(int i=0; i<9; i++) {
            if(!elements[i][yIndex].getPossibleValues().contains((Integer)oldValue))
            elements[i][yIndex].getPossibleValues().add((Integer)oldValue);
        }

        //column
        for(int i=0; i<9; i++) {
            if(!elements[xIndex][i].getPossibleValues().contains((Integer)oldValue))
            elements[xIndex][i].getPossibleValues().add((Integer)oldValue);
        }

        //3x3 section
        int xStartIndex = xIndex - xIndex%3;
        int yStartIndex = yIndex - yIndex%3;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(!elements[xStartIndex + i][yStartIndex + j].getPossibleValues().contains((Integer)oldValue))
                elements[xStartIndex + i][yStartIndex + j].getPossibleValues().add((Integer)oldValue);
            }
        }
    }

    public void generateRandomNumbers(int howMany) {
        //initial check of range
        if(howMany < 1 || howMany > 81) {
            System.out.println("Sorry, can't generate " + howMany + " numbers, valid range is: 1 - 81");
            return;
        }

        //check if there are enough not set elements to fill
        int numberOfEmptyElements = getNumberOfEmptyElements();
        if(numberOfEmptyElements < howMany) {
            System.out.println("Sorry, can't generate that many numbers: " + howMany + " there is only: " +
                    + numberOfEmptyElements + " empty elements left.");
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
                if(elements[i][j].getValue() == 0 && elements[i][j].getPossibleValues().size() > 0) {
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
        SudokuElement chosenElement = elements[chosenFieldCoordinates.getX()][chosenFieldCoordinates.getY()];
        int chosenValue = chosenElement.getPossibleValues().get(random.nextInt(chosenElement.getPossibleValues().size()));
        setElement(chosenFieldCoordinates.getX(), chosenFieldCoordinates.getY(), chosenValue);
        return true;
    }

    /**
     * Method assumes that original board is a square, eg. 9x9
     * @param original
     * @return
     */
    private SudokuElement[][] copySudokuBoard(SudokuElement[][] original) {

        //check if original is a square
        int originalSide = original.length;
        for(int i=0; i<original.length; i++) {
            if(original[i].length != originalSide) {
                throw new IllegalArgumentException("Array passed to this method must be a square.");
            }
        }

        //create a copy
        SudokuElement[][] copy = new SudokuElement[original.length][original.length];
        for(int i=0; i<original.length; i++) {
            for(int j=0; j<original.length; j++) {
                copy[i][j] = original[i][j].getCopy();
            }
        }
        return copy;
    }

    private int getNumberOfEmptyElements() {
        int numberOfEmptyElements = 0;
        for(int i=0; i<elements.length; i++) {
            for(int j=0; j<elements.length; j++) {
                if(elements[i][j].getValue() == 0) {
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
        SudokuElement[][] boardCopy = copySudokuBoard(this.elements);
        if(!solveSudoku(true)) {
            System.out.println("Sorry but the board is not solvable at the moment, no numbers generated. You can remove " +
                    "some numbers and try again.");
            this.elements = boardCopy;
            return;
        }
        this.elements = boardCopy;

        //check if there are enough not set elements to fill
        int numberOfEmptyElements = getNumberOfEmptyElements();
        if(numberOfEmptyElements < howManyNumbers) {
            System.out.println("Sorry, can't generate that many numbers: " + howManyNumbers + " there is only: " +
                    + numberOfEmptyElements + " empty elements left.");
            return;
        }

        //generate numbers
        while(howManyNumbers > 0) {
            SudokuElement[][] boardCopyBefore = copySudokuBoard(this.elements);
            generateOneNumber();
            SudokuElement[][] boardCopyAfter = copySudokuBoard(this.elements);
            if(solveSudoku(true)) {
                howManyNumbers--;
                this.elements = boardCopyAfter;
                continue;
            } else {
                this.elements = boardCopyBefore;
            }
        }

        printBoard();
    }

    public void clearTheBoard() {
        this.elements = SudokuArrayFactory.getEmptySudokuArray();
    }

}
