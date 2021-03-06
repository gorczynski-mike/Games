package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class MenuBar extends JMenuBar {

    private static final String CHECKMARK = " \u2713";
    private static final String POLSKI = "Polski";
    private static final String ENGLISH = "English";
    private float menuBarFontSize = 13.0f;

    private final JMenu player = new JMenu("Player");
    private final JMenuItem changePlayer = new JMenuItem("Change player");
    private final JMenuItem topPlayers = new JMenuItem("Top 10 players");

    private final JMenu language = new JMenu("Language");
    private final JMenuItem polishLanguage = new JMenuItem(POLSKI);
    private final JMenuItem englishLanguage = new JMenuItem(ENGLISH + CHECKMARK);

    private ActionListener languageActionListener;
    private ActionListener playerChangeActionListener;
    private ActionListener topPlayersActionListener;

    public void setTopPlayersActionListener(ActionListener topPlayersActionListener) {
        this.topPlayersActionListener = topPlayersActionListener;
    }

    public void setLanguageActionListener(ActionListener languageActionListener) {
        this.languageActionListener = languageActionListener;
    }

    public void setPlayerChangeActionListener(ActionListener playerChangeActionListener) {
        this.playerChangeActionListener = playerChangeActionListener;
    }

    public MenuBar(){
        Font menuBarFont = this.getFont().deriveFont(menuBarFontSize);

        player.add(changePlayer);
        player.add(topPlayers);
        changePlayer.addActionListener(e -> playerChangeActionListener.performAction("change player"));
        topPlayers.addActionListener(e -> topPlayersActionListener.performAction("top players"));

        language.add(polishLanguage);
        language.add(englishLanguage);
        polishLanguage.addActionListener(e -> {
            this.languageActionListener.performAction("polish");
            polishLanguage.setText(POLSKI + CHECKMARK);
            englishLanguage.setText(ENGLISH);
        });
        englishLanguage.addActionListener(e -> {
            this.languageActionListener.performAction("english");
            polishLanguage.setText(POLSKI);
            englishLanguage.setText(ENGLISH + CHECKMARK);
        });
        language.setFont(menuBarFont);
        polishLanguage.setFont(menuBarFont);
        englishLanguage.setFont(menuBarFont);

        this.add(language);
        this.add(player);
    }

    public void updateLanguage(ResourceBundle menuTexts){
        this.language.setText(menuTexts.getString("language"));
    }

}
