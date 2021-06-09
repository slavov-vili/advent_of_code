package de.cas.adventofcode.day10;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test extends DayRealTest<Long> {
	private final Day10 day10 = new Day10();
	private final List<String> inputSimple = FileReader.getFileContents("day10/input_simple.txt");
	private final List<String> inputComplex = FileReader.getFileContents("day10/input_complex.txt");

	@Test
	void solvePart1Simple() {
		final long result = this.day10.solvePart1(this.inputSimple);
		assertEquals(35, result);
	}

	@Test
	void solvePart1Complex() {
		final long result = this.day10.solvePart1(this.inputComplex);
		assertEquals(220, result);
	}

	@Test
	void solvePart2Simple() {
		final long result = this.day10.solvePart2(this.inputSimple);
		assertEquals(8, result);
	}

	@Test
	void solvePart2Complex() {
		final long result = this.day10.solvePart2(this.inputComplex);
		assertEquals(19208L, result);
	}

	@Override
	public Day<Long> getDay() {
		return this.day10;
	}

	@Override
	public Map<String, Long> getExpectedResults1Personal() {
		final Map<String, Long> nameToExpectedResult = new HashMap<>();

		nameToExpectedResult.put(FileReader.HENNING, 2812L);
		nameToExpectedResult.put(FileReader.OSWALDO, 2176L);
		nameToExpectedResult.put(FileReader.VELISLAV, 1984L);

		return nameToExpectedResult;
	}

	@Override
	public Map<String, Long> getExpectedResults2Personal() {
		final Map<String, Long> nameToExpectedResult = new HashMap<>();

		nameToExpectedResult.put(FileReader.HENNING, 386869246296064L);
		nameToExpectedResult.put(FileReader.OSWALDO, 18512297918464L);
		nameToExpectedResult.put(FileReader.VELISLAV, 3543369523456L);

		return nameToExpectedResult;
	}
}
