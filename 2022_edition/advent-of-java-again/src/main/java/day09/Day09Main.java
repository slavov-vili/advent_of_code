package day09;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.function.UnaryOperator;

import utils.AdventOfCodeUtils;
import utils.Grid2D;
import utils.PointUtils;

public class Day09Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day09Main.class);

		System.out.println("Number of positions for tail: " + solveA(input));
	}

	public static int solveA(List<String> motions) {
		Point headPos = new Point(0, 0);
		Point tailPos = new Point(0, 0);
		var visitedPositions = new HashSet<Point>();
		visitedPositions.add(tailPos);

		for (String motion : motions) {
			var parts = motion.split(" ");
			var moveDirection = getMoveDirection(parts[0]);

			for (int step = 0; step < Integer.parseInt(parts[1]); step++) {
				var nextHeadPos = moveDirection.apply(headPos);

				if (PointUtils.calcChebyshevDistance(tailPos, nextHeadPos) > 1) {
					tailPos = headPos;
					visitedPositions.add(tailPos);
				}
				headPos = nextHeadPos;
			}
		}

		return visitedPositions.size();
	}

	public static void solveB() {
	}

	public static UnaryOperator<Point> getMoveDirection(String shortName) {
		return switch (shortName) {
		case "U" -> Grid2D.UP;
		case "R" -> Grid2D.RIGHT;
		case "D" -> Grid2D.DOWN;
		case "L" -> Grid2D.LEFT;
		default -> null;
		};
	}
}
