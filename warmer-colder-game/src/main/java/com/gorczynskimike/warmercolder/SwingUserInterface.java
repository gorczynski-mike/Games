package com.gorczynskimike.warmercolder;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class SwingUserInterface extends JFrame{

    private final Game game = new Game();
    private static final MessageService consoleMessageService = text -> System.out.println(text);

    private final ControlPanel controlPanel = new ControlPanel();
    private final TextPanel textPanel = new TextPanel();
    private final SubmitPanel submitPanel = new SubmitPanel();
    private final MenuBar mainMenuBar = new MenuBar();
    private PlayerChangePopUp playerChangePopUp = null;
    private TopScorersPopUp topScorersPopUp = null;

    DatabaseService databaseService = new DatabaseH2Service();

    private Locale currentLocale = new Locale("en", "US");
    private ResourceBundle menuTexts;

    {
        //control panel listeners
            controlPanel.setStartGameButtonListener(text -> {
                controlPanel.animationStarted();
                submitPanel.disableButtons();
                textPanel.clearTextAnimate();
            });
            controlPanel.setEndGameButtonListener(text -> game.userEndsTheGame());
            controlPanel.setFinishAnimationButtonListener(text -> {
                textPanel.interruptAnimation();
                game.startNewGame();
                controlPanel.animationEnded();
                submitPanel.enableButtons();
            });
            controlPanel.setAnimateFasterButtonListener(text -> textPanel.animateFaster());
            controlPanel.setSmallerFontButtonListener(text -> textPanel.makeFontSmaller());
            controlPanel.setBiggerFontButtonListener(text -> textPanel.makeFontBigger());


        //submit panel listeners
            submitPanel.setSubmitButtonListener(text -> game.checkUserNumber(text));


        //text panel listeners
            textPanel.setAnimationFinishedListener(text -> {
                game.startNewGame();
                controlPanel.animationEnded();
                submitPanel.enableButtons();
            });


        //game listeners
            game.setSendMessageListener(text -> {
                textPanel.appendText(text + System.lineSeparator());
                consoleMessageService.sendMessage(text);
            } );
            game.setPlayerChangeListener(text -> submitPanel.setPlayerName(text));
            game.setGameWonListener(text -> {
                String[] result = text.split("&&");
                String playerName = result[0];
                int playerScore = Integer.parseInt(result[1]);
                databaseService.addScore(playerName, playerScore);
            });


        //main menu bar listeners
            mainMenuBar.setLanguageActionListener(text -> {
                if(text.equalsIgnoreCase("polish")) {
                    setPolishLanguage();
                } else if(text.equalsIgnoreCase("english")) {
                    setEnglishLanguage();
                }
            });
            mainMenuBar.setPlayerChangeActionListener(text -> {
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
            mainMenuBar.setTopPlayersActionListener(text -> {
                topScorersPopUp = new TopScorersPopUp();
                String topPlayers = databaseService.getTop10Scores();
                topScorersPopUp.processResultSet(topPlayers);
            });
    }

    private void setEnglishLanguage() {
        currentLocale = new Locale("en","US");
        menuTexts = ResourceBundle.getBundle("menuTexts", currentLocale);
        this.setTitle(menuTexts.getString("appName"));
        controlPanel.updateLanguage(menuTexts);
        submitPanel.updateLanguage(menuTexts);
        mainMenuBar.updateLanguage(menuTexts);
        game.setLanguage(ResourceBundle.getBundle("gameMessages", currentLocale));
        textPanel.setMessagesHistory(game.getHistory("ENG"));
    }

    private void setPolishLanguage() {
        currentLocale = new Locale("pl","PL");
        menuTexts = ResourceBundle.getBundle("menuTexts", currentLocale);
        this.setTitle(menuTexts.getString("appName"));
        controlPanel.updateLanguage(menuTexts);
        submitPanel.updateLanguage(menuTexts);
        mainMenuBar.updateLanguage(menuTexts);
        game.setLanguage(ResourceBundle.getBundle("gameMessages", currentLocale));
        textPanel.setMessagesHistory(game.getHistory("PL"));
    }

    public SwingUserInterface() {
        setSize(1024,800);
        setMinimumSize(new Dimension(1024, 800));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Warmer colder game");

        add(controlPanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);
        setJMenuBar(mainMenuBar);

        game.setPlayer("Guest");

        setVisible(true);
    }

}
