package com.gorczynskimike.warmercolder;


public class Application {

    private final UserInteractionService userInteractionService = new ConsoleUserInteractionService(this);
    private final MessageService messageService = new ConsoleMessageService();

    public void startGame() {
    }

    public void endGame() {

    }

    public Integer getUserNumber() {
        return userInteractionService.getUserNumber();
    }

    public void sendMessage(String message) {
        this.messageService.sendMessage(message);
    }
}
