package com.gorczynskimike.sudoku.swinggui;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private final MainWindow mainWindow;

    private JButton solveButton = new JButton("Solve!");
    private JButton clearButton = new JButton("Clear");
    private JButton easyButton = new JButton("Easy");
    private JButton mediumButton = new JButton("Medium");
    private JButton hardButton = new JButton("Hard");
    private JButton startNewGameButton = new JButton("New Game");
    private JButton endGameButton = new JButton("End Game");

    private static final int buttonWidth = 150;
    private static final int buttonHeight = 30;

    public ControlPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        this.setPreferredSize(new Dimension(300,0));
        this.setBorder(AppBorderFactory.getStandardBorder("Control Panel"));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weighty = 1.0;
        solveButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        solveButton.addActionListener(e -> {mainWindow.sendUserInput("sudoku");});
        this.add(solveButton, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.weighty = 1.0;
        clearButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        clearButton.addActionListener(e -> {mainWindow.sendUserInput("clear");});
        this.add(clearButton, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.weighty = 1.0;
        easyButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        easyButton.addActionListener(e -> {mainWindow.sendUserInput("easy");});
        this.add(easyButton, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.weighty = 1.0;
        mediumButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        mediumButton.addActionListener(e -> {mainWindow.sendUserInput("medium");});
        this.add(mediumButton,gc);

        gc.gridx = 0;
        gc.gridy = 4;
        gc.weighty = 1.0;
        hardButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        hardButton.addActionListener(e -> {mainWindow.sendUserInput("hard");});
        this.add(hardButton,gc);

        gc.gridx = 0;
        gc.gridy = 5;
        gc.weighty = 1.0;
        startNewGameButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        startNewGameButton.addActionListener(e -> {mainWindow.sendUserInput("y");});
        startNewGameButton.setEnabled(false);
        this.add(startNewGameButton,gc);

        gc.gridx = 0;
        gc.gridy = 6;
        gc.weighty = 1.0;
        endGameButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        endGameButton.addActionListener(e -> {mainWindow.sendUserInput("n");});
        endGameButton.setEnabled(false);
        this.add(endGameButton,gc);

        gc.gridx = 0;
        gc.gridy = 7;
        gc.weighty = 10.0;
        this.add(new Component() {}, gc);
    }

    public void setNewGameDecisionActive(boolean isNewGameDecision) {
        System.out.println("new game decision active");
        this.solveButton.setEnabled(!isNewGameDecision);
        this.clearButton.setEnabled(!isNewGameDecision);
        this.easyButton.setEnabled(!isNewGameDecision);
        this.mediumButton.setEnabled(!isNewGameDecision);
        this.hardButton.setEnabled(!isNewGameDecision);
        this.startNewGameButton.setEnabled(isNewGameDecision);
        this.endGameButton.setEnabled(isNewGameDecision);
    }

}
