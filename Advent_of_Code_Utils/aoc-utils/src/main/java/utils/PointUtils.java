package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PointUtils {
	public static final Point ORIGIN_POINT = new Point(0, 0);

	public static Double calcDeltaX(Point from, Point to) {
		return Double.valueOf(to.x - from.x);
	}

	public static Double calcDeltaY(Point from, Point to) {
		return Double.valueOf(to.y - from.y);
	}

	public static int calcManhattanDistance(Point from, Point to) {
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}

	public static Point findDirection(Point from, Point to) {
		int dirX = to.x - from.x;
		int dirY = to.y - from.y;

		return new Point(dirX / Math.abs(dirX), dirY / Math.abs(dirY));
	}

	// = Manhattan + diagonals
	public static int calcChebyshevDistance(Point from, Point to) {
		return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
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
