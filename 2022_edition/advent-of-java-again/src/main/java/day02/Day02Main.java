package day02;

import java.util.List;

import utils.AdventOfCodeUtils;
import utils.IntegerUtils;

public class Day02Main {
	public static final int POINTS_LOSE = 0;
	public static final int POINTS_DRAW = 3;
	public static final int POINTS_WIN = 6;

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day02Main.class);

		System.out.println("Potential score A: " + solveA(input));
	}

	public static int solveA(List<String> input) {
		return input.stream().mapToInt(Day02Main::calcLineScore).sum();
	}

	public static void solveB() {
		
	}

	public static int calcLineScore(String line) {
		int oponent = line.charAt(0);
		int me = line.charAt(2) - 23;
		int shapeScore = me - 64;

		int gameScore = playRPS(me, oponent);
		return gameScore + shapeScore;
	}

	public static int playRPS(int player1, int player2) {
		int diff = player1 - player2;
		int dist = IntegerUtils.calcDistance(player1, player2);
		if (diff < 0) {
			if (dist == 2)
				return POINTS_WIN;
			else
				return POINTS_LOSE;
		} else if (diff > 0) {
			if (dist == 2)
				return POINTS_LOSE;
			else
				return POINTS_WIN;
		} else {
			return POINTS_DRAW;
		}
	}
}
