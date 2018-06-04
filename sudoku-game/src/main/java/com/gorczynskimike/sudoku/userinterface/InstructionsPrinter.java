package com.gorczynskimike.sudoku.userinterface;

public class InstructionsPrinter {

    public static void printInstructions(MessageService messageService) {
        messageService.acceptMessage("");
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

}
