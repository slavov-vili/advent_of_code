package day15;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import day13.Day13Main;
import utils.AdventOfCodeUtils;

public class Day15Main {

	public static Point NORTH = new Point(0, -1);
	public static Point EAST = new Point(1, 0);
	public static Point SOUTH = new Point(0, 1);
	public static Point WEST = new Point(-1, 0);

	public static List<Point> DIRECTIONS = Arrays.asList(NORTH, EAST, SOUTH, WEST);

	public static List<List<Integer>> riskLevels;

	public static void main(String[] args) {
		riskLevels = parseInput();
		System.out.println("Lowest risk level: " + solveA());

		riskLevels = extendGrid(5);
		riskLevels.forEach(System.out::println);
		System.out.println("Lowest risk level in extended grid: " + solveA());
	}

	public static int solveA() {
		return findMinRisk(getStartPoint());
	}

	public static int findMinRisk(Point startPosition) {
		Map<Point, Integer> minRiskMap = new HashMap<>();
		Queue<Point> nextPositions = buildPriorityQueue(minRiskMap);
		Map<Point, Point> prevMap = new HashMap<>();
		minRiskMap.put(startPosition, 0);

		while (!nextPositions.isEmpty()) {
			Point curPosition = nextPositions.poll();

			for (Point neighbour : getNeighbours(curPosition)) {
				int curRisk = minRiskMap.getOrDefault(curPosition, Integer.MAX_VALUE) + getRiskLevelAt(neighbour);
				if (curRisk < minRiskMap.getOrDefault(neighbour, Integer.MAX_VALUE)) {
					minRiskMap.put(neighbour, curRisk);
					prevMap.put(neighbour, curPosition);

					// readjust priority
					nextPositions.remove(neighbour);
					nextPositions.add(neighbour);
				}
			}
		}

		Set<Point> path = new HashSet<>();
		Point curPoint = getEndPoint();
		while (prevMap.containsKey(curPoint)) {
			path.add(curPoint);
			curPoint = prevMap.get(curPoint);
		}
		System.out.println(Day13Main.buildMessage(path));

		return minRiskMap.get(getEndPoint());
	}

	public static Queue<Point> buildPriorityQueue(Map<Point, Integer> minRiskMap) {
		Queue<Point> priority = new PriorityQueue<>((p1, p2) -> minRiskMap.getOrDefault(p1, Integer.MAX_VALUE)
				.compareTo(minRiskMap.getOrDefault(p2, Integer.MAX_VALUE)));

		IntStream.range(0, getSize()).boxed().flatMap(y -> IntStream.range(0, getSize()).mapToObj(x -> new Point(x, y)))
				.forEach(priority::add);

		return priority;
	}

	public static List<Point> getNeighbours(Point curPoint) {
		return DIRECTIONS.stream().map(dir -> new Point(dir.x + curPoint.x, dir.y + curPoint.y))
				.filter(dir -> dir.x >= 0 && dir.x < getSize()).filter(dir -> dir.y >= 0 && dir.y < getSize())
				.collect(Collectors.toList());
	}

	public static int getRiskLevelAt(Point position) {
		return riskLevels.get(position.y).get(position.x);
	}

	public static Point getStartPoint() {
		return new Point(0, 0);
	}

	public static Point getEndPoint() {
		return new Point(getSize() - 1, getSize() - 1);
	}

	public static int getSize() {
		return riskLevels.size();
	}

	public static List<List<Integer>> extendGrid(int gridRepeat) {
		List<List<Integer>> extendedRows = IntStream.range(0, getSize())
				.mapToObj(i -> extendHorizontally(riskLevels.get(i), gridRepeat)).collect(Collectors.toList());
		return extendVertically(extendedRows, gridRepeat);
	}

	public static List<Integer> extendHorizontally(List<Integer> row, int colRepeat) {
		return IntStream.range(0, colRepeat).boxed().flatMap(i -> row.stream().map(value -> wrapValue(value + i)))
				.collect(Collectors.toList());
	}

	public static List<List<Integer>> extendVertically(List<List<Integer>> rows, int rowRepeat) {
		return IntStream.range(0, rowRepeat).boxed()
				.flatMap(i -> rows.stream()
						.map(row -> row.stream().map(value -> wrapValue(value + i)).collect(Collectors.toList())))
				.collect(Collectors.toList());
	}

	public static int wrapValue(int value) {
		return (value > 9) ? value % 10 + 1 : value;
	}

	public static List<List<Integer>> parseInput() {
		List<String> input = AdventOfCodeUtils.readInput(Day15Main.class);
		return input.stream()
				.map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).collect(Collectors.toList()))
				.collect(Collectors.toList());
	}
}
