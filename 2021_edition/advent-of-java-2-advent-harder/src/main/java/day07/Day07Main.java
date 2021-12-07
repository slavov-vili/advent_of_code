package day07;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import utils.AdventOfCodeUtils;

public class Day07Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day07Main.class);
		List<Long> crabPositions = Arrays.stream(input.get(0).split(",")).map(Long::parseLong)
				.collect(Collectors.toList());

		System.out.println("Minimal Fuel for constant costs: " + solveA(crabPositions));
		System.out.println();
		System.out.println("Minimal Fuel for increasing costs: " + solveB(crabPositions));
	}
	
	public static long solveA(List<Long> crabPositions) {
		return findMinFuelCost(crabPositions, Day07Main::calcFuelCostA);
	}
	
	public static long solveB(List<Long> crabPositions) {
		return findMinFuelCost(crabPositions, Day07Main::calcFuelCostB);
	}

	// The minimum fuel should be around the average of the crab distances
	// I am looking from the middle of the values before the average
	// to the middle of the values after the average
	public static long findMinFuelCost(List<Long> crabPositions, BiFunction<List<Long>, Long, Long> fuelCalculator) {
		Long positionSum = crabPositions.stream().mapToLong(pos -> pos).sum();
		Long avgPosition = positionSum / crabPositions.size();
		long maxPossiblePos = avgPosition + (positionSum - avgPosition ) / 2;
		long minPossiblePos = avgPosition / 2;
		
		long minFuelCost = Long.MAX_VALUE;
		long bestPosition = 0;
		for (long pos = minPossiblePos; pos <= maxPossiblePos; pos++) {
			long curFuelCost = fuelCalculator.apply(crabPositions, pos);
			if (curFuelCost < minFuelCost) {
				minFuelCost = curFuelCost;
				bestPosition = pos;
			}
		}
		
		System.out.println("The average crab Position is: " + avgPosition);
		System.out.println("Look in range [" + minPossiblePos + ", " + maxPossiblePos + "]:");
		System.out.println("The best position for all crabs was: " + bestPosition);
		System.out.println();
		return minFuelCost;
	}

	public static long calcFuelCostA(List<Long> crabPositions, long positionToCheck) {
		return crabPositions.stream().mapToLong(pos -> Math.abs(pos - positionToCheck)).sum();
	}

	public static long calcFuelCostB(List<Long> crabPositions, long positionToCheck) {
		return crabPositions.stream().mapToLong(pos -> Math.abs(pos - positionToCheck))
				.map(Day07Main::calcExpensiveSteps).sum();
	}

	public static long calcExpensiveSteps(long stepCount) {
		return LongStream.range(1, stepCount + 1).sum();
	}
}
