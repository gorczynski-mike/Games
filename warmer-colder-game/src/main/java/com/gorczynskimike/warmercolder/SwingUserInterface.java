package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class SwingUserInterface extends JFrame{

    private final Game game = new Game();
    private final MessageService consoleMessageService = text -> System.out.println(text);

    private final ControlPanel controlPanel = new ControlPanel();
    private final TextPanel textPanel = new TextPanel();
    private final SubmitPanel submitPanel = new SubmitPanel();

    {
        submitPanel.setSubmitButtonListener(text -> game.checkUserNumber(text));
        controlPanel.setStartGameButtonListener(text -> {
            controlPanel.animationStarted();
            submitPanel.disableButtons();
            textPanel.clearTextAnimate();
        });
        textPanel.setAnimationFinishedListener(text -> {
            game.startNewGame();
            controlPanel.animationEnded();
            submitPanel.enableButtons();
        });
        controlPanel.setEndGameButtonListener(text -> game.userEndsTheGame());
        game.setGameActionListener(text -> {
            textPanel.appendText(text + System.lineSeparator());
            consoleMessageService.sendMessage(text);
        } );
        controlPanel.setFinishAnimationButtonListener(text -> {
            textPanel.interruptAnimation();
            game.startNewGame();
            controlPanel.animationEnded();
            submitPanel.enableButtons();
        });
    }

    public SwingUserInterface() {
        super("Warmer colder game");
        setSize(1024,800);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(controlPanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);

        setVisible(true);
        textPanel.appendText("Hello user. You can start the game after clicking \"Start game\" button." + System.lineSeparator());
    }

}
