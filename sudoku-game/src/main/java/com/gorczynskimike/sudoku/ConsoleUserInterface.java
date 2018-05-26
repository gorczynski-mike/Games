package com.gorczynskimike.sudoku;

import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {

    private static Scanner scanner = new Scanner(System.in);
    private static final String VALID_INPUT = "\\d,\\d,\\d|sudoku";

    @Override
    public String getUserInput() {
        System.out.println("Please type either new value for the board in format 'x,y,value' or type 'SUDOKU' to solve the game");
        String userInput = scanner.nextLine();
        userInput = userInput.toLowerCase();
        if(!userInput.matches(VALID_INPUT)) {
            System.out.println("Invalid format.");
            return "error";
        } else {
            return userInput;
        }
    }
}
