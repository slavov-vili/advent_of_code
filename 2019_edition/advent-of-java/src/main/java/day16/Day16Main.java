package day16;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;
import utils.AdventOfCodeUtils;

public class Day16Main {
	public static final List<Integer> BASE_PATTERN = List.of(0, 1, 0, -1);

	public static void main(String[] args) throws InvalidArgumentException, InvalidIntCodeException {
		String solutionA = solveA(getInput());
		System.out.println("After 100 FFT Rounds: " + solutionA);
		
		String solutionB = solveB(getInput()).stream().map(Object::toString).reduce("", String::concat);
		System.out.println("Input*10K After 100 FFT Rounds: " + solutionB);
	}

	public static String solveA(List<Integer> input) {
		List<Integer> nextInput = input;
		for (int i = 0; i < 100; i++) {
			nextInput = doRoundOfFFT(nextInput, BASE_PATTERN);
		}
		return nextInput.stream().map(Object::toString).reduce("", String::concat).substring(0, 8);
	}
	
	public static List<Integer> solveB(List<Integer> input) {
		int messageOffset = Integer.parseInt(input.subList(0, 7).stream().map(Object::toString).reduce("", String::concat));
		
	}

	public static List<Integer> doRoundOfFFT(List<Integer> digits, List<Integer> pattern) {
		return IntStream.range(1, digits.size() + 1).mapToObj(depth -> calcFFTNumber(digits, depth, pattern))
				.collect(Collectors.toList());
	}

	public static int calcFFTNumber(List<Integer> digits, int depth, List<Integer> pattern) {
		int sum = IntStream.range(0, digits.size())
				.map(i -> digits.get(i) * pattern.get(calcMatchingIndexInPattern(i, depth, pattern))).sum();
		return Math.abs(sum) % 10;
	}

	public static int calcMatchingIndexInPattern(int digitIndex, int depth, List<Integer> pattern) {
		return ((digitIndex + 1) / depth) % pattern.size();
	}

	public static List<Integer> getInput() {
		List<String> input = AdventOfCodeUtils.readInputLines(Day16Main.class);
		return Arrays.stream(input.get(0).split("")).map(Integer::parseInt).collect(Collectors.toList());
	}
}
