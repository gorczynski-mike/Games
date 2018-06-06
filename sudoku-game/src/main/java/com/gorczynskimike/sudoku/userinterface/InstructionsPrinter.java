package com.gorczynskimike.sudoku.userinterface;

/**
 * A simple utility class that prints the instructions (valid commands) to MessageService passed as an argument
 */
public final class InstructionsPrinter {

    private InstructionsPrinter() {
        //do nothing
    }

    public static void printInstructions(MessageService messageService) {
        messageService.sendMessage("");
        messageService.sendMessage("Please type: ");
        messageService.sendMessage("- new value for the board in format 'x,y,value' (<value> is a single digit number)");
        messageService.sendMessage("- 'sudoku' to solve the board");
        messageService.sendMessage("- 'x,y,unset' to unset given element");
        messageService.sendMessage("- 'random' to generate one new number on the board");
        messageService.sendMessage("- 'random,z' to generate <z> new numbers on the board");
        messageService.sendMessage("- 'solvable,z' to generate <z> new numbers on the board");
        messageService.sendMessage("- 'clear' to clear the board");
        messageService.sendMessage("- 'easy' to generate new easy sudoku (possible to solve without guessing)");
        messageService.sendMessage("- 'medium' to generate new medium sudoku (around 2* guesses needed to solve)");
        messageService.sendMessage("- 'hard' to generate hard sudoku (around 5* guesses needed to solve)");
        messageService.sendMessage("- * because of slightly random nature of algorithm it's not always possible to precisely predict number of guesses");
        messageService.sendMessage("(IMPORTANT: valid range for <x>, <y>, <value>: 1-9, valid range for <z>: 1-81)");
        messageService.sendMessage("(IMPORTANT: 'sudoku', 'unset', 'solvable', 'clear', 'easy' and 'random' are complete english words)");
        messageService.sendMessage("(IMPORTANT: generated random numbers won't violate sudoku rules, but might create unsolvable sudoku)");
        messageService.sendMessage("(IMPORTANT: 'solvable' guarantees that created board will be solvable but is a slower algorithm)");
    }

}
