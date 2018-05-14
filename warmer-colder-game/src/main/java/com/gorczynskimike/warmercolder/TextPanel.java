package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private final JTextArea textArea = new JTextArea();

    public TextPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void appendText(String text) {
        this.textArea.append(text);
    }

}
