package com.gorczynskimike.sudoku.userinterface;

public class UserInputValidator {

    private static final String VALID_INPUT = "\\d,\\d,\\d|sudoku|\\d,\\d,unset|random|random,\\d+|solvable,\\d+|clear|easy|medium|hard";
    private static final String VALID_NEW_GAME_CHOICE = "[yn]";

    private MessageService messageService = (text) -> {
        System.out.println(text);
    };

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public String validateUserInput(String userInput) {
        userInput = userInput.toLowerCase();
        if(!userInput.matches(VALID_INPUT)) {
            messageService.sendMessage("Invalid format.");
            return "error";
        } else {
            return userInput;
        }
    }

    public boolean validateNewGameDecision(String newGameDecision) {
        newGameDecision = newGameDecision.toLowerCase();
        while(!newGameDecision.matches(VALID_NEW_GAME_CHOICE)) {
            messageService.sendMessage("Sorry, invalid format, type either 'y' or 'n'.");
            return false;
        }
        return true;
    }
}
