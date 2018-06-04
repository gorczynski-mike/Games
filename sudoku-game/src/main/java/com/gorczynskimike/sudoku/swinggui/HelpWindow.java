package com.gorczynskimike.sudoku.swinggui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class HelpWindow extends JFrame {

    private JLabel helpText = new JLabel();
    private JTextArea textArea = new JTextArea();

    public HelpWindow(int screenWidth, int screenHeight) {
        this.setLocation(screenWidth/3, screenHeight/3);
        this.setSize(screenWidth/2, screenHeight/2);
        this.setTitle("Help");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(textArea.getFont().deriveFont(Font.BOLD, 24.0f));
        textArea.setBorder(AppBorderFactory.getStandardBorder(null));

        textArea.setText("There is a number of valid commands you can use. You can send commands using the buttons " +
                "on the control panel or by typing them into the input text field below the console.");
        this.add(textArea, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void setWindowListener(WindowListener windowListener) {
        this.addWindowListener(windowListener);
    }

}
