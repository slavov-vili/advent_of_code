package de.cas.adventofcode.day03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import de.cas.adventofcode.shared.ListUtils;

class Day03Test extends DayRealTest<Long> {
    private final Day<Long> day03 = new Day03();
    private final List<String> input = FileReader.getFileContents("day03/input.txt");

    @Test
    void solvePart1() {
        final long result = day03.solvePart1(input);
        assertEquals(7, result);
    }

    @Test
    void solvePart2() {
        final long result = day03.solvePart2(input);
        assertEquals(336, result);
    }
    
    @Override
    public Day<Long> getDay() {
    	return this.day03;
    }

    @Override
    public Map<String, Long> getExpectedResults1Personal() {
    	Map<String, Long> nameToExpectedResult = new HashMap<>();

    	nameToExpectedResult.put(FileReader.HENNING, 237L);
    	nameToExpectedResult.put(FileReader.OSWALDO, 178L);
    	nameToExpectedResult.put(FileReader.VELISLAV, 209L);

    	return nameToExpectedResult;
    }

    @Override
    public Map<String, Long> getExpectedResults2Personal() {
    	Map<String, Long> nameToExpectedResult = new HashMap<>();

    	nameToExpectedResult.put(FileReader.HENNING, 2106818610L);
    	nameToExpectedResult.put(FileReader.OSWALDO, 3492520200L);
    	nameToExpectedResult.put(FileReader.VELISLAV, 1574890240L);

    	return nameToExpectedResult;
    }
}