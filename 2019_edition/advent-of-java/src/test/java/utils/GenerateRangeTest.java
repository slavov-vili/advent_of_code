package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GenerateRangeTest {
	@Test
	void generateRange_sameValueZeroTest() {
		Integer valueToAdd = 0;
		List<Integer> expected = Arrays.asList(valueToAdd);
		List<Integer> actual = AdventOfCodeUtils.generateRange(valueToAdd, valueToAdd);

		assertEquals(expected, actual,
				"When the first and last values are the same, the range should only consist of that value!");
	}

	@Test
	void generateRange_sameValueNegativeTest() {
		Integer valueToAdd = -100;
		List<Integer> expected = Arrays.asList(valueToAdd);
		List<Integer> actual = AdventOfCodeUtils.generateRange(valueToAdd, valueToAdd);

		assertEquals(expected, actual,
				"When the first and last values are the same, the range should only consist of that value!");
	}

	@Test
	void generateRange_sameValuePositiveTest() {
		Integer valueToAdd = 20;
		List<Integer> expected = Arrays.asList(valueToAdd);
		List<Integer> actual = AdventOfCodeUtils.generateRange(valueToAdd, valueToAdd);

		assertEquals(expected, actual,
				"When the first and last values are the same, the range should only consist of that value!");
	}

	@Test
	void generateRange_positiveIncreasingFromZeroTest() {
		Integer firstValue = 0;
		Integer lastValue = 5;
		List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4, 5);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}

	@Test
	void generateRange_positiveIncreasingFromIntTest() {
		Integer firstValue = 1;
		Integer lastValue = 5;
		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}

	@Test
	void generateRange_positiveDecreasingToZeroTest() {
		Integer firstValue = 5;
		Integer lastValue = 0;
		List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1, 0);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}

	@Test
	void generateRange_positiveDecreasingToIntTest() {
		Integer firstValue = 5;
		Integer lastValue = 1;
		List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}

	@Test
	void generateRange_negativeDecreasingFromZeroTest() {
		Integer firstValue = 0;
		Integer lastValue = -5;
		List<Integer> expected = Arrays.asList(0, -1, -2, -3, -4, -5);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}

	@Test
	void generateRange_negativeDecreasingFromIntTest() {
		Integer firstValue = -1;
		Integer lastValue = -5;
		List<Integer> expected = Arrays.asList(-1, -2, -3, -4, -5);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}

	@Test
	void generateRange_negativeIncreasingToZeroTest() {
		Integer firstValue = -5;
		Integer lastValue = 0;
		List<Integer> expected = Arrays.asList(-5, -4, -3, -2, -1, 0);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}

	@Test
	void generateRange_negativeIncreasingToIntTest() {
		Integer firstValue = -5;
		Integer lastValue = -1;
		List<Integer> expected = Arrays.asList(-5, -4, -3, -2, -1);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}

	@Test
	void generateRange_negativeToPositiveTest() {
		Integer firstValue = -3;
		Integer lastValue = 3;
		List<Integer> expected = Arrays.asList(-3, -2, -1, 0, 1, 2, 3);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}

	@Test
	void generateRange_PositiveToNegativeTest() {
		Integer firstValue = 3;
		Integer lastValue = -3;
		List<Integer> expected = Arrays.asList(3, 2, 1, 0, -1, -2, -3);
		List<Integer> actual = AdventOfCodeUtils.generateRange(firstValue, lastValue);

		assertEquals(expected, actual);
	}
}
