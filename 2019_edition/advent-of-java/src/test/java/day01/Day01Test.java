package day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day01Test {

    
    @Test
    void calcFuelForMass_BasicTest() {
	int expected = 2;
	int inputMass = 12;
	int actual = Day01Solver.calcFuelCostOfModule(inputMass);
	assertEquals(expected, actual, "A module mass of " + inputMass + " should yield a fuel cost of " + expected);
    }
    
    @Test
    void calcFuelForMass_RoundingTest() {
	int expected = 2;
	int inputMass = 14;
	int actual = Day01Solver.calcFuelCostOfModule(inputMass);
	assertEquals(expected, actual, "A module mass of " + inputMass + " should yield a fuel cost of " + expected);
    }
    
    @Test
    void calcFuelForMass_AdvancedTest() {
	int expected = 654;
	int inputMass = 1969;
	int actual = Day01Solver.calcFuelCostOfModule(inputMass);
	assertEquals(expected, actual, "A module mass of " + inputMass + " should yield a fuel cost of " + expected);
    }
    
    @Test
    void calcFuelForMass_ComplicatedTest() {
	int expected =33583;
	int inputMass = 100756;
	int actual = Day01Solver.calcFuelCostOfModule(inputMass);
	assertEquals(expected, actual, "A module mass of " + inputMass + " should yield a fuel cost of " + expected);
    }
    
    @Test
    void calcFuelCostOfFuel_BasicTest() {
	int expected = 2;
	int inputFuelMass = 2;
	int actual = Day01Solver.calcFuelCostOfFuel(inputFuelMass);
	assertEquals(expected, actual, "A fuel mass of " + inputFuelMass + " should yield a fuel cost of " + expected);
    }
    
    @Test
    void calcFuelCostOfFuel_AdvancedTest() {
	int expected = 966;
	int inputFuelMass = 654;
	int actual = Day01Solver.calcFuelCostOfFuel(inputFuelMass);
	assertEquals(expected, actual, "A fuel mass of " + inputFuelMass + " should yield a fuel cost of " + expected);
    }    
    
    @Test
    void calcFuelCostOfFuel_ComplicatedTest() {
	int expected = 50346;
	int inputFuelMass = 33583;
	int actual = Day01Solver.calcFuelCostOfFuel(inputFuelMass);
	assertEquals(expected, actual, "A fuel mass of " + inputFuelMass + " should yield a fuel cost of " + expected);
    }
    
}
