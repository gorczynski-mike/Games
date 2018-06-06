package com.gorczynskimike.sudoku.swinggui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

/**
 * Class used to create new window with 'About' information
 */
public class AboutWindow extends JFrame{

    private JTextArea textArea = new JTextArea();

    /**
     * Creates window and makes it visible
     * @param screenWidth current screen resolution width
     * @param screenHeight current screen resolution height
     */
    public AboutWindow(int screenWidth, int screenHeight) {
        this.setLocation(screenWidth/3, screenHeight/3);
        this.setTitle("About");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(textArea.getFont().deriveFont(Font.BOLD, 20.0f));
        textArea.setBorder(AppBorderFactory.getStandardBorderExtraInnerIndent(null));

        textArea.append("App: Sudoku app" + System.lineSeparator());
        textArea.append("Made by: Michal Gorczynski" + System.lineSeparator());
        textArea.append("GitHub: https://github.com/gorczynski-mike" + System.lineSeparator());
        textArea.append("Mail: michalgorczynski89@gmail.com" + System.lineSeparator());
        textArea.append("" + System.lineSeparator());
        textArea.append("Feel free to contact me if you find any bugs / have any suggestions.");
        this.add(textArea, BorderLayout.CENTER);

        this.setSize(screenWidth/2, textArea.getLineCount()*50 + 20);
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
