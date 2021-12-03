package day03;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day03Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day03Main.class);

		System.out.println("Power consumption: " + solveA(input));

		System.out.println("Life support rating: " + solveB(input));

	}

	public static long solveA(List<String> input) {
		long gammaRate = determineRate(input, true);
		long epsilonRate = determineRate(input, false);

		return gammaRate * epsilonRate;
	}

	public static long solveB(List<String> input) {
		long oxygenGeneratorRating = determineRating(input, 0, true);
		long co2ScrubberRating = determineRating(input, 0, false);
		return oxygenGeneratorRating * co2ScrubberRating;
	}

	public static long determineRate(List<String> input, boolean calculatingGammaRate) {
		String rate = "";
		for (int i = 0; i < input.get(0).length(); i++) {
			char mostCommonBit = findMostCommonBitAt(input, i);
			rate += determineBitToUse(mostCommonBit, calculatingGammaRate);
		}

		return parseUnsigned(rate);
	}

	public static long determineRating(List<String> numbersToConsider, int curPosition,
			boolean calculatingOxygenGeneratorRating) {
		if (numbersToConsider.size() == 1)
			return parseUnsigned(numbersToConsider.get(0));
		else {
			char mostCommonBit = findMostCommonBitAt(numbersToConsider, curPosition);
			char bitToUse = determineBitToUse(mostCommonBit, calculatingOxygenGeneratorRating);

			List<String> nextNumbers = numbersToConsider.stream()
					.filter(number -> number.charAt(curPosition) == bitToUse).collect(Collectors.toList());
			return determineRating(nextNumbers, curPosition + 1, calculatingOxygenGeneratorRating);
		}
	}

	public static char determineBitToUse(char mostCommonBit, boolean lookingForMCB) {
		return (lookingForMCB) ? mostCommonBit : negateBit(mostCommonBit);
	}

	// Returns 1 if there are equally many 0s and 1s
	public static char findMostCommonBitAt(List<String> numbers, int position) {
		char mostCommonBit = '1';
		long zeros = numbers.stream().filter(number -> number.charAt(position) == '0').count();
		if (zeros > numbers.size() / 2)
			mostCommonBit = '0';
		return mostCommonBit;
	}

	public static long parseUnsigned(String value) {
		return Long.parseLong("0" + value, 2);
	}

	public static char negateBit(char bit) {
		return (bit == '0') ? '1' : '0';
	}
}
