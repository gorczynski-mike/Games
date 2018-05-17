package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class SubmitPanel extends JPanel {

    private final JLabel inputLabel = new JLabel("Your number: ");
    private final JTextField inputField = new JTextField(10);
    private final JButton submitButton = new JButton("Submit");
    private final JLabel playerLabel = new JLabel("Your name: ");
    private final JLabel playerName = new JLabel("Guest");

    private ActionListener submitButtonListener;

    public SubmitPanel() {
        submitButton.addActionListener(event -> {
            submitButtonListener.performAction(inputField.getText());
            inputField.setText("");
            inputField.grabFocus();
        });

        inputField.addActionListener(event -> {
            submitButtonListener.performAction(inputField.getText());
            inputField.setText("");
            inputField.grabFocus();
        });

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(10);
        setLayout(flowLayout);
        setBorder(AppBorderFactory.getStandardBorder("Submit panel"));
        add(inputLabel);
        add(inputField);
        add(submitButton);
        add(playerLabel);
        add(playerName);
    }

    public void setSubmitButtonListener(ActionListener submitButtonListener) {
        this.submitButtonListener = submitButtonListener;
    }

    public void setPlayerName(String playerName) {
        this.playerName.setText(playerName);
    }

    public void disableButtons() {
        submitButton.setEnabled(false);
    }

    public void enableButtons(){
        submitButton.setEnabled(true);
    }

    public void updateLanguage(ResourceBundle menuTexts) {
        inputLabel.setText(menuTexts.getString("yourNumber"));
        submitButton.setText(menuTexts.getString("submitButton"));
        this.setBorder(AppBorderFactory.getStandardBorder(menuTexts.getString("submitPanel")));
    }
}
