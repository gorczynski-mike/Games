package com.gorczynskimike.sudoku.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    private SudokuGenerator() {
        //do nothing
    }

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
        int chosenValue = chosenElement.getPossibleValues().get(random.nextInt(chosenElement.getPossibleValues().size()));
        simpleSudokuBoard.setElement(chosenFieldCoordinates.getX(), chosenFieldCoordinates.getY(), chosenValue);
        return true;
    }

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
            if(chosenElement.getPossibleValues().size() == 0) {
                listOfEmptyFields.remove(chosenElement);
                continue;
            }
            int chosenValue = chosenElement.getPossibleValues().get(random.nextInt(chosenElement.getPossibleValues().size()));
            simpleSudokuBoard.setElement(chosenFieldCoordinates.getX(), chosenFieldCoordinates.getY(), chosenValue);
            if(!simpleSudokuBoard.checkIfSolvable()) {
                simpleSudokuBoard.unsetElement(chosenFieldCoordinates.getX(), chosenFieldCoordinates.getY());
                chosenElement.getPossibleValues().remove((Integer) chosenValue);
                continue;
            } else {
                generatedNumberSuccessfully = true;
            }
        }
        return generatedNumberSuccessfully;
    }

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

    private static int getNumberOfEmptyElements(SudokuElement[][] sudokuElementsArray) {
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

    private static List<CoordinatePair> getListOfEmptyFieldsCoordinates(SudokuElement[][] sudokuElementsArray) {
        List<CoordinatePair> listOfEmptyFields = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudokuElementsArray[i][j].getValue() == 0 && sudokuElementsArray[i][j].getPossibleValues().size() > 0) {
                    listOfEmptyFields.add(new CoordinatePair(i,j));
                }
            }
        }
        return listOfEmptyFields;
    }

}
