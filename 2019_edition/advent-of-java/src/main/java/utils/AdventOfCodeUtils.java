package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class AdventOfCodeUtils {

    /*
     * Returns an immutable list of the lines of a given file. If the file does not
     * exist, it returns an empty list and prints an exception.
     */
    public static List<String> readInputLines(Class<?> sourceClass) {
        List<String> fileLines = Collections.emptyList();

        try {
            fileLines = getLinesFromClasspathFile(sourceClass, "input.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return fileLines;
    }

    private static List<String> getLinesFromClasspathFile(Class<?> sourceClass, String fileName)
            throws URISyntaxException, IOException {
        URL fileUrl = sourceClass.getResource(fileName);

        if (fileUrl == null) {
            throw new FileNotFoundException();
        }

        File fileToRead = new File(fileUrl.toURI().getPath());
        return Files.readAllLines(fileToRead.toPath());
    }

}
