package org.vinted.homeworktask.service;
import org.junit.jupiter.api.Test;
import org.vinted.homeworktask.config.Constants;
import org.vinted.homeworktask.service.FileService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {
    private static final FileService fileService = new FileService();

    @Test
    void testReadDataFromFileTrowsFileNotFoundException() {
        assertThrows(FileNotFoundException.class,() ->  fileService.readDataFromFile("input2.txt"));
    }

    @Test
    void testReadDataFromFileDoesNotThrowsFileNotFoundException() {
        assertDoesNotThrow(() ->  fileService.readDataFromFile("input.txt"));
    }

    @Test
    void testWriteDataFromFileCreatesFile() {
        List<String> stringList = new ArrayList<>();
        stringList.add("test data");
        stringList.add("test data1");
        stringList.add("test data2");

        fileService.writeDataToFile(stringList,"outputTest.txt");
        Path path = Paths.get(Constants.PATH_TO_DATA_FILE + "outputTest.txt").toAbsolutePath();
        File outputFile = new File(path.toString());

        assertTrue(outputFile.exists());

        boolean delete = outputFile.delete();
        assertTrue(delete, "File was not deleted");
    }
}
