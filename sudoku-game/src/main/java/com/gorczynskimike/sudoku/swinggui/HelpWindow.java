package com.gorczynskimike.sudoku.swinggui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

/**
 * Class used to create new window with 'About' information
 */
public class HelpWindow extends JFrame {

    private JTextArea textArea = new JTextArea();

    /**
     * Creates window and makes it visible
     * @param screenWidth current screen resolution width
     * @param screenHeight current screen resolution height
     */
    public HelpWindow(int screenWidth, int screenHeight) {
        this.setLocation(screenWidth/3, screenHeight/3);
        this.setTitle("Help");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(textArea.getFont().deriveFont(Font.BOLD, 20.0f));
        textArea.setBorder(AppBorderFactory.getStandardBorderExtraInnerIndent(null));

        textArea.append("There is a number of valid commands you can use." + System.lineSeparator());
        textArea.append("You can send commands using the buttons on the control panel or by typing them into the " +
                "input text field below the console." + System.lineSeparator());
        textArea.append(System.lineSeparator());
        textArea.append("To see the list of valid commands please press 'Print Commands' button.");
        this.add(textArea, BorderLayout.CENTER);

        this.setSize(screenWidth/2, textArea.getLineCount()*60 + 20);
        this.setVisible(true);
    }

    /**
     * It facilitates adding listeners to this window.
     * @param windowListener New listener for this window.
     */
    public void setWindowListener(WindowListener windowListener) {
        this.addWindowListener(windowListener);
    }

}
