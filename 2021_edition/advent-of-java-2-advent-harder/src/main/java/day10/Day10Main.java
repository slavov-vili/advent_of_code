package day10;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day10Main {
	public static Map<Character, Character> CLOSING_BRACKETS_MAP = getClosingBracketsMap();

	public static void main(String[] args) {
		Set<List<Character>> uncorruptedLines = getInput();
		Set<List<Character>> corruptedLines = uncorruptedLines.stream()
				.filter(line -> findFirstCorruptedCharacterIndex(line).isPresent()).collect(Collectors.toSet());
		uncorruptedLines.removeAll(corruptedLines);

		System.out.println("Syntax error score: " + solveA(corruptedLines));
		System.out.println("Autocomplete score: " + solveB(uncorruptedLines));
	}

	public static int solveA(Set<List<Character>> lines) {
		List<Character> corruptedCharacters = lines.stream().map(Day10Main::findFirstCorruptedCharacter)
				.filter(Optional::isPresent).map(Optional<Character>::get).collect(Collectors.toList());
		return calcSyntaxErrorScore(corruptedCharacters);
	}

	public static long solveB(Set<List<Character>> lines) {
		return lines.stream().map(Day10Main::findClosingBrackets).map(Day10Main::calcAutocompleteScore).sorted()
				.skip(lines.size() / 2).findFirst().get();
	}

	public static Optional<Character> findFirstCorruptedCharacter(List<Character> chunks) {
		Optional<Integer> characterIndex = findFirstCorruptedCharacterIndex(chunks);
		return (characterIndex.isPresent()) ? Optional.of(chunks.get(characterIndex.get())) : Optional.empty();
	}

	public static Optional<Integer> findFirstCorruptedCharacterIndex(List<Character> chunks) {
		Deque<Character> lastOpenBrackets = new ArrayDeque<>();
		Optional<Integer> firstCorruptedCharacterIndex = Optional.empty();
		for (int i = 0; i < chunks.size(); i++) {
			Character curBracket = chunks.get(i);
			if (isOpening(curBracket))
				lastOpenBrackets.push(curBracket);
			else {
				Character expectedClosingBracket = CLOSING_BRACKETS_MAP.get(lastOpenBrackets.pop());
				if (!expectedClosingBracket.equals(curBracket)) {
					firstCorruptedCharacterIndex = Optional.of(i);
					break;
				}
			}
		}

		return firstCorruptedCharacterIndex;
	}

	public static int calcSyntaxErrorScore(Collection<Character> closingBrackets) {
		Map<Character, Integer> characterScores = getSyntaxErrorScoresMap();
		return closingBrackets.stream().mapToInt(characterScores::get).sum();
	}

	public static List<Character> findClosingBrackets(List<Character> chunks) {
		return findUnclosedBrackets(chunks).stream().map(CLOSING_BRACKETS_MAP::get).collect(Collectors.toList());
	}

	public static Deque<Character> findUnclosedBrackets(List<Character> chunks) {
		Deque<Character> lastOpenBrackets = new ArrayDeque<>();
		for (Character curBracket : chunks) {
			if (isOpening(curBracket))
				lastOpenBrackets.push(curBracket);
			else
				lastOpenBrackets.pop();
		}

		return lastOpenBrackets;
	}

	public static long calcAutocompleteScore(List<Character> closingBrackets) {
		Map<Character, Integer> characterScores = getAutocompleteScoresMap();
		return closingBrackets.stream().mapToLong(characterScores::get).reduce(0L, (a, b) -> a * 5 + b);
	}

	public static boolean isOpening(Character chr) {
		return CLOSING_BRACKETS_MAP.containsKey(chr);
	}

	public static Map<Character, Character> getClosingBracketsMap() {
		Map<Character, Character> closingBracketsMap = new HashMap<>();
		closingBracketsMap.put('(', ')');
		closingBracketsMap.put('[', ']');
		closingBracketsMap.put('{', '}');
		closingBracketsMap.put('<', '>');
		return closingBracketsMap;
	}

	public static Map<Character, Integer> getSyntaxErrorScoresMap() {
		Map<Character, Integer> closingBracketsMap = new HashMap<>();
		closingBracketsMap.put(')', 3);
		closingBracketsMap.put(']', 57);
		closingBracketsMap.put('}', 1197);
		closingBracketsMap.put('>', 25137);
		return closingBracketsMap;
	}

	public static Map<Character, Integer> getAutocompleteScoresMap() {
		Map<Character, Integer> closingBracketsMap = new HashMap<>();
		closingBracketsMap.put(')', 1);
		closingBracketsMap.put(']', 2);
		closingBracketsMap.put('}', 3);
		closingBracketsMap.put('>', 4);
		return closingBracketsMap;
	}

	public static Set<List<Character>> getInput() {
		List<String> input = AdventOfCodeUtils.readInput(Day10Main.class);
		return input.stream().map(
				line -> IntStream.range(0, line.length()).mapToObj(i -> line.charAt(i)).collect(Collectors.toList()))
				.collect(Collectors.toSet());
	}
}
