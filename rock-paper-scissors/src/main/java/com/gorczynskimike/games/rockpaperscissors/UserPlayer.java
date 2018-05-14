package com.gorczynskimike.games.rockpaperscissors;

public class UserPlayer extends Player {

    private final String name;
    private final UserInterface userInterface;
    private final GameController gameController;

    public UserPlayer(UserInterface userInterface, GameController gameController) {
        this.userInterface = userInterface;
        this.name = userInterface.getUserName();
        this.gameController = gameController;
    }

    @Override
    public ValidPlays play() {
        mainLoop:
        while(true) {
            String move = userInterface.getUserChoice();
            if (move.equalsIgnoreCase("x")) {
                System.out.println("You are about to end the game. Please confirm you are sure.");
                if(userInterface.confirmUserChoice().equalsIgnoreCase("Y")) {
                    gameController.endProgram();
                    return null;
                } else {
                    System.out.println("You aborted ending the game. Please finish current game.");
                    continue mainLoop;
                }
            }
            if (move.equalsIgnoreCase("n")) {
                System.out.println("You are about to start a new game. Please confirm you are sure.");
                if(userInterface.confirmUserChoice().equalsIgnoreCase("Y")) {
                    gameController.startNewGame();
                    return null;
                } else {
                    System.out.println("You aborted starting a new game. Please finish current game.");
                    continue mainLoop;
                }
            }
            int moveInt = Integer.parseInt(move);
            return ValidPlays.values()[moveInt - 1];
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UserPlayer{" +
                "name='" + name + '\'' +
                '}';
    }
}
