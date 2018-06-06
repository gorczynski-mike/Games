package com.gorczynskimike.sudoku.swinggui;

import com.gorczynskimike.sudoku.userinterface.InstructionsPrinter;
import com.gorczynskimike.sudoku.userinterface.MessageService;
import com.gorczynskimike.sudoku.userinterface.UserInputService;

import javax.swing.*;
import java.awt.*;

/**
 * Main GUI for the application.
 */
public class MainWindow extends JFrame implements MessageService, UserInputService {

    private HelpWindow helpWindow = null;
    private AboutWindow aboutWindow = null;

    private JTextArea sudokuTextArea = new JTextArea();
    private JPanel sudokuTextAreaPanel = new JPanel();
    private JTextArea console = new JTextArea();
    private JTextField textField = new JTextField(20);
    private JLabel textFieldLabel = new JLabel("Your input: ");
    private JPanel textFieldPanel = new JPanel();
    private JPanel centralPanel = new JPanel();
    private ControlPanel controlPanel = new ControlPanel(this);

    private int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100;
    private int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100;

    private String userInput = "";

    private boolean userInputReady = false;

    /**
     * It creates the main window and makes it visible.
     */
    public MainWindow() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setSize(screenWidth,screenHeight);
        this.setMinimumSize(new Dimension(800,650));
        this.setTitle("Sudoku");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        centralPanel.setLayout(new BorderLayout());

        sudokuTextArea.setPreferredSize(new Dimension(340,320));
        sudokuTextArea.setEditable(false);
        sudokuTextArea.setFont(new Font("monospaced", Font.BOLD, 20));
        sudokuTextAreaPanel.setLayout(new FlowLayout());
        JPanel sudokuInnerPanel = new JPanel();
        sudokuInnerPanel.add(sudokuTextArea);
        sudokuInnerPanel.setBorder(AppBorderFactory.getInnerIndentBorder("Sudoku Board", 5));
        sudokuTextAreaPanel.add(sudokuInnerPanel);
        centralPanel.add(sudokuTextAreaPanel, BorderLayout.NORTH);

        console.setFont(new Font("monospaced", Font.PLAIN, 20));
        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(new BorderLayout());
        consolePanel.setBorder(AppBorderFactory.getInnerIndentBorder("Console", 5));
        consolePanel.add(new JScrollPane(console), BorderLayout.CENTER);
        centralPanel.add(consolePanel, BorderLayout.CENTER);

        textField.addActionListener(e -> {
            synchronized (MainWindow.class) {
                userInputReady = true;
                this.userInput = textField.getText();
                textField.setText("");
                MainWindow.class.notifyAll();
            }
        });

        Dimension textFieldPanelDimension = new Dimension(100,30);
        Font textFiledPanelFont = new Font("Default", Font.BOLD, 16);
        textFieldLabel.setPreferredSize(textFieldPanelDimension);
        textFieldLabel.setFont(textFiledPanelFont);
        textFieldPanel.add(textFieldLabel);
        textField.setPreferredSize(textFieldPanelDimension);
        textFieldPanel.add(textField);
        centralPanel.add(textFieldPanel, BorderLayout.SOUTH);

        this.add(controlPanel, BorderLayout.WEST);
        this.add(centralPanel, BorderLayout.CENTER);

        SwingUtilities.updateComponentTreeUI(this);

        this.setVisible(true);
    }

    /**
     * Closes the window.
     */
    public void closeMainWindow() {
        this.dispose();
    }

    /**
     * It redraws the sudoku board.
     * @param sudokuTextRepresentation String representation of the sudoku board.
     */
    public void updateSudoku(String sudokuTextRepresentation) {
        this.sudokuTextArea.setText(sudokuTextRepresentation);
    }

    @Override
    public void sendMessage(String message) {
        this.console.append(" " + message + System.lineSeparator());
        this.console.setCaretPosition(console.getText().length());
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

    /**
     * It prints valid commands.
     */
    public void printCommands() {
        InstructionsPrinter.printInstructions(this);
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

    /**
     * Checks if user input is ready and if yes then it notifies all classes waiting for it.
     * @param text
     */
    public void sendUserInput(String text) {
        synchronized (MainWindow.class) {
            userInputReady = true;
            this.userInput = text;
            MainWindow.class.notifyAll();
        }
    }

    /**
     * It checks if help window is not already available and if not it creates it.
     */
    public void showHelpWindow() {
        if(this.helpWindow == null) {
            this.helpWindow = new HelpWindow(screenWidth, screenHeight);
            this.helpWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    MainWindow.this.helpWindow = null;
                }
            });
        }
    }

    /**
     * It checks if about window is not already available and if not it creates it.
     */
    public void showAboutWindow() {
        if(this.aboutWindow == null) {
            this.aboutWindow = new AboutWindow(screenWidth, screenHeight);
            this.aboutWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    MainWindow.this.aboutWindow = null;
                }
            });
        }
    }
}
