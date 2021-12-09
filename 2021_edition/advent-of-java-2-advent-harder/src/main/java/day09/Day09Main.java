package day09;

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
import utils.IntegerUtils;

public class Day09Main {

	public static final Point NORTH = new Point(0, -1);
	public static final Point EAST = new Point(1, 0);
	public static final Point SOUTH = new Point(0, 1);
	public static final Point WEST = new Point(-1, 0);
	public static final List<Point> DIRECTIONS = new ArrayList<>(Arrays.asList(NORTH, EAST, SOUTH, WEST));

	public static void main(String[] args) {
		List<List<Integer>> heightmap = getInput();

		List<Point> lowPoints = findLowPoints(heightmap);
		System.out.println("Sum of risk levels: " + solveA(lowPoints, heightmap));

		List<Set<Point>> basins = findBasins(lowPoints, heightmap);
		System.out.println("Top 3 basin sizes multiplied: " + solveB(basins));
	}

	public static int solveA(List<Point> lowPoints, List<List<Integer>> heightmap) {
		return lowPoints.stream().mapToInt(lowPoint -> getHeightAt(lowPoint, heightmap) + 1).sum();
	}

	public static long solveB(List<Set<Point>> basins) {
		return basins.stream().mapToLong(Set::size).sorted().skip(basins.size() - 3).reduce((a, b) -> a * b)
				.getAsLong();
	}

	public static List<Point> findLowPoints(List<List<Integer>> heightmap) {
		return generateLocations(getMaxX(heightmap), getMaxY(heightmap)).stream()
				.filter(location -> isLowPoint(location, heightmap)).collect(Collectors.toList());
	}

	public static List<Point> generateLocations(int maxX, int maxY) {
		return IntStream.rangeClosed(0, maxY).boxed()
				.flatMap(y -> IntStream.rangeClosed(0, maxX).mapToObj(x -> new Point(x, y)))
				.collect(Collectors.toList());
	}

	public static boolean isLowPoint(Point curLocation, List<List<Integer>> heightmap) {
		Collection<Point> adjacentLocations = getAdjacentLocations(curLocation, getMaxX(heightmap), getMaxY(heightmap));
		int curHeight = getHeightAt(curLocation, heightmap);
		return adjacentLocations.stream().map(location -> getHeightAt(location, heightmap))
				.allMatch(height -> height > curHeight);
	}

	public static List<Set<Point>> findBasins(List<Point> lowPoints, List<List<Integer>> heightmap) {
		return lowPoints.stream().map(
				curLocation -> findLocationsOfBasin(curLocation, new HashSet<>(Arrays.asList(curLocation)), heightmap))
				.collect(Collectors.toList());
	}

	public static Set<Point> findLocationsOfBasin(Point curLocation, Set<Point> knownLocationsInBasin,
			List<List<Integer>> heightmap) {
		int curHeight = getHeightAt(curLocation, heightmap);

		if (curHeight == 9)
			return new HashSet<>();
		else {
			Set<Point> locationsInBasin = new HashSet<>(knownLocationsInBasin);
			locationsInBasin.add(curLocation);

			Collection<Point> adjacentLocations = getAdjacentLocations(curLocation, getMaxX(heightmap),
					getMaxY(heightmap));
			adjacentLocations.stream().filter(location -> !locationsInBasin.contains(location))
					.map(location -> findLocationsOfBasin(location, locationsInBasin, heightmap))
					.forEach(locations -> locationsInBasin.addAll(locations));

			return locationsInBasin;
		}
	}

	public static int getHeightAt(Point location, List<List<Integer>> heightmap) {
		return heightmap.get(location.y).get(location.x);
	}

	public static int getMaxX(List<List<Integer>> heightmap) {
		return heightmap.get(0).size() - 1;
	}

	public static int getMaxY(List<List<Integer>> heightmap) {
		return heightmap.size() - 1;
	}

	public static Collection<Point> getAdjacentLocations(Point curLocation, int maxX, int maxY) {
		List<Point> newLocations = DIRECTIONS.stream().map(Point::new).collect(Collectors.toList());
		newLocations.forEach(direction -> direction.translate(curLocation.x, curLocation.y));
		return newLocations.stream().filter(location -> (IntegerUtils.integerIsWithinRange(location.x, 0, maxX)))
				.filter(location -> (IntegerUtils.integerIsWithinRange(location.y, 0, maxY)))
				.collect(Collectors.toList());
	}

	public static List<List<Integer>> getInput() {
		List<String> input = AdventOfCodeUtils.readInput(Day09Main.class);
		return input.stream()
				.map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).collect(Collectors.toList()))
				.collect(Collectors.toList());
	}
}
