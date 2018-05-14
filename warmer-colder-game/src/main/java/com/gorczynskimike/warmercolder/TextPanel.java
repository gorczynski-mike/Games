package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private final JTextArea textArea = new JTextArea();

    public TextPanel() {
        setLayout(new BorderLayout());
        textArea.setFont(textArea.getFont().deriveFont(20.0f));
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void appendText(String text) {
        this.textArea.append(text);
    }

    public void clearText() {
        this.textArea.setText("");
    }

}
