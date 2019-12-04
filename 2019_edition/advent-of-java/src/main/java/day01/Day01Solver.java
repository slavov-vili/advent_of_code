package day01;

import java.util.stream.Stream;

public class Day01Solver {

    public static int solveA(Stream<Integer> moduleMasses) {
        return moduleMasses.mapToInt(Day01Solver::calcSimplifiedFuelCostOfModule).sum();
    }

    public static int solveB(Stream<Integer> moduleMasses) {
        return moduleMasses.mapToInt(Day01Solver::calcAdvancedFueldCostOfModule).sum();
    }

    protected static int calcSimplifiedFuelCostOfModule(int moduleMass) {
        return (Math.floorDiv(moduleMass, 3) - 2);
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
