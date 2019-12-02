package day01;

import java.util.List;

import utils.AdventOfCodeUtils;

public class Day01Solver {

    public static int solveA(List<Integer> moduleMasses) {
        return AdventOfCodeUtils.mapAndSumList(moduleMasses, Day01Solver::calcSimplifiedFuelCostOfModule);
    }

    protected static int calcSimplifiedFuelCostOfModule(int moduleMass) {
        return (Math.floorDiv(moduleMass, 3) - 2);
    }

    public static int solveB(List<Integer> moduleMasses) {
        return AdventOfCodeUtils.mapAndSumList(moduleMasses, Day01Solver::calcAdvancedFueldCostOfModule);
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
