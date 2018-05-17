package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class MenuBar extends JMenuBar {

    private final String checkmark = " \u2713";
    private float menuBarFontSize = 13.0f;

    private final JMenu language = new JMenu("Language");
    private final JMenuItem polishLanguage = new JMenuItem("Polski");
    private final JMenuItem englishLanguage = new JMenuItem("English" + checkmark);

    private ActionListener languageActionListener;

    public void setLanguageActionListener(ActionListener languageActionListener) {
        this.languageActionListener = languageActionListener;
    }

    public MenuBar(){
        Font menuBarFont = this.getFont().deriveFont(menuBarFontSize);

        language.add(polishLanguage);
        language.add(englishLanguage);
        polishLanguage.addActionListener(e -> {
            this.languageActionListener.performAction("polish");
            polishLanguage.setText("Polski" + checkmark);
            englishLanguage.setText("English");
        });
        englishLanguage.addActionListener(e -> {
            this.languageActionListener.performAction("english");
            polishLanguage.setText("Polski");
            englishLanguage.setText("English" + checkmark);
        });
        language.setFont(menuBarFont);
        polishLanguage.setFont(menuBarFont);
        englishLanguage.setFont(menuBarFont);

        this.add(language);
    }

    public void updateLanguage(ResourceBundle menuTexts){
        this.language.setText(menuTexts.getString("language"));
    }

}
