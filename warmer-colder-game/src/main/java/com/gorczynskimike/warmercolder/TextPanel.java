package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private final JTextArea textArea = new JTextArea();
    private SwingWorker swingWorker;

    private ActionListener animationFinishedListener;

    public void setAnimationFinishedListener(ActionListener animationFinishedListener) {
        this.animationFinishedListener = animationFinishedListener;
    }

    public TextPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        textArea.setFont(textArea.getFont().deriveFont(20.0f));
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void appendText(String text) {
        this.textArea.append(text);
    }

    public void clearText() {
        this.textArea.setText("");
    }

    public void clearTextAnimate() {
        swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                String text = textArea.getText();
                while(text.length() > 0) {
                    text = text.substring(0, text.length()-1);
                    textArea.setText(text);
                    textArea.repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                TextPanel.this.animationFinishedListener.performAction("animation finished");
                return null;
            }

        };
        new Thread(swingWorker).start();
    }

}
