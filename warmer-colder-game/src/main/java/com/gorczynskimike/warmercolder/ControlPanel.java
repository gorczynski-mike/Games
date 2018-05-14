package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel {

    private final JButton startGameButton = new JButton("Start game");
    private final JButton endGameButton = new JButton("End game");
    private final List<JButton> buttons = new ArrayList<>();

    private ActionListener startGameButtonListener;
    private ActionListener endGameButtonListener;

    public ControlPanel() {
        startGameButton.addActionListener(event -> startGameButtonListener.performAction("start new game"));
        endGameButton.addActionListener(event -> endGameButtonListener.performAction("end the game"));
        buttons.add(startGameButton);
        buttons.add(endGameButton);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(AppBorderFactory.getStandardBorder("Control Panel"));
        add(startGameButton);
        add(endGameButton);
    }

    public void setStartGameButtonListener(ActionListener startGameButtonListener) {
        this.startGameButtonListener = startGameButtonListener;
    }

    public void setEndGameButtonListener(ActionListener endGameButtonListener) {
        this.endGameButtonListener = endGameButtonListener;
    }

    public void disableButtons() {
        for(JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    public void enableButtons(){
        for(JButton button : buttons) {
            button.setEnabled(true);
        }
    }
}
