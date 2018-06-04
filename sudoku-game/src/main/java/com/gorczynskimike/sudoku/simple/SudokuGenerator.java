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
    public static boolean generateOneRandomNumberSolvable(SimpleSudokuBoard simpleSudokuBoard, int mainLoopLimit) {
        System.out.println("In function: generateOneRandomNumberSolvable");
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
                System.out.println("One number generator limit met. Out of function generateOneRandomNumberSolvable");
                return false;
            }

        }
        System.out.println("Out of function: generateOneRandomNumberSolvable");
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
            generateOneRandomNumberSolvable(simpleSudokuBoard, Integer.MAX_VALUE);
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
        System.out.println("In function: generateSudokuNGuesses");
        simpleSudokuBoard.clearTheBoard();
        int howManyGuessesToSolve = simpleSudokuBoard.howManyGuessesNeededToSolve();
//        int howManyGuessesToSolveOld;
        outerLoop:
        while(howManyGuessesToSolve > goalGuesses) {
            System.out.println(howManyGuessesToSolve);
//            howManyGuessesToSolveOld = howManyGuessesToSolve;
//            SimpleSudokuBoard copy = new SimpleSudokuBoard(simpleSudokuBoard);

            int triesCounter = 0;
            while(!generateOneRandomNumberSolvable(simpleSudokuBoard, 2000)) {
                triesCounter++;
                if(triesCounter > 5) {
                    System.out.println("Tries counter limit met.");
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
        System.out.println("Out of function: generateSudokuNGuesses");
        simpleSudokuBoard.recalculateBoard();
        return simpleSudokuBoard;
    }

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
