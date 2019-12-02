package day02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Day02Test {

    @Test
    void IntCodeComputer_InvlidIntCodeTest() {
	int invalidIntCode = 11;
	IntCodeComputer computer = new IntCodeComputer(Arrays.asList(11, 0, 0, 0, 99), 4);
	try {
	    computer.processInput();
	    fail("An exception should be thrown, since IntCode " + invalidIntCode + " should not be known");
	} catch (InvalidIntCodeException e) {
	    return;
	}
	catch (Exception e) {
	    fail("Only InvalidIntCodeException should be thrown!");
	}
    }
    
    @Test
    void IntCodeComputer_BasicInput1Test() {
	List<Integer> expected = Arrays.asList(2, 0, 0, 0, 99);
	IntCodeComputer computer = new IntCodeComputer(Arrays.asList(1, 0, 0, 0, 99), 4);
	try {
	    computer.processInput();
	} catch (InvalidIntCodeException e) {
	    return;
	}

	List<Integer> actual = computer.getEndSequence();
	assertEquals(expected, actual, "End sequence should look like " + expected + " instead of " + actual);
    }
    
    @Test
    void IntCodeComputer_BasicInput2Test() {
	List<Integer> expected = Arrays.asList(2, 3, 0, 6, 99);
	IntCodeComputer computer = new IntCodeComputer(Arrays.asList(2, 3, 0, 3, 99), 4);
	try {
	    computer.processInput();
	} catch (InvalidIntCodeException e) {
	    return;
	}

	List<Integer> actual = computer.getEndSequence();
	assertEquals(expected, actual, "End sequence should look like " + expected + " instead of " + actual);
    }
    
    @Test
    void IntCodeComputer_AdvancedInputTest() {
	List<Integer> expected = Arrays.asList(2, 4, 4, 5, 99, 9801);
	IntCodeComputer computer = new IntCodeComputer(Arrays.asList(2, 4, 4, 5, 99, 0), 4);
	try {
	    computer.processInput();
	} catch (InvalidIntCodeException e) {
	    return;
	}

	List<Integer> actual = computer.getEndSequence();
	assertEquals(expected, actual, "End sequence should look like " + expected + " instead of " + actual);
    }
    
    @Test
    void IntCodeComputer_ComplicatedInputTest() {
	List<Integer> expected = Arrays.asList(30, 1, 1, 4, 2, 5, 6, 0, 99);
	IntCodeComputer computer = new IntCodeComputer(Arrays.asList(1, 1, 1, 4, 99, 5, 6, 0, 99), 4);
	try {
	    computer.processInput();
	} catch (InvalidIntCodeException e) {
	    return;
	}

	List<Integer> actual = computer.getEndSequence();
	assertEquals(expected, actual, "End sequence should look like " + expected + " instead of " + actual);
    }
}
