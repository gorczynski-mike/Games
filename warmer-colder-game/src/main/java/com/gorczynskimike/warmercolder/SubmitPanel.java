package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class SubmitPanel extends JPanel {

    private final JTextField inputField = new JTextField(10);
    private final JButton submitButton = new JButton("Submit");

    private ActionListener submitButtonListener;

    public SubmitPanel() {
        submitButton.addActionListener(event -> {
            submitButtonListener.performAction(inputField.getText());
            inputField.setText("");
        });

        setLayout(new FlowLayout());
        setBorder(AppBorderFactory.getStandardBorder("Submit panel"));
        add(inputField);
        add(submitButton);
    }

    public void setSubmitButtonListener(ActionListener submitButtonListener) {
        this.submitButtonListener = submitButtonListener;
    }

}
