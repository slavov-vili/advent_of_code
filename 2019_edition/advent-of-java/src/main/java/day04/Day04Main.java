package day04;

import java.util.List;

import utils.AdventOfCodeUtils;

public class Day04Main {

	public static void main(String[] args) {
		System.out.println("Solution A: " + solveA());

		System.out.println("Solution B: " + solveB());

	}

	public static int solveA() {
		return getRange().stream().filter(value -> PasswordUtils.checkPartA(value)).toArray().length;
	}

	public static int solveB() {
		return getRange().stream().filter(value -> PasswordUtils.checkForDay04PartB(value)).toArray().length;
	}

	private static List<Integer> getRange() {
		String input = AdventOfCodeUtils.readClasspathFileLines(Day04Main.class, "input.txt").get(0);
		String[] inputSplit = input.split("-");
		return AdventOfCodeUtils.generateRange(Integer.parseInt(inputSplit[0]), Integer.parseInt(inputSplit[1]));
	}
}
