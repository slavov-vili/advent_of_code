package day08;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day08Main {

	public static Set<Integer> DIGITS_WITH_UNIQUE_SEGMENT_COUNTS = new HashSet<>(Arrays.asList(1, 4, 7, 8));

	public static void main(String[] args) {
		Map<List<Set<Character>>, List<Set<Character>>> displayMap = parseInput();

		System.out.println("Digits with unique segment count: " + solveA(displayMap.values()));

		System.out.println("Sum of decoded outputs: " + solveB(displayMap));
	}

	public static int solveA(Collection<List<Set<Character>>> outputs) {
		return (int) outputs.stream()
				.mapToLong(digits -> digits.stream().filter(Day08Main::hasUniqueSegmentCount).count()).sum();
	}

	public static int solveB(Map<List<Set<Character>>, List<Set<Character>>> displayMap) {
		return displayMap.entrySet().stream().mapToInt(Day08Main::decodeOutput).sum();
	}

	public static int decodeOutput(Map.Entry<List<Set<Character>>, List<Set<Character>>> displayEntry) {
		Map<Integer, Set<Character>> segmentDigitMap = decodeDisplay(displayEntry.getKey());
		List<Integer> decodedOutput = displayEntry.getValue().stream()
				.map(segments -> decodeSegments(segments, segmentDigitMap)).collect(Collectors.toList());
		return Integer.parseInt(decodedOutput.stream().map(i -> i.toString()).reduce(String::concat).get());
	}

	public static Map<Integer, Set<Character>> decodeDisplay(List<Set<Character>> display) {
		Map<Integer, Set<Character>> segmentDigitMap = new HashMap<>();
		List<Set<Character>> encodedDigits = new LinkedList<>(display);

		encodedDigits.stream().filter(Day08Main::hasUniqueSegmentCount)
				.forEach(segments -> segmentDigitMap.put(determineDigit(segments, segmentDigitMap), segments));
		encodedDigits.stream()
				.forEach(segments -> segmentDigitMap.put(determineDigit(segments, segmentDigitMap), segments));

		return segmentDigitMap;
	}

	public static int decodeSegments(Set<Character> segments, Map<Integer, Set<Character>> segmentDigitMap) {
		return segmentDigitMap.keySet().stream().filter(digit -> segments.equals(segmentDigitMap.get(digit)))
				.findFirst().get();
	}

	public static boolean hasUniqueSegmentCount(Set<Character> digitSegments) {
		return DIGITS_WITH_UNIQUE_SEGMENT_COUNTS.contains(determineDigit(digitSegments, new HashMap<>()));
	}

	public static int determineDigit(Set<Character> digitSegments, Map<Integer, Set<Character>> segmentDigitMap) {
		int digit = -1;
		Set<Character> oneSegments = segmentDigitMap.getOrDefault(1, new HashSet<>());
		Set<Character> fourSegments = segmentDigitMap.getOrDefault(4, new HashSet<>());
		if ((digitSegments.size() == 6) && (countSegmentsInCommon(digitSegments, fourSegments) == 3)
				&& (countSegmentsInCommon(digitSegments, oneSegments) == 2))
			digit = 0;
		else if (digitSegments.size() == 2)
			digit = 1;
		else if ((digitSegments.size() == 5) && (countSegmentsInCommon(digitSegments, fourSegments) == 2))
			digit = 2;
		else if ((digitSegments.size() == 5) && (countSegmentsInCommon(digitSegments, oneSegments) == 2))
			digit = 3;
		else if (digitSegments.size() == 4)
			digit = 4;
		else if ((digitSegments.size() == 5) && (countSegmentsInCommon(digitSegments, fourSegments) == 3)
				&& (countSegmentsInCommon(digitSegments, oneSegments) == 1))
			digit = 5;
		else if ((digitSegments.size() == 6) && (countSegmentsInCommon(digitSegments, oneSegments) == 1))
			digit = 6;
		else if (digitSegments.size() == 3)
			digit = 7;
		else if (digitSegments.size() == 7)
			digit = 8;
		else if ((digitSegments.size() == 6) && (countSegmentsInCommon(digitSegments, fourSegments) == 4))
			digit = 9;

		return digit;
	}

	public static int countSegmentsInCommon(Set<Character> unknownDigitSegments, Set<Character> knownDigitSegments) {
		Set<Character> segmentsInCommon = new HashSet<>(unknownDigitSegments);
		segmentsInCommon.retainAll(knownDigitSegments);
		return segmentsInCommon.size();
	}

	public static Map<List<Set<Character>>, List<Set<Character>>> parseInput() {
		List<String> input = AdventOfCodeUtils.readInput(Day08Main.class);
		return input.stream().map(line -> line.split("\\|")).collect(Collectors
				.toMap(displaySplit -> extractDigits(displaySplit[0]), displaySplit -> extractDigits(displaySplit[1])));
	}

	public static List<Set<Character>> extractDigits(String digits) {
		return Arrays.stream(digits.trim().split(" ")).map(Day08Main::getUniqueCharacters).collect(Collectors.toList());
	}

	public static Set<Character> getUniqueCharacters(String text) {
		return IntStream.range(0, text.length()).mapToObj(i -> text.charAt(i)).collect(Collectors.toSet());
	}
}
