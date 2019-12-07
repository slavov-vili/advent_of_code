package day02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
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
            computer.processCodes(inputList, 0, null, null);
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
        List<Integer> expected = Arrays.asList(2, 0, 0, 0, 99);
        List<Integer> inputList = Arrays.asList(1, 0, 0, 0, 99);
        List<Integer> actual = new ArrayList<>();
        IntCodeComputer computer = Day02Main.getComputer();
        try {
            actual = computer.processCodes(inputList, 0, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should be thrown!");
        }

        assertEquals(expected, actual);
    }

    @Test
    void BasicInput2Test() {
        List<Integer> expected = Arrays.asList(2, 3, 0, 6, 99);
        List<Integer> inputList = Arrays.asList(2, 3, 0, 3, 99);
        List<Integer> actual = new ArrayList<>();
        IntCodeComputer computer = Day02Main.getComputer();
        try {
            actual = computer.processCodes(inputList, 0, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should be thrown!");
        }

        assertEquals(expected, actual);
    }

    @Test
    void AdvancedInputTest() {
        List<Integer> expected = Arrays.asList(2, 4, 4, 5, 99, 9801);
        List<Integer> inputList = Arrays.asList(2, 4, 4, 5, 99, 0);
        List<Integer> actual = new ArrayList<>();
        IntCodeComputer computer = Day02Main.getComputer();
        try {
            actual = computer.processCodes(inputList, 0, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should be thrown!");
        }

        assertEquals(expected, actual);
    }

    @Test
    void ComplicatedInputTest() {
        List<Integer> expected = Arrays.asList(30, 1, 1, 4, 2, 5, 6, 0, 99);
        List<Integer> inputList = Arrays.asList(1, 1, 1, 4, 99, 5, 6, 0, 99);
        List<Integer> actual = new ArrayList<>();
        IntCodeComputer computer = Day02Main.getComputer();
        try {
            actual = computer.processCodes(inputList, 0, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should be thrown!");
        }

        assertEquals(expected, actual);
    }
}
