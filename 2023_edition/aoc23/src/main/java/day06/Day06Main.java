package day06;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day06Main {
	public static final Pattern NUMBERS_PATTERN = Pattern.compile("([0-9]+ [[0-9]+ ]+)");

	public static void main(String[] args) {
		List<Race> races = parseRaces();

		System.out.println("Margin of error: " + solveA(races));

		System.out.println("Winning times in big race: " + solveB(races));
	}

	public static long solveA(List<Race> races) {
		return races.stream().map(Race::countWinTimes).reduce((a, b) -> a * b).get();
	}

	public static long solveB(List<Race> races) {
		Race realRace = races.stream().reduce(Race::combineWith).get();
		return realRace.countWinTimes();
	}

	record Race(long time, long record) {
		public long countWinTimes() {
			return IntStream.range(1, (int) time).mapToObj(this::calcDistance).filter(this::isWinningDistance).count();
		}

		public boolean isWinningDistance(long dist) {
			return dist > record;
		}

		public long calcDistance(long waitTime) {
			if (waitTime >= this.time)
				return 0;

			return waitTime * (this.time - waitTime);
		}

		public Race combineWith(Race other) {
			long newTime = Long.parseLong("" + this.time + other.time);
			long newRecord = Long.parseLong("" + this.record + other.record);
			return new Race(newTime, newRecord);
		}
	}

	public static List<Race> parseRaces() {
		List<String> input = AdventOfCodeUtils.readInput(Day06Main.class);
		List<Long> times = parseNumbers(input.get(0));
		List<Long> distances = parseNumbers(input.get(1));
		return IntStream.range(0, times.size()).mapToObj(i -> new Race(times.get(i), distances.get(i))).toList();
	}

	public static List<Long> parseNumbers(String str) {
		Matcher matcher = NUMBERS_PATTERN.matcher(str);
		matcher.find();
		return Arrays.stream(matcher.group(1).split(" +")).map(Long::parseLong).toList();
	}
}
