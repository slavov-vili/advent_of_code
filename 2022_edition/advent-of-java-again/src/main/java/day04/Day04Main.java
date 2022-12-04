package day04;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.IntegerUtils;

public class Day04Main {
	public static final Pattern rangePairPattern = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day04Main.class);
		List<RangePairs> rangePairs = input.stream().map(RangePairs::fromLine).collect(Collectors.toList());

		System.out.println("Number of Range Pairs where one contains the other: " + solveA(rangePairs));
		System.out.println("Number of Range Pairs with overlaps: " + solveB(rangePairs));
	}

	public static long solveA(List<RangePairs> rangePairs) {
		return rangePairs.stream().filter(pair -> pair.oneContainsOther() != 0).count();
	}

	public static long solveB(List<RangePairs> rangePairs) {
		return rangePairs.stream().filter(RangePairs::haveOverlaps).count();
	}

	public record RangePairs(int firstStart, int firstEnd, int secondStart, int secondEnd) {
		public int oneContainsOther() {
			if (firstContainsSecond())
				return 1;
			else if (secondContainsFirst())
				return -1;
			else
				return 0;
		}

		public boolean firstContainsSecond() {
			return firstStart <= secondStart && firstEnd >= secondEnd;
		}

		public boolean secondContainsFirst() {
			return secondStart <= firstStart && secondEnd >= firstEnd;
		}

		public boolean haveOverlaps() {
			return firstOverlapsInSecond() || secondOverlapsInFirst();
		}

		public boolean firstOverlapsInSecond() {
			return IntegerUtils.integerIsWithinRange(firstStart, secondStart, secondEnd)
					|| IntegerUtils.integerIsWithinRange(firstEnd, secondStart, secondEnd);
		}

		public boolean secondOverlapsInFirst() {
			return IntegerUtils.integerIsWithinRange(secondStart, firstStart, firstEnd)
					|| IntegerUtils.integerIsWithinRange(secondEnd, firstStart, firstEnd);
		}

		public static RangePairs fromLine(String line) {
			Matcher matcher = rangePairPattern.matcher(line);
			matcher.matches();
			return new RangePairs(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
		}
	};
}
