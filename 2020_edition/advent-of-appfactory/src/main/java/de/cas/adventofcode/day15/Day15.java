package de.cas.adventofcode.day15;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.cas.adventofcode.shared.Day;

public class Day15 extends Day<Integer> {
	protected Day15() {
		super(15);
	}

	public static void main(final String[] args) {
		new Day15().run();
	}

	@Override
	public Integer solvePart1(List<String> input) {
		return findNthNumber(input, 2020);
	}

	@Override
	public Integer solvePart2(List<String> input) {
		return findNthNumber(input, 30000000);
	}
	
	private int findNthNumber(List<String> input, int nthNumberToFind) {
		int numberCount = input.size();
		Map<Integer, LastEncounters> spokenMap = IntStream.range(0, numberCount)
				.boxed()
				.collect(Collectors.toMap(i -> Integer.parseInt(input.get(i)), LastEncounters::create));
		
		int lastNumber = Integer.parseInt(input.get(numberCount-1));
		for (int i = numberCount; i < nthNumberToFind; i++) {
			Optional<Integer> lastNumberAge = getLastEncountersOf(spokenMap, lastNumber).calculateAge();
			
			if (lastNumberAge.isPresent()) {
				lastNumber = lastNumberAge.get();
				getLastEncountersOf(spokenMap, lastNumber).encounter(i);
				
			} else
				throw new RuntimeException(String.format("The number %d could not be found in the map!", lastNumber));
		}
		
		return lastNumber;
	}
	
	private LastEncounters getLastEncountersOf(Map<Integer, LastEncounters> spokenMap, int keyToFind) {
		if (spokenMap.containsKey(keyToFind)) {
			return spokenMap.get(keyToFind);
		} else {
			LastEncounters lastEncounters = new LastEncounters();
			spokenMap.put(keyToFind, lastEncounters);
			return lastEncounters;
		}
	}
}
