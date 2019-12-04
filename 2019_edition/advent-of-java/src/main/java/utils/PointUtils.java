package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PointUtils {
    public static final Point ORIGIN_POINT = new Point(0, 0);

    /*
     * Generates a list of all points which exist within the area of the square that
     * is formed after connecting the given arguments.
     * 
     * The generation first goes horizontally and then vertically!
     */
    public static List<Point> generatePointsInArea(Point pointA, Point pointB) {
        List<Point> points = new ArrayList<>();
        List<Integer> rangeX = ListUtils.generateRange(pointA.x, pointB.x);
        List<Integer> rangeY = ListUtils.generateRange(pointA.y, pointB.y);
        for (int y : rangeY)
            for (int x : rangeX)
                points.add(new Point(x, y));
        return points;
    }

    public static int calcManhattanDistance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
