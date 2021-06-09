package de.cas.adventofcode.day07;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.cas.adventofcode.shared.Day;

public class Day07 extends Day<Integer> {
	private static final String BAG_NUMBER_AND_COLOR = "([0-9]+) ([a-z ]+)";
	private static final String GROUP_INNER_BAG_COUNT = "$1";
	private static final String GROUP_INNER_BAG_COLOR = "$2";
	
	private static final String SHINY_GOLD = "shiny gold";
	private static final String EMPTY_BAG_CONTENTS = "no other bags.";

	private BagMapBuilder bagBuilder;

	protected Day07() {
		super(7);
		this.bagBuilder = new BagMapBuilder(BAG_NUMBER_AND_COLOR);
	}

	public static void main(final String[] args) {
		new Day07().run();
	}

	@Override
	public Integer solvePart1(List<String> input) {
		Map<String, Set<String>> bagToOuterBags = this.bagBuilder.mapInnerToOuterBags(input);
		Set<String> shinyGoldContainers = findOuterBagsOf(bagToOuterBags, SHINY_GOLD, new HashSet<>());
		return shinyGoldContainers.size();
	}

	private Set<String> findOuterBagsOf(Map <String, Set<String>> map, String bagToFind, Set<String> foundOuterBags) {
		Set<String> outerBags = map.getOrDefault(bagToFind, new HashSet<>());
		if (outerBags.isEmpty()) {
			return foundOuterBags;
		}
		
		for (String outerBag : outerBags) {
			if (!foundOuterBags.contains(outerBag)) {
				foundOuterBags.add(outerBag);
				foundOuterBags.addAll(findOuterBagsOf(map, outerBag, foundOuterBags));
			}
		}
		return foundOuterBags;
	}

	@Override
	public Integer solvePart2(List<String> input) {
		Map<String, Set<String>> bagToInnerBags = this.bagBuilder.mapOuterToInnerBags(input);
		return findInnerBagsOf(bagToInnerBags, SHINY_GOLD) - 1;
	}

	private int findInnerBagsOf(Map<String, Set<String>> bagToInnerBags, String bagToFind) {
		Set<String> innerBags = bagToInnerBags.getOrDefault(bagToFind, new HashSet<>());
		if (innerBags.isEmpty() || Set.of(EMPTY_BAG_CONTENTS).equals(innerBags) ) {
			return 1;
		}
		
		int sum = 1;
		for (String innerBag : innerBags) {
			int bagCount = Integer.parseInt(innerBag.replaceAll(BAG_NUMBER_AND_COLOR, GROUP_INNER_BAG_COUNT));
			String bagColor = innerBag.replaceAll(BAG_NUMBER_AND_COLOR, GROUP_INNER_BAG_COLOR);
			
			sum += bagCount * findInnerBagsOf(bagToInnerBags, bagColor);
		}
		return sum;
	}

}
