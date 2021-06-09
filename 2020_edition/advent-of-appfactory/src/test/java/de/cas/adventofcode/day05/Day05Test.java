package de.cas.adventofcode.day05;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {
    private final Day05 day05 = new Day05();

    @Test
    void solvePart1Example1() {
        final List<String> input = Collections.singletonList("FBFBBFFRLR");
        final int result = this.day05.solvePart1(input);
        assertEquals(357, result);
    }

    @Test
    void solvePart1Example2() {
        final List<String> input = Collections.singletonList("BFFFBBFRRR");
        final int result = this.day05.solvePart1(input);
        assertEquals(567, result);
    }

    @Test
    void solvePart1Example3() {
        final List<String> input = Collections.singletonList("FFFBBBFRRR");
        final int result = this.day05.solvePart1(input);
        assertEquals(119, result);
    }

    @Test
    void solvePart1Example4() {
        final List<String> input = Collections.singletonList("BBFFBBFRLL");
        final int result = this.day05.solvePart1(input);
        assertEquals(820, result);
    }

    @Test
    void solvePart1HighestSeatId() {
        final List<String> input = Arrays.asList("FBFBBFFRLR", "BBFFBBFRLL", "BFFFBBFRRR", "FFFBBBFRRR");
        final int result = this.day05.solvePart1(input);
        assertEquals(820, result);
    }

    @Test
    void solvePart2() {
    }
}
