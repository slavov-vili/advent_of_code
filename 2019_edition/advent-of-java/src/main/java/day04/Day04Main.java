package day04;

import java.util.List;

import utils.AdventOfCodeUtils;

public class Day04Main {

	public static void main(String[] args) {
		List<Integer> range = getRange();
		
		System.out.println("Solution A: " + solveA(range));

		System.out.println("Solution B: " + solveB(range));
	}

	public static int solveA(List<Integer> range) {
		return range.stream().filter(value -> PasswordUtils.checkForDay04PartA(value)).toArray().length;
	}

	public static int solveB(List<Integer> range) {
		return range.stream().filter(value -> PasswordUtils.checkForDay04PartB(value)).toArray().length;
	}

	private static List<Integer> getRange() {
		String input = AdventOfCodeUtils.readClasspathFileLines(Day04Main.class, "input.txt").get(0);
		String[] inputSplit = input.split("-");
		return AdventOfCodeUtils.generateRange(Integer.parseInt(inputSplit[0]), Integer.parseInt(inputSplit[1]));
	}
}
