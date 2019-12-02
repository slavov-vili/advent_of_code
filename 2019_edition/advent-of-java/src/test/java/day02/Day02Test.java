package day02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import exceptions.InvalidIntCodeException;

public class Day02Test {

    @Test
    void partATest() {
        int expected = 2894520;
        int actual;
        
        try {
            actual = Day02Main.solveA(Day02Main.getComputerDay02());

            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("No exception should be thrown!");
        }
    }
    
    @Test
    void partB_BasicTest() {
        int expected = 1202;
        int valueToFind = 2894520;
        int actual = Day02Main.solveB(Day02Main.getComputerDay02(), valueToFind);
        
        assertEquals(expected, actual);
    }
    
    @Test
    void partB_RealTest() {
        int expected = 9342;
        int valueToFind = 19690720;
        int actual = Day02Main.solveB(Day02Main.getComputerDay02(), valueToFind);
        
        assertEquals(expected, actual);
    }
}
