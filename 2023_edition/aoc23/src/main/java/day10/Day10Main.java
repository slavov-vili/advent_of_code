package day10;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;
import utils.DijkstraShortestPathFinder;
import utils.Grid2D;
import utils.ListUtils;
import utils.PointUtils;

public class Day10Main {

	public static final char VERTICAL = '|';
	public static final char HORIZONTAL = '-';
	public static final char UP_RIGHT = 'L';
	public static final char UP_LEFT = 'J';
	public static final char DOWN_LEFT = '7';
	public static final char DOWN_RIGHT = 'F';
	public static final char GROUND = '.';
	public static final char START = 'S';

	public static void main(String[] args) {
		var grid = parseGrid();
		Point startPos = grid.getPositionsWhere(ch -> ch.isStart()).get(0);
		var mainLoop = findMainLoop(startPos, grid);

		System.out.println("Most steps from start: " + solveA(mainLoop));
	}

	public static int solveA(List<Point> mainLoop) {
		var pathFinder = new DijkstraShortestPathFinder<Integer>(i -> ListUtils.getNeighborsOf(mainLoop, i));
		pathFinder.findAll(0);
		return IntStream.range(0, mainLoop.size()).map(pathFinder::getDistFromStart).max().getAsInt();
	}

	public static void solveB() {
	}

	public static List<Point> findMainLoop(Point startPos, Grid2D<Pipe> grid) {
		List<Point> mainLoop = new ArrayList<>();
		mainLoop.add(startPos);
		Point prevPos = startPos;
		Point curPos = grid.get(startPos).findValidConnections(grid).iterator().next();
		while (!curPos.equals(startPos)) {
			mainLoop.add(curPos);
			Point nextPos = grid.get(prevPos).followPipe(grid.get(curPos)).get(0);
			prevPos = curPos;
			curPos = nextPos;
		}
		return mainLoop;
	}

	record Pipe(char symbol, Point position) {
		public List<Point> followPipe(Pipe nextPipe) {
			if (!(this.connectsTo(nextPipe)))
				return List.of();

			return nextPipe.findConnections().stream().filter(pos -> !pos.equals(this.position)).toList();
		}

		public boolean connectsTo(Pipe otherPipe) {
			return this.findConnections().contains(otherPipe.position)
					&& otherPipe.findConnections().contains(this.position);
		}

		public Set<Point> findValidConnections(Grid2D<Pipe> grid) {
			return this.findConnections().stream()
					.filter(pos -> grid.hasPosition(pos) && this.connectsTo(grid.get(pos))).collect(Collectors.toSet());
		}

		public Set<Point> findConnections() {
			return switch (this.symbol) {
			case VERTICAL -> Set.of(PointUtils.UP.apply(this.position), PointUtils.DOWN.apply(this.position));
			case HORIZONTAL -> Set.of(PointUtils.LEFT.apply(this.position), PointUtils.RIGHT.apply(this.position));
			case UP_RIGHT -> Set.of(PointUtils.UP.apply(this.position), PointUtils.RIGHT.apply(this.position));
			case UP_LEFT -> Set.of(PointUtils.UP.apply(this.position), PointUtils.LEFT.apply(this.position));
			case DOWN_LEFT -> Set.of(PointUtils.DOWN.apply(this.position), PointUtils.LEFT.apply(this.position));
			case DOWN_RIGHT -> Set.of(PointUtils.DOWN.apply(this.position), PointUtils.RIGHT.apply(this.position));
			case START ->
				PointUtils.getDirections().stream().map(dir -> dir.apply(this.position)).collect(Collectors.toSet());
			default -> Set.of();
			};
		}

		public boolean isGround() {
			return this.symbol == GROUND;
		}

		public boolean isStart() {
			return this.symbol == START;
		}
	}

	public static Grid2D<Pipe> parseGrid() {
		List<String> input = AdventOfCodeUtils.readInput(Day10Main.class);
		var grid = new Grid2D<Pipe>(input.size(), input.get(0).length());
		for (int row = 0; row < input.size(); row++) {
			String line = input.get(row);
			for (int col = 0; col < line.length(); col++) {
				char symbol = line.charAt(col);
				Point position = new Point(row, col);
				grid.set(position, new Pipe(symbol, position));
			}
		}
		return grid;
	}
}
