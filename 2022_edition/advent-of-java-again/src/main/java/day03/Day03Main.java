package day03;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day03Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day03Main.class);

		System.out.println("Sum of priorities of duplicates: " + solveA(input));

		System.out.println("Sum of group badge priorities: " + solveB(input));
	}

	public static int solveA(List<String> lines) {
		return lines.stream().mapToInt(Day03Main::calcRucksackPriority).sum();
	}

	public static int solveB(List<String> lines) {
		int sum = 0;
		for (int i = 0; i <= lines.size() - 3; i += 3) {
			sum += calcGroupBadgePriority(lines.get(i), lines.get(i + 1), lines.get(i + 2));
		}
		return sum;
	}

	public static int calcRucksackPriority(String rucksack) {
		String firstCompartment = rucksack.substring(0, rucksack.length() / 2);
		String secondCompartment = rucksack.substring(rucksack.length() / 2, rucksack.length());

		var firstCompartmentContents = extractContents(firstCompartment);
		return secondCompartment.chars().mapToObj(x -> (char) x).filter(firstCompartmentContents::contains).findFirst()
				.map(Day03Main::charToPrio).get();
	}

	public static int calcGroupBadgePriority(String firstMember, String secondMember, String thirdMember) {
		var possibleBadges = extractContents(firstMember);
		possibleBadges.retainAll(extractContents(secondMember));
		possibleBadges.retainAll(extractContents(thirdMember));
		char badge = (char) possibleBadges.toArray()[0];
		return charToPrio(badge);
	}

	public static Set<Character> extractContents(String compartment) {
		return compartment.chars().mapToObj(x -> (char) x).collect(Collectors.toSet());
	}

	public static int charToPrio(char character) {
		if (Character.isLowerCase(character))
			return character - 96;
		else
			return character - 38;
	}
}
