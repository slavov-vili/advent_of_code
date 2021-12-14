package day14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day14Main {

	public static Map<String, String> pairInsertions;

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day14Main.class);
		pairInsertions = extractPairInsertions(input);
		LinkedList<String> template = extractTemplate(input);

		System.out.println("Max - Min after 10 steps: " + solveA(template));
		System.out.println("Max - Min after 40 steps: " + solveB(template));
	}

	public static int solveA(LinkedList<String> template) {
		LinkedList<String> nextTemplate = new LinkedList<>(template);
		List<Integer> frequenciesSorted = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			nextTemplate = doStepOfPairInsertion(nextTemplate);
			frequenciesSorted = getFrequenciesSorted(nextTemplate);
		}

		return frequenciesSorted.get(frequenciesSorted.size() - 1) - frequenciesSorted.get(0);
	}

	public static long solveB(LinkedList<String> template) {
		List<String> pairs = IntStream.range(0, template.size() - 1)
				.mapToObj(i -> template.get(i).concat(template.get(i + 1))).collect(Collectors.toList());
		Map<String, Long> pairFrequencies = pairs.stream().distinct()
				.collect(Collectors.toMap(pair -> pair, pair -> (long) Collections.frequency(pairs, pair)));
		Map<String, Long> letterFrequencies = template.stream().distinct()
				.collect(Collectors.toMap(letter -> letter, letter -> (long) Collections.frequency(template, letter)));

		for (int i = 0; i < 40; i++) {
			Map<String, Long> newPairFrequencies = new HashMap<>();
			for (String pair : pairFrequencies.keySet()) {
				long curFrequency = pairFrequencies.get(pair);
				// technically the if-else is not needed
				if (matchesAnyRule(pair)) {
					String letterToInsert = applyRule(pair);
					adjustFrequency(letterToInsert, curFrequency, letterFrequencies);

					String firstHalf = pair.substring(0, 1).concat(letterToInsert);
					String secondHalf = letterToInsert.concat(pair.substring(1));
					adjustFrequency(firstHalf, curFrequency, newPairFrequencies);
					adjustFrequency(secondHalf, curFrequency, newPairFrequencies);
				} else {
					newPairFrequencies.put(pair, curFrequency);
				}
			}
			pairFrequencies = newPairFrequencies;
		}

		long minFreq = letterFrequencies.values().stream().mapToLong(freq -> freq).min().getAsLong();
		long maxFreq = letterFrequencies.values().stream().mapToLong(freq -> freq).max().getAsLong();
		return maxFreq - minFreq;
	}

	public static LinkedList<String> doStepOfPairInsertion(LinkedList<String> template) {
		LinkedList<String> nextTemplate = new LinkedList<>(template);
		int insertIndex = 0;
		for (int i = 0; i < template.size() - 1; i++) {
			String pair = template.get(i).concat(template.get(i + 1));
			if (matchesAnyRule(pair)) {
				insertIndex++;
				nextTemplate.add(insertIndex, applyRule(pair));
			}
			insertIndex++;
		}
		return nextTemplate;
	}

	public static <T> List<Integer> getFrequenciesSorted(List<T> elements) {
		return elements.stream().mapToInt(element -> Collections.frequency(elements, element)).sorted()
				.mapToObj(freq -> freq).collect(Collectors.toList());
	}
	
	public static void adjustFrequency(String keyToAdjust, long adjustment, Map<String, Long> map) {
		map.put(keyToAdjust, map.getOrDefault(keyToAdjust, 0L) + adjustment);
	}

	public static boolean matchesAnyRule(String pair) {
		return pairInsertions.containsKey(pair);
	}

	public static String applyRule(String pair) {
		return pairInsertions.getOrDefault(pair, "");
	}

	public static LinkedList<String> extractTemplate(List<String> input) {
		LinkedList<String> template = new LinkedList<>();
		for (String letter : input.get(0).split(""))
			template.add(letter);
		return template;
	}

	public static Map<String, String> extractPairInsertions(List<String> input) {
		Map<String, String> pairInsertions = new HashMap<>();
		for (String line : input.subList(2, input.size())) {
			String[] lineSplit = line.split(" -> ");
			pairInsertions.put(lineSplit[0], lineSplit[1]);
		}
		return pairInsertions;
	}

}
