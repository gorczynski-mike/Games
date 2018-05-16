package com.gorczynskimike.warmercolder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControlPanel extends JPanel {

    private final JButton startGameButton = new JButton("Start game");
    private final JButton endGameButton = new JButton("End game");
    private final JButton finishAnimationButton = new JButton("Finish animation");
    private final JButton animateFasterButton = new JButton("Animate faster");
    private final JLabel fontLabel = new JLabel("Change font size: ");
    private final JButton biggerFontButton = new JButton("+");
    private final JButton smallerFontButton = new JButton("-");
    private Border border = AppBorderFactory.getStandardBorder("Control Panel");
    private final List<JButton> buttons = new ArrayList<>();

    private ActionListener startGameButtonListener;
    private ActionListener endGameButtonListener;
    private ActionListener finishAnimationButtonListener;
    private ActionListener animateFasterButtonListener;
    private ActionListener biggerFontButtonListener;
    private ActionListener smallerFontButtonListener;

    public ControlPanel() {
        startGameButton.addActionListener(event -> startGameButtonListener.performAction("start new game"));
        endGameButton.addActionListener(event -> endGameButtonListener.performAction("end the game"));
        finishAnimationButton.addActionListener(event -> finishAnimationButtonListener.performAction("finish animation"));
        animateFasterButton.addActionListener(event -> animateFasterButtonListener.performAction("animate faster"));
        biggerFontButton.addActionListener(event -> biggerFontButtonListener.performAction("bigger font"));
        smallerFontButton.addActionListener(event -> smallerFontButtonListener.performAction("smaller font"));
        buttons.add(startGameButton);
        buttons.add(endGameButton);
        finishAnimationButton.setVisible(false);
        animateFasterButton.setVisible(false);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(AppBorderFactory.getStandardBorder("Control Panel"));
        add(startGameButton);
        add(endGameButton);
        add(finishAnimationButton);
        add(animateFasterButton);
        add(fontLabel);
        smallerFontButton.setFont(smallerFontButton.getFont().deriveFont(16.0f));
        biggerFontButton.setFont(biggerFontButton.getFont().deriveFont(16.0f));
        add(smallerFontButton);
        add(biggerFontButton);

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

    public void setAnimateFasterButtonListener(ActionListener animateFasterButtonListener) {
        this.animateFasterButtonListener = animateFasterButtonListener;
    }

    public void setBiggerFontButtonListener(ActionListener biggerFontButtonListener) {
        this.biggerFontButtonListener = biggerFontButtonListener;
    }

    public void setSmallerFontButtonListener(ActionListener smallerFontButtonListener) {
        this.smallerFontButtonListener = smallerFontButtonListener;
    }

    public void animationStarted() {
        for(JButton button : buttons) {
            button.setEnabled(false);
        }
        finishAnimationButton.setVisible(true);
        animateFasterButton.setVisible(true);
    }

    public void animationEnded(){
        for(JButton button : buttons) {
            button.setEnabled(true);
        }
        finishAnimationButton.setVisible(false);
        animateFasterButton.setVisible(false);
    }

    public void updateLanguage(ResourceBundle menuTexts) {
        this.startGameButton.setText(menuTexts.getString("start"));
        this.endGameButton.setText(menuTexts.getString("end"));
        this.finishAnimationButton.setText(menuTexts.getString("animationStop"));
        this.animateFasterButton.setText(menuTexts.getString("animationFaster"));
        this.fontLabel.setText(menuTexts.getString("fontChange"));
        this.setBorder(AppBorderFactory.getStandardBorder(menuTexts.getString("controlBorderTitle")));
    }
}
