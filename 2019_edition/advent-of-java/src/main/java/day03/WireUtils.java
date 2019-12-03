package day03;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class WireUtils {
    public static Set<Point> intersectWires(Wire wireA, Wire wireB) {
        Set<Point> intersections = new HashSet(wireA.getPositionHistory());
        Set<Point> otherPositionHistory = new HashSet(wireB.getPositionHistory());
        intersections.retainAll(otherPositionHistory);
        intersections.remove(wireA.getInitialPosition());
        intersections.remove(wireB.getInitialPosition());
        return intersections;
    }
     
    public static int calcCombinedDistance(Point point, Wire wireA, Wire wireB) {
        int combinedDistance = -1;
        
        try {
            combinedDistance = wireA.calcStepsToPreviousPosition(point) + wireB.calcStepsToPreviousPosition(point);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return combinedDistance;
    }
}
