package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.util.ResourceBundle;

public class MenuBar extends JMenuBar {

    private final JMenu language = new JMenu("Language");
    private final JMenuItem polishLanguage = new JMenuItem("Polski");
    private final JMenuItem englishLanguage = new JMenuItem("English");

    private ActionListener languageActionListener;

    public void setLanguageActionListener(ActionListener languageActionListener) {
        this.languageActionListener = languageActionListener;
    }

    public MenuBar(){
        language.add(polishLanguage);
        language.add(englishLanguage);
        polishLanguage.addActionListener(e -> this.languageActionListener.performAction("polish"));
        englishLanguage.addActionListener(e -> this.languageActionListener.performAction("english"));

        this.add(language);
    }

    public void updateLanguage(ResourceBundle menuTexts){
        this.language.setText(menuTexts.getString("language"));
    }

}
