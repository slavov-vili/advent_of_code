package day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day01Test {


    @Test
    void calcSimplifiedFuelForMass_BasicTest() {
        int expected = 2;
        int inputMass = 12;
        int actual = Day01Solver.calcSimplifiedFuelCostOfModule(inputMass);
        assertEquals(expected, actual, "A module mass of " + inputMass + " should yield a fuel cost of " + expected);
    }

    @Test
    void calcSimplifiedFuelForMass_RoundingTest() {
        int expected = 2;
        int inputMass = 14;
        int actual = Day01Solver.calcSimplifiedFuelCostOfModule(inputMass);
        assertEquals(expected, actual, "A module mass of " + inputMass + " should yield a fuel cost of " + expected);
    }

    @Test
    void calcSimplifiedFuelForMass_AdvancedTest() {
        int expected = 654;
        int inputMass = 1969;
        int actual = Day01Solver.calcSimplifiedFuelCostOfModule(inputMass);
        assertEquals(expected, actual, "A module mass of " + inputMass + " should yield a fuel cost of " + expected);
    }

    @Test
    void calcSimplifiedFuelForMass_ComplicatedTest() {
        int expected =33583;
        int inputMass = 100756;
        int actual = Day01Solver.calcSimplifiedFuelCostOfModule(inputMass);
        assertEquals(expected, actual, "A module mass of " + inputMass + " should yield a fuel cost of " + expected);
    }





    @Test
    void calcAdvancedFuelCostOfFuel_BasicTest() {
        int expected = 2;
        int inputMass = 14;
        int actual = Day01Solver.calcAdvancedFueldCostOfModule(inputMass);
        assertEquals(expected, actual, "A fuel mass of " + inputMass + " should yield a fuel cost of " + expected);
    }

    @Test
    void calcAdvancedFuelCostOfFuel_AdvancedTest() {
        int expected = 966;
        int inputMass = 1969;
        int actual = Day01Solver.calcAdvancedFueldCostOfModule(inputMass);
        assertEquals(expected, actual, "A fuel mass of " + inputMass + " should yield a fuel cost of " + expected);
    }

    @Test
    void calcAdvancedFuelCostOfFuel_ComplicatedTest() {
        int expected = 50346;
        int inputMass = 100756;
        int actual = Day01Solver.calcAdvancedFueldCostOfModule(inputMass);
        assertEquals(expected, actual, "A fuel mass of " + inputMass + " should yield a fuel cost of " + expected);
    }

}
