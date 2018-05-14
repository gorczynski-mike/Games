package com.gorczynskimike.warmercolder;

import java.util.Scanner;

public class ConsoleUserInteractionService implements UserInteractionService{

    private final Application application;
    private final Scanner scanner = new Scanner(System.in);
    private static final String VALID_USER_NUMBER = "\\d+||x";

    public ConsoleUserInteractionService(Application application) {
        this.application = application;
    }

    public Integer getUserNumber() {
        boolean isUserNumberValid = false;
        int answerInt = -1;
        while(!isUserNumberValid) {
            isUserNumberValid = true;
            sendMessage("What is your number? (0 - 100)");
            String answer = scanner.nextLine();
            if(answer.equalsIgnoreCase("x")) {
                application.endGame();
                return null;
            }
            if(!answer.matches(VALID_USER_NUMBER)) {
                sendMessage("Invalid format, try again");
                isUserNumberValid = false;
                continue;
            }
            answerInt = Integer.parseInt(answer);
            if(answerInt < 0 || answerInt > 100) {
                sendMessage("Number too large or too small, try again. (Range 0 - 100 inclusive)");
                isUserNumberValid = false;
                continue;
            }
        }
        return answerInt;

    }

    private void sendMessage(String message) {
        this.application.sendMessage(message);
    }
}
