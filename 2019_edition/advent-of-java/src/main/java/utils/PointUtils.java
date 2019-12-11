package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PointUtils {
    public static final Point ORIGIN_POINT = new Point(0, 0);

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

    public static Optional<Double> calcSlopeOf(Point startPoint, Point endPoint) {
        if (endPoint.equals(startPoint))
            return Optional.empty();

        if (endPoint.x == startPoint.x)
            return Optional.of(Double.POSITIVE_INFINITY);

        return Optional.of((double) (endPoint.y - startPoint.y) / (double) (endPoint.x - startPoint.x));
    }

    public static int calcManhattanDistance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
