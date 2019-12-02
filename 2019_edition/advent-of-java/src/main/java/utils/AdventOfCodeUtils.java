package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdventOfCodeUtils {

    /*
     * Returns an immutable list of the lines of a given file.
     * If the file does not exists, it returns an empty list and prints an exception.
     */
    public static List<String> readClasspathFileLines(Class sourceClass, String fileName) {
        List<String> fileLines = Collections.emptyList();

        try {
            fileLines = getLinesFromClasspathFile(sourceClass, fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return fileLines;
    }

    private static List<String> getLinesFromClasspathFile(Class sourceClass, String fileName)
    throws URISyntaxException, IOException {
        URL fileUrl = sourceClass.getResource(fileName);

        if (fileUrl == null) {
            throw new FileNotFoundException();
        }

        File fileToRead = new File(fileUrl.toURI().getPath());
        return Files.readAllLines(fileToRead.toPath());
    }

    public static <T> int mapAndSumList(List<T> inputList, Function<? super T, Integer> functionToApply) {
        return inputList.stream()
        .map(functionToApply)
        .reduce(0, (a, b) -> a + b);
    }

    public static List<Integer> parseAllStringsToInt(List<String> valuesString) {
        return valuesString.stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    }

}
