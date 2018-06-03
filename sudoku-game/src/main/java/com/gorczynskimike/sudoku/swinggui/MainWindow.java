package com.gorczynskimike.sudoku.swinggui;

import com.gorczynskimike.sudoku.userinterface.MessageService;
import com.gorczynskimike.sudoku.userinterface.UserInputService;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements MessageService, UserInputService {

    private JTextArea sudokuTextArea = new JTextArea();
    private JPanel sudokuTextAreaPanel = new JPanel();
    private JTextArea textArea = new JTextArea();
    private JTextField textField = new JTextField(20);
    private JLabel textFieldLabel = new JLabel("Your input: ");
    private JPanel textFieldPanel = new JPanel();
    private String userInput = "";

    private boolean userInputReady = false;

    public MainWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        this.setSize(width,height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        sudokuTextArea.setPreferredSize(new Dimension(320,320));
        sudokuTextArea.setFont(new Font("monospaced", Font.PLAIN, 20));
        sudokuTextAreaPanel.add(sudokuTextArea);
        this.add(sudokuTextAreaPanel, BorderLayout.NORTH);

        textArea.setFont(new Font("monospaced", Font.PLAIN, 20));
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);

        textField.addActionListener(e -> {
            synchronized (MainWindow.class) {
                userInputReady = true;
                this.userInput = textField.getText();
                textField.setText("");
                MainWindow.class.notifyAll();
            }
        });
        textFieldPanel.add(textFieldLabel);
        textFieldPanel.add(textField);
        this.add(textFieldPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public void closeMainWindow() {
        this.dispose();
    }

    public void setSudoku(String sudokuTextRepresentation) {
        this.sudokuTextArea.setText(sudokuTextRepresentation);
    }

    @Override
    public void acceptMessage(String message) {
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
}