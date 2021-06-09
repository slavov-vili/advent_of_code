package de.cas.adventofcode.day11;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test extends DayRealTest<Integer> {
    private final Day11 day11 = new Day11();
    private final List<String> input = FileReader.getFileContents("day11/input.txt");

    @Test
    void solvePart1() {
        final int result = this.day11.solvePart1(this.input);
        assertEquals(37, result);
    }

    @Test
    void solvePart2() {
        final int result = this.day11.solvePart2(this.input);
        assertEquals(26, result);
    }

	@Override
	public Day<Integer> getDay() {
		return this.day11;
	}
	
	@Override
    public Map<String, Integer> getExpectedResults1Personal() {
        final Map<String, Integer> nameToExpectedResult = new HashMap<>();

        nameToExpectedResult.put(FileReader.HENNING, 2289);
        nameToExpectedResult.put(FileReader.OSWALDO, 2164);
        nameToExpectedResult.put(FileReader.VELISLAV, 2354);

        return nameToExpectedResult;
    }
    
    @Override
    public Map<String, Integer> getExpectedResults2Personal() {
        final Map<String, Integer> nameToExpectedResult = new HashMap<>();

        nameToExpectedResult.put(FileReader.HENNING, 2059);
        nameToExpectedResult.put(FileReader.OSWALDO, 1974);
        nameToExpectedResult.put(FileReader.VELISLAV, 2072);

        return nameToExpectedResult;
    }
}
