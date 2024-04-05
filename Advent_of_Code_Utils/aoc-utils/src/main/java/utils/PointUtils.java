package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class PointUtils {
	public static final Point ORIGIN_POINT = new Point(0, 0);

	public static final UnaryOperator<Point> UP = point -> new Point(point.x - 1, point.y);
	public static final UnaryOperator<Point> RIGHT = point -> new Point(point.x, point.y + 1);
	public static final UnaryOperator<Point> DOWN = point -> new Point(point.x + 1, point.y);
	public static final UnaryOperator<Point> LEFT = point -> new Point(point.x, point.y - 1);

	public static List<UnaryOperator<Point>> getDirections() {
		return List.of(UP, RIGHT, DOWN, LEFT);
	}

	public static final UnaryOperator<Point> UP_LEFT = point -> UP.andThen(LEFT).apply(point);
	public static final UnaryOperator<Point> UP_RIGHT = point -> UP.andThen(RIGHT).apply(point);
	public static final UnaryOperator<Point> DOWN_LEFT = point -> DOWN.andThen(LEFT).apply(point);
	public static final UnaryOperator<Point> DOWN_RIGHT = point -> DOWN.andThen(RIGHT).apply(point);

	public static List<UnaryOperator<Point>> getDiagonals() {
		return List.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT);
	}

	public static List<UnaryOperator<Point>> getAllDirections() {
		var allDirections = new ArrayList<>(getDirections());
		allDirections.addAll(getDiagonals());
		return allDirections;
	}

	public static List<Point> getNeighborPositions(Point curPos, Predicate<Point> positionValidator) {
		return getPositionsInDirections(curPos, getDirections(), positionValidator);
	}

	public static List<Point> getAllNeighborPositions(Point curPos, Predicate<Point> positionValidator) {
		return getPositionsInDirections(curPos, getAllDirections(), positionValidator);
	}

	public static List<Point> getPositionsInDirections(Point curPos, List<UnaryOperator<Point>> directions,
			Predicate<Point> positionValidator) {
		return directions.stream().map(dir -> dir.apply(curPos)).filter(positionValidator::test).toList();
	}

	public static List<Point> getPositionsInPaths(Point curPos, Predicate<Point> positionValidator) {
		return getPositionsInPaths(curPos, curPos, getDirections(), positionValidator);
	}

	public static List<Point> getAllPositionsInPaths(Point curPos, Predicate<Point> positionValidator) {
		return getPositionsInPaths(curPos, curPos, getAllDirections(), positionValidator);
	}

	public static List<Point> getPositionsInPaths(Point curPos, Point prevPos, List<UnaryOperator<Point>> directions,
			Predicate<Point> positionValidator) {
		var nextPositions = directions.stream().map(dir -> dir.apply(curPos))
				.filter(nextPos -> !nextPos.equals(prevPos) && positionValidator.test(nextPos))
				.collect(Collectors.toList());
		var positionsInPaths = new ArrayList<>(nextPositions);
		nextPositions.stream()
				.flatMap(nextPos -> getPositionsInPaths(nextPos, curPos, directions, positionValidator).stream())
				.forEach(positionsInPaths::add);
		return positionsInPaths;
	}

	public static Double calcDeltaX(Point from, Point to) {
		return Double.valueOf(to.x - from.x);
	}

	public static Double calcDeltaY(Point from, Point to) {
		return Double.valueOf(to.y - from.y);
	}

	public static int calcManhattanDistance(Point from, Point to) {
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}

	// Manhattan + diagonals
	public static int calcChebyshevDistance(Point from, Point to) {
		return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
	}

	// Manhattan + diagonals
	public static UnaryOperator<Point> getDirection(Point from, Point to) {
		int diffX = to.x - from.x;
		int dirX = (diffX == 0) ? 0 : diffX / Math.abs(diffX);

		int diffY = to.y - from.y;
		int dirY = (diffY == 0) ? 0 : diffY / Math.abs(diffY);

		return pos -> new Point(pos.x + dirX, pos.y + dirY);
	}

	/*
	 * Generates a list of all points which exist within the area of the square that
	 * is formed after connecting the given arguments.
	 * 
	 * The generation first goes horizontally and then vertically!
	 */
	public static List<Point> generatePointsInAreaInclusive(Point startPoint, Point endPoint) {
		List<Point> points = new ArrayList<>();
		List<Integer> rangeX = ListUtils.generateRange(startPoint.x, endPoint.x);
		List<Integer> rangeY = ListUtils.generateRange(startPoint.y, endPoint.y);
		for (int y : rangeY)
			for (int x : rangeX)
				points.add(new Point(x, y));
		return points;
	}

	public static Double calcAngleOfLine(Point startPoint, Point endPoint) {
		double xRelativeToRoot = endPoint.x - startPoint.x;
		double yRelativeToRoot = startPoint.y - endPoint.y;
		double possiblyNegativeDegrees = Math.toDegrees(Math.atan2(xRelativeToRoot, yRelativeToRoot));
		return (possiblyNegativeDegrees >= 0) ? possiblyNegativeDegrees : 360 + possiblyNegativeDegrees;
	}

	public static Double calcSlopeOfLine(Point startPoint, Point endPoint) {
		if (endPoint.equals(startPoint))
			return Double.NEGATIVE_INFINITY;

		if (endPoint.x == startPoint.x)
			return Double.POSITIVE_INFINITY;

		double deltaX = calcDeltaX(startPoint, endPoint);
		double deltaY = calcDeltaY(startPoint, endPoint);
		return deltaY / deltaX;
	}

	public static int calcAbsoluteSum(ThreeDPoint point) {
		return Math.abs(point.x) + Math.abs(point.y) + Math.abs(point.z);
	}
}
