package de.cas.adventofcode.day09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.cas.adventofcode.shared.Day;

public class Day09 extends Day<Long> {
	private static final int PREAMBLE = 25;

	protected Day09() {
		super(9);
	}

	public static void main(final String[] args) {
		new Day09().run();
	}

	@Override
	public Long solvePart1(List<String> input) {
		List<Long> parsedInput = input.stream().map(Long::parseLong).collect(Collectors.toList());
		
		return findInvalidNumber(parsedInput, PREAMBLE);
	}

	private Long findInvalidNumber(List<Long> numbers, int preamble) {
		Long curNumber = -1L;
		
		for (int i=preamble; i<numbers.size(); i++) {
			Set<Long> prevNumbers = new HashSet<>(numbers.subList(i-preamble, i+1));
			curNumber = numbers.get(i);
			
			if (!isSumOf(curNumber, prevNumbers))
				break;
		}
		
		return curNumber;
	}

	private boolean isSumOf(Long sumToFind, Set<Long> numbers) {
		for (Long curNumber : numbers)
			if (numbers.contains(sumToFind-curNumber))
				return true;

		return false;
	}

	@Override
	public Long solvePart2(List<String> input) {
		List<Long> parsedInput = input.stream().map(Long::parseLong).collect(Collectors.toList());
		long invalidNum = solvePart1(input);
		
		List<Long> numsToSum = findNumsToSum(invalidNum, parsedInput);
		
		Collections.sort(numsToSum);
		return numsToSum.get(0) + numsToSum.get(numsToSum.size()-1);
			
	}

	private List<Long> findNumsToSum(long sumToFind, List<Long> numsToSearch) {
		List<Long> numsToSum = new ArrayList<>();
		
		for (int i=0; i<numsToSearch.size(); i++) {
			long curSum = 0L;
			numsToSum = new ArrayList<>();
			
			while (curSum < sumToFind) {
				long nextNum = numsToSearch.get(i+numsToSum.size());
				curSum += nextNum;
				numsToSum.add(nextNum);
			}
			
			if (curSum == sumToFind)
				break;
		}
		
		return numsToSum;
	}

}
