package day02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class Day02Test {

	@Test
	void partATest() {
		try {
			int expected = 2894520;
			Long actual = Day02Main.solveA();

			assertEquals(expected, actual);
		} catch (Exception e) {
			fail("No exception should be thrown!");
		}
	}

	@Test
	void partB_BasicTest() {
		try {
			int expected = 1202;
			Long valueToFind = 2894520L;
			Long actual = Day02Main.solveB(valueToFind);

			assertEquals(expected, actual);
		} catch (Exception e) {
			fail("No exception should be thrown!");
		}
	}

	@Test
	void partB_RealTest() {
		try {
			int expected = 9342;
			Long valueToFind = 19690720L;
			Long actual = Day02Main.solveB(valueToFind);

			assertEquals(expected, actual);
		} catch (Exception e) {
			fail("No exception should be thrown!");
		}
	}
}
