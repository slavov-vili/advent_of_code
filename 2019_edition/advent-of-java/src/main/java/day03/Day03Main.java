package day03;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import utils.AdventOfCodeUtils;

public class Day03Main {

	public static void main(String[] args) {
		System.out.println("Solution A: " + solveA());
		
		System.out.println("Solution B: " + solveB());
		
	}

	protected static int solveA() {
	    Set<Point> intersections = determineIntersections();
	    
		Point closestPoint = intersections.stream()
				.min(Comparator.comparing(point -> AdventOfCodeUtils.calcManhattanDistance(point, getOriginPoint())))
				.get();
		
		return AdventOfCodeUtils.calcManhattanDistance(closestPoint, getOriginPoint());
	}

    protected static int solveB() {
        List<Wire> wires = getWires();
        Set<Point> intersections = determineIntersections();
        
        Point closestPoint = intersections.stream()
                .min(Comparator.comparing(point -> calcCombinedDistance(point, wires.get(0), wires.get(1))))
                .get();
        
        return calcCombinedDistance(closestPoint, wires.get(0), wires.get(1));
    }
	
	protected static int calcCombinedDistance(Point point, Wire wireA, Wire wireB) {
	    int combinedDistance = -1;
	    
	    try {
	        combinedDistance = wireA.calcStepsToPreviousPosition(point) + wireB.calcStepsToPreviousPosition(point);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.exit(1);
	    }
	    return combinedDistance;
    }

    protected static Set<Point> determineIntersections() {
	    List<Wire> wires = getWires();
	    
        return WireUtils.intersectWires(wires.get(0), wires.get(1));
	}
	
	protected static List<Wire> getWires() {
	    Wire wireA = new Wire(getOriginPoint());
        Wire wireB = new Wire(getOriginPoint());
        wireA.moveAlongPath(getInputWireA());
        wireB.moveAlongPath(getInputWireB());
        
	    return Arrays.asList(wireA, wireB);
	}
	
	protected static Point getOriginPoint() {
		return new Point(0, 0);
	}
	
    protected static List<String> getInputWireA() {
        String pathWireA = AdventOfCodeUtils.readClasspathFileLines(Day03Main.class, "input.txt").get(0);
        return Arrays.asList(pathWireA.split(","));
    }
    
    protected static List<String> getInputWireB() {
        String pathWireB = AdventOfCodeUtils.readClasspathFileLines(Day03Main.class, "input.txt").get(1);
        return Arrays.asList(pathWireB.split(","));
    }
}
