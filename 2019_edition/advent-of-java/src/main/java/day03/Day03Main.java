package day03;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.PointUtils;

public class Day03Main {

    public static void main(String[] args) {
        List<List<String>> wireInputs = getInputs();
        List<Wire> wires = getWires(wireInputs);
        Set<Point> intersections = WireUtils.intersectWires(wires.get(0), wires.get(1));

        System.out.println("Solution A: " + solveA(intersections));

        System.out.println("Solution B: " + solveB(intersections, wires));
    }

    protected static int solveA(Set<Point> intersections) {
        Point closestPoint = intersections.stream()
                .min(Comparator.comparing(point -> PointUtils.calcManhattanDistance(point, PointUtils.ORIGIN_POINT)))
                .get();

        return PointUtils.calcManhattanDistance(closestPoint, PointUtils.ORIGIN_POINT);
    }

    protected static int solveB(Set<Point> intersections, List<Wire> wires) {
        Point closestPoint = intersections.stream()
                .min(Comparator.comparing(point -> WireUtils.calcCombinedDistance(point, wires.get(0), wires.get(1))))
                .get();

        return WireUtils.calcCombinedDistance(closestPoint, wires.get(0), wires.get(1));
    }

    protected static List<Wire> getWires(List<List<String>> wireInputLists) {
        List<Wire> wires = new ArrayList<>();

        for (List<String> curWireInput : wireInputLists) {
            Wire newWire = new Wire(PointUtils.ORIGIN_POINT);
            newWire.moveAlongPath(curWireInput);
            wires.add(newWire);
        }

        return wires;
    }

    protected static List<List<String>> getInputs() {
        List<String> inputLines = AdventOfCodeUtils.readClasspathFileLines(Day03Main.class, "input.txt");
        return inputLines.stream().map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
    }
}
