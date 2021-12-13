package day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;
import utils.AdventOfCodeUtils;

public class Day16Main {
	public static final List<Integer> PATTERN = List.of(0, 1, 0, -1);

	public static void main(String[] args) throws InvalidArgumentException, InvalidIntCodeException {
		String solutionA = solveA(getInput());
		System.out.println("100 rounds of FFT on initial input: " + solutionA);

		String solutionB = solveB(getInput());
		System.out.println("100 rounds of FFT on extended input: " + solutionB);
	}

	public static String solveA(List<Integer> input) {
		List<Integer> nextDigits = input;
		for (int i = 0; i < 100; i++) {
			nextDigits = doRoundOfFFT(nextDigits, PATTERN);
		}
		return nextDigits.subList(0, 8).stream().map(Object::toString).reduce("", String::concat);
	}

	public static String solveB(List<Integer> input) {
		int messageOffset = Integer
				.parseInt(input.subList(0, 7).stream().map(Object::toString).reduce("", String::concat));
		int inputSize = input.size();
		List<Integer> relevantDigits = IntStream.range(messageOffset, inputSize * 10000)
				.mapToObj(i -> input.get(i % inputSize)).collect(Collectors.toList());

		List<Integer> nextDigits = relevantDigits;
		for (int i = 0; i < 100; i++) {
			System.out.println("Round " + (i+1));
			nextDigits = doRoundOfFFTSmart(nextDigits);
		}
		return nextDigits.subList(0, 8).stream().map(Object::toString).reduce("", String::concat);
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
	
	public static List<Integer> doRoundOfFFTSmart(List<Integer> digits) {
		List<Integer> nextDigits = new ArrayList<>();
		int suffixSum = sumList(digits);
		
		for (int digit : digits) {
			nextDigits.add(suffixSum % 10);
			suffixSum -= digit;
		}
		return nextDigits;
	}

	public static int calcMatchingIndexInPattern(int digitIndex, int depth, List<Integer> pattern) {
		return ((digitIndex + 1) / depth) % pattern.size();
	}
	
	public static int sumList(List<Integer> digits) {
		return digits.stream().mapToInt(i -> i).sum();
	}

	public static List<Integer> getInput() {
		List<String> input = AdventOfCodeUtils.readInputLines(Day16Main.class);
		return Arrays.stream(input.get(0).split("")).map(Integer::parseInt).collect(Collectors.toList());
	}
}
