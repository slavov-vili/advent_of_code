package day03;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import utils.AdventOfCodeUtils;
import utils.Grid2D;

public class Day03Main {

	public static void main(String[] args) {
		Grid2D<String> schematic = parseSchematic();

		System.out.println("Sum of relevant numbers: " + solveA(schematic));
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

	public static void solveA() {

	public static Optional<String> getRelevantNumber(Grid2D<String> schematic, Point position) {
		List<Point> digitPositions = getDigitPositionsForNumber(schematic, position);
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

	public static boolean neighborsSymbol(Grid2D<String> schematic, Point position) {
		return schematic.getAllNeighbors(position).stream().anyMatch(pos -> isSymbol(schematic, pos));
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
