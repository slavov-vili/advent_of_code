package day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.AdventOfCodeUtils;
import utils.DijkstraShortestPathFinder;

public class Day11Main {

	public static char GALAXY = '#';

	public static void main(String[] args) {
		var galaxies = parseGalaxies();

		System.out.println("Sum of shortest paths: " + solveA(galaxies));

		System.out.println("Sum of huge shortest paths: " + solveB(galaxies));
	}

	public static long solveA(List<Galaxy> galaxies) {
		return calcMinDistanceSum(galaxies, 2);
	}

	public static long solveB(List<Galaxy> galaxies) {
		return calcMinDistanceSum(galaxies, 1000000);
	}

	public static long calcMinDistanceSum(List<Galaxy> galaxies, int expansionFactor) {
		var expandedGalaxies = expand(galaxies, expansionFactor);
		var perPointSum = new HashMap<Galaxy, Long>();
		for (Galaxy curGal : expandedGalaxies) {
			var others = new ArrayList<>(expandedGalaxies);
			others.removeAll(perPointSum.keySet());
			var pathFinder = new DijkstraShortestPathFinder<Galaxy>(Galaxy::calcDistanceTo, gal -> others);
			var curDistances = pathFinder.findAll(curGal);
			long curSum = curDistances.values().stream().mapToLong(d -> d).sum();
			perPointSum.put(curGal, curSum);
		}

		return perPointSum.values().stream().mapToLong(ds -> ds).sum();
	}

	public static List<Galaxy> expand(List<Galaxy> galaxies, int expansionFactor) {
		var emptySpace = findEmptySpace();
		var extraRows = emptySpace.get(0).stream().map(count -> count * expansionFactor - count).toList();
		var extraCols = emptySpace.get(1).stream().map(count -> count * expansionFactor - count).toList();

		return galaxies.stream()
				.map(gal -> new Galaxy(gal.row + extraRows.get((int) gal.row), gal.col + extraCols.get((int) gal.col)))
				.toList();
	}

	public static boolean isGalaxy(char ch) {
		return ch == GALAXY;
	}

	record Galaxy(long row, long col) {
		public long calcDistanceTo(Galaxy other) {
			return Math.abs(this.row - other.row) + Math.abs(this.col - other.col);
		}
	}

	public static List<Galaxy> parseGalaxies() {
		List<String> input = AdventOfCodeUtils.readInput(Day11Main.class);
		List<Galaxy> galaxies = new ArrayList<>();
		for (int row = 0; row < input.size(); row++)
			for (int col = 0; col < input.get(0).length(); col++)
				if (isGalaxy(input.get(row).charAt(col)))
					galaxies.add(new Galaxy(row, col));

		return galaxies;
	}

	public static List<List<Long>> findEmptySpace() {
		List<String> input = AdventOfCodeUtils.readInput(Day11Main.class);
		List<Long> emptyRows = new ArrayList<>(input.size());
		List<Long> emptyCols = new ArrayList<>(input.get(0).length());

		long curEmptyRows = 0;
		for (int row = 0; row < input.size(); row++) {
			emptyRows.add(curEmptyRows);
			if (!input.get(row).contains("" + GALAXY))
				curEmptyRows++;
		}

		long curEmptyCols = 0;
		for (int col = 0; col < input.get(0).length(); col++) {
			emptyCols.add(curEmptyCols);
			if (!colHasGalaxies(input, col))
				curEmptyCols++;
		}

		return List.of(emptyRows, emptyCols);
	}

	public static boolean colHasGalaxies(List<String> input, int col) {
		return input.stream().map(row -> row.charAt(col)).anyMatch(Day11Main::isGalaxy);
	}
}
