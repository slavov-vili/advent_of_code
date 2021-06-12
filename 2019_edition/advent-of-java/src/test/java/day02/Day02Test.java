package day02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class Day02Test {

	@Test
	void partATest() {
		try {
			int expected = 2894520;
			int actual = Day02Main.solveA();

			assertEquals(expected, actual);
		} catch (Exception e) {
			fail("No exception should be thrown!");
		}
	}

	@Test
	void partB_BasicTest() {
		try {
			int expected = 1202;
			int valueToFind = 2894520;
			int actual = Day02Main.solveB(valueToFind);

			assertEquals(expected, actual);
		} catch (Exception e) {
			fail("No exception should be thrown!");
		}
	}

	@Test
	void partB_RealTest() {
		try {
			int expected = 9342;
			int valueToFind = 19690720;
			int actual = Day02Main.solveB(valueToFind);

			assertEquals(expected, actual);
		} catch (Exception e) {
			fail("No exception should be thrown!");
		}
	}
}
