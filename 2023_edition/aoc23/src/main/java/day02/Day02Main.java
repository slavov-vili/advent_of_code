package day02;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.AdventOfCodeUtils;

public class Day02Main {
	public static Pattern GAME_PATTERN = Pattern.compile("Game ([0-9]+): (.*)");
	public static Pattern DICE_PATTERN = Pattern.compile("([0-9]+) ([a-z]+)");

	public static void main(String[] args) {
		List<Game> games = parseGames();

		System.out.println("Sum of IDs of possible games: " + solveA(games));

		System.out.println("Sum of min set powers: " + solveB(games));
	}

	public static int solveA(List<Game> games) {
		return games.stream().filter(Game::isPossible).mapToInt(Game::id).sum();
	}

	public static int solveB(List<Game> games) {
		return games.stream().mapToInt(Game::calcPowerOfMinSet).sum();
	}

	record Game(int id, List<Map<String, Integer>> draws) {
		public static Game fromString(String str) {
			Matcher matcher = GAME_PATTERN.matcher(str);
			matcher.find();
			return new Game(Integer.parseInt(matcher.group(1)), parseDraws(matcher.group(2)));
		}

		public boolean isPossible() {
			return this.draws.stream().allMatch(Day02Main::drawIsPossible);
		}

		public int calcPowerOfMinSet() {
			return this.findMinSet().values().stream().reduce((a, b) -> a * b).get();
		}

		public Map<String, Integer> findMinSet() {
			Map<String, Integer> minSet = new HashMap<>();
			for (Map<String, Integer> draw : this.draws)
				for (String die : draw.keySet()) {
					int oldCount = minSet.getOrDefault(die, 0);
					int newCount = draw.getOrDefault(die, 0);
					if (newCount > oldCount)
						minSet.put(die, newCount);
				}
			return minSet;
		}
	}

	public static boolean drawIsPossible(Map<String, Integer> draw) {
		Map<String, Integer> rules = getPossibilityRulesA();
		return draw.keySet().stream().allMatch(die -> draw.get(die) <= rules.getOrDefault(die, Integer.MAX_VALUE));
	}

	public static Map<String, Integer> getPossibilityRulesA() {
		Map<String, Integer> rules = new HashMap<>();
		rules.put("red", 12);
		rules.put("green", 13);
		rules.put("blue", 14);
		return rules;
	}

	public static List<Game> parseGames() {
		return AdventOfCodeUtils.readInput(Day02Main.class).stream().map(Game::fromString).toList();
	}

	public static List<Map<String, Integer>> parseDraws(String drawsStr) {
		return Arrays.stream(drawsStr.split("; ")).map(Day02Main::parseDraw).toList();
	}

	public static Map<String, Integer> parseDraw(String drawStr) {
		Map<String, Integer> draw = new HashMap<>();
		Matcher matcher = DICE_PATTERN.matcher(drawStr);
		while (matcher.find()) {
			draw.put(matcher.group(2), Integer.parseInt(matcher.group(1)));
		}
		return draw;
	}
}
