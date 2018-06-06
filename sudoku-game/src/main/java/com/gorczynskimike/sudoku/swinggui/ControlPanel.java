package com.gorczynskimike.sudoku.swinggui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

/**
 * Panel that holds buttons, sliders and comboboxes the user can use to send game commands.
 */
public class ControlPanel extends JPanel {

    private final MainWindow mainWindow;

    private JButton solveButton = new JButton("Solve!");
    private JButton clearButton = new JButton("Clear");
    private JButton easyButton = new JButton("Easy");
    private JButton mediumButton = new JButton("Medium");
    private JButton hardButton = new JButton("Hard");
    private JButton startNewGameButton = new JButton("New Game");
    private JButton exitButton = new JButton("Exit");

    private JComboBox<Integer> xIndex = new JComboBox<>();
    private JComboBox<Integer> yIndex = new JComboBox<>();
    private JComboBox<Integer> value = new JComboBox<>();
    private List<Integer> validValues = Collections.unmodifiableList(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9}));
    private JPanel comboBoxesPanel = new JPanel();

    private JButton setElementButton = new JButton("Set element");
    private JButton clearElementButton = new JButton("Clear element");
    private JPanel setClearButtonsPanel = new JPanel();

    private JPanel mainValueControlPanel = new JPanel();

    private JSlider randomValuesSlider = new JSlider(JSlider.HORIZONTAL, 1, 81, 1);
    private JLabel selectedRandomValue = new JLabel("1");
    private JButton randomValuesButton = new JButton("Random");
    private JButton solvableValuesButton = new JButton("Solvable");
    private JButton removeFieldsButton = new JButton("Remove");
    private JPanel randomValuesPanel = new JPanel();

    private JButton showHelpWindowButton = new JButton("Show Help");
    private JButton showAboutWindowButton = new JButton("About");
    private JButton printCommandsButton = new JButton("Print commands");

    private List<JButton> wideButtons = new ArrayList<>();

    private static final int buttonWidth = 150;
    private static final int buttonHeight = 30;

    public ControlPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        this.setPreferredSize(new Dimension(320,0));
        this.setBorder(AppBorderFactory.getStandardBorder("Control Panel"));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weighty = 1.0;
        solveButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        solveButton.setSize(new Dimension(buttonWidth,buttonHeight));
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
        exitButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        exitButton.addActionListener(e -> {mainWindow.sendUserInput("n");});
        exitButton.setEnabled(false);
        this.add(exitButton,gc);

        for(Integer i : validValues) {
            xIndex.addItem(i);
            yIndex.addItem(i);
            value.addItem(i);
        }
        xIndex.setSelectedIndex(0);
        xIndex.setMaximumRowCount(9);
        xIndex.setPreferredSize(new Dimension(40,30));
        yIndex.setSelectedIndex(0);
        yIndex.setMaximumRowCount(9);
        yIndex.setPreferredSize(new Dimension(40,30));
        value.setSelectedIndex(0);
        value.setMaximumRowCount(9);
        value.setPreferredSize(new Dimension(40,30));
        comboBoxesPanel.setLayout(new FlowLayout());
        comboBoxesPanel.add(new JLabel(" X: "));
        comboBoxesPanel.add(xIndex);
        comboBoxesPanel.add(new JLabel(" Y: "));
        comboBoxesPanel.add(yIndex);
        comboBoxesPanel.add(new JLabel(" Value: "));
        comboBoxesPanel.add(value);

        setElementButton.addActionListener(e -> {mainWindow.sendUserInput(xIndex.getSelectedItem() + "," +
                                                yIndex.getSelectedItem() + "," + value.getSelectedItem());});
        setClearButtonsPanel.add(setElementButton);
        clearElementButton.addActionListener(e -> {mainWindow.sendUserInput(xIndex.getSelectedItem() + "," +
                                                    yIndex.getSelectedItem() + ",unset");});
        setClearButtonsPanel.add(clearElementButton);

        mainValueControlPanel.setLayout(new BorderLayout());
        mainValueControlPanel.add(comboBoxesPanel, BorderLayout.NORTH);
        mainValueControlPanel.add(setClearButtonsPanel, BorderLayout.SOUTH);
        gc.gridx = 0;
        gc.gridy = 7;
        gc.weighty = 1.0;
        this.add(mainValueControlPanel, gc);

        randomValuesSlider.setMajorTickSpacing(20);
        randomValuesSlider.setMinorTickSpacing(5);
        randomValuesSlider.setPaintTicks(true);
        randomValuesSlider.setPaintLabels(true);
        randomValuesSlider.addChangeListener(e -> {selectedRandomValue.setText(String.valueOf(randomValuesSlider.getValue()));});
        randomValuesPanel.setLayout(new BorderLayout());
        randomValuesPanel.add(randomValuesSlider, BorderLayout.NORTH);
        JPanel selectedValuePanel = new JPanel();
        selectedValuePanel.setLayout(new FlowLayout());
        selectedValuePanel.add(new JLabel("Selected: "));
        selectedValuePanel.add(selectedRandomValue);
        randomValuesPanel.add(selectedValuePanel, BorderLayout.CENTER);
        JPanel randomButtonsPanel = new JPanel();
        randomButtonsPanel.setLayout(new FlowLayout());
        randomValuesButton.addActionListener(e -> {mainWindow.sendUserInput("random," + randomValuesSlider.getValue());});
        randomButtonsPanel.add(randomValuesButton);
        solvableValuesButton.addActionListener(e -> {mainWindow.sendUserInput("solvable," + randomValuesSlider.getValue());});
        randomButtonsPanel.add(solvableValuesButton);
        removeFieldsButton.addActionListener(e -> {mainWindow.sendUserInput("remove," + randomValuesSlider.getValue());});
        randomButtonsPanel.add(removeFieldsButton);
        randomValuesPanel.add(randomButtonsPanel, BorderLayout.SOUTH);
        gc.gridx = 0;
        gc.gridy = 8;
        gc.weighty = 1.0;
        this.add(randomValuesPanel, gc);

        gc.gridx = 0;
        gc.gridy = 9;
        gc.weighty = 10.0;
        this.add(new Component() {}, gc);

        gc.gridx = 0;
        gc.gridy = 10;
        gc.weighty = 1.0;
        printCommandsButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        printCommandsButton.addActionListener(e -> {mainWindow.printCommands();});
        this.add(printCommandsButton, gc);

        gc.gridx = 0;
        gc.gridy = 11;
        gc.weighty = 1.0;
        showHelpWindowButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        showHelpWindowButton.addActionListener(e -> {mainWindow.showHelpWindow();});
        this.add(showHelpWindowButton, gc);

        gc.gridx = 0;
        gc.gridy = 12;
        gc.weighty = 1.0;
        showAboutWindowButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        showAboutWindowButton.addActionListener(e -> {mainWindow.showAboutWindow();});
        this.add(showAboutWindowButton, gc);

        wideButtons.addAll(Arrays.asList(solveButton, clearButton, easyButton, mediumButton, hardButton,
                            startNewGameButton, exitButton, showHelpWindowButton, showAboutWindowButton, printCommandsButton));
    }

    /**
     * Depending if the app is in "new game decision" state some controls should be active or not. This method
     * enables and disables them.
     * @param isNewGameDecision True if "new game decision" state is active, False otherwise.
     */
    public void setNewGameDecisionActive(boolean isNewGameDecision) {
        System.out.println("new game decision active");

        //These buttons are disabled in "new game decision" state
        this.solveButton.setEnabled(!isNewGameDecision);
        this.clearButton.setEnabled(!isNewGameDecision);
        this.easyButton.setEnabled(!isNewGameDecision);
        this.mediumButton.setEnabled(!isNewGameDecision);
        this.hardButton.setEnabled(!isNewGameDecision);
        this.setElementButton.setEnabled(!isNewGameDecision);
        this.clearElementButton.setEnabled(!isNewGameDecision);
        this.randomValuesButton.setEnabled(!isNewGameDecision);
        this.solvableValuesButton.setEnabled(!isNewGameDecision);
        this.removeFieldsButton.setEnabled(!isNewGameDecision);

        //These buttons are enabled in "new game decision" state
        this.startNewGameButton.setEnabled(isNewGameDecision);
        this.exitButton.setEnabled(isNewGameDecision);
    }

}
