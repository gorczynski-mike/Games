package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class SwingUserInterface extends JFrame{

    private final Game game = new Game();

    private final TextPanel textPanel = new TextPanel();
    private final ControlPanel controlPanel = new ControlPanel();

    {
        controlPanel.setSubmitButtonListener(text -> game.checkUserNumber(text));
        controlPanel.setStartGameButtonListener(text -> game.startNewGame());
        controlPanel.setEndGameButtonListener(text -> game.userEndsTheGame());
        game.setGameActionListener(text -> textPanel.appendText(String.format(text + "%n")));
    }

    public SwingUserInterface() {
        super("Warmer colder game");
        setSize(600,600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(textPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

}
