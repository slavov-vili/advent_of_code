package day10;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
		var mainLoop = findMainLoop(grid.get(startPos), grid);
		printLoop(mainLoop, grid);

		System.out.println("Most steps from start: " + solveA(mainLoop));

		System.out.println("Enclosed tiles: " + solveB(mainLoop));
	}

	public static long solveA(List<Pipe> mainLoop) {
		var pathFinder = new DijkstraShortestPathFinder<Integer>(i -> ListUtils.getNeighborsOf(mainLoop, i));
		pathFinder.findAll(0);
		return IntStream.range(0, mainLoop.size()).mapToLong(pathFinder::getDistFromStart).max().getAsLong();
	}

	public static int solveB(List<Pipe> mainLoop) {
		return findEnclosedTiles(mainLoop).size();
	}

	public static List<Pipe> findMainLoop(Pipe startPipe, Grid2D<Pipe> grid) {
		List<Pipe> mainLoop = new ArrayList<>();
		mainLoop.add(startPipe);
		Pipe prevPipe = startPipe;
		Pipe curPipe = startPipe.findValidConnections(grid).iterator().next();
		while (!curPipe.equals(startPipe)) {
			mainLoop.add(prevPipe);
			Point nextPos = prevPipe.followPipe(curPipe).get(0);
			prevPipe = curPipe;
			curPipe = grid.get(nextPos);
		}
		return mainLoop;
	}

	// FIXME: find something else (e.x. all surrounding must be part of path or also
	// inside)
	public static Set<Point> findEnclosedTiles(List<Pipe> mainLoop) {
		Set<Point> encosedTiles = new HashSet<>();

		var loopPositions = mainLoop.stream().map(Pipe::position).collect(Collectors.toSet());
		int minRow = loopPositions.stream().mapToInt(pos -> pos.x).min().getAsInt();
		int maxRow = loopPositions.stream().mapToInt(pos -> pos.x).max().getAsInt();
		int minCol = loopPositions.stream().mapToInt(pos -> pos.y).min().getAsInt();
		int maxCol = loopPositions.stream().mapToInt(pos -> pos.y).max().getAsInt();
		int row = minRow;
		while (row <= maxRow) {
			int col = minCol;
			int pathGroups = 0;
			boolean groupStarted = false;
			while (col <= maxCol) {
				Point curPos = new Point(row, col);
				if (loopPositions.contains(curPos)) {
					if (!groupStarted) {
						groupStarted = true;
						pathGroups++;
					}
				} else {
					if (groupStarted)
						groupStarted = false;
					if (pathGroups % 2 == 1)
						encosedTiles.add(curPos);
				}
				col++;
			}
			row++;
		}
		return encosedTiles;
	}

	record Pipe(char symbol, Point position) {
		public List<Point> followPipe(Pipe nextPipe) {
			if (!(this.connectsTo(nextPipe)))
				return List.of();

			return nextPipe.findConnectionPositions().stream().filter(pos -> !pos.equals(this.position)).toList();
		}

		public boolean connectsTo(Pipe otherPipe) {
			return this.findConnectionPositions().contains(otherPipe.position)
					&& otherPipe.findConnectionPositions().contains(this.position);
		}

		public Set<Pipe> findValidConnections(Grid2D<Pipe> grid) {
			return this.findConnectionPositions().stream()
					.filter(pos -> grid.hasPosition(pos) && this.connectsTo(grid.get(pos))).map(grid::get)
					.collect(Collectors.toSet());
		}

		public Set<Point> findConnectionPositions() {
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

	public static void printLoop(List<Pipe> loop, Grid2D<Pipe> grid) {
		int minX = loop.stream().mapToInt(pipe -> pipe.position.x).min().getAsInt();
		int maxX = loop.stream().mapToInt(pipe -> pipe.position.x).max().getAsInt();
		int minY = loop.stream().mapToInt(pipe -> pipe.position.y).min().getAsInt();
		int maxY = loop.stream().mapToInt(pipe -> pipe.position.y).max().getAsInt();
		for (int row = minX; row <= maxX; row++) {
			for (int col = minY; col <= maxY; col++) {
				Point pos = new Point(row, col);
				char symbol = grid.hasPosition(pos) ? grid.get(pos).symbol() : GROUND;
				System.out.print(symbol);
			}
			System.out.println();
		}
	}
}
