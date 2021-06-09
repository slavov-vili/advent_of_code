package de.cas.adventofcode.day02;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import de.cas.adventofcode.shared.ListUtils;

class Day02Test extends DayRealTest<Integer>{
    private final Day<Integer> day02 = new Day02();
    private final List<String> input = FileReader.getFileContents("day02/input.txt");
    
    @Test
    void solvePart1() {
        final int result = (int) day02.solvePart1(input);
        assertEquals(2, result);
    }

    @Test
    void solvePart2() {
        final int result = (int) day02.solvePart2(input);
        assertEquals(1, result);
    }



	@Override
	public Day<Integer> getDay() {
		return this.day02;
	}

	@Override
	public Map<String, Integer> getExpectedResults1Personal() {
		Map<String, Integer> nameToExpectedResult = new HashMap<>();
    	nameToExpectedResult.put(FileReader.HENNING, 467);
    	nameToExpectedResult.put(FileReader.OSWALDO, 603);
    	nameToExpectedResult.put(FileReader.VELISLAV, 645);
    	return nameToExpectedResult;
	}

	@Override
	public Map<String, Integer> getExpectedResults2Personal() {
		Map<String, Integer> nameToExpectedResult = new HashMap<>();
    	nameToExpectedResult.put(FileReader.HENNING, 441);
    	nameToExpectedResult.put(FileReader.OSWALDO, 404);
    	nameToExpectedResult.put(FileReader.VELISLAV, 737);
    	return nameToExpectedResult;
	}
}