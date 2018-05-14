package com.gorczynskimike.warmercolder;

public class ConsoleMessageService implements MessageService{
    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
