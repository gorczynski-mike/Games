package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private final JTextArea textArea = new JTextArea();
    private SwingWorker swingWorker;
    private Thread swingWorkerThread;
    private boolean finishAnimationNowFlag = false;
    private int animationSpeed = 20;

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
        textArea.setCaretPosition(textArea.getText().length());
    }

    public void clearTextAnimate() {
        swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                finishAnimationNowFlag = false;
                animationSpeed = 20;
                String text = textArea.getText();
                while(text.length() > 0) {
                    if(finishAnimationNowFlag) {
                        return null;
                    }
                    text = text.substring(0, text.length()-1);
                    textArea.setText(text);
                    textArea.repaint();
                    try {
                        Thread.sleep(animationSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                TextPanel.this.animationFinishedListener.performAction("animation finished");
                return null;
            }

        };
        swingWorkerThread = new Thread(swingWorker);
        swingWorkerThread.start();
    }

    public void interruptAnimation() {
        finishAnimationNowFlag = true;
        textArea.setText("");
    }

    public void animateFaster() {
        if(animationSpeed > 0) {
            animationSpeed = animationSpeed - 2;
        }
    }

}
