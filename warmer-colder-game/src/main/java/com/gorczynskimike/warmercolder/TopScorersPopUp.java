package com.gorczynskimike.warmercolder;

import javax.swing.*;

public class TopScorersPopUp extends JFrame {

    private JTextArea textArea = new JTextArea();

    public TopScorersPopUp() {
        setSize(600, 600);
        textArea.setEditable(false);
        setTitle("Top players");
        textArea.setFont(textArea.getFont().deriveFont(15.0f));
        add(textArea);
        setVisible(true);
    }

    public void processResultSet(String topScorers) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%25s : %-25s%n", "Player name", "Moves to win"));
        String[] playerScorePairs = topScorers.split(System.lineSeparator());
        for(String playerScorePair : playerScorePairs) {
            String[] items = playerScorePair.split("&&");
            sb.append(items[0] + " : " + items[1] + System.lineSeparator());
        }
        textArea.setText(sb.toString());
    }

}
