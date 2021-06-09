package de.cas.adventofcode.day18;

import com.google.common.collect.ImmutableList;
import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test extends DayRealTest<Long> {
    private final Day18 day18 = new Day18();

    @Test
    void solvePart1Example1() {
        assertEquals(26L, day18.solvePart1(ImmutableList.of("2 * 3 + (4 * 5)")));
    }

    @Test
    void solvePart1Example2() {
        assertEquals(437L, day18.solvePart1(ImmutableList.of("5 + (8 * 3 + 9 + 3 * 4 * 3)")));
    }

    @Test
    void solvePart1Example3() {
        assertEquals(12240L, day18.solvePart1(ImmutableList.of("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")));
    }

    @Test
    void solvePart1Example4() {
        assertEquals(13632L, day18.solvePart1(ImmutableList.of("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")));
    }

    @Test
    void solvePart1AllExamples() {
        assertEquals(26335L, day18.solvePart1(Arrays.asList(
                "2 * 3 + (4 * 5)",
                "5 + (8 * 3 + 9 + 3 * 4 * 3)",
                "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))",
                "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")));
    }

    @Override
    public Day<Long> getDay() {
        return this.day18;
    }

    @Override
    public Map<String, Long> getExpectedResults1Personal() {
        Map<String, Long> nameToExpectedResult = new HashMap<>();

        nameToExpectedResult.put(FileReader.HENNING, 11076907812171L);
        nameToExpectedResult.put(FileReader.OSWALDO, 1408133923393L);
        nameToExpectedResult.put(FileReader.VELISLAV, 3647606140187L);

        return nameToExpectedResult;
    }

    @Override
    public Map<String, Long> getExpectedResults2Personal() {
        Map<String, Long> nameToExpectedResult = new HashMap<>();

        nameToExpectedResult.put(FileReader.HENNING, 283729053022731L);
        nameToExpectedResult.put(FileReader.OSWALDO, 314455761823725L);
        nameToExpectedResult.put(FileReader.VELISLAV, 323802071857594L);

        return nameToExpectedResult;
    }
}
