package com.gorczynskimike.sudoku.userinterface;

import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {

    private static Scanner scanner = new Scanner(System.in);
    private static final String VALID_INPUT = "\\d,\\d,\\d|sudoku|\\d,\\d,unset|random|random,\\d+|solvable,\\d+|clear|easy|medium|hard";
    private static final String VALID_NEW_GAME_CHOICE = "[yn]";

    private MessageService messageService = (text) -> {
        System.out.println(text);
    };

    private UserInputService userInputService = () -> {
        return scanner.nextLine();
    };

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setUserInputService(UserInputService userInputService) {
        this.userInputService = userInputService;
    }

    @Override
    public String getUserInput() {
        printInstructions();
        String userInput = null;
        try {
            userInput = userInputService.getUserInput();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userInput = userInput.toLowerCase();
        if(!userInput.matches(VALID_INPUT)) {
            messageService.acceptMessage("Invalid format.");
            return "error";
        } else {
            return userInput;
        }
    }

    public void printInstructions() {
        messageService.acceptMessage("Please type: ");
        messageService.acceptMessage("- new value for the board in format 'x,y,value' (<value> is a single digit number)");
        messageService.acceptMessage("- 'sudoku' to solve the board");
        messageService.acceptMessage("- 'x,y,unset' to unset given element");
        messageService.acceptMessage("- 'random' to generate one new number on the board");
        messageService.acceptMessage("- 'random,z' to generate <z> new numbers on the board");
        messageService.acceptMessage("- 'solvable,z' to generate <z> new numbers on the board");
        messageService.acceptMessage("- 'clear' to clear the board");
        messageService.acceptMessage("- 'easy' to generate new easy sudoku (possible to solve without guessing)");
        messageService.acceptMessage("- 'medium' to generate new medium sudoku (around 2* guesses needed to solve)");
        messageService.acceptMessage("- 'hard' to generate hard sudoku (around 5* guesses needed to solve)");
        messageService.acceptMessage("- * because of slightly random nature of algorithm it's not always possible to precisely predict number of guesses");
        messageService.acceptMessage("(IMPORTANT: valid range for <x>, <y>, <value>: 1-9, valid range for <z>: 1-81)");
        messageService.acceptMessage("(IMPORTANT: 'sudoku', 'unset', 'solvable', 'clear', 'easy' and 'random' are complete english words)");
        messageService.acceptMessage("(IMPORTANT: generated random numbers won't violate sudoku rules, but might create unsolvable sudoku)");
        messageService.acceptMessage("(IMPORTANT: 'solvable' guarantees that created board will be solvable but is a slower algorithm)");
    }

    @Override
    public boolean getNewGameDecision() throws InterruptedException {
        messageService.acceptMessage("Do you want to start new game? Y - yes, N - exit application");
        String userInput = null;
        userInput = userInputService.getUserInput();
        userInput = userInput.toLowerCase();
        while(!userInput.matches(VALID_NEW_GAME_CHOICE)) {
            messageService.acceptMessage("Sorry, invalid format, type either 'y' or 'n'.");
            userInput = userInputService.getUserInput();
            userInput = userInput.toLowerCase();
        }
        return userInput.matches("y") ? true : false;
    }
}
