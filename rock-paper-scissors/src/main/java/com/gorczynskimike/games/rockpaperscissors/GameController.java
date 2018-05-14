package com.gorczynskimike.games.rockpaperscissors;

public class GameController {

    private Game game;
    private UserInterface userInterface;
    private boolean end = false;

    public void start() {
        userInterface = new UserInterface();
        // you can play against either HonestAiPlayer, CrookedAiPlayer or CrookedPreciseAiPlayer
        // - pass corresponding argument to method
        game = new Game(new UserPlayer(userInterface, this), new CrookedPreciseAiPlayer(90,10), userInterface);
        while(!end) {
            GameDto gameResult = game.startNewGame();
            if(gameResult != null) {
                System.out.println(gameResult.toString());
            }
        }
    }

    public void endProgram() {
        end = true;
        game.stopTheGame();
    }

    public void startNewGame() {
        game.stopTheGame();
    }

}
