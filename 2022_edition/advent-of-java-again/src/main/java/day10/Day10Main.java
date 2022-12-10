package day10;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day10Main {
	public static final String NOOP = "noop";
	public static final String ADDX = "addx";
	public static final int LAST_CYCLE = 220;
	public static final List<Integer> relevantCycles = List.of(20, 60, 100, 140, 180, LAST_CYCLE);

	public static final int CRT_WIDTH = 40;
	public static final int CRT_HEIGHT = 6;
	public static final int CRT_SIZE = CRT_WIDTH * CRT_HEIGHT;
	public static final String PIXEL_LIT = "#";
	public static final String PIXEL_DARK = " ";

	public static void main(String[] args) {
		var cycleValues = getCycleValues(AdventOfCodeUtils.readInput(Day10Main.class), LAST_CYCLE);

		System.out.println("Sum of relevant signal strengths: " + solveA(cycleValues));

		System.out.println("CRT Screen:\n" + solveB(cycleValues));
	}

	public static int solveA(List<Integer> cycleValues) {
		return relevantCycles.stream().mapToInt(cycle -> cycle * cycleValues.get(cycle - 1)).sum();
	}

	public static String solveB(List<Integer> cycleValues) {
		return IntStream.range(0, CRT_SIZE).mapToObj(i -> evalCRTCycle(i % 40, cycleValues.get(i)))
				.reduce(String::concat).get();
	}

	public static String evalCRTCycle(int curPixelIndex, int registerValue) {
		int pixelDist = Math.abs(curPixelIndex - registerValue);
		String crtCycleValue = (pixelDist <= 1) ? PIXEL_LIT : PIXEL_DARK;
		if (curPixelIndex == CRT_WIDTH - 1)
			crtCycleValue += "\n";
		return crtCycleValue;
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
