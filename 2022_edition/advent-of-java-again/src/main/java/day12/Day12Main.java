package day12;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.DijkstraShortestPathFinder;
import utils.Grid2D;

public class Day12Main {
	public static final char START = 'S';
	public static final char END = 'E';
	public static final char MIN_HEIGHT = 'a';
	public static final char MAX_HEIGHT = 'z';

	public static void main(String[] args) {
		var grid = parseGrid();

		System.out.println("Shortest path from start: " + solveA(grid));

		System.out.println("Shortest path from any lowest point: " + solveB(grid));
	}

	public static int solveA(HeightGrid grid) {
		var positions = new ArrayList<Point>();
		grid.positionIterator().forEachRemaining(positions::add);
		var pathFinder = new DijkstraShortestPathFinder<Point>(positions, grid::findPossibleSteps);
		return pathFinder.find(grid.getStart(), grid.getEnd());
	}

	public static int solveB(HeightGrid grid) {
		var nodes = new ArrayList<Point>();
		grid.positionIterator().forEachRemaining(nodes::add);
		int minPath = Integer.MAX_VALUE;

		for (var startPosition : grid.getPositionsWhere(x -> x.equals(MIN_HEIGHT))) {
			var pathFinder = new DijkstraShortestPathFinder<Point>(nodes, grid::findPossibleSteps);
			var curPath = pathFinder.find(startPosition, grid.getEnd());
			if (curPath < minPath)
				minPath = curPath;
		}

		return minPath;
	}

	public static HeightGrid parseGrid() {
		List<String> input = AdventOfCodeUtils.readInput(Day12Main.class);
		int rowCount = input.size();
		int colCount = input.get(0).length();
		var grid = new HeightGrid(rowCount, colCount);

		for (int row = 0; row < rowCount; row++) {
			String line = input.get(row);

			for (int col = 0; col < colCount; col++) {
				char curHeight = line.charAt(col);
				var curPosition = new Point(row, col);

				if (START == curHeight) {
					grid.setStart(curPosition);
					grid.set(curPosition, MIN_HEIGHT);
				} else if (END == curHeight) {
					grid.setEnd(curPosition);
					grid.set(curPosition, MAX_HEIGHT);
				} else
					grid.set(curPosition, curHeight);
			}
		}

		return grid;
	}

	public static class HeightGrid extends Grid2D<Character> {
		private Point start;
		private Point end;

		public HeightGrid(int rowCount, int columnCount) {
			super(rowCount, columnCount);
			this.setStart(new Point());
			this.setEnd(new Point());
		}

		public List<Point> findPossibleSteps(Point curPosition) {
			return Grid2D.DIRECTIONS.stream().map(dir -> dir.apply(curPosition)).filter(this::hasPosition)
					.filter(pos -> this.calcHeightDifference(curPosition, pos) <= 1).collect(Collectors.toList());
		}

		public int calcHeightDifference(Point lowerPoint, Point higherPoint) {
			this.checkPosition(lowerPoint);
			this.checkPosition(higherPoint);
			return this.get(higherPoint) - this.get(lowerPoint);
		}

		public Point getStart() {
			return this.start;
		}

		public void setStart(Point newStart) {
			this.start = newStart;
		}

		public Point getEnd() {
			return this.end;
		}

		public void setEnd(Point newEnd) {
			this.end = newEnd;
		}
	}
}
