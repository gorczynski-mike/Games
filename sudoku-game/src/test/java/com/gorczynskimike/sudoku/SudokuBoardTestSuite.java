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

}
