package de.cas.adventofcode.day03;

import java.util.List;

import de.cas.adventofcode.shared.Day;

public class Day03 extends Day<Long> {

	protected Day03() {
		super(3);
	}

	public static void main(final String[] args) {
		new Day03().run();
	}

	@Override
	public Long solvePart1(List<String> input) {
		boolean[][] booleanMap = parseInput(input);
		
		return (long) simulateSlope(booleanMap, 1, 3);
	}

	@Override
	public Long solvePart2(List<String> input) {
		boolean[][] booleanMap = parseInput(input);
		long result = 1;
		
		result *= simulateSlope(booleanMap, 1, 1);
		result *= simulateSlope(booleanMap, 1, 3);
		result *= simulateSlope(booleanMap, 1, 5);
		result *= simulateSlope(booleanMap, 1, 7);
		result *= simulateSlope(booleanMap, 2, 1);
		
		return result;
	}

	private int simulateSlope(boolean[][] booleanMap, int lineIncrement, int charIncrement) {
		int countTrees = 0;
		int lineIndex = 0;
		int charIndex = 0;
		int lineCount = booleanMap.length;
		int charCount = booleanMap[0].length;
		
		while (lineIndex < lineCount) {
			if (booleanMap[lineIndex][charIndex])
				countTrees++;
			
			lineIndex+=lineIncrement;
			charIndex = calcNextCharIndex(charIncrement, charIndex, charCount);
		}
		
		return countTrees;
	}

	private int calcNextCharIndex(int curCharIndex, int charIncrement, int charCount) {
		return (curCharIndex + charIncrement) % charCount;
	}

	private boolean[][] parseInput(List<String> input) {
		boolean[][] booleanMap = new boolean[input.size()][input.get(0).length()];
		
		for (int lineIndex=0; lineIndex<input.size(); lineIndex++) {
			String line = input.get(lineIndex);
			
			for (int charIndex=0; charIndex<line.length(); charIndex++) {
				booleanMap[lineIndex][charIndex] = (line.charAt(charIndex) == '#');
			}
		}
		return booleanMap;
	}
}
