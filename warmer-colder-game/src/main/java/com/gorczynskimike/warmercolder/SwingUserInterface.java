package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class SwingUserInterface extends JFrame{

    private final Game game = new Game();
    private final MessageService consoleMessageService = new ConsoleMessageService();

    private final TextPanel textPanel = new TextPanel();
    private final ControlPanel controlPanel = new ControlPanel();

    {
        controlPanel.setSubmitButtonListener(text -> game.checkUserNumber(text));
        controlPanel.setStartGameButtonListener(text -> {
            textPanel.clearText();
            game.startNewGame();
        });
        controlPanel.setEndGameButtonListener(text -> game.userEndsTheGame());
        game.setGameActionListener(text -> {
            textPanel.appendText(text + System.lineSeparator());
            consoleMessageService.sendMessage(text);
        } );
    }

    public SwingUserInterface() {
        super("Warmer colder game");
        setSize(1024,800);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(textPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
        textPanel.appendText("Hello user. You can start the game after clicking \"Start game\" button." + System.lineSeparator());
    }

}
