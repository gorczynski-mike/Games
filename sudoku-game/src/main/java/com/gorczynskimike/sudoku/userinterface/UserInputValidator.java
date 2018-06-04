package com.gorczynskimike.sudoku.userinterface;

public class UserInputValidator {

    private static final String VALID_INPUT = "\\d,\\d,\\d|sudoku|\\d,\\d,unset|random|random,\\d+|solvable,\\d+|clear|easy|medium|hard";
    private static final String VALID_NEW_GAME_CHOICE = "[yn]";

    private MessageService messageService = (text) -> {
        System.out.println(text);
    };

    private UserInputService userInputService;

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setUserInputService(UserInputService userInputService) {
        this.userInputService = userInputService;
    }

    public String getUserInput() {
        InstructionsPrinter.printInstructions(this.messageService);
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

    public boolean getNewGameDecision() throws InterruptedException {
        messageService.acceptMessage("Do you want to start new game? Y - yes, N - exit application");
        String userInput = userInputService.getNewGameDecision();
        userInput = userInput.toLowerCase();
        while(!userInput.matches(VALID_NEW_GAME_CHOICE)) {
            messageService.acceptMessage("Sorry, invalid format, type either 'y' or 'n'.");
            userInput = userInputService.getNewGameDecision();
            userInput = userInput.toLowerCase();
        }
        return userInput.matches("y");
    }
}
