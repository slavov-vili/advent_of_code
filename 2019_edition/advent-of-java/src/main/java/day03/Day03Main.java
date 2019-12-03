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
		
	}

	protected static int solveA() {
		Wire wireA = new Wire(getOriginPoint());
		Wire wireB = new Wire(getOriginPoint());
		wireA.moveAlongPath(getInputWireA());
		wireB.moveAlongPath(getInputWireB());
		
		Set<Point> intersections = wireA.getIntersectionsWith(wireB);
		Point closestPoint = intersections.stream()
				.min(Comparator.comparing(point -> AdventOfCodeUtils.calcManhattanDistance(point, getOriginPoint())))
				.get();
		
		return AdventOfCodeUtils.calcManhattanDistance(closestPoint, getOriginPoint());
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
