package com.gorczynskimike.sudoku;

import org.junit.Assert;
import org.junit.Test;

public class SudokuBoardTestSuite {

    @Test
    public void testCreateEmptyBoard() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();

        //When
        String boardString = sudokuBoard.getBoardStringRepresentation();

        //Then
        Assert.assertEquals(
        "-------------------" + System.lineSeparator() +
                "| | | | | | | | | |" + System.lineSeparator() +
                "-------------------" + System.lineSeparator() +
                "| | | | | | | | | |" + System.lineSeparator() +
                "-------------------" + System.lineSeparator() +
                "| | | | | | | | | |" + System.lineSeparator() +
                "-------------------" + System.lineSeparator() +
                "| | | | | | | | | |" + System.lineSeparator() +
                "-------------------" + System.lineSeparator() +
                "| | | | | | | | | |" + System.lineSeparator() +
                "-------------------" + System.lineSeparator() +
                "| | | | | | | | | |" + System.lineSeparator() +
                "-------------------" + System.lineSeparator() +
                "| | | | | | | | | |" + System.lineSeparator() +
                "-------------------" + System.lineSeparator() +
                "| | | | | | | | | |" + System.lineSeparator() +
                "-------------------" + System.lineSeparator() +
                "| | | | | | | | | |" + System.lineSeparator() +
                "-------------------", boardString);
    }

    @Test
    public void testSetElementValue() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();

        //When
        sudokuBoard.setElementValue(0,0,1);
        sudokuBoard.setElementValue(4,4,5);
        sudokuBoard.setElementValue(8,8,9);
        String boardString = sudokuBoard.getBoardStringRepresentation();

        //Then
        Assert.assertEquals(
                "-------------------" + System.lineSeparator() +
                        "|1| | | | | | | | |" + System.lineSeparator() +
                        "-------------------" + System.lineSeparator() +
                        "| | | | | | | | | |" + System.lineSeparator() +
                        "-------------------" + System.lineSeparator() +
                        "| | | | | | | | | |" + System.lineSeparator() +
                        "-------------------" + System.lineSeparator() +
                        "| | | | | | | | | |" + System.lineSeparator() +
                        "-------------------" + System.lineSeparator() +
                        "| | | | |5| | | | |" + System.lineSeparator() +
                        "-------------------" + System.lineSeparator() +
                        "| | | | | | | | | |" + System.lineSeparator() +
                        "-------------------" + System.lineSeparator() +
                        "| | | | | | | | | |" + System.lineSeparator() +
                        "-------------------" + System.lineSeparator() +
                        "| | | | | | | | | |" + System.lineSeparator() +
                        "-------------------" + System.lineSeparator() +
                        "| | | | | | | | |9|" + System.lineSeparator() +
                        "-------------------", boardString);
    }

    @Test
    public void testCantAssignTheSameNumberTwiceInARow() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();

        //When
        boolean resultFirstElement = sudokuBoard.setElementValue(0,0,1);
        boolean resultSameRow = sudokuBoard.setElementValue(8,0,1);
        boolean resultDifferentRow = sudokuBoard.setElementValue(8,1,1);

        //Then
        Assert.assertEquals(true, resultFirstElement);
        Assert.assertEquals(false, resultSameRow);
        Assert.assertEquals(true, resultDifferentRow);
    }

    @Test
    public void testCantAssignTheSameNumberTwiceInAColumn() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();

        //When
        boolean resultFirstElement = sudokuBoard.setElementValue(0,0,1);
        boolean resultSameColumn = sudokuBoard.setElementValue(0,8,1);
        boolean resultDifferentColumn = sudokuBoard.setElementValue(1,8,1);

        //Then
        Assert.assertEquals(true, resultFirstElement);
        Assert.assertEquals(false, resultSameColumn);
        Assert.assertEquals(true, resultDifferentColumn);
    }

    @Test
    public void testCantAssignTheSameNumberTwiceInASection() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();

        //When
        boolean resultFirstElement = sudokuBoard.setElementValue(0,0,1);
        boolean resultSameSection = sudokuBoard.setElementValue(2,2,1);
        boolean resultDifferentSection = sudokuBoard.setElementValue(3,3,1);

        //Then
        Assert.assertEquals(true, resultFirstElement);
        Assert.assertEquals(false, resultSameSection);
        Assert.assertEquals(true, resultDifferentSection);
    }
}
