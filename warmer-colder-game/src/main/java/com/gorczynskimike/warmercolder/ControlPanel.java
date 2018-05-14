package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private final JTextField inputField = new JTextField(10);
    private final JButton submitButton = new JButton("Submit");
    private final JButton startGameButton = new JButton("Start game");
    private final JButton endGameButton = new JButton("End game");

    private ActionListener submitButtonListener;
    private ActionListener startGameButtonListener;
    private ActionListener endGameButtonListener;

    public ControlPanel() {
        submitButton.addActionListener(event -> {
            submitButtonListener.performAction(inputField.getText());
            inputField.setText("");
        });
        startGameButton.addActionListener(event -> startGameButtonListener.performAction("start new game"));
        endGameButton.addActionListener(event -> endGameButtonListener.performAction("end the game"));

        setLayout(new FlowLayout());
        add(startGameButton);
        add(endGameButton);
        add(inputField);
        add(submitButton);
    }

    public void setSubmitButtonListener(ActionListener submitButtonListener) {
        this.submitButtonListener = submitButtonListener;
    }

    public void setStartGameButtonListener(ActionListener startGameButtonListener) {
        this.startGameButtonListener = startGameButtonListener;
    }

    public void setEndGameButtonListener(ActionListener endGameButtonListener) {
        this.endGameButtonListener = endGameButtonListener;
    }
}
