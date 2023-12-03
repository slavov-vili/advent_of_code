package day03;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import utils.AdventOfCodeUtils;
import utils.Grid2D;

public class Day03Main {

	public static void main(String[] args) {
		Grid2D<String> schematic = parseSchematic();

		System.out.println("Sum of relevant numbers: " + solveA(schematic));
		System.out.println("Sum of gear ratios: " + solveB(schematic));
	}

	public static long solveA(Grid2D<String> schematic) {
		long sum = 0;
		int row = 0;
		while (row < schematic.getRowCount()) {
			int col = 0;
			while (col < schematic.getColumnCount()) {
				Point curPos = new Point(row, col);
				String curChar = schematic.get(curPos);
				if (isDigit(curChar)) {
					Optional<String> curNumber = getRelevantNumber(schematic, curPos);
					if (curNumber.isPresent()) {
						sum += Long.parseLong(curNumber.get());
						col += curNumber.get().length();
					}
				}
				col++;
			}

			row++;
		}

		return sum;
	}

	public static long solveB(Grid2D<String> schematic) {
		Map<Point, List<Long>> gearNeighbors = new HashMap<>();
		int row = 0;
		while (row < schematic.getRowCount()) {
			int col = 0;
			while (col < schematic.getColumnCount()) {
				Point curPos = new Point(row, col);
				String curChar = schematic.get(curPos);
				if (isDigit(curChar)) {
					List<Point> digitPositions = getDigitPositionsForNumber(schematic, curPos);
					if (!digitPositions.isEmpty()) {
						Set<Point> neighboringGears = findNeighboringGears(schematic, digitPositions);
						Long number = Long.parseLong(
								digitPositions.stream().map(schematic::get).reduce((num, digit) -> num + digit).get());
						for (Point gear : neighboringGears) {
							List<Long> neighboringNumbers = gearNeighbors.getOrDefault(gear, new ArrayList<>());
							neighboringNumbers.add(number);
							gearNeighbors.put(gear, neighboringNumbers);
						}
						col += digitPositions.size();
					}
				}
				col++;
			}

			row++;
		}

		return gearNeighbors.keySet().stream().filter(gearPos -> gearNeighbors.get(gearPos).size() == 2)
				.mapToLong(gearPos -> gearNeighbors.get(gearPos).stream().reduce((a, b) -> a * b).get()).sum();
	}

	public static Optional<String> getRelevantNumber(Grid2D<String> schematic, Point startPos) {
		List<Point> digitPositions = getDigitPositionsForNumber(schematic, startPos);
		boolean isRelevant = digitPositions.stream().anyMatch(pos -> neighborsSymbol(schematic, pos));
		if (!isRelevant)
			return Optional.empty();

		return digitPositions.stream().map(schematic::get).reduce((num, digit) -> num + digit);
	}

	public static List<Point> getDigitPositionsForNumber(Grid2D<String> schematic, Point position) {
		if (!isDigit(schematic.get(position)))
			return List.of();

		List<Point> digitPositions = new ArrayList<>();
		Point curPos = position;
		while (schematic.hasPosition(curPos) && isDigit(schematic.get(curPos))) {
			digitPositions.add(curPos);
			curPos = new Point(curPos.x, curPos.y + 1);
		}
		return digitPositions;
	}

	public static Set<Point> findNeighboringGears(Grid2D<String> schematic, List<Point> digitPositions) {
		Set<Point> gearPositions = new HashSet<>();
		for (Point digitPos : digitPositions)
			schematic.getAllNeighborPositions(digitPos).stream().filter(pos -> "*".equals(schematic.get(pos)))
					.forEach(gearPositions::add);
		return gearPositions;
	}

	public static boolean neighborsSymbol(Grid2D<String> schematic, Point position) {
		return schematic.getAllNeighborPositions(position).stream().anyMatch(pos -> isSymbol(schematic, pos));
	}

	public static boolean isSymbol(Grid2D<String> schematic, Point position) {
		String curChar = schematic.get(position);
		return !(".".equals(curChar) || isDigit(curChar));
	}

	public static boolean isDigit(String str) {
		return Character.isDigit(str.charAt(0));
	}

	public static Grid2D<String> parseSchematic() {
		List<String> input = AdventOfCodeUtils.readInput(Day03Main.class);
		Grid2D<String> schematic = new Grid2D<>(input.size(), input.get(0).length());
		schematic.positionIterator()
				.forEachRemaining(pos -> schematic.set(pos, input.get(pos.x).substring(pos.y, pos.y + 1)));
		return schematic;
	}
}
