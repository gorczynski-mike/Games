package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class PlayerChangePopUp extends JFrame {

    private final JLabel playerNameLabel = new JLabel("Player name: ");
    private final JTextField playerNameField = new JTextField(10);
    private final JButton okButton = new JButton("OK");

    private ActionListener okButtonActionListener;

    public void setOkButtonActionListener(ActionListener okButtonActionListener) {
        this.okButtonActionListener = okButtonActionListener;
    }

    public PlayerChangePopUp() {
        setTitle("Player name");
        setSize(250, 100);
        setLocation(100, 100);
        setResizable(false);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        okButton.addActionListener(e -> {
            okButtonActionListener.performAction(playerNameField.getText());
            this.dispose();
        });

        add(playerNameLabel);
        add(playerNameField);
        add(okButton);
        setVisible(true);
    }

}
