package day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day01Test {

	@Test
	void partATest() {
		int expected = 3323874;
		int actual = Day01Solver.solveA(Day01Main.getInput());

		assertEquals(expected, actual);
	}

	@Test
	void partBTest() {
		int expected = 4982961;
		int actual = Day01Solver.solveB(Day01Main.getInput());

		assertEquals(expected, actual);
	}
}
