package day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day06Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day06Main.class);
		List<Integer> fishAges = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt)
				.collect(Collectors.toList());

		System.out.println("Fish after 80 days: " + solveA(fishAges));
	}
	
	public static int solveA(List<Integer> fishAges) {
		return simulateNDays(fishAges, 80).size();
	}
	
	public static List<Integer> simulateNDays(List<Integer> fishAges, int n) {
		List<Integer> finalAges = new LinkedList<>(fishAges);
		for (int i=0; i<n; i++) {
			System.out.println("Day " + i);
			finalAges = simulateDay(finalAges);
		}
		return finalAges;
	}

	public static List<Integer> simulateDay(List<Integer> fishAges) {
		List<Integer> births = fishAges.stream().filter(age -> age == 0).map(age -> 8).collect(Collectors.toList());
		List<Integer> nextAges = fishAges.stream().map(Day06Main::calcNextAge).collect(Collectors.toList());
		nextAges.addAll(births);
		return nextAges;
	}
	
	public static int calcNextAge(int age) {
		if (age == 0)
			return 6;
		else
			return age - 1;
	}
 }
