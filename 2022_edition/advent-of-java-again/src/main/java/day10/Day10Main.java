package day10;

import java.util.ArrayList;
import java.util.List;

import utils.AdventOfCodeUtils;

public class Day10Main {
	public static final String NOOP = "noop";
	public static final String ADDX = "addx";
	public static final int LAST_CYCLE = 220;
	public static final List<Integer> relevantCycles = List.of(20, 60, 100, 140, 180, LAST_CYCLE);

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day10Main.class);

		System.out.println("Sum of relevant signal strengths: " + solveA(input));
	}

	public static int solveA(List<String> program) {
		var cycleValues = getCycleValues(program, LAST_CYCLE);
		return relevantCycles.stream().mapToInt(cycle -> cycle * cycleValues.get(cycle - 1)).sum();
	}

	public static void solveB() {
	}

	public static List<Integer> getCycleValues(List<String> program, int maxCycle) {
		var cycleValues = new ArrayList<Integer>();
		var curValue = 1;

		for (String line : program) {
			var parts = line.split(" ");

			cycleValues.add(curValue);
			if (ADDX.equals(parts[0])) {
				cycleValues.add(curValue);
				curValue += Integer.parseInt(parts[1]);
			}
		}

		return cycleValues;
	}
}
