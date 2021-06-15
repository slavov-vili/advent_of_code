package day02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import exceptions.InvalidIntCodeException;

public class IntCodeComputerTest {
	@Test
	void InvlidIntCodeTest() {
		try {
			int invalidIntCode = 11;
			List<Integer> inputList = Arrays.asList(invalidIntCode, 0, 0, 0, 99);
			IntCodeComputer computer = Day02Main.getDefaultComputer(inputList);

			computer.run();
			fail("An exception should be thrown, since IntCode " + invalidIntCode + " should not be known");

		} catch (InvalidIntCodeException e) {
			return;
		} catch (Exception e) {
			e.printStackTrace();
			fail("Only InvalidIntCodeException should be thrown!");
		}
	}

	@Test
	void BasicInput1Test() {
		try {
			Integer expected = 2;
			List<Integer> inputList = Arrays.asList(1, 0, 0, 0, 99);
			IntCodeComputer computer = Day02Main.getDefaultComputer(inputList);

			computer.run();
			Integer actual = computer.readFromMemory(0);

			assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception should be thrown!");
		}

	}

	@Test
	void BasicInput2Test() {
		try {
			Integer expected = 6;
			List<Integer> inputList = Arrays.asList(2, 3, 0, 3, 99);
			IntCodeComputer computer = Day02Main.getDefaultComputer(inputList);
			computer.run();
			Integer actual = computer.readFromMemory(3);

			assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception should be thrown!");
		}

	}

	@Test
	void AdvancedInputTest() {
		try {
			Integer expected = 9801;
			List<Integer> inputList = Arrays.asList(2, 4, 4, 5, 99, 0);
			IntCodeComputer computer = Day02Main.getDefaultComputer(inputList);
			computer.run();
			
			Integer actual = computer.readFromMemory(5);
			
			assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception should be thrown!");
		}

	}

	@Test
	void ComplicatedInputTest() {
		try {
			Integer expected = 30;
			List<Integer> inputList = Arrays.asList(1, 1, 1, 4, 99, 5, 6, 0, 99);
			
			IntCodeComputer computer = Day02Main.getDefaultComputer(inputList);
			computer.run();
			Integer actual = computer.readFromMemory(0);

			assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception should be thrown!");
		}

	}
}
