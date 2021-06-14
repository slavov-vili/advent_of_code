package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AdventOfCodeUtilsTest {

    @Test
    void readClasspathFileLines_NonExistentFileTest() {
        List<String> expected = Collections.emptyList();

        String nameOfNonexistentFile = "myTestFilename.txt";
        List<String> actual = AdventOfCodeUtils.readInputLines(AdventOfCodeUtils.class);

        assertEquals(expected, actual, "Calling the function with a non-existent file should return an empty list!");
    }

    @Test
    void reactClasspathFileLines_ExistentFile_HelloWorldTxtTest() {
        List<String> expected = Arrays.asList("Hello", "World", "txt");
        String fileName = "input.txt";
        List<String> actual = AdventOfCodeUtils.readInputLines(AdventOfCodeUtils.class);
        assertEquals(expected, actual, "File contents do not match expectations!");
    }

}
