package org.vinted.homeworktask.service;

import org.vinted.homeworktask.config.Constants;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileService {

    /**
     * Writes the contents of a list of strings to a file.
     *
     * @param stringList the list of strings to write to the file
     * @param filename the name of the file to write to
     */
    public void writeDataToFile(List<String> stringList, String filename) {
        Path path = Paths.get(Constants.PATH_TO_DATA_FILE + filename).toAbsolutePath();
        File outputFile = new File(path.toString());
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile.toPath(), StandardCharsets.UTF_8)) {
            for (String line : stringList) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads data from a file and returns it as a list of strings.
     *
     * @param filename the name of the file to read data from
     * @return a list of strings containing the data from the file
     * @throws FileNotFoundException if the specified file is not found
     */
    public List<String> readDataFromFile(String filename) throws FileNotFoundException {
        Path path = Paths.get(Constants.PATH_TO_DATA_FILE + filename).toAbsolutePath();
        List<String> records = new ArrayList<>();
        File file = new File(path.toString());

        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + path);
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                records.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }
}

