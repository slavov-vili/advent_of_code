package day01;

import java.util.ArrayList;
import java.util.List;

import utils.AdventOfCodeUtils;

public class Day01Main {

	public static void main(String[] args) {
		var elfCalories = parseElfCalories();

		System.out.println("Elf with max calories: " + solveA(elfCalories));

		System.out.println("Sum of calories of top 3 elves: " + solveB(elfCalories));
	}

	public static int solveA(List<Integer> elfCalories) {
		return elfCalories.stream().mapToInt(x -> x).max().getAsInt();
	}

	public static int solveB(List<Integer> elfCalories) {
		return elfCalories.stream().mapToInt(x -> x).sorted().skip(elfCalories.size() - 3).sum();
	}

	private static List<Integer> parseElfCalories() {
		var elfCalories = new ArrayList<Integer>();
		var input = AdventOfCodeUtils.readInput(Day01Main.class);

		int curElfCalories = 0;
		for (int i = 0; i < input.size(); i++) {
			String curLine = input.get(i);
			if (curLine.isEmpty() || i == input.size() - 1) {
				elfCalories.add(curElfCalories);
				curElfCalories = 0;
			} else {
				curElfCalories += Integer.parseInt(curLine);
			}
		}

		return elfCalories;
	}
}
