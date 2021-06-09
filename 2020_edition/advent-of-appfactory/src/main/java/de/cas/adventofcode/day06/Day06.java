package de.cas.adventofcode.day06;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.cas.adventofcode.shared.Day;

public class Day06 extends Day<Integer> {
	protected Day06() {
		super(6);
	}

	public static void main(final String[] args) {
		new Day06().run();
	}

	@Override
	public Integer solvePart1(List<String> input) {
		String combinedGroupLines = new String();
		List<Integer> groupUniqueCharsCount = new ArrayList<>();
		input.add("");
		for (String line : input) {
			if(line.isBlank()) {
				groupUniqueCharsCount.add(getSetOfChars(combinedGroupLines).size());
				combinedGroupLines = new String();
				continue;
			}
			combinedGroupLines = combinedGroupLines + line;
		}
		return groupUniqueCharsCount.stream().reduce(0, Integer::sum);
	}

	@Override
	public Integer solvePart2(List<String> input) {
		List<Integer> groupUniqueCharsCounts = new ArrayList<>();
		ArrayList<String> groupEntryList = new ArrayList<>();
		input.add("");
		for (String line : input) {
			if(line.isBlank() && !groupEntryList.isEmpty()) {
				groupUniqueCharsCounts.add(getCommonCharactersCount(groupEntryList));
				groupEntryList.removeAll(groupEntryList);
				continue;
			}
			groupEntryList.add(line);
		}
		return groupUniqueCharsCounts.stream().reduce(0, Integer::sum);
	}

	private int getCommonCharactersCount(List<String> groupEntryList) {
		Set<Character> commonCharacters = getSetOfChars(groupEntryList.get(0));
		for (String entry : groupEntryList) {
			Set<Character> setOfChars = getSetOfChars(entry);
			commonCharacters.retainAll(setOfChars);
		}
		return commonCharacters.size();
	}

	private Set<Character> getSetOfChars(String string) {
		char[] chars = string.toCharArray();
		Set<Character> charsSet = new HashSet<>();
		for (int i = 0; i < chars.length; i++) {
			charsSet.add(chars[i]);
		}
		return charsSet;
	}

}
