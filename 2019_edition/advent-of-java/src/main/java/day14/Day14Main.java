package day14;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day14Main {

	public static final String ORE = "ORE";
	public static final long FUEL_AMOUNT = 1;
	public static final long ORE_SUPPLY = 1000000000000L;
	public static final Pattern chemicalPattern = Pattern.compile("\\d+ [a-zA-Z]+");

	public static Map<String, List<SimpleEntry<String, Long>>> reactionMap;
	public static Map<String, Long> leftoversMap;

	public static void main(String[] args) {
		reactionMap = getInput();

		System.out.printf("Cost of producing %d FUEL: %d\n", FUEL_AMOUNT, solveA(FUEL_AMOUNT));

		System.out.printf("You can produce %d FUEL with %d ORE\n", solveB(0, Integer.MAX_VALUE), ORE_SUPPLY);
	}

	public static long solveA(long fuelAmount) {
		leftoversMap = new HashMap<>();
		return calcCostOf("FUEL", fuelAmount);
	}

	// Basically binary search in a range of integers
	// Always finds the last smallest one, because integer division rounds down
	public static long solveB(int lowerBound, int upperBound) {
		int increment = (upperBound - lowerBound) / 2;

		if (increment == 0) {
			return lowerBound;
		} else {
			int curFuelCount = lowerBound + increment;
			long curCost = solveA(curFuelCount);

			long result = 0L;

			if (curCost < ORE_SUPPLY) {
				result = solveB(curFuelCount, upperBound);
			} else if (curCost > ORE_SUPPLY) {
				result = solveB(lowerBound, curFuelCount);
			} else {
				result = curFuelCount;
			}

			return result;
		}
	}

	public static long calcCostOf(SimpleEntry<String, Long> chemicalEntry) {
		return calcCostOf(chemicalEntry.getKey(), chemicalEntry.getValue());
	}

	public static long calcCostOf(String chemical, long quantity) {
		if (ORE.equals(chemical)) {
			return quantity;
		} else if (leftoversMap.getOrDefault(chemical, 0L) >= quantity) {
			leftoversMap.put(chemical, leftoversMap.getOrDefault(chemical, 0L) - quantity);
			return 0;
		} else {
			quantity = quantity - leftoversMap.getOrDefault(chemical, 0L);
			long productionCount = reactionMap.get(chemical).get(0).getValue();
			long reactionCount = (int) Math.ceil((double) quantity / (double) productionCount);

			long newLeftovers = (productionCount * reactionCount) - quantity;
			leftoversMap.put(chemical, newLeftovers);

			return reactionMap.get(chemical).subList(1, reactionMap.get(chemical).size()).stream()
					.map(inputEntry -> new SimpleEntry<String, Long>(inputEntry.getKey(),
							inputEntry.getValue() * reactionCount))
					.mapToLong(inputEntry -> calcCostOf(inputEntry)).sum();
		}
	}

	// Maps chemical to the reaction which creates it
	// Reaction = list of tuples
	// 0 = output
	// rest = inputs
	// Tuples = chemical and its quantity
	public static Map<String, List<SimpleEntry<String, Long>>> getInput() {
		List<String> inputLines = AdventOfCodeUtils.readInputLines(Day14Main.class);
		return inputLines.stream().map(Day14Main::convertLineToReaction)
				.collect(Collectors.toMap(reaction -> reaction.get(0).getKey(), reaction -> reaction));
	}

	public static List<SimpleEntry<String, Long>> convertLineToReaction(String inputLine) {
		List<SimpleEntry<String, Long>> reaction = new ArrayList<>();
		String[] split = inputLine.split("=>");
		reaction.add(convertChemicalToTuple(split[1]));

		Matcher chemicalMatcher = chemicalPattern.matcher(split[0]);
		while (chemicalMatcher.find()) {
			String curChemical = chemicalMatcher.group();
			reaction.add(convertChemicalToTuple(curChemical));
		}

		return reaction;
	}

	public static SimpleEntry<String, Long> convertChemicalToTuple(String chemical) {
		String[] split = chemical.trim().split(" ");
		return new SimpleEntry<>(split[1], Long.parseLong(split[0]));
	}
}
