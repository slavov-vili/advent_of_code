package day09;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;
import utils.Grid2D;
import utils.PointUtils;

public class Day09Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day09Main.class);

		System.out.println("Number of tail positions for short rope: " + solveA(input));

		System.out.println("Number of tail positions for long rope: " + solveB(input));
	}

	public static int solveA(List<String> motions) {
		return simulateRope(motions, getRope(2));
	}

	public static int solveB(List<String> motions) {
		return simulateRope(motions, getRope(10));
	}

	public static int simulateRope(List<String> motions, List<Point> initialRope) {
		var curRope = new ArrayList<>(initialRope);
		var visitedPositions = new HashSet<Point>();
		visitedPositions.add(getTailPosition(initialRope));

		for (String motion : motions) {
			var parts = motion.split(" ");
			var stepCount = Integer.parseInt(parts[1]);

			for (int step = 0; step < stepCount; step++) {
				updateHeadPosition(curRope, getHeadMoveDirection(parts[0]));

				for (int i = 1; i < curRope.size(); i++) {
					var curKnotPosition = curRope.get(i);
					var nextKnotPosition = curRope.get(i - 1);

					if (PointUtils.calcChebyshevDistance(curKnotPosition, nextKnotPosition) > 1) {
						updateKnotPosition(curRope, i, PointUtils.getDirection(curKnotPosition, nextKnotPosition));
					}
				}

				visitedPositions.add(getTailPosition(curRope));
			}
		}

		return visitedPositions.size();
	}

	public static Point updateHeadPosition(List<Point> rope, UnaryOperator<Point> direction) {
		return updateKnotPosition(rope, 0, direction);
	}

	public static Point updateKnotPosition(List<Point> rope, int knotIndex, UnaryOperator<Point> direction) {
		return rope.set(knotIndex, direction.apply(rope.get(knotIndex)));
	}

	public static Point getTailPosition(List<Point> rope) {
		return rope.get(rope.size() - 1);
	}

	public static List<Point> getRope(int size) {
		return IntStream.range(0, size).mapToObj(i -> new Point(0, 0)).collect(Collectors.toList());
	}

	public static UnaryOperator<Point> getHeadMoveDirection(String shortName) {
		return switch (shortName) {
		case "U" -> Grid2D.UP;
		case "R" -> Grid2D.RIGHT;
		case "D" -> Grid2D.DOWN;
		case "L" -> Grid2D.LEFT;
		default -> null;
		};
	}
}
