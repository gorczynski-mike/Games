package com.gorczynskimike.sudoku.userinterface;

/**
 * Class that validates commands sent by the user. It holds valid inputs in form of a regular expression that either
 * matches the commands or not.
 */
public class UserInputValidator {

    private static final String VALID_INPUT = "\\d,\\d,\\d|sudoku|\\d,\\d,unset|random|random,\\d+|solvable,\\d+|clear|easy|medium|hard|remove,\\d+";
    private static final String VALID_NEW_GAME_CHOICE = "[yn]";


    private MessageService messageService = (text) -> {
        System.out.println(text);
    };

    /**
     * Changes MessageService used by this class. Standard sysout is the default MessageService.
     * @param messageService MessageService passed as an argument will be used by this class.
     */
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Checks if passed userInput matches one of the valid commands.
     * @param userInput user input.
     * @return True if userInput is valid, False otherwise.
     */
    public String validateUserInput(String userInput) {
        userInput = userInput.toLowerCase();
        if(!userInput.matches(VALID_INPUT)) {
            messageService.sendMessage("Invalid format.");
            return "error";
        } else {
            return userInput;
        }
    }

    /**
     * Checks if passed user input is a valid input for 'new game decision' query.
     * @param newGameDecision user input
     * @return True if user input matches 'new game decision' pattern, False otherwise
     */
    public boolean validateNewGameDecision(String newGameDecision) {
        newGameDecision = newGameDecision.toLowerCase();
        while(!newGameDecision.matches(VALID_NEW_GAME_CHOICE)) {
            messageService.sendMessage("Sorry, invalid format, type either 'y' or 'n'.");
            return false;
        }
        return true;
    }
}
