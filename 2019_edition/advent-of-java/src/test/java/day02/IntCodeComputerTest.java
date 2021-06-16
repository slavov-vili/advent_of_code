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
			Long invalidIntCode = 11L;
			List<Long> inputList = Arrays.asList(invalidIntCode, 0L, 0L, 0L, 99L);
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
	void BasicInput1LTest() {
		try {
			Long expected = 2L;
			List<Long> inputList = Arrays.asList(1L, 0L, 0L, 0L, 99L);
			IntCodeComputer computer = Day02Main.getDefaultComputer(inputList);

			computer.run();
			Long actual = computer.readFromMemory(0L);

			assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception should be thrown!");
		}

	}

	@Test
	void BasicInput2LTest() {
		try {
			Long expected = 6L;
			List<Long> inputList = Arrays.asList(2L, 3L, 0L, 3L, 99L);
			IntCodeComputer computer = Day02Main.getDefaultComputer(inputList);
			computer.run();
			Long actual = computer.readFromMemory(3L);

			assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception should be thrown!");
		}

	}

	@Test
	void AdvancedInputTest() {
		try {
			Long expected = 9801L;
			List<Long> inputList = Arrays.asList(2L, 4L, 4L, 5L, 99L, 0L);
			IntCodeComputer computer = Day02Main.getDefaultComputer(inputList);
			computer.run();
			
			Long actual = computer.readFromMemory(5L);
			
			assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception should be thrown!");
		}

	}

	@Test
	void ComplicatedInputTest() {
		try {
			Long expected = 30L;
			List<Long> inputList = Arrays.asList(1L, 1L, 1L, 4L, 99L, 5L, 6L, 0L, 99L);
			
			IntCodeComputer computer = Day02Main.getDefaultComputer(inputList);
			computer.run();
			Long actual = computer.readFromMemory(0L);

			assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception should be thrown!");
		}

	}
}
