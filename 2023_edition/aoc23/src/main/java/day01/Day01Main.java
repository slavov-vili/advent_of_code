package day01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day01Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day01Main.class);

		System.out.println("Calibration value sum: " + solveA(input));

		System.out.println("Real Calibration value sum: " + solveB(input));
	}

	public static int solveA(List<String> input) {
		return input.stream().mapToInt(Day01Main::calcValueA).sum();
	}

	public static int calcValueA(String line) {
		int sum = 10 * findDigitA(line, 0);
		sum += findDigitA(line, line.length() - 1);
		return sum;
	}

	public static int findDigitA(String line, int index) {
		int increment = (index == 0) ? 1 : -1;
		int nextIndex = index;
		char curChar = line.charAt(nextIndex);
		while (!Character.isDigit(curChar)) {
			nextIndex += increment;
			curChar = line.charAt(nextIndex);
		}
		return Integer.parseInt("" + curChar);
	}

	public static int solveB(List<String> input) {
		return input.stream().mapToInt(Day01Main::calcValueB).sum();
	}

	public static int calcValueB(String line) {
		int sum = 10 * findDigitB(line, 0);
		sum += findDigitB(line, line.length() - 1);
		return sum;
	}

	public static int findDigitB(String line, int index) {
		BiPredicate<String, String> affixChecker = (index == 0) ? affixChecker = String::startsWith : String::endsWith;

		String curLine = line;
		var digitMap = getDigitMap();
		Optional<String> digit;
		do {
			// Streams don't like changing variables even though they change afterwards...
			String curLineCopy = new String(curLine);
			digit = digitMap.keySet().stream().filter(d -> affixChecker.test(new String(curLineCopy), d)).findFirst();
			curLine = (index == 0) ? curLine.substring(index + 1, curLine.length())
					: curLine.substring(0, curLine.length() - 1);
		} while (digit.isEmpty());
		return digitMap.get(digit.get());
	}

	public static Map<String, Integer> getDigitMap() {
		var digitMap = new HashMap<String, Integer>();
		IntStream.rangeClosed(0, 9).forEach(d -> digitMap.put("" + d, d));
		digitMap.put("zero", 0);
		digitMap.put("one", 1);
		digitMap.put("two", 2);
		digitMap.put("three", 3);
		digitMap.put("four", 4);
		digitMap.put("five", 5);
		digitMap.put("six", 6);
		digitMap.put("seven", 7);
		digitMap.put("eight", 8);
		digitMap.put("nine", 9);
		return digitMap;
	}
}
