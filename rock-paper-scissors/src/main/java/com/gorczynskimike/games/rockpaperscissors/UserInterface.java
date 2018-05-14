package com.gorczynskimike.games.rockpaperscissors;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner = new Scanner(System.in);
    private final String invalidUserNamePattern = "\\s*";
    private final String validWinningRoundsPattern = "(?!0)\\d+";
    private final String validUserChoice = "[123xn]{1}";
    private final String validUserConfirmation = "[yYnN]{1}";

    public String getUserName(){
        System.out.println("Hello user, what is your name?");
        String userName = scanner.nextLine();
        boolean userNameIsInvalid = userName.matches(invalidUserNamePattern);
        while(userNameIsInvalid) {
            System.out.println("Sorry, user name must contain at least one character different than whitespace. Try again.");
            userName = scanner.nextLine();
            userNameIsInvalid = userName.matches(invalidUserNamePattern);
        }
        return userName.trim();
    }

    public int getWinningNumberOfRounds() {
        System.out.println("Please type the winning number of rounds:");
        String winningRounds = scanner.nextLine();
        boolean winningRoundsIsValid = winningRounds.matches(validWinningRoundsPattern);
        while(!winningRoundsIsValid) {
            System.out.println("Sorry, winning number of rounds must be a positive number. Try again.");
            winningRounds = scanner.nextLine();
            winningRoundsIsValid = winningRounds.matches(validWinningRoundsPattern);
        }
        return Integer.parseInt(winningRounds);
    }

    public void printInstructions() {
        System.out.println("Game instructions: ");
        System.out.println("Type '1' to cheat Rock");
        System.out.println("Type '2' to cheat Paper");
        System.out.println("Type '3' to cheat Scissors");
        System.out.println("Type 'x' to end the game (after confirmation)");
        System.out.println("Type 'n' to start a new game (after confirmation)");
    }

    public String getUserChoice() {
        System.out.println("Please type your move: ");
        String move = scanner.nextLine();
        boolean moveIsValid = move.matches(validUserChoice);
        while(!moveIsValid) {
            System.out.println("Sorry, invalid move. Try again. (valid: 1, 2, 3, x, n)");
            move = scanner.nextLine();
            moveIsValid = move.matches(validUserChoice);
        }
        return move;
    }

    public String confirmUserChoice() {
        System.out.println("Are you really sure? Y - yes, N - no");
        String userConfirmation = scanner.nextLine();
        boolean confirmationIsValid = userConfirmation.matches(validUserConfirmation);
        while(!confirmationIsValid) {
            System.out.println("Sorry, invalid choice. Try again. (valid: Y, N)");
            userConfirmation = scanner.nextLine();
            confirmationIsValid = userConfirmation.matches(validUserConfirmation);
        }
        return userConfirmation;
    }

}
