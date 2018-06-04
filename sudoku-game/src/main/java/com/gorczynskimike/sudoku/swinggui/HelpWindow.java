package com.gorczynskimike.sudoku.swinggui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowListener;

public class HelpWindow extends JFrame {

    private JTextArea textArea = new JTextArea();

    public HelpWindow(int screenWidth, int screenHeight) {
        this.setLocation(screenWidth/3, screenHeight/3);
        this.setSize(screenWidth/2, screenHeight/5);
        this.setTitle("Help");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(textArea.getFont().deriveFont(Font.BOLD, 20.0f));
        textArea.setBorder(AppBorderFactory.getStandardBorderExtraInnerIndent(null));

        textArea.append("There is a number of valid commands you can use. You can send commands using the buttons " +
                "on the control panel or by typing them into the input text field below the console.");
        textArea.append(System.lineSeparator());
        textArea.append("To see the list of valid commands please press 'Print Commands' button.");
        this.add(textArea, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void setWindowListener(WindowListener windowListener) {
        this.addWindowListener(windowListener);
    }

}
