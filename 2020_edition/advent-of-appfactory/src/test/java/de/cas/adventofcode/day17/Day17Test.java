package de.cas.adventofcode.day17;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test extends DayRealTest<Integer> {
    private final Day17 day17 = new Day17();

    @Test
    void solvePart1() {
    }

    @Test
    void solvePart1WithInput() {
        List<String> lines = FileReader.getFileContents("day17/input.txt");
        long result = day17.solvePart1(lines);
        assertEquals(112, result);
    }

    @Test
    void solvePart2WithInput() {
        List<String> lines = FileReader.getFileContents("day17/input.txt");
        long result = day17.solvePart2(lines);
        assertEquals(848, result);
    }
    
	@Override
    public Map<String, Integer> getExpectedResults1Personal() {
    	Map<String, Integer> nameToExpectedResult = new HashMap<>();

    	nameToExpectedResult.put(FileReader.HENNING, 306);
    	nameToExpectedResult.put(FileReader.OSWALDO, 317);
    	nameToExpectedResult.put(FileReader.VELISLAV, 319);
    	
    	return nameToExpectedResult;
    }
    
    @Override
    public Map<String, Integer> getExpectedResults2Personal() {
    	Map<String, Integer> nameToExpectedResult = new HashMap<>();

    	nameToExpectedResult.put(FileReader.HENNING, 2572);
    	nameToExpectedResult.put(FileReader.OSWALDO, 1692);
    	nameToExpectedResult.put(FileReader.VELISLAV, 2324);
    	
    	return nameToExpectedResult;
    }

	@Override
	public Day<Integer> getDay() {
		return day17;
	}
}
