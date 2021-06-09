package de.cas.adventofcode.day01;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import de.cas.adventofcode.shared.ListUtils;

class Day01Test extends DayRealTest<Integer> {
    private final Day<Integer> day01 = new Day01();
    private final List<String> lines = FileReader.getFileContents("day01/input.txt");

    @Test
    void solvePart1() {
        final int result = (int) day01.solvePart1(lines);
        assertEquals(514579, result);
    }

    @Test
    void solvePart2() {
        final int result = (int) day01.solvePart2(lines);
        assertEquals(241861950, result);
    }

	@Override
	public Day<Integer> getDay() {
		return this.day01;
	}

    @Override
    public Map<String, Integer> getExpectedResults1Personal() {
    	Map<String, Integer> nameToExpectedResult = new HashMap<>();
    	nameToExpectedResult.put(FileReader.HENNING, 1010884);
    	nameToExpectedResult.put(FileReader.OSWALDO, 703131);
    	nameToExpectedResult.put(FileReader.VELISLAV, 800139);
    	return nameToExpectedResult;
    }

	@Override
	public Map<String, Integer> getExpectedResults2Personal() {
		Map<String, Integer> nameToExpectedResult = new HashMap<>();
		nameToExpectedResult.put(FileReader.HENNING, 253928438);
		nameToExpectedResult.put(FileReader.OSWALDO, 272423970);
		nameToExpectedResult.put(FileReader.VELISLAV, 59885340);
		return nameToExpectedResult;
	}
}