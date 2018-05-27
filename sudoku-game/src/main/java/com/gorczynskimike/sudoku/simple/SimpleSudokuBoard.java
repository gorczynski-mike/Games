package com.gorczynskimike.sudoku.simple;

import java.util.Arrays;

public class SimpleSudokuBoard {

    public static void main(String[] args) {
//        SimpleSudokuBoard simpleSudokuBoard = new SimpleSudokuBoard();
//        simpleSudokuBoard.printBoard();
//        long startTime = System.currentTimeMillis();
//        simpleSudokuBoard.solveSudoku();
//        long endTime = System.currentTimeMillis();
//        System.out.println("It took " + (endTime - startTime) + " milliseconds to solve empty sudoku.");

        SimpleSudokuBoard simpleSudokuBoard2 = new SimpleSudokuBoard();
        simpleSudokuBoard2.setElement(0,0,5);
        simpleSudokuBoard2.setElement(0,1,6);
        simpleSudokuBoard2.setElement(0,2,7);
        long startTime2 = System.currentTimeMillis();
        simpleSudokuBoard2.solveSudoku();
        long endTime2 = System.currentTimeMillis();
        System.out.println("It took " + (endTime2 - startTime2) + " milliseconds to solve sudoku.");
    }

    private SudokuElement[][] elements = new SudokuElement[9][9];

    public SimpleSudokuBoard() {
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                elements[i][j] = new SudokuElement();
            }
        }
    }

    public void solveSudoku() {
        long startTime = System.currentTimeMillis();
        boolean isSolved = false;
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
                    System.out.println("Sorry, it's impossible to solve this sudoku.");
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
                System.out.println("Solved");
                printBoard();
                SudokuStack.printStackSize();
                System.out.println("Number of loops: " + counter);
                isSolved = true;
                break mainLoop;
            }
            if(modifiedElements > 0) {
                continue mainLoop;
            } else {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if(elements[i][j].getValue() == 0) {
//                            System.out.println("===============");
//                            printBoard();
                            int guessedNumber = elements[i][j].getPossibleValues().get(0);
                            SudokuStack.pushSudokuState(new SudokuState(this.elements, i, j, guessedNumber));
                            setElement(i,j,guessedNumber);
                            continue mainLoop;
                        }
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Solving sudoku procedure took " + (endTime - startTime) + " milliseconds.");
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

}