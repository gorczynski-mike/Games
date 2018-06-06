package com.gorczynskimike.sudoku.board;

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
            throw new IllegalArgumentException("Sorry, can't generate " + howManyNumbersToGenerate + " numbers, valid range is: 1 - 81");
//            System.out.println("Sorry, can't generate " + howManyNumbersToGenerate + " numbers, valid range is: 1 - 81");
//            return;
        }

        SudokuElement[][] sudokuElementsArray = simpleSudokuBoard.getSudokuElementsArrayCopy();

        //check if there are enough not set sudokuElementsArray to fill
        int numberOfEmptyElements = getNumberOfEmptyElements(sudokuElementsArray);
        if(numberOfEmptyElements < howManyNumbersToGenerate) {
            throw new IllegalStateException("Sorry, can't generate that many numbers: " + howManyNumbersToGenerate + " there is only: " +
                    + numberOfEmptyElements + " empty fields left.");
//            System.out.println("Sorry, can't generate that many numbers: " + howManyNumbersToGenerate + " there is only: " +
//                    + numberOfEmptyElements + " empty sudokuElementsArray left.");
//            return;
        }

        //generate
        int succesfullyGeneratedNumbers = 0;
        while(howManyNumbersToGenerate > 0) {
            boolean wasNumberGenerated = SudokuGenerator.generateOneRandomNumber(simpleSudokuBoard);
            if(!wasNumberGenerated) {
                throw new IllegalStateException("Sorry, it's impossible to generate more numbers without breaking sudoku rules. " +
                        succesfullyGeneratedNumbers + " numbers were generated successfully");
//                System.out.println("Sorry, it's impossible to generate more numbers without breaking sudoku rules. " +
//                succesfullyGeneratedNumbers + " numbers were generated successfully");
//                break;
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
    public static boolean generateOneRandomNumberSolvable(SimpleSudokuBoard simpleSudokuBoard, int mainLoopLimit) {
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
        int counter = 0;
        while(!generatedNumberSuccessfully) {
            counter++;
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
            if(!simpleSudokuBoardCopy.checkIfSolvableWithLimit(mainLoopLimit)) {
                chosenElement.removePossibleValue(chosenValue);
            } else {
                simpleSudokuBoard.setElement(chosenFieldCoordinates.getX(), chosenFieldCoordinates.getY(), chosenValue);
                generatedNumberSuccessfully = true;
            }

            if(counter == 10) {
                System.out.println("One number generator limit reached. Out of function generateOneRandomNumberSolvable");
                return false;
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
            throw new IllegalStateException("Sorry but the board is not solvable at the moment, no numbers generated. You can remove " +
                    "some numbers and try again.");
//            System.out.println("Sorry but the board is not solvable at the moment, no numbers generated. You can remove " +
//                    "some numbers and try again.");
//            return;
        }

        //check if there are enough not set sudokuElementsArray to fill
        int numberOfEmptyElements = getNumberOfEmptyElements(simpleSudokuBoard.getSudokuElementsArrayCopy());
        if(numberOfEmptyElements < howManyNumbersToGenerate) {
            throw new IllegalStateException("Sorry, can't generate that many numbers: " + howManyNumbersToGenerate + " there is only: " +
                    + numberOfEmptyElements + " empty sudoku elements left.");
//            System.out.println("Sorry, can't generate that many numbers: " + howManyNumbersToGenerate + " there is only: " +
//                    + numberOfEmptyElements + " empty sudoku elements left.");
//            return;
        }

        //generate numbers
        while(howManyNumbersToGenerate > 0) {
            generateOneRandomNumberSolvable(simpleSudokuBoard, Integer.MAX_VALUE);
            howManyNumbersToGenerate--;
        }
    }

    /**
     * It removes given number of values from the board.
     * @param howManyNumbersToRemove How many numbers to remove.
     * @param simpleSudokuBoard The board that the values will be removed from
     * @throws IllegalStateException If there are not enough elements to remove from the board, the exception is thrown.
     */
    public static void removeRandomNumbers(int howManyNumbersToRemove, SimpleSudokuBoard simpleSudokuBoard) {
        List<CoordinatePair> notEmptyFields = getListOfNotEmptyFieldsCoordinates(simpleSudokuBoard.getSudokuElementsArrayCopy());
        if(howManyNumbersToRemove > notEmptyFields.size()) {
            throw new IllegalStateException("Cannot remove " + howManyNumbersToRemove + " numbers. There are only " +
            notEmptyFields.size() + " set elements.");
        }

        while(howManyNumbersToRemove > 0) {
            removeOneRandomNumberFromTheBoard(simpleSudokuBoard);
            howManyNumbersToRemove--;
        }
    }

    /**
     * It generates an easy (possible to solve without guessing) sudoku board. Given board will be cleared first
     * and then new numbers will be generated.
     * @param simpleSudokuBoard The board passed will be cleared and new easy configuration will be generated on it.
     * @return Board representing easy sudoku.
     */
    public static SimpleSudokuBoard generateEasySudoku(SimpleSudokuBoard simpleSudokuBoard) {
        return generateSudokuNGuesses(0, simpleSudokuBoard);
    }

    /**
     * It generates an medium (around 2 guesses to solve) sudoku board. Given board will be cleared first
     * and then new numbers will be generated.
     * @param simpleSudokuBoard The board passed will be cleared and new easy configuration will be generated on it.
     * @return Board representing medium sudoku.
     */
    public static SimpleSudokuBoard generateMediumSudoku(SimpleSudokuBoard simpleSudokuBoard) {
        return generateSudokuNGuesses(2, simpleSudokuBoard);
    }

    /**
     * It generates an hard (around 5 guesses to solve) sudoku board. Given board will be cleared first
     * and then new numbers will be generated.
     * @param simpleSudokuBoard The board passed will be cleared and new easy configuration will be generated on it.
     * @return Board representing hard sudoku.
     */
    public static SimpleSudokuBoard generateHardSudoku(SimpleSudokuBoard simpleSudokuBoard) {
        return generateSudokuNGuesses(5, simpleSudokuBoard);
    }

    /**
     * It generates a new sudoku board that is possible to solve in given number of guesses. (Final number of guesses
     * cannot be guaranteed but should be close to given value)
     * @param goalGuesses How many guesses should be needed to solve generated sudoku.
     * @param simpleSudokuBoard The board that will be cleared and on which new sudoku will be generated.
     * @return Generated board.
     */
    private static SimpleSudokuBoard generateSudokuNGuesses(int goalGuesses, SimpleSudokuBoard simpleSudokuBoard) {
        simpleSudokuBoard.clearTheBoard();
        int howManyGuessesToSolve = simpleSudokuBoard.howManyGuessesNeededToSolve();
        outerLoop:
        while(howManyGuessesToSolve > goalGuesses) {
//            howManyGuessesToSolveOld = howManyGuessesToSolve;
//            SimpleSudokuBoard copy = new SimpleSudokuBoard(simpleSudokuBoard);

            int triesCounter = 0;
            while(!generateOneRandomNumberSolvable(simpleSudokuBoard, 3000)) {
                triesCounter++;
                if(triesCounter > 5) {
                    System.out.println("Tries counter limit reached.");
                    simpleSudokuBoard.clearTheBoard();
                    howManyGuessesToSolve = Integer.MAX_VALUE;
                    continue outerLoop;
                }
            }
            howManyGuessesToSolve = simpleSudokuBoard.howManyGuessesNeededToSolve();
            if(howManyGuessesToSolve == -1) {
                simpleSudokuBoard.clearTheBoard();
                howManyGuessesToSolve = simpleSudokuBoard.howManyGuessesNeededToSolve();
            }
            while(howManyGuessesToSolve < goalGuesses) {
                System.out.println("too little guesses, removing one number");
                removeOneRandomNumberFromTheBoard(simpleSudokuBoard);
                howManyGuessesToSolve = simpleSudokuBoard.howManyGuessesNeededToSolve();
                System.out.println("new guesses: " + howManyGuessesToSolve);
            }
        }
        System.out.println("how many guesses: " + howManyGuessesToSolve);
        System.out.println("Final number of guesses: " + simpleSudokuBoard.howManyGuessesNeededToSolve());
        simpleSudokuBoard.recalculateBoard();
        return simpleSudokuBoard;
    }

    /**
     * It removes one random number from the board.
     * @param simpleSudokuBoard The board from which the number will be removed.
     * @throws IllegalStateException If there are no set elements on the board the exception is thrown.
     */
    private static void removeOneRandomNumberFromTheBoard(SimpleSudokuBoard simpleSudokuBoard) {
        List<CoordinatePair> listOfNotEmptyFields = getListOfNotEmptyFieldsCoordinates(simpleSudokuBoard.getSudokuElementsArrayCopy());
        if(listOfNotEmptyFields.size() == 0) {
            throw new IllegalStateException("Cannot remove element, all elements are empty.");
        }
        Random random = new Random();
        CoordinatePair removedCoordinates = listOfNotEmptyFields.get(random.nextInt(listOfNotEmptyFields.size()));
        simpleSudokuBoard.unsetElement(removedCoordinates.getX(), removedCoordinates.getY());
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

    /**
     * Returns list of sudoku elements coordinates that are not empty (that had been set)
     * @param sudokuElementsArray An array of sudoku elements.
     * @return List of coordinates of not empty elements.
     */
    private static List<CoordinatePair> getListOfNotEmptyFieldsCoordinates(SudokuElement[][] sudokuElementsArray) {
        List<CoordinatePair> listOfNotEmptyFields = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudokuElementsArray[i][j].getValue() != 0) {
                    listOfNotEmptyFields.add(new CoordinatePair(i,j));
                }
            }
        }
        return listOfNotEmptyFields;
    }
}
