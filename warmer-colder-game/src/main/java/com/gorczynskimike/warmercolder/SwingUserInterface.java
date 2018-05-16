package com.gorczynskimike.warmercolder;

import javafx.stage.Stage;

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
    private final SettingsPanel settingsPanel = new SettingsPanel();

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
        controlPanel.setFinishAnimationButtonListener(text -> {
            textPanel.interruptAnimation();
            game.startNewGame();
            controlPanel.animationEnded();
            submitPanel.enableButtons();
        });
        controlPanel.setAnimateFasterButtonListener(text -> textPanel.animateFaster());
        controlPanel.setSmallerFontButtonListener(text -> textPanel.makeFontSmaller());
        controlPanel.setBiggerFontButtonListener(text -> textPanel.makeFontBigger());

        settingsPanel.setPolishButtonListener(text -> {
            this.setTitle("Gra ciepło zimno");
            currentLocale = new Locale("pl","PL");
            menuTexts = ResourceBundle.getBundle("menuTexts", currentLocale);
            controlPanel.updateLanguage(menuTexts);
            submitPanel.updateLanguage(menuTexts);
            game.setLanguage(ResourceBundle.getBundle("gameMessages", currentLocale));
            textPanel.setMessagesHistory(game.getHistory("PL"));
        });
        settingsPanel.setEnglishButtonListener(text -> {
            this.setTitle("Warmer colder game");
            currentLocale = new Locale("en","US");
            menuTexts = ResourceBundle.getBundle("menuTexts", currentLocale);
            controlPanel.updateLanguage(menuTexts);
            submitPanel.updateLanguage(menuTexts);
            game.setLanguage(ResourceBundle.getBundle("gameMessages", currentLocale));
            textPanel.setMessagesHistory(game.getHistory("ENG"));
        });
    }

    public SwingUserInterface() {
        super("Warmer colder game");
        try {
            new Thread(new MusicClass()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(1024,800);
        setMinimumSize(new Dimension(1024, 800));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(controlPanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);
        add(settingsPanel, BorderLayout.WEST);

        setVisible(true);
//        textPanel.appendText("Hello user. You can start the game after clicking \"Start game\" button." + System.lineSeparator());
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
