package day11;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day11Main {
	public static List<List<Integer>> octopuses = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("Flashes after 100 steps : " + solveA());
		System.out.println("Steps until all flash : " + solveB());
	}

	public static int solveA() {
		octopuses = getInput();
		return IntStream.range(0, 100).map(i -> doStep()).sum();
	}

	public static int solveB() {
		octopuses = getInput();
		int octopusCount = octopuses.size() * octopuses.get(0).size();
		int steps = 0;
		int flashedCount = 0;
		do {
			flashedCount = doStep();
			steps++;
		} while (flashedCount != octopusCount);
		return steps;
	}

	public static int doStep() {
		incrementAll(getAllPositions());
		Set<Point> flashedOctopuses = flashRecursively(new HashSet<>());
		flashedOctopuses.forEach(pos -> setOctopusAt(pos, 0));
		return flashedOctopuses.size();
	}

	public static Set<Point> flashRecursively(Set<Point> knownFlashPositions) {
		Set<Point> needToFlash = getFlashPositions();
		needToFlash.removeAll(knownFlashPositions);

		if (needToFlash.isEmpty())
			return knownFlashPositions;
		else {
			flashOnce(needToFlash);
			Set<Point> newKnownFlashPositions = new HashSet<>(knownFlashPositions);
			newKnownFlashPositions.addAll(needToFlash);
			return flashRecursively(newKnownFlashPositions);
		}
	}

	public static void flashOnce(Collection<Point> positions) {
		positions.forEach(Day11Main::flashOnce);
	}

	public static void flashOnce(Point position) {
		incrementAll(getNeighbours(position));
	}

	public static Set<Point> getNeighbours(Point curPoint) {
		Set<Point> neighbours = new HashSet<>();
		int startY = (curPoint.y == 0) ? 0 : curPoint.y - 1;
		int endY = (curPoint.y == getMaxY()) ? getMaxY() : curPoint.y + 1;
		int startX = (curPoint.x == 0) ? 0 : curPoint.x - 1;
		int endX = (curPoint.x == getMaxX()) ? getMaxX() : curPoint.x + 1;

		for (int y = startY; y <= endY; y++)
			for (int x = startX; x <= endX; x++) {
				neighbours.add(new Point(x, y));
			}
		neighbours.remove(curPoint);
		return neighbours;
	}

	public static Set<Point> getFlashPositions() {
		return getAllPositions().stream().filter(pos -> getOctopusAt(pos) > 9).collect(Collectors.toSet());
	}

	public static List<Point> getAllPositions() {
		return IntStream.rangeClosed(0, getMaxY()).boxed()
				.flatMap(y -> IntStream.rangeClosed(0, getMaxX()).mapToObj(x -> new Point(x, y)))
				.collect(Collectors.toList());
	}

	public static void incrementAll(Collection<Point> positions) {
		positions.forEach(Day11Main::increment);
	}

	public static void increment(Point position) {
		int curValue = getOctopusAt(position);
		setOctopusAt(position, curValue + 1);
	}

	public static int getOctopusAt(Point position) {
		return octopuses.get(position.y).get(position.x);
	}

	public static void setOctopusAt(Point position, int newValue) {
		octopuses.get(position.y).set(position.x, newValue);
	}

	public static int getMaxX() {
		return octopuses.get(0).size() - 1;
	}

	public static int getMaxY() {
		return octopuses.size() - 1;
	}

	public static List<List<Integer>> getInput() {
		List<String> input = AdventOfCodeUtils.readInput(Day11Main.class);
		return input.stream()
				.map(line -> Arrays.stream(line.trim().split("")).map(Integer::parseInt).collect(Collectors.toList()))
				.collect(Collectors.toList());
	}
}
