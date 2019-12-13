package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PointUtils {
    public static final Point ORIGIN_POINT = new Point(0, 0);

    public static int calcAbsoluteSum(ThreeDPoint point) {
        return Math.abs(point.x) + Math.abs(point.y) + Math.abs(point.z);
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

    public static Double calcDeltaX(Point startPoint, Point endPoint) {
        return Double.valueOf(endPoint.x - startPoint.x);
    }

    public static Double calcDeltaY(Point startPoint, Point endPoint) {
        return Double.valueOf(endPoint.y - startPoint.y);
    }

    public static int calcManhattanDistance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
