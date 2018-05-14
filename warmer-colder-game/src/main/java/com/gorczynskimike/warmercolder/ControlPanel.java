package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel {

    private final JButton startGameButton = new JButton("Start game");
    private final JButton endGameButton = new JButton("End game");
    private final JButton finishAnimationButton = new JButton("Finish animation");
    private final List<JButton> buttons = new ArrayList<>();

    private ActionListener startGameButtonListener;
    private ActionListener endGameButtonListener;
    private ActionListener finishAnimationButtonListener;

    public ControlPanel() {
        startGameButton.addActionListener(event -> startGameButtonListener.performAction("start new game"));
        endGameButton.addActionListener(event -> endGameButtonListener.performAction("end the game"));
        finishAnimationButton.addActionListener(event -> finishAnimationButtonListener.performAction("finish animation"));
        buttons.add(startGameButton);
        buttons.add(endGameButton);
        finishAnimationButton.setVisible(false);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(AppBorderFactory.getStandardBorder("Control Panel"));
        add(startGameButton);
        add(endGameButton);
        add(finishAnimationButton);
    }

    public void setStartGameButtonListener(ActionListener startGameButtonListener) {
        this.startGameButtonListener = startGameButtonListener;
    }

    public void setEndGameButtonListener(ActionListener endGameButtonListener) {
        this.endGameButtonListener = endGameButtonListener;
    }

    public void setFinishAnimationButtonListener(ActionListener finishAnimationButtonListener) {
        this.finishAnimationButtonListener = finishAnimationButtonListener;
    }

    public void animationStarted() {
        for(JButton button : buttons) {
            button.setEnabled(false);
        }
        finishAnimationButton.setVisible(true);
    }

    public void animationEnded(){
        for(JButton button : buttons) {
            button.setEnabled(true);
        }
        finishAnimationButton.setVisible(false);
    }
}
