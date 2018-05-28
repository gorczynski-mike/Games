package com.gorczynskimike.sudoku.visualisation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SwingVisualizer extends JFrame {

    public static void main(String[] args) {
        new SwingVisualizer();
    }

    private static final Path DIRECTORY_PATH = Paths.get("output");
    private JTextArea textArea = new JTextArea();
    private JButton visualize = new JButton("Visualize");

    public SwingVisualizer() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,800);
        this.setLayout(new BorderLayout());
        this.setTitle("Visualizer");

        visualize.addActionListener(event -> visualize());

        textArea.setFont(textArea.getFont().deriveFont(28.0f));

        this.add(visualize, BorderLayout.SOUTH);
        this.add(textArea, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void visualize() {
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                try {
                    List<Path> paths = Files.list(DIRECTORY_PATH).collect(Collectors.toList());
                    List<String> strings = new ArrayList<>();
                    for (Path path : paths) {
                        textArea.setText("");
                        String fileContent = path.toString() + System.lineSeparator();
                        fileContent += Files.lines(path).collect(Collectors.joining(System.lineSeparator()));
                        strings.add(fileContent);
                        textArea.setText(fileContent);
                    }
                    for(String screen : strings) {
                        textArea.setText(screen);
                        Thread.sleep(300);
                    }
                } catch (IOException | InterruptedException e) {e.printStackTrace();}
                return null;
            }
        };
        swingWorker.execute();
    }

}
