package day21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day21Main {
	public static Pattern PLAYER_POSITION = Pattern.compile("Player (\\d) starting position: (\\d)");
	public static Map<Integer, Integer> OUTCOMES = getOutcomes();

	public static void main(String[] args) {
		System.out.println("Practice score: " + solveA());
		System.out.println("Real game wins: " + solveB());
	}

	public static long solveA() {
		List<Integer> positions = parseStartingPositions();
		List<Integer> scores = generateScores(positions.size());
		int curPlayerIndex = 0;
		int curDie = 0;
		int turnCount = 0;
		while (scores.stream().allMatch(score -> score < 1000)) {
			int dieRoll = 0;
			for (int i = 0; i < 3; i++) {
				curDie = curDie % 100 + 1;
				dieRoll += curDie;
			}

			int nextPosition = positions.get(curPlayerIndex) + dieRoll;
			if (nextPosition % 10 == 0)
				nextPosition = 10;
			else if (nextPosition > 10)
				nextPosition = nextPosition % 10;
			int newScore = scores.get(curPlayerIndex) + nextPosition;

			positions.set(curPlayerIndex, nextPosition);
			scores.set(curPlayerIndex, newScore);

			curPlayerIndex = (curPlayerIndex + 1) % 2;
			turnCount++;
		}

		long losingScore = (long) scores.stream().mapToInt(score -> score).min().getAsInt();
		return losingScore * turnCount * 3;
	}

	public static long solveB() {
		List<Integer> positions = parseStartingPositions();
		List<Long> winCounts = playGame(positions, generateScores(positions.size()), 0);
		return winCounts.stream().mapToLong(count -> count).max().getAsLong();
	}

	// TODO: figure out how to optimize, only run from unknown distances?
	public static List<Long> playGame(List<Integer> positions, List<Integer> scores, int curPlayerIndex) {
		List<Long> winCounts = IntStream.range(0, scores.size()).mapToObj(i -> 0L).collect(Collectors.toList());
		int prevPlayerIndex = (curPlayerIndex + 1) % 2;
		if (scores.get(prevPlayerIndex) >= 21) {
			winCounts.set(prevPlayerIndex, 1L);
			return winCounts;
		}

		if (scores.get(curPlayerIndex) == 20) {
			winCounts.set(curPlayerIndex, 7L);
			return winCounts;
		}

		for (int outcome : OUTCOMES.keySet()) {
			int nextPosition = positions.get(curPlayerIndex) + outcome;
			if (nextPosition % 10 == 0)
				nextPosition = 10;
			else if (nextPosition > 10)
				nextPosition = nextPosition % 10;
			int newScore = scores.get(curPlayerIndex) + nextPosition;
			System.out.printf("Player %d, rolled a %d, moving from %d to %d, Score change: %d -> %d\n", curPlayerIndex,
					outcome, positions.get(curPlayerIndex), nextPosition, scores.get(curPlayerIndex), newScore);

			List<Integer> newPositions = new ArrayList<>(positions);
			List<Integer> newScores = new ArrayList<>(scores);
			newPositions.set(curPlayerIndex, nextPosition);
			newScores.set(curPlayerIndex, newScore);

			List<Long> newWinCounts = playGame(newPositions, newScores, (curPlayerIndex + 1) % 2);
			for (int i = 0; i < winCounts.size(); i++) {
				winCounts.set(i, winCounts.get(i) + newWinCounts.get(i) * OUTCOMES.get(outcome));
			}
		}
//		System.out.println("Win counts: " + winCounts);
		return winCounts;
	}

	public static Map<Integer, Integer> getOutcomes() {
		Map<Integer, Integer> outcomes = new HashMap<>();
		for (int i = 1; i <= 3; i++)
			for (int j = 1; j <= 3; j++)
				for (int k = 1; k <= 3; k++) {
					int curSum = i + j + k;
					int curCount = outcomes.getOrDefault(curSum, 0);
					outcomes.put(curSum, curCount + 1);
				}
		return outcomes;
	}

	public static List<Integer> parseStartingPositions() {
		return AdventOfCodeUtils.readInput(Day21Main.class).stream()
				.map(line -> line.replaceAll("Player (\\d) starting position: ", "")).map(Integer::parseInt)
				.collect(Collectors.toList());
	}

	public static List<Integer> generateScores(int playerCount) {
		return IntStream.range(0, playerCount).mapToObj(i -> 0).collect(Collectors.toList());
	}
}
