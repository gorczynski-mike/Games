package com.gorczynskimike.sudoku;

import com.gorczynskimike.sudoku.complex.ComplexSudokuBoard;
import org.junit.Assert;
import org.junit.Test;

public class ComplexSudokuBoardTestSuite {

    @Test
    public void testCreateEmptyBoard() {
        //Given
        ComplexSudokuBoard complexSudokuBoard = new ComplexSudokuBoard();

        //When
        String boardString = complexSudokuBoard.getBoardStringRepresentation();

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
        ComplexSudokuBoard complexSudokuBoard = new ComplexSudokuBoard();

        //When
        complexSudokuBoard.setElementValue(0,0,1);
        complexSudokuBoard.setElementValue(4,4,5);
        complexSudokuBoard.setElementValue(8,8,9);
        String boardString = complexSudokuBoard.getBoardStringRepresentation();

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

//    @Test
//    public void testGetFilledBoardSequentially() {
//        //Given
//        SimpleSudokuBoard sudokuBoard = SimpleSudokuBoard.getFilledBoard();
//
//        //When
//        sudokuBoard.setElementValue(0,0,1);
//        sudokuBoard.setElementValue(4,4,5);
//        sudokuBoard.setElementValue(8,8,9);
//        String boardString = sudokuBoard.getBoardStringRepresentation();
//
//        //Then
//        Assert.assertEquals(
//                "-------------------" + System.lineSeparator() +
//                        "|1|2|3|4|5|6|7|8|9|" + System.lineSeparator() +
//                        "-------------------" + System.lineSeparator() +
//                        "| | | | | | | | | |" + System.lineSeparator() +
//                        "-------------------" + System.lineSeparator() +
//                        "| | | | | | | | | |" + System.lineSeparator() +
//                        "-------------------" + System.lineSeparator() +
//                        "| | | | | | | | | |" + System.lineSeparator() +
//                        "-------------------" + System.lineSeparator() +
//                        "| | | | |5| | | | |" + System.lineSeparator() +
//                        "-------------------" + System.lineSeparator() +
//                        "| | | | | | | | | |" + System.lineSeparator() +
//                        "-------------------" + System.lineSeparator() +
//                        "| | | | | | | | | |" + System.lineSeparator() +
//                        "-------------------" + System.lineSeparator() +
//                        "| | | | | | | | | |" + System.lineSeparator() +
//                        "-------------------" + System.lineSeparator() +
//                        "| | | | | | | | |9|" + System.lineSeparator() +
//                        "-------------------", boardString);
//    }

    @Test
    public void testCantAssignTheSameNumberTwiceInARow() {
        //Given
        ComplexSudokuBoard complexSudokuBoard = new ComplexSudokuBoard();

        //When
        boolean resultFirstElement = complexSudokuBoard.setElementValue(0,0,1);
        boolean resultSameRow = complexSudokuBoard.setElementValue(8,0,1);
        boolean resultDifferentRow = complexSudokuBoard.setElementValue(8,1,1);

        //Then
        Assert.assertEquals(true, resultFirstElement);
        Assert.assertEquals(false, resultSameRow);
        Assert.assertEquals(true, resultDifferentRow);
    }

    @Test
    public void testCantAssignTheSameNumberTwiceInAColumn() {
        //Given
        ComplexSudokuBoard complexSudokuBoard = new ComplexSudokuBoard();

        //When
        boolean resultFirstElement = complexSudokuBoard.setElementValue(0,0,1);
        boolean resultSameColumn = complexSudokuBoard.setElementValue(0,8,1);
        boolean resultDifferentColumn = complexSudokuBoard.setElementValue(1,8,1);

        //Then
        Assert.assertEquals(true, resultFirstElement);
        Assert.assertEquals(false, resultSameColumn);
        Assert.assertEquals(true, resultDifferentColumn);
    }

    @Test
    public void testCantAssignTheSameNumberTwiceInASection() {
        //Given
        ComplexSudokuBoard complexSudokuBoard = new ComplexSudokuBoard();

        //When
        boolean resultFirstElement = complexSudokuBoard.setElementValue(0,0,1);
        boolean resultSameSection = complexSudokuBoard.setElementValue(2,2,1);
        boolean resultDifferentSection = complexSudokuBoard.setElementValue(3,3,1);

        //Then
        Assert.assertEquals(true, resultFirstElement);
        Assert.assertEquals(false, resultSameSection);
        Assert.assertEquals(true, resultDifferentSection);
    }

    @Test
    public void testBordSolve() {
        ComplexSudokuBoard complexSudokuBoard = new ComplexSudokuBoard();
        complexSudokuBoard.solveBoard();
    }
}
