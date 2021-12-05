package day05;

import java.awt.Point;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.IntegerUtils;

public class Day05Main {
	public static final Pattern COORDINATE_PATTERN = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day05Main.class);
		List<SimpleEntry<Point, Point>> lines = extractLines(input);

		System.out.println("NonDiagonal crossings: " + solveA(lines));
		System.out.println("All crossings: " + solveB(lines));
	}

	public static int solveA(List<SimpleEntry<Point, Point>> lines) {
		return findAtLeastTwoLineCrossings(
				lines.stream().filter(line -> !isDiagonal(line)).collect(Collectors.toList()));
	}

	public static int solveB(List<SimpleEntry<Point, Point>> lines) {
		return findAtLeastTwoLineCrossings(lines);
	}

	public static int findAtLeastTwoLineCrossings(List<SimpleEntry<Point, Point>> lines) {
		Map<Point, Integer> lineCrossingsMap = new HashMap<>();
		for (SimpleEntry<Point, Point> line : lines) {
			for (Point point : generatePath(line)) {
				int curCount = lineCrossingsMap.getOrDefault(point, 0);
				lineCrossingsMap.put(point, curCount + 1);
			}
		}

		List<Point> atLeastTwoCrossings = lineCrossingsMap.keySet().stream()
				.filter(point -> lineCrossingsMap.get(point) >= 2).collect(Collectors.toList());

		return atLeastTwoCrossings.size();
	}

	public static List<Point> generatePath(SimpleEntry<Point, Point> line) {
		return (isDiagonal(line)) ? generateDiagonalPath(line) : generateNonDiagonalPath(line);
	}

	public static List<Point> generateNonDiagonalPath(SimpleEntry<Point, Point> line) {
		Point source = line.getKey();
		Point target = line.getValue();
		int incrementX = -IntegerUtils.compareInts(source.x, target.x);
		int incrementY = -IntegerUtils.compareInts(source.y, target.y);
		
		List<Point> path = new ArrayList<>();
		if (source.x == target.x)
			for (int y = source.y; y != target.y + incrementY; y += incrementY)
				path.add(new Point(source.x, y));
		else if (source.y == target.y)
			for (int x = source.x; x != target.x + incrementX; x += incrementX)
				path.add(new Point(x, source.y));
			
				
		return path;
	}

	public static List<Point> generateDiagonalPath(SimpleEntry<Point, Point> line) {
		Point source = line.getKey();
		Point target = line.getValue();
		int incrementX = -IntegerUtils.compareInts(source.x, target.x);
		int incrementY = -IntegerUtils.compareInts(source.y, target.y);

		List<Point> path = new ArrayList<>();
		int curX = source.x;
		int curY = source.y;
		while (curX != (target.x + incrementX) && curY != (target.y + incrementY)) {
			path.add(new Point(curX, curY));
			curX += incrementX;
			curY += incrementY;
		}
		return path;
	}

	public static boolean isDiagonal(SimpleEntry<Point, Point> line) {
		Point source = line.getKey();
		Point target = line.getValue();
		return (source.x != target.x) && (source.y != target.y);
	}

	public static List<SimpleEntry<Point, Point>> extractLines(List<String> input) {
		return input.stream().map(Day05Main::parseLine).collect(Collectors.toList());
	}

	public static SimpleEntry<Point, Point> parseLine(String line) {
		Matcher coordinateMatcher = COORDINATE_PATTERN.matcher(line);
		coordinateMatcher.find();
		return new SimpleEntry<>(
				new Point(Integer.parseInt(coordinateMatcher.group(1)), Integer.parseInt(coordinateMatcher.group(2))),
				new Point(Integer.parseInt(coordinateMatcher.group(3)), Integer.parseInt(coordinateMatcher.group(4))));
	}
}
