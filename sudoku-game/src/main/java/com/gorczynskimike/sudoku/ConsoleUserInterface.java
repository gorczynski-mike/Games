package com.gorczynskimike.sudoku;

import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {

    private static Scanner scanner = new Scanner(System.in);
//    private static final String VALID_INPUT = "\\d,\\d,\\d|sudoku|\\d,\\d,unset";
    private static final String VALID_INPUT = "\\d,\\d,\\d|sudoku";
    private static final String VALID_NEW_GAME_CHOICE = "[yn]";

    @Override
    public String getUserInput() {
        System.out.println("Please type either new value for the board in format 'x,y,value' or type 'SUDOKU' to solve the game. " +
                "if you type 'x,y,unset' you will unset given element.");
        String userInput = scanner.nextLine();
        userInput = userInput.toLowerCase();
        if(!userInput.matches(VALID_INPUT)) {
            System.out.println("Invalid format.");
            return "error";
        } else {
            return userInput;
        }
    }

    @Override
    public boolean getNewGameDecision() {
        System.out.println("Do you want to start new game? Y - yes, N - exit application");
        String userInput = scanner.nextLine();
        userInput = userInput.toLowerCase();
        while(!userInput.matches(VALID_NEW_GAME_CHOICE)) {
            System.out.println("Sorry, invalid format, type either 'y' or 'n'.");
            userInput = scanner.nextLine();
            userInput = userInput.toLowerCase();
        }
        return userInput.matches("y") ? true : false;
    }
}
