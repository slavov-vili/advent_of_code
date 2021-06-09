package de.cas.adventofcode.day07;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BagMapBuilder {
	private static final String SEPARATOR_OUTER_BAG = " bags contain ";
	private static final String SEPARATOR_INNER_BAGS = ", ";
	private static final String GROUP_INNER_BAG_COUNT = "$1";
	private static final String GROUP_INNER_BAG_COLOR = "$2";
	private static final String GROUP_PATTERN_INNER_BAG = GROUP_INNER_BAG_COUNT + " " + GROUP_INNER_BAG_COLOR;

	private final String innerBagFullPattern;
	
	public BagMapBuilder(String bagNumberAndColorPattern) {
		this.innerBagFullPattern = bagNumberAndColorPattern + " bags?\\.?";
	}
	
	protected Map<String, Set<String>> mapInnerToOuterBags(List<String> input) {
		return buildBagMap(input, this::extractInnerBagsByColor, this::extractOuterBags);
	}
	
	protected Map<String, Set<String>> mapOuterToInnerBags(List<String> input) {
		return buildBagMap(input, this::extractOuterBags, this::extractInnerBagsFull);
	}
	
	protected Map<String, Set<String>> buildBagMap(List<String> input,
			Function<String, List<String>> keyGetter,
			Function<String, List<String>> valueGetter) {
		Map<String, Set<String>> bagToOuterBags = new HashMap<>();
		for (String line : input) {
			List<String> keyBags = keyGetter.apply(line);
			List<String> valueBags = valueGetter.apply(line);
			
			for (String keyBag : keyBags)
				for (String valueBag : valueBags) {
					bagToOuterBags.putIfAbsent(keyBag, new HashSet<>());
					bagToOuterBags.get(keyBag).add(valueBag);
			}
		}
		return bagToOuterBags;
	}
	
	private List<String> extractOuterBags(String inputLine) {
		String[] outerInnerBagsSplit = splitOuterInnerBags(inputLine);
		return List.of(outerInnerBagsSplit[0]);
	}
	
	private String[] splitOuterInnerBags(String inputLine) {
		return inputLine.split(SEPARATOR_OUTER_BAG, 2);
	}
	
	private List<String> extractInnerBagsByColor(String inputLine) {
		return extractInnerBags(inputLine, this::extractInnerBagColor);
	}
	
	private List<String> extractInnerBagsFull(String inputLine) {
		return extractInnerBags(inputLine, this::extractInnerBagFull);
	}
	
	private String extractInnerBagColor(String innerBagDescription) {
		return innerBagDescription.replaceAll(this.innerBagFullPattern, GROUP_INNER_BAG_COLOR);
	}
	
	private String extractInnerBagFull(String innerBagDescription) {
		return innerBagDescription.replaceAll(this.innerBagFullPattern, GROUP_PATTERN_INNER_BAG);
	}
	
	private List<String> extractInnerBags(String inputLine, Function<String, String> innerBagExtractor) {
		String[] outerInnerBagsSplit = splitOuterInnerBags(inputLine);
		String[] innerBagsSplit = outerInnerBagsSplit[1].split(SEPARATOR_INNER_BAGS);
		return Arrays.stream(innerBagsSplit)
				.map(innerBagExtractor)
				.collect(Collectors.toList());
	}
}
