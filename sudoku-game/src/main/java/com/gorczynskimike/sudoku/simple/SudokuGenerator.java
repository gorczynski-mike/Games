package com.gorczynskimike.sudoku.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class that can generate new numbers on sudoku board given as an argument to the methods.
 */
public final class SudokuGenerator {

    /**
     * This utility class cannot be instantiated, thus a single private constructor.
     */
    private SudokuGenerator() {
        //do nothing
    }

    /**
     * Generate one random number on the board. New number won't violate sudoku rules but might create unsolvable board.
     * @param simpleSudokuBoard Board to be modified
     * @return information if it was possible to generate one random number
     */
    public static boolean generateOneRandomNumber(SimpleSudokuBoard simpleSudokuBoard) {
        if(simpleSudokuBoard == null) {
            return false;
        }
        SudokuElement[][] sudokuElementsArray = simpleSudokuBoard.getSudokuElementsArrayCopy();
        List<CoordinatePair> listOfEmptyFields = getListOfEmptyFieldsCoordinates(sudokuElementsArray);
        if(listOfEmptyFields.size() == 0) {
            return false;
        }
        Random random = new Random();
        CoordinatePair chosenFieldCoordinates = listOfEmptyFields.get(random.nextInt(listOfEmptyFields.size()));
        SudokuElement chosenElement = sudokuElementsArray[chosenFieldCoordinates.getX()][chosenFieldCoordinates.getY()];
        int chosenValue = chosenElement.getPossibleValuesCopy().get(random.nextInt(chosenElement.getPossibleValuesCopy().size()));
        simpleSudokuBoard.setElement(chosenFieldCoordinates.getX(), chosenFieldCoordinates.getY(), chosenValue);
        return true;
    }

    /**
     * Generate N random numbers on the board. New numbers won't violate sudoku rules but might create unsolvable board.
     * @param howManyNumbersToGenerate how many new numbers are going to be generated.
     * @param simpleSudokuBoard board to be modified.
     */
    public static void generateRandomNumbers(int howManyNumbersToGenerate, SimpleSudokuBoard simpleSudokuBoard) {
        //initial check of range
        if(howManyNumbersToGenerate < 1 || howManyNumbersToGenerate > 81) {
            System.out.println("Sorry, can't generate " + howManyNumbersToGenerate + " numbers, valid range is: 1 - 81");
            return;
        }

        SudokuElement[][] sudokuElementsArray = simpleSudokuBoard.getSudokuElementsArrayCopy();

        //check if there are enough not set sudokuElementsArray to fill
        int numberOfEmptyElements = getNumberOfEmptyElements(sudokuElementsArray);
        if(numberOfEmptyElements < howManyNumbersToGenerate) {
            System.out.println("Sorry, can't generate that many numbers: " + howManyNumbersToGenerate + " there is only: " +
                    + numberOfEmptyElements + " empty sudokuElementsArray left.");
            return;
        }

        //generate
        int succesfullyGeneratedNumbers = 0;
        while(howManyNumbersToGenerate > 0) {
            boolean wasNumberGenerated = SudokuGenerator.generateOneRandomNumber(simpleSudokuBoard);
            if(!wasNumberGenerated) {
                System.out.println("Sorry, it's impossible to generate more numbers without breaking sudoku rules.");
                break;
            } else {
                succesfullyGeneratedNumbers++;
            }
            howManyNumbersToGenerate--;
        }
        System.out.println(succesfullyGeneratedNumbers + " numbers were generated successfully.");
    }

    /**
     * Generate one random number on the board. This method guarantees that new number won't violate sudoku rules and
     * created board will be solvable.
     * @param simpleSudokuBoard board to be modified.
     * @return information if it was possible to generate one number.
     */
    public static boolean generateOneRandomNumberSolvable(SimpleSudokuBoard simpleSudokuBoard) {
        if(simpleSudokuBoard == null) {
            return false;
        }
        SudokuElement[][] sudokuElementsArray = simpleSudokuBoard.getSudokuElementsArrayCopy();
        List<CoordinatePair> listOfEmptyFields = getListOfEmptyFieldsCoordinates(sudokuElementsArray);
        if(listOfEmptyFields.size() == 0) {
            return false;
        }

        boolean generatedNumberSuccessfully = false;
        Random random = new Random();
        while(!generatedNumberSuccessfully) {
            if(listOfEmptyFields.size() == 0) {
                break;
            }
            CoordinatePair chosenFieldCoordinates = listOfEmptyFields.get(random.nextInt(listOfEmptyFields.size()));
            SudokuElement chosenElement = sudokuElementsArray[chosenFieldCoordinates.getX()][chosenFieldCoordinates.getY()];
            if(chosenElement.getPossibleValuesCopy().size() == 0) {
                listOfEmptyFields.remove(chosenElement);
                continue;
            }
            int chosenValue = chosenElement.getPossibleValuesCopy().get(random.nextInt(chosenElement.getPossibleValuesCopy().size()));
            SimpleSudokuBoard simpleSudokuBoardCopy = new SimpleSudokuBoard(simpleSudokuBoard);
            simpleSudokuBoardCopy.setElement(chosenFieldCoordinates.getX(), chosenFieldCoordinates.getY(), chosenValue);
            if(!simpleSudokuBoardCopy.checkIfSolvable()) {
                chosenElement.removePossibleValue(chosenValue);
            } else {
                simpleSudokuBoard.setElement(chosenFieldCoordinates.getX(), chosenFieldCoordinates.getY(), chosenValue);
                generatedNumberSuccessfully = true;
            }
        }
        return generatedNumberSuccessfully;
    }

