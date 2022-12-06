package day06;

import utils.AdventOfCodeUtils;

public class Day06Main {
	public static void main(String[] args) {
		String input = AdventOfCodeUtils.readInput(Day06Main.class).get(0);

		System.out.println("Characters until packet marker: " + solveA(input));
		System.out.println("Characters until message marker: " + solveB(input));
	}

	public static int solveA(String datastream) {
		return findCharsBeforeObject(datastream, 4);
	}

	public static int solveB(String datastream) {
		return findCharsBeforeObject(datastream, 14);
	}

	public static int findCharsBeforeObject(String datastream, int objectSize) {
		for (int i = objectSize; i < datastream.length(); i++) {
			String lastFour = datastream.substring(i - objectSize, i);
			if (lastFour.chars().distinct().count() == objectSize)
				return i;
		}
		return -1;
	}
}
