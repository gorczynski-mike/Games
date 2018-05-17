package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class SwingUserInterface extends JFrame{

    private final Game game = new Game();
    private final MessageService consoleMessageService = text -> System.out.println(text);

    private final ControlPanel controlPanel = new ControlPanel();
    private final TextPanel textPanel = new TextPanel();
    private final SubmitPanel submitPanel = new SubmitPanel();
    private final MenuBar menuBar = new MenuBar();
    private PlayerChangePopUp playerChangePopUp = null;

    private Locale currentLocale = new Locale("en", "US");
    private ResourceBundle menuTexts;

    {
        submitPanel.setSubmitButtonListener(text -> game.checkUserNumber(text));
        controlPanel.setStartGameButtonListener(text -> {
            controlPanel.animationStarted();
            submitPanel.disableButtons();
            textPanel.clearTextAnimate();
        });
        textPanel.setAnimationFinishedListener(text -> {
            game.startNewGame();
            controlPanel.animationEnded();
            submitPanel.enableButtons();
        });
        controlPanel.setEndGameButtonListener(text -> game.userEndsTheGame());
        game.setGameActionListener(text -> {
            textPanel.appendText(text + System.lineSeparator());
            consoleMessageService.sendMessage(text);
        } );
        game.setPlayerChangeListener(text -> this.submitPanel.setPlayerName(text));
        controlPanel.setFinishAnimationButtonListener(text -> {
            textPanel.interruptAnimation();
            game.startNewGame();
            controlPanel.animationEnded();
            submitPanel.enableButtons();
        });
        controlPanel.setAnimateFasterButtonListener(text -> textPanel.animateFaster());
        controlPanel.setSmallerFontButtonListener(text -> textPanel.makeFontSmaller());
        controlPanel.setBiggerFontButtonListener(text -> textPanel.makeFontBigger());

        menuBar.setLanguageActionListener(text -> {
            if(text.equalsIgnoreCase("polish")) {
                setPolishLanguage();
            } else if(text.equalsIgnoreCase("english")) {
                setEnglishLanguage();
            }
        });
        menuBar.setPlayerChangeActionListener(text -> {
            if(playerChangePopUp == null) {
                playerChangePopUp = new PlayerChangePopUp();
                playerChangePopUp.setOkButtonActionListener(playerName -> {
                    game.setPlayer(playerName);
                    playerChangePopUp = null;
                });
            } else {
                playerChangePopUp.requestFocus();
            }
        });
    }

    private void setEnglishLanguage() {
        currentLocale = new Locale("en","US");
        menuTexts = ResourceBundle.getBundle("menuTexts", currentLocale);
        this.setTitle(menuTexts.getString("appName"));
        controlPanel.updateLanguage(menuTexts);
        submitPanel.updateLanguage(menuTexts);
        menuBar.updateLanguage(menuTexts);
        game.setLanguage(ResourceBundle.getBundle("gameMessages", currentLocale));
        textPanel.setMessagesHistory(game.getHistory("ENG"));
    }

    private void setPolishLanguage() {
        currentLocale = new Locale("pl","PL");
        menuTexts = ResourceBundle.getBundle("menuTexts", currentLocale);
        this.setTitle(menuTexts.getString("appName"));
        controlPanel.updateLanguage(menuTexts);
        submitPanel.updateLanguage(menuTexts);
        menuBar.updateLanguage(menuTexts);
        game.setLanguage(ResourceBundle.getBundle("gameMessages", currentLocale));
        textPanel.setMessagesHistory(game.getHistory("PL"));
    }

    public SwingUserInterface() {
        DatabaseH2Service databaseH2Service = new DatabaseH2Service();
        setSize(1024,800);
        setMinimumSize(new Dimension(1024, 800));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Warmer colder game");

        add(controlPanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);
        setJMenuBar(menuBar);

        game.setPlayer("Guest");

        setVisible(true);
    }

    private ResourceBundle getResourceBundle(String resourceName) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceName);
        Reader reader;
        try {
            reader = new InputStreamReader(stream, "UTF-8");
            return new PropertyResourceBundle(reader);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
