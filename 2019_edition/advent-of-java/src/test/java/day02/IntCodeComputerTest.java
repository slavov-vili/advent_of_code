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
        int invalidIntCode = 11;
        List<Integer> inputList = Arrays.asList(invalidIntCode, 0, 0, 0, 99);
        IntCodeComputer computer = Day02Main.getComputer();
        try {
            computer.processInput(inputList, 0);
            fail("An exception should be thrown, since IntCode " + invalidIntCode + " should not be known");
        } catch (InvalidIntCodeException e) {
            return;
        } catch (Exception e) {
            fail("Only InvalidIntCodeException should be thrown!");
        }
    }

    @Test
    void BasicInput1Test() {
        List<Integer> expected = Arrays.asList(2, 0, 0, 0, 99);
        List<Integer> inputList = Arrays.asList(1, 0, 0, 0, 99);
        List<Integer> actual;
        IntCodeComputer computer = Day02Main.getComputer();
        try {
            actual = computer.processInput(inputList, 0);
        } catch (InvalidIntCodeException e) {
            return;
        }

        assertEquals(expected, actual);
    }

    @Test
    void BasicInput2Test() {
        List<Integer> expected = Arrays.asList(2, 3, 0, 6, 99);
        List<Integer> inputList = Arrays.asList(2, 3, 0, 3, 99);
        List<Integer> actual;
        IntCodeComputer computer = Day02Main.getComputer();
        try {
            actual = computer.processInput(inputList, 0);
        } catch (InvalidIntCodeException e) {
            return;
        }

        assertEquals(expected, actual);
    }

    @Test
    void AdvancedInputTest() {
        List<Integer> expected = Arrays.asList(2, 4, 4, 5, 99, 9801);
        List<Integer> inputList = Arrays.asList(2, 4, 4, 5, 99, 0);
        List<Integer> actual;
        IntCodeComputer computer = Day02Main.getComputer();
        try {
            actual = computer.processInput(inputList, 0);
        } catch (InvalidIntCodeException e) {
            return;
        }

        assertEquals(expected, actual);
    }

    @Test
    void ComplicatedInputTest() {
        List<Integer> expected = Arrays.asList(30, 1, 1, 4, 2, 5, 6, 0, 99);
        List<Integer> inputList = Arrays.asList(1, 1, 1, 4, 99, 5, 6, 0, 99);
        List<Integer> actual;
        IntCodeComputer computer = Day02Main.getComputer();
        try {
            actual = computer.processInput(inputList, 0);
        } catch (InvalidIntCodeException e) {
            return;
        }

        assertEquals(expected, actual);
    }
}
