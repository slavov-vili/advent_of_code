package day08;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import utils.AdventOfCodeUtils;

public class Day08Main {

	// 1 = 2 segments, 7 = 3, 4 = 4, 8 = 7
	public static final Map<Integer, Integer> UNIQUE_SEGMENT_LENGTH_MAP = getUniqueSegmentLengthMap();

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day08Main.class);
		List<String[]> displaySplits = input.stream().map(line -> line.split("\\|")).collect(Collectors.toList());

		List<String> inputA = displaySplits.stream().map(split -> split[1]).collect(Collectors.toList());
		System.out.println("Digits with unique segment count: " + solveA(inputA));

		System.out.println("Sum of decoded outputs: " + solveB(displaySplits));
	}

	public static int solveA(List<String> outputs) {
		return (int) outputs.stream().flatMap(Day08Main::extractSegments)
				.filter(digitSegments -> hasUniqueSegmentCount(digitSegments)).count();
	}

	public static int solveB(List<String[]> input) {
		return input.stream().mapToInt(Day08Main::decodeOutput).sum();
	}

	// TODO: parse as single number, not separate digits
	public static int decodeOutput(String[] displaySplit) {
		Map<Integer, Set<String>> segmentDigitMap = decodeDisplay(displaySplit[0]);
		String decodedOutput = extractSegments(displaySplit[1])
				.map(segments -> decodeSegments(segments, segmentDigitMap).toString())
				.reduce(String::concat).get();
		return Integer.parseInt(decodedOutput);
	}

	public static Integer decodeSegments(String segments, Map<Integer, Set<String>> segmentDigitMap) {
		return segmentDigitMap.keySet().stream()
				.filter(digit -> hasAllSegmentsInCommon(segments, segmentDigitMap.get(digit))).findFirst().get();
	}

	public static Map<Integer, Set<String>> decodeDisplay(String display) {
		Map<Integer, Set<String>> segmentDigitMap = new HashMap<>();
		List<String> encodedDigits = extractSegments(display).collect(Collectors.toList());

		String one = encodedDigits.stream().filter(segments -> segments.length() == 2).findFirst().get();
		segmentDigitMap.put(1, getUniqueCharacters(one));
		String four = encodedDigits.stream().filter(segments -> segments.length() == 4).findFirst().get();
		segmentDigitMap.put(4, getUniqueCharacters(four));
		String seven = encodedDigits.stream().filter(segments -> segments.length() == 3).findFirst().get();
		segmentDigitMap.put(7, getUniqueCharacters(seven));
		String eight = encodedDigits.stream().filter(segments -> segments.length() == 7).findFirst().get();
		segmentDigitMap.put(8, getUniqueCharacters(eight));
		encodedDigits.removeAll(Arrays.asList(one, four, seven, eight));

		String nine = encodedDigits.stream()
				.filter(segments -> countSegmentsInCommon(segments, segmentDigitMap.get(4)) == 4).findFirst().get();
		segmentDigitMap.put(9, getUniqueCharacters(nine));
		encodedDigits.remove(nine);

		String three = encodedDigits.stream()
				.filter(segments -> countSegmentsInCommon(segments, segmentDigitMap.get(7)) == 3)
				.filter(segments -> segments.length() == segmentDigitMap.get(7).size() + 2).findFirst().get();
		segmentDigitMap.put(3, getUniqueCharacters(three));
		encodedDigits.remove(three);

		String zero = encodedDigits.stream()
				.filter(segments -> countSegmentsInCommon(segments, segmentDigitMap.get(7)) == 3)
				.filter(segments -> countSegmentsInCommon(segments, segmentDigitMap.get(8)) == 6).findFirst().get();
		segmentDigitMap.put(0, getUniqueCharacters(zero));
		encodedDigits.remove(zero);

		String six = encodedDigits.stream()
				.filter(segments -> countSegmentsInCommon(segments, segmentDigitMap.get(8)) == 6).findFirst().get();
		segmentDigitMap.put(6, getUniqueCharacters(six));
		encodedDigits.remove(six);

		String five = encodedDigits.stream()
				.filter(segments -> countSegmentsInCommon(segments, segmentDigitMap.get(6)) == 5).findFirst().get();
		segmentDigitMap.put(5, getUniqueCharacters(five));
		encodedDigits.remove(five);

		String two = encodedDigits.get(0);
		segmentDigitMap.put(2, getUniqueCharacters(two));
		encodedDigits.remove(two);

		return segmentDigitMap;
	}

	public static boolean hasAllSegmentsInCommon(String digitSegments, Set<String> knownDigitSegments) {
		return getUniqueCharacters(digitSegments).equals(knownDigitSegments);
	}

	public static int countSegmentsInCommon(String digitSegments, Set<String> knownDigitSegments) {
		Set<String> uniqueSegmentsInDigit = getUniqueCharacters(digitSegments);
		uniqueSegmentsInDigit.retainAll(knownDigitSegments);
		return uniqueSegmentsInDigit.size();
	}

	public static Stream<String> extractSegments(String line) {
		return Arrays.stream(line.trim().split(" "));
	}

	public static boolean hasUniqueSegmentCount(String digitSegments) {
		return UNIQUE_SEGMENT_LENGTH_MAP.keySet().contains(getUniqueCharacters(digitSegments).size());
	}

	public static Set<String> getUniqueCharacters(String text) {
		return Arrays.stream(text.split("")).collect(Collectors.toSet());
	}

	public static Map<Integer, Integer> getUniqueSegmentLengthMap() {
		Map<Integer, Integer> uniqueSegmentLengthMap = new HashMap<>();
		uniqueSegmentLengthMap.put(2, 1);
		uniqueSegmentLengthMap.put(3, 7);
		uniqueSegmentLengthMap.put(4, 4);
		uniqueSegmentLengthMap.put(7, 8);
		return uniqueSegmentLengthMap;
	}
}
