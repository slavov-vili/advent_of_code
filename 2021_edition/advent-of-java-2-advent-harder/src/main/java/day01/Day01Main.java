package day01;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day01Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day01Main.class);
		List<Long> measurements = input.stream().map(Long::parseLong).collect(Collectors.toList());

		System.out.println(solveA(measurements, AdventOfCodeUtils.WANT_ONELINER));

		System.out.println(solveB(measurements, AdventOfCodeUtils.WANT_ONELINER));
	}

	private static long solveA(List<Long> measurements, boolean wantOneliner) {
		if (!wantOneliner) {
			int countIncreases = 0;
			for (int i = 1; i < measurements.size(); i++)
				if (measurements.get(i) > measurements.get(i - 1))
					countIncreases++;
			return countIncreases;
		} else {
			return IntStream.range(0, measurements.size() - 1)
					.filter(i -> measurements.get(i) < measurements.get(i + 1)).count();
		}
	}

	private static long solveB(List<Long> measurements, boolean wantOneliner) {
		if (!wantOneliner) {
			int countSumIncreases = 0;
			for (int i = 1; i < measurements.size() - 2; i++) {
				long prevSum = measurements.get(i - 1) + measurements.get(i) + measurements.get(i + 1);
				long curSum = measurements.get(i) + measurements.get(i + 1) + measurements.get(i + 2);
				if (curSum > prevSum)
					countSumIncreases++;
			}
			return countSumIncreases;
		} else {
			return solveA(
					IntStream.range(0, measurements.size() - 2).mapToObj(i -> measurements.subList(i, i + 3))
							.map(subList -> subList.stream().reduce(0L, Long::sum)).collect(Collectors.toList()),
					wantOneliner);
		}
	}
}
