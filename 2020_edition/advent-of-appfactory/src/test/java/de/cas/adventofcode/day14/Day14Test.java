package de.cas.adventofcode.day14;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test extends DayRealTest<Long> {
	private final Day14 day14 = new Day14();
	private final List<String> input1 = FileReader.getFileContents("day14/input_1.txt");
	private final List<String> input2 = FileReader.getFileContents("day14/input_2.txt");

	@Test
	void solvePart1() {
		final long result = this.day14.solvePart1(this.input1);
		assertEquals(165L, result);
	}

	@Test
	void solvePart2() {
		final long result = this.day14.solvePart2(this.input2);
		assertEquals(208L, result);
	}

	@Override
	public Day<Long> getDay() {
		return this.day14;
	}

	@Override
	public Map<String, Long> getExpectedResults1Personal() {
		final Map<String, Long> nameToExpectedResult = new HashMap<>();

		nameToExpectedResult.put(FileReader.HENNING, 10885823581193L);
		nameToExpectedResult.put(FileReader.OSWALDO, 18630548206046L);
		nameToExpectedResult.put(FileReader.VELISLAV, 12512013221615L);

		return nameToExpectedResult;
	}
    
    @Override
    public Map<String, Long> getExpectedResults2Personal() {
		final Map<String, Long> nameToExpectedResult = new HashMap<>();

		nameToExpectedResult.put(FileReader.HENNING, 3816594901962L);
		nameToExpectedResult.put(FileReader.OSWALDO, 4254673508445L);
		nameToExpectedResult.put(FileReader.VELISLAV, 3905642473893L);

		return nameToExpectedResult;
	}
}
