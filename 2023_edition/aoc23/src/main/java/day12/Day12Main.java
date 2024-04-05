package day12;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day12Main {

	public static final char OPERATIONAL = '.';
	public static final char DAMAGED = '#';
	public static final char UNKNOWN = '?';
	public static final Set<Character> GROUP_STARTS = Set.of(DAMAGED, UNKNOWN);

	public static void main(String[] args) {
		var springRows = parseSprings();

		System.out.println("Total arrangements: " + solveA(springRows));
	}

	public static long solveA(List<SpringRow> springRows) {
		return springRows.stream().mapToLong(SpringRow::countArrangements).sum();
	}

	public static void solveB() {
	}

	record SpringRow(String line, List<Integer> sizes) {

		public long countArrangements() {
			return countArrangements(this.line, 0);
		}

		public long countArrangements(String curLine, int curSizeIndex) {
			int groupStart = findNextGroupStart(curLine);
			if (groupStart == -1)
				return curSizeIndex == this.sizes.size() ? 1 : 0;

			long arrangements = 0;
			if (isDamaged(groupStart)) {
				// check if group of size exists and next is empty
				// if yes - recurse with new line from empty
				if (curSizeIndex != -1) {
					
				}
			} else {
				String nextLine = createNextLine(DAMAGED, 1, curLine);
				arrangements += countArrangements(nextLine, curSizeIndex);

				nextLine = createNextLine(OPERATIONAL, 1, curLine);
				arrangements += countArrangements(nextLine, curSizeIndex);
			}
			return arrangements;
		}

		public String createNextLine(char nextChar, int repeat, String prevLine) {
			String charRepeat = IntStream.range(0, repeat).mapToObj(i -> Character.toString(nextChar))
					.collect(Collectors.joining());
			return charRepeat + prevLine.substring(repeat);
		}

		public boolean isDamaged(int i) {
			return DAMAGED == line.charAt(i);
		}

		public static int findNextGroupStart(String line) {
			return GROUP_STARTS.stream().mapToInt(line::indexOf).min().getAsInt();
		}

		public static SpringRow fromInput(String input) {
			var parts = input.split(" ");
			String line = parts[1];
			List<Integer> sizes = Arrays.stream(parts[1].split(",")).map(Integer::parseInt).toList();
			return new SpringRow(line, sizes);
		}
	}

	public static List<SpringRow> parseSprings() {
		List<String> input = AdventOfCodeUtils.readInput(Day12Main.class);
		return input.stream().map(SpringRow::fromInput).toList();
	}
}
