package day01;

import java.util.List;
import java.util.stream.Collectors;

public class Day01Solver {

    public int solveA(List<Integer> moduleMasses) {
	return calcFuelCostForAllModuleMasses(moduleMasses).stream()
		.reduce(0, (a, b) -> a + b);
    }

    protected static List<Integer> calcFuelCostForAllModuleMasses(List<Integer> moduleMasses) {
	return moduleMasses.stream()
		.map(mass -> calcFuelCostOfModule(mass))
		.collect(Collectors.toList());
    }

    protected static int calcFuelCostOfModule(int moduleMass) {
	return (Math.floorDiv(moduleMass, 3) - 2);
    }

    
    
    public int solveB(List<Integer> moduleMasses) {
	List<Integer> fuelMasses = calcFuelCostForAllModuleMasses(moduleMasses);

	return fuelMasses.stream()
		.map(fuelMass -> calcFuelCostOfFuel(fuelMass))
		.reduce(0, (a, b) -> a + b);
    }

    protected static int calcFuelCostOfFuel(int fuelMass) {
	if (fuelMass <= 0) {
	    return 0;
	}

	return fuelMass + calcFuelCostOfFuel(calcFuelCostOfModule(fuelMass));
    }
}
