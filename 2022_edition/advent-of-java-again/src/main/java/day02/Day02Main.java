package day02;

import java.util.List;

import utils.AdventOfCodeUtils;

public class Day02Main {
	enum OUTCOME {
		LOSE, DRAW, WIN
	}

	public static final char ROCK = 'A';
	public static final char PAPER = 'B';
	public static final char SCISSORS = 'C';

	public static final char ROCK2 = 'X';
	public static final char PAPER2 = 'Y';
	public static final char SCISSORS2 = 'Z';

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day02Main.class);

		System.out.println("Potential score A: " + solveA(input));

		System.out.println("Potential score B: " + solveB(input));
	}

	public static int solveA(List<String> input) {
		return input.stream().mapToInt(Day02Main::calcLineScoreA).sum();
	}

	public static int solveB(List<String> input) {
		return input.stream().mapToInt(Day02Main::calcLineScoreB).sum();
	}

	public static int calcLineScoreA(String line) {
		char oponent = line.charAt(0);
		char me = normalizeShape(line.charAt(2));
		int shapeScore = calcShapePoints(me);

		int gameScore = playRPS(me, oponent);
		return gameScore + shapeScore;
	}

	public static int playRPS(char player1, char player2) {
		if (player2 == getLoserTo(player1))
			return calcGamePoints(OUTCOME.WIN);
		else if (player2 == getWinnerTo(player1))
			return calcGamePoints(OUTCOME.LOSE);
		else
			return calcGamePoints(OUTCOME.DRAW);
	}

	public static int calcLineScoreB(String line) {
		char oponent = line.charAt(0);
		OUTCOME outcome = OUTCOME.values()[line.charAt(2) - 'X'];

		char shape = switch (outcome) {
		case LOSE -> getLoserTo(oponent);
		case DRAW -> oponent;
		case WIN -> getWinnerTo(oponent);
		};

		return calcGamePoints(outcome) + calcShapePoints(shape);
	}

	public static char getLoserTo(char winner) {
		return switch (winner) {
		case ROCK -> SCISSORS;
		case SCISSORS -> PAPER;
		case PAPER -> ROCK;
		default -> throw new RuntimeException("Unknown shape: " + winner);
		};
	}

	public static char getWinnerTo(char loser) {
		return switch (loser) {
		case ROCK -> PAPER;
		case PAPER -> SCISSORS;
		case SCISSORS -> ROCK;
		default -> throw new RuntimeException("Unknown shape: " + loser);
		};
	}

	// Convert X,Y,Z to A,B,C
	public static char normalizeShape(char shape) {
		return (char) (shape - 23);
	}

	public static int calcGamePoints(OUTCOME outcome) {
		return outcome.ordinal() * 3;
	}

	public static int calcShapePoints(char shape) {
		return switch (shape) {
		case ROCK, PAPER, SCISSORS -> shape - 64;
		default -> throw new RuntimeException("Unknown shape: " + shape);
		};
	}
}
