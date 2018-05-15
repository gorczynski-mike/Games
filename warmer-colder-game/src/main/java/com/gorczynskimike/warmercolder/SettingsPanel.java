package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private JButton englishButton = new JButton("English");
    private JButton polishButton = new JButton("Polski");

    private ActionListener polishButtonListener;
    private ActionListener englishButtonListener;

    {
        polishButton.addActionListener(e -> polishButtonListener.performAction("polish"));
        englishButton.addActionListener(e -> englishButtonListener.performAction("english"));
    }

    public SettingsPanel() {
        setPreferredSize(new Dimension(100, 10));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.gridx = 0;
        gc.weighty = 1.0d;
        add(polishButton, gc);

        gc.gridy = 1;
        gc.gridx = 0;
        gc.weighty = 1.0d;
        add(englishButton, gc);

        gc.gridy = 2;
        gc.gridx = 0;
        gc.weighty = 15.0d;
        add(new JPanel(), gc);
    }

    public void setPolishButtonListener(ActionListener polishButtonListener) {
        this.polishButtonListener = polishButtonListener;
    }

    public void setEnglishButtonListener(ActionListener englishButtonListener) {
        this.englishButtonListener = englishButtonListener;
    }
}
