package day21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import day21.GameState.PlayerState;
import utils.AdventOfCodeUtils;
import utils.ListUtils;

public class Day21Main {
	public static Pattern PLAYER_POSITION = Pattern.compile("Player (\\d) starting position: (\\d)");

	public static Map<Integer, Integer> SUM_TO_COUNT = DiracDie.roll();

	public static void main(String[] args) {
		System.out.println("Practice score: " + solveA());
		System.out.println("Real game wins: " + solveB());
	}

	public static long solveA() {
		List<Integer> positions = parseStartingPositions();
		List<Integer> scores = ListUtils.generateAndInitialize(positions.size(), 0);
		int curPlayerIndex = 0;
		DeterministicDie die = new DeterministicDie();
		while (scores.stream().allMatch(score -> score < 1000)) {
			int nextPosition = calcNewPosition(positions.get(curPlayerIndex), die.roll());
			int newScore = scores.get(curPlayerIndex) + nextPosition;

			positions.set(curPlayerIndex, nextPosition);
			scores.set(curPlayerIndex, newScore);

			curPlayerIndex = (curPlayerIndex + 1) % 2;
		}

		long losingScore = (long) scores.stream().mapToInt(score -> score).min().getAsInt();
		return losingScore * die.getRollCount();
	}

	public static long solveB() {
		List<Integer> positions = parseStartingPositions();
		GameState initialState = new GameState(new PlayerState(positions.get(0), 0),
				new PlayerState(positions.get(1), 0));
		Pair<Long, Long> winLoseCount = playGame(initialState, new HashMap<GameState, Pair<Long, Long>>());

		return Long.max(winLoseCount.getValue1(), winLoseCount.getValue2());
	}

	public static Pair<Long, Long> playGame(GameState gameState, Map<GameState, Pair<Long, Long>> stateToWinLoseCount) {
		if (stateToWinLoseCount.containsKey(gameState))
			return stateToWinLoseCount.get(gameState);

		long winCount = 0;
		long loseCount = 0;

		for (int roll : SUM_TO_COUNT.keySet()) {
			int rollFrequency = SUM_TO_COUNT.get(roll);
			PlayerState statePlayer1 = gameState.getStatePlayer1();
			int newPosition = calcNewPosition(statePlayer1.getPosition(), roll);
			int newScore = statePlayer1.getScore() + newPosition;

			if (newScore >= 21)
				winCount += rollFrequency;
			else {
				GameState newGameState = new GameState(gameState.getStatePlayer2(),
						statePlayer1.clone().setPosition(newPosition).setScore(newScore));
				Pair<Long, Long> loseWinCount = playGame(newGameState, stateToWinLoseCount);
				stateToWinLoseCount.put(newGameState, loseWinCount);

				winCount += loseWinCount.getValue2() * rollFrequency;
				loseCount += loseWinCount.getValue1() * rollFrequency;
			}
		}

		stateToWinLoseCount.put(gameState, new Pair<Long, Long>(winCount, loseCount));

		return new Pair<Long, Long>(winCount, loseCount);
	}

	public static int calcNewPosition(int oldPosition, int dieRoll) {
		int newPosition = oldPosition + dieRoll;
		if (newPosition % 10 == 0)
			newPosition = 10;
		else if (newPosition > 10)
			newPosition = newPosition % 10;
		return newPosition;
	}

	public static int calcDiracState(PlayerState statePlayer1, PlayerState statePlayer2) {
		// In the dirac version the scores only go up to 21, so 2 digits is enough
		return (statePlayer1.getScore() * 100) + statePlayer2.getScore();
	}

	public static List<Integer> parseStartingPositions() {
		return AdventOfCodeUtils.readInput(Day21Main.class).stream()
				.map(line -> line.replaceAll("Player (\\d) starting position: ", "")).map(Integer::parseInt)
				.collect(Collectors.toList());
	}
}
