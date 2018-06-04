package com.gorczynskimike.sudoku.swinggui;

import javax.swing.*;
import java.awt.event.WindowListener;

public class HelpWindow extends JFrame {

    public HelpWindow(int screenWidth, int screenHeight) {
        this.setLocation(screenWidth/3, screenHeight/3);
        this.setSize(screenWidth/2, screenHeight/2);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void setWindowListener(WindowListener windowListener) {
        this.addWindowListener(windowListener);
    }

}
