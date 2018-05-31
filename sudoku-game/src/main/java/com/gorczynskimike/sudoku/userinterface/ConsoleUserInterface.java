package com.gorczynskimike.sudoku.userinterface;

import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {

    private static Scanner scanner = new Scanner(System.in);
    private static final String VALID_INPUT = "\\d,\\d,\\d|sudoku|\\d,\\d,unset|random|random,\\d+|solvable,\\d+|clear|easy|medium|hard";
    private static final String VALID_NEW_GAME_CHOICE = "[yn]";

    @Override
    public String getUserInput() {
        System.out.println("Please type: ");
        System.out.println("- new value for the board in format 'x,y,value' (<value> is a single digit number)");
        System.out.println("- 'sudoku' to solve the board");
        System.out.println("- 'x,y,unset' to unset given element");
        System.out.println("- 'random' to generate one number on the board");
        System.out.println("- 'random,z' to generate <z> numbers on the board");
        System.out.println("- 'solvable,z' to generate <z> numbers on the board");
        System.out.println("- 'clear' to clear the board");
        System.out.println("- 'easy' to generate easy sudoku");
        System.out.println("- 'medium' to generate medium sudoku");
        System.out.println("- 'hard' to generate hard sudoku");
        System.out.println("(IMPORTANT: valid range for <x>, <y>, <value>: 1-9, valid range for <z>: 1-81)");
        System.out.println("(IMPORTANT: 'sudoku', 'unset', 'solvable', 'clear', 'easy' and 'random' are complete english words)");
        System.out.println("(IMPORTANT: generated random numbers won't violate sudoku rules, but might create unsolvable sudoku)");
        System.out.println("(IMPORTANT: 'solvable' guarantees that created board will be solvable but is a slower algorithm)");
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
