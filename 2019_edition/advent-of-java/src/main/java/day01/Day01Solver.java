package day01;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day01Solver {

    public int solveA(List<Integer> moduleMasses) {
	return AdventOfCodeUtils.sumListOfIntegers(calcSimplifiedFuelCostForAllModuleMasses(moduleMasses));
    }

    protected static List<Integer> calcSimplifiedFuelCostForAllModuleMasses(List<Integer> moduleMasses) {
	return moduleMasses.stream()
		.map(mass -> calcSimplifiedFuelCostOfModule(mass))
		.collect(Collectors.toList());
    }

    protected static int calcSimplifiedFuelCostOfModule(int moduleMass) {
	return (Math.floorDiv(moduleMass, 3) - 2);
    }

    
    
    public int solveB(List<Integer> moduleMasses) {
	return AdventOfCodeUtils.sumListOfIntegers(calcAdvancedFuelCostForAllModuleMasses(moduleMasses));
    }
    
    protected static List<Integer> calcAdvancedFuelCostForAllModuleMasses(List<Integer> moduleMasses) {
	return moduleMasses.stream()
		.map(mass -> calcAdvancedFueldCostOfModule(mass))
		.collect(Collectors.toList());
    }
    
    protected static int calcAdvancedFueldCostOfModule(int moduleMass) {
	int simplifiedFuelCost = calcSimplifiedFuelCostOfModule(moduleMass);
	
	return calcFuelCostOfFuel(simplifiedFuelCost);
    }

    protected static int calcFuelCostOfFuel(int fuelMass) {
	if (fuelMass <= 0) {
	    return 0;
	}
	
	return fuelMass + calcFuelCostOfFuel(calcSimplifiedFuelCostOfModule(fuelMass));
    }
}
