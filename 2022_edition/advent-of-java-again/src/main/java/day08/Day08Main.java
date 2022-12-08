package day08;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.Grid2D;

public class Day08Main {

	public static void main(String[] args) {
		Grid2D<Integer> forest = parseInput();

		System.out.println("Trees visible from the outside: " + solveA(forest));

		System.out.println("Max scenic score: " + solveB(forest));
	}

	public static int solveA(Grid2D<Integer> forest) {
		var visiblePositions = new HashSet<Point>();
		visiblePositions.addAll(findVisibleFromTop(forest));
		visiblePositions.addAll(findVisibleFromLeft(forest));
		visiblePositions.addAll(findVisibleFromRight(forest));
		visiblePositions.addAll(findVisibleFromBottom(forest));
		return visiblePositions.size();
	}

	public static int solveB(Grid2D<Integer> forest) {
		var iterator = forest.positionIterator();
		var maxScore = Integer.MIN_VALUE;
		while (iterator.hasNext()) {
			var curPosition = iterator.next();
			var curScore = calcScenicScore(curPosition, forest);

			if (curScore > maxScore)
				maxScore = curScore;
		}
		return maxScore;
	}

	public static Set<Point> findVisibleFromTop(Grid2D<Integer> forest) {
		return findVisibleFromOutside(new Point(0, 0), forest, Grid2D.RIGHT, Grid2D.DOWN);
	}

	public static Set<Point> findVisibleFromLeft(Grid2D<Integer> forest) {
		return findVisibleFromOutside(new Point(0, 0), forest, Grid2D.DOWN, Grid2D.RIGHT);
	}

	public static Set<Point> findVisibleFromRight(Grid2D<Integer> forest) {
		return findVisibleFromOutside(new Point(forest.getRowCount() - 1, forest.getColumnCount() - 1), forest,
				Grid2D.UP, Grid2D.LEFT);
	}

	public static Set<Point> findVisibleFromBottom(Grid2D<Integer> forest) {
		return findVisibleFromOutside(new Point(forest.getRowCount() - 1, forest.getColumnCount() - 1), forest,
				Grid2D.LEFT, Grid2D.UP);
	}

	public static Set<Point> findVisibleFromOutside(Point startPosition, Grid2D<Integer> forest,
			UnaryOperator<Point> movingDirection, UnaryOperator<Point> lookingDirection) {
		var visiblePositions = new HashSet<Point>();
		var curPosition = startPosition;
		while (forest.hasPosition(curPosition)) {
			visiblePositions.addAll(findVisibleInLine(curPosition, forest, lookingDirection));

			curPosition = movingDirection.apply(curPosition);
		}
		return visiblePositions;
	}

	public static List<Point> findVisibleInLine(Point startPosition, Grid2D<Integer> forest,
			UnaryOperator<Point> lookingDirection) {
		var visiblePositions = new ArrayList<Point>();
		var curPosition = startPosition;
		var curMaxHeight = Integer.MIN_VALUE;
		while (forest.hasPosition(curPosition)) {
			var curHeight = forest.get(curPosition);
			if (curHeight > curMaxHeight) {
				visiblePositions.add(curPosition);
				curMaxHeight = curHeight;
			}

			curPosition = lookingDirection.apply(curPosition);
		}

		return visiblePositions;
	}

	public static int calcScenicScore(Point position, Grid2D<Integer> forest) {
		return Grid2D.DIRECTIONS.stream().mapToInt(dir -> countVisibleTreesInDirection(position, forest, dir))
				.reduce((x, y) -> x * y).getAsInt();
	}

	public static int countVisibleTreesInDirection(Point position, Grid2D<Integer> forest,
			UnaryOperator<Point> direction) {
		var maxHeight = forest.get(position);
		var nextPosition = direction.apply(position);
		int treeCount = 0;
		while (forest.hasPosition(nextPosition)) {
			var nextHeight = forest.get(nextPosition);
			if (nextHeight < maxHeight)
				treeCount++;
			else if (nextHeight >= maxHeight) {
				treeCount++;
				break;
			}

			nextPosition = direction.apply(nextPosition);
		}
		return treeCount;
	}

	public static Grid2D<Integer> parseInput() {
		List<String> input = AdventOfCodeUtils.readInput(Day08Main.class);
		int rowCount = input.size();
		int columnCount = input.get(0).length();
		var grid = new Grid2D<Integer>(columnCount, rowCount);

		for (int row = 0; row < rowCount; row++) {
			var trees = Arrays.stream(input.get(row).split("")).map(Integer::parseInt).collect(Collectors.toList());

			for (int col = 0; col < columnCount; col++)
				grid.set(new Point(row, col), trees.get(col));
		}
		return grid;
	}
}