    /**
     * Generate N random numbers on the board. This method guarantees that new numbers won't violate sudoku rules and
     * created board will be solvable.
     * @param simpleSudokuBoard board to be modified.
     * @return information if it was possible to generate one number.
     */
    public static void generateRandomNumbersSolvable(int howManyNumbersToGenerate, SimpleSudokuBoard simpleSudokuBoard) {
        //initial check of range
        if(howManyNumbersToGenerate < 0 || howManyNumbersToGenerate > 81) {
            throw new IllegalArgumentException("Valid range is 0-81. Passed value: " + howManyNumbersToGenerate);
        }

        //check if the board is solvable before any modifications
        if(!simpleSudokuBoard.checkIfSolvable()) {
            System.out.println("Sorry but the board is not solvable at the moment, no numbers generated. You can remove " +
                    "some numbers and try again.");
            return;
        }

        //check if there are enough not set sudokuElementsArray to fill
        int numberOfEmptyElements = getNumberOfEmptyElements(simpleSudokuBoard.getSudokuElementsArrayCopy());
        if(numberOfEmptyElements < howManyNumbersToGenerate) {
            System.out.println("Sorry, can't generate that many numbers: " + howManyNumbersToGenerate + " there is only: " +
                    + numberOfEmptyElements + " empty sudoku elements left.");
            return;
        }

        //generate numbers
        while(howManyNumbersToGenerate > 0) {
            generateOneRandomNumberSolvable(simpleSudokuBoard);
            howManyNumbersToGenerate--;
        }
    }

    //te metody mogłyby generować nowy obiekt

    public static SimpleSudokuBoard generateEasySudoku(SimpleSudokuBoard simpleSudokuBoard) {
        return generateSudokuNGuesses(0, simpleSudokuBoard);
    }

    public static SimpleSudokuBoard generateMediumSudoku(SimpleSudokuBoard simpleSudokuBoard) {
        return generateSudokuNGuesses(2, simpleSudokuBoard);
    }

    public static SimpleSudokuBoard generateHardSudoku(SimpleSudokuBoard simpleSudokuBoard) {
        return generateSudokuNGuesses(5, simpleSudokuBoard);
    }

    private static SimpleSudokuBoard generateSudokuNGuesses(int goalGuesses, SimpleSudokuBoard simpleSudokuBoard) {
        simpleSudokuBoard.clearTheBoard();
        int howManyGuessesToSolve = simpleSudokuBoard.howManyGuessesNeededToSolve();
        int howManyGuessesToSolveOld;
        int counter = 0;
        int totalCounter = 0;
        while(howManyGuessesToSolve > goalGuesses) {
            counter++;
            totalCounter++;
            System.out.println(howManyGuessesToSolve);
            howManyGuessesToSolveOld = howManyGuessesToSolve;
            SimpleSudokuBoard copy = new SimpleSudokuBoard(simpleSudokuBoard);
            generateOneRandomNumberSolvable(simpleSudokuBoard);
            howManyGuessesToSolve = simpleSudokuBoard.howManyGuessesNeededToSolve();
            if(howManyGuessesToSolve < goalGuesses) {
                simpleSudokuBoard = copy;
                howManyGuessesToSolve = howManyGuessesToSolveOld;
                System.out.println("restored: " + howManyGuessesToSolve);
            }
            if(counter == 300) {
                simpleSudokuBoard.clearTheBoard();
                howManyGuessesToSolve = simpleSudokuBoard.howManyGuessesNeededToSolve();
                counter = 0;
            }
        }
        System.out.println("Total counter: " + totalCounter);
        return simpleSudokuBoard;
    }

    /**
     * @param sudokuElementsArray array to be checked
     * @return number of elements not set
     */
    private static int getNumberOfEmptyElements(SudokuElement[][] sudokuElementsArray) {
        return getListOfEmptyFieldsCoordinates(sudokuElementsArray).size();
    }

    /**
     * @param sudokuElementsArray array to be checked
     * @return list of coordinates of elements not set
     */
    private static List<CoordinatePair> getListOfEmptyFieldsCoordinates(SudokuElement[][] sudokuElementsArray) {
        List<CoordinatePair> listOfEmptyFields = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudokuElementsArray[i][j].getValue() == 0 && sudokuElementsArray[i][j].getPossibleValuesCopy().size() > 0) {
                    listOfEmptyFields.add(new CoordinatePair(i,j));
                }
            }
        }
        return listOfEmptyFields;
    }

}
