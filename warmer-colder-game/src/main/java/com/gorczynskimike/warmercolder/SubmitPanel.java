package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class SubmitPanel extends JPanel {

    private final JLabel inputLabel = new JLabel("Your number: ");
    private final JTextField inputField = new JTextField(10);
    private final JButton submitButton = new JButton("Submit");

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

        setLayout(new FlowLayout());
        setBorder(AppBorderFactory.getStandardBorder("Submit panel"));
        add(inputLabel);
        add(inputField);
        add(submitButton);
    }

    public void setSubmitButtonListener(ActionListener submitButtonListener) {
        this.submitButtonListener = submitButtonListener;
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
