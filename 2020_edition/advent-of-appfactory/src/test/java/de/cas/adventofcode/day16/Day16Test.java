package de.cas.adventofcode.day16;

import de.cas.adventofcode.shared.FileReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {
    private final Day16 day16 = new Day16();

    @Test
    void solvePart1() {
    }

    @Test
    void solvePart2WithInput1() {
        List<String> lines = FileReader.getFileContents("day16/input_1.txt");
        long result = day16.solvePart2(lines);
        assertEquals(7 * 1 * 14, result);
    }

    @Test
    void solvePart2WithInput2() {
        List<String> lines = FileReader.getFileContents("day16/input_2.txt");
        long result = day16.solvePart2(lines);
        assertEquals(11 * 12 * 13, result);
    }
}
