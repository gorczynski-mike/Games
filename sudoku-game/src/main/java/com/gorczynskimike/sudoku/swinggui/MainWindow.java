package com.gorczynskimike.sudoku.swinggui;

import com.gorczynskimike.sudoku.userinterface.MessageService;
import com.gorczynskimike.sudoku.userinterface.UserInputService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame implements MessageService, UserInputService {

    private HelpWindow helpWindow = null;

    private JTextArea sudokuTextArea = new JTextArea();
    private JPanel sudokuTextAreaPanel = new JPanel();
    private JTextArea textArea = new JTextArea();
    private JTextField textField = new JTextField(20);
    private JLabel textFieldLabel = new JLabel("Your input: ");
    private JPanel textFieldPanel = new JPanel();
    private JPanel centralPanel = new JPanel();
    private ControlPanel controlPanel = new ControlPanel(this);

    private int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100;
    private int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100;

    private String userInput = "";

    private boolean userInputReady = false;

    public MainWindow() {
        this.setSize(screenWidth,screenHeight);
        this.setMinimumSize(new Dimension(800,600));
        this.setTitle("Sudoku");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        centralPanel.setLayout(new BorderLayout());

        sudokuTextArea.setPreferredSize(new Dimension(320,320));
        sudokuTextArea.setEditable(false);
        sudokuTextArea.setFont(new Font("monospaced", Font.BOLD, 20));
        sudokuTextArea.setForeground(Color.black);
        sudokuTextAreaPanel.add(sudokuTextArea);
        centralPanel.add(sudokuTextAreaPanel, BorderLayout.NORTH);

        textArea.setFont(new Font("monospaced", Font.PLAIN, 20));
        centralPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        textField.addActionListener(e -> {
            synchronized (MainWindow.class) {
                userInputReady = true;
                this.userInput = textField.getText();
                textField.setText("");
                MainWindow.class.notifyAll();
            }
        });

        Dimension textFieldPanelDimension = new Dimension(120,30);
        Font textFiledPanelFont = new Font("Default", Font.BOLD, 20);
        textFieldLabel.setPreferredSize(textFieldPanelDimension);
        textFieldLabel.setFont(textFiledPanelFont);
        textFieldPanel.add(textFieldLabel);
        textField.setPreferredSize(textFieldPanelDimension);
        textFieldPanel.add(textField);
        centralPanel.add(textFieldPanel, BorderLayout.SOUTH);

        this.add(controlPanel, BorderLayout.WEST);
        this.add(centralPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void closeMainWindow() {
        this.dispose();
    }

    public void updateSudoku(String sudokuTextRepresentation) {
        this.sudokuTextArea.setText(sudokuTextRepresentation);
    }

    @Override
    public void sendMessage(String message) {
        this.textArea.append(message + System.lineSeparator());
        this.textArea.setCaretPosition(textArea.getText().length());
    }

    @Override
    public String getUserInput() throws InterruptedException {
        synchronized (MainWindow.class) {
            while(!userInputReady) {
                MainWindow.class.wait();
            }
        }
        userInputReady = false;
        System.out.println("returning user input: " + userInput);
        return this.userInput;
    }

    @Override
    public String getNewGameDecision() throws InterruptedException{
        System.out.println("in new game decision");
        this.controlPanel.setNewGameDecisionActive(true);
        synchronized (MainWindow.class) {
            while(!userInputReady) {
                MainWindow.class.wait();
            }
        }
        userInputReady = false;
        System.out.println("returning user input: " + userInput);
        this.controlPanel.setNewGameDecisionActive(false);
        return this.userInput;
    }

    public void sendUserInput(String text) {
        synchronized (MainWindow.class) {
            userInputReady = true;
            this.userInput = text;
            MainWindow.class.notifyAll();
        }
    }

    public void showHelpWindow() {
        if(this.helpWindow == null) {
            this.helpWindow = new HelpWindow(screenWidth, screenHeight);
            this.helpWindow.setWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) { }

                @Override
                public void windowClosing(WindowEvent e) { }

                @Override
                public void windowClosed(WindowEvent e) {
                    MainWindow.this.helpWindow = null;
                }

                @Override
                public void windowIconified(WindowEvent e) { }

                @Override
                public void windowDeiconified(WindowEvent e) { }

                @Override
                public void windowActivated(WindowEvent e) { }

                @Override
                public void windowDeactivated(WindowEvent e) { }
            });
        }
    }
}
