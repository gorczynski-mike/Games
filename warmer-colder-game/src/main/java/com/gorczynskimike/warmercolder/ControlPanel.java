package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private final JButton startGameButton = new JButton("Start game");
    private final JButton endGameButton = new JButton("End game");

    private ActionListener startGameButtonListener;
    private ActionListener endGameButtonListener;

    public ControlPanel() {
        startGameButton.addActionListener(event -> startGameButtonListener.performAction("start new game"));
        endGameButton.addActionListener(event -> endGameButtonListener.performAction("end the game"));

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(startGameButton);
        add(endGameButton);
    }

    public void setStartGameButtonListener(ActionListener startGameButtonListener) {
        this.startGameButtonListener = startGameButtonListener;
    }

    public void setEndGameButtonListener(ActionListener endGameButtonListener) {
        this.endGameButtonListener = endGameButtonListener;
    }
}
