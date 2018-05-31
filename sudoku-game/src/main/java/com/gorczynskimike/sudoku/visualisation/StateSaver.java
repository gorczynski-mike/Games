package com.gorczynskimike.sudoku.visualisation;

import com.gorczynskimike.sudoku.simple.SudokuElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StateSaver {

    private static final Path DIRECTORY_PATH = Paths.get("output");

    private static void prepareDirectory() throws IOException {
        if(!Files.exists(DIRECTORY_PATH)) {
            Files.createDirectory(DIRECTORY_PATH);
        }
    }

    public static void deleteOldFiles() throws IOException{
        prepareDirectory();
        List<Path> files = Files.list(DIRECTORY_PATH).collect(Collectors.toList());
        for(Path path : files) {
            Files.delete(path);
        }
    }

    public static void saveSudokuArrayToFile(SudokuElement[][] array, String filename)  throws IOException {
        Path pathToFile = Paths.get(DIRECTORY_PATH.toString(), filename);
        Files.createFile(pathToFile);
        try(
                PrintWriter pw = new PrintWriter(Files.newBufferedWriter(pathToFile));
                ) {
            for(int i=0; i<array.length; i++) {
                pw.println(Arrays.toString(array[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
