package de.cas.adventofcode.day04;

import org.junit.jupiter.api.Test;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import de.cas.adventofcode.shared.ListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04Test extends DayRealTest<Long>{
    private final Day<Long> day04 = new Day04();

    @Test
    void solvePart1() {
        final List<String> input = FileReader.getFileContents("day04/input.txt");
        final long result = this.day04.solvePart1(input);
        assertEquals(2, result);
    }

    @Test
    void solvePart2() {
        final List<String> inputInvalid = FileReader.getFileContents("day04/input_invalid.txt");
        final List<String> inputValid = FileReader.getFileContents("day04/input_valid.txt");

        final long resultInvalid = this.day04.solvePart2(inputInvalid);
        final long resultValid = this.day04.solvePart2(inputValid);
        assertEquals(0, resultInvalid);
        assertEquals(4, resultValid);
    }
    
	@Override
	public Day<Long> getDay() {
		return this.day04;
	}

	@Override
	public Map<String, Long> getExpectedResults1Personal() {
		Map<String, Long> nameToExpectedResult = new HashMap<>();
    	nameToExpectedResult.put(FileReader.HENNING, 222L);
    	nameToExpectedResult.put(FileReader.OSWALDO, 202L);
    	nameToExpectedResult.put(FileReader.VELISLAV, 254L);
    	return nameToExpectedResult;
	}

	@Override
	public Map<String, Long> getExpectedResults2Personal() {
		Map<String, Long> nameToExpectedResult = new HashMap<>();
    	nameToExpectedResult.put(FileReader.HENNING, 140L);
    	nameToExpectedResult.put(FileReader.OSWALDO, 137L);
    	nameToExpectedResult.put(FileReader.VELISLAV, 184L);
    	return nameToExpectedResult;
	}
}
