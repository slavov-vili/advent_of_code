package de.cas.adventofcode.day15;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test extends DayRealTest<Integer> {
    private final Day15 day15 = new Day15();
    
    @Test
    void solvePart1() {
    	List<String> input = List.of("0","3","6");
        final long result = this.day15.solvePart1(input);
        assertEquals(436, result);
    }

    @Test
    void solvePart2_036() {
    	List<String> input = List.of("0","3","6");
        final long result = this.day15.solvePart2(input);
        assertEquals(175594, result);
    }
    
    @Test
    void solvePart2_132() {
    	List<String> input = List.of("1","3","2");
        final long result = this.day15.solvePart2(input);
        assertEquals(2578, result);
    }

    @Test
    void solvePart2_213() {
    	List<String> input = List.of("2","1","3");
        final long result = this.day15.solvePart2(input);
        assertEquals(3544142, result);
    }

    @Test
    void solvePart2_123() {
    	List<String> input = List.of("1","2","3");
        final long result = this.day15.solvePart2(input);
        assertEquals(261214, result);
    }

    @Test
    void solvePart2_231() {
    	List<String> input = List.of("2","3","1");
        final long result = this.day15.solvePart2(input);
        assertEquals(6895259, result);
    }

    @Test
    void solvePart2_321() {
    	List<String> input = List.of("3","2","1");
        final long result = this.day15.solvePart2(input);
        assertEquals(18, result);
    }

    @Test
    void solvePart2_312() {
    	List<String> input = List.of("3","1","2");
        final long result = this.day15.solvePart2(input);
        assertEquals(362, result);
    }
    
	@Override
	public Day<Integer> getDay() {
		return this.day15;
	}
	
	@Override
    public Map<String, Integer> getExpectedResults1Personal() {
    	Map<String, Integer> nameToExpectedResult = new HashMap<>();

    	nameToExpectedResult.put(FileReader.HENNING, 755);
    	nameToExpectedResult.put(FileReader.OSWALDO, 273);
    	nameToExpectedResult.put(FileReader.VELISLAV, 273);
    	
    	return nameToExpectedResult;
    }
    
    @Override
    public Map<String, Integer> getExpectedResults2Personal() {
    	Map<String, Integer> nameToExpectedResult = new HashMap<>();

    	nameToExpectedResult.put(FileReader.HENNING, 11962);
    	nameToExpectedResult.put(FileReader.OSWALDO, 47205);
    	nameToExpectedResult.put(FileReader.VELISLAV, 47205);
    	
    	return nameToExpectedResult;
    }
}
