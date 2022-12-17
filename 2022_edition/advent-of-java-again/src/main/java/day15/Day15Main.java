package day15;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.IntegerUtils;
import utils.PointUtils;

public class Day15Main {
	public static final Pattern SENSOR_DATA = Pattern
			.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
	public static final int RELEVANT_ROW = 2000000;
	public static final int GRID_SIZE = 4000000;

	public static void main(String[] args) {
		var sensors = parseInput();

		System.out.printf("Positions of non-beacons at row %d: %d\n", RELEVANT_ROW, solveA(sensors));

		System.out.println("Position of distress beacon: " + solveB(sensors));
	}

	public static int solveA(List<Sensor> sensors) {
		var sensorsCloseToRelevantRow = sensors.stream().filter(s -> s.isCloseToRow(RELEVANT_ROW)).toList();
		var intervalsInRelevantRow = sensorsCloseToRelevantRow.stream().map(s -> s.getIntervalForRow(RELEVANT_ROW))
				.collect(Collectors.toList());
		intervalsInRelevantRow.sort((i1, i2) -> Integer.compare(i1.x, i2.x));
		intervalsInRelevantRow = removeOverlaps(intervalsInRelevantRow);
		return intervalsInRelevantRow.stream().mapToInt(i -> Math.abs(i.x - i.y) + 1).sum()
				- getSortedOccupiedPositionsPerRow(sensors).getOrDefault(RELEVANT_ROW, new TreeSet<Integer>()).size();
	}

	public static long solveB(List<Sensor> sensors) {
		var intervalsPerRow = sensors.stream().map(s -> wrapRowIntervalMap(s.getRowIntervals()))
				.reduce(Day15Main::mergeRowIntervalMaps).get();
		Point distressBeaconPosition = findDistressBeacon(intervalsPerRow);
		return ((long) GRID_SIZE) * distressBeaconPosition.x + distressBeaconPosition.y;
	}

	public static List<Point> removeOverlaps(List<Point> sortedIntervals) {
		var sortedIntervalsWithoutOverlaps = new ArrayList<Point>();
		Point curInterval = sortedIntervals.get(0);
		for (int i = 1; i < sortedIntervals.size(); i++) {
			Point nextInterval = sortedIntervals.get(i);

			// beginnings overlap
			if (IntegerUtils.integerIsWithinRange(nextInterval.x, curInterval.x, curInterval.y)) {
				// ends don't overlap
				if (!IntegerUtils.integerIsWithinRange(nextInterval.y, curInterval.x, curInterval.y)) {
					curInterval = new Point(curInterval.x, nextInterval.y);
				}
			} else {
				sortedIntervalsWithoutOverlaps.add(curInterval);
				curInterval = nextInterval;
			}
		}

		sortedIntervalsWithoutOverlaps.add(curInterval);
		return sortedIntervalsWithoutOverlaps;
	}

	public static Map<Integer, TreeSet<Integer>> getSortedOccupiedPositionsPerRow(List<Sensor> sensors) {
		var sortedOccupiedPositionsPerRow = new HashMap<Integer, TreeSet<Integer>>();
		for (Sensor sensor : sensors) {
			int row = sensor.position().y;
			var occupiedPositions = sortedOccupiedPositionsPerRow.getOrDefault(row, new TreeSet<Integer>());
			occupiedPositions.add(sensor.position().x);
			sortedOccupiedPositionsPerRow.put(row, occupiedPositions);

			row = sensor.beaconPosition().y;
			occupiedPositions = sortedOccupiedPositionsPerRow.getOrDefault(row, new TreeSet<Integer>());
			occupiedPositions.add(sensor.beaconPosition().x);
			sortedOccupiedPositionsPerRow.put(row, occupiedPositions);
		}
		return sortedOccupiedPositionsPerRow;
	}

	public static Map<Integer, List<Point>> wrapRowIntervalMap(Map<Integer, Point> rowIntervalMap) {
		return rowIntervalMap.entrySet().stream().collect(
				Collectors.toMap(entry -> entry.getKey(), entry -> new ArrayList<Point>(List.of(entry.getValue()))));
	}

	public static Map<Integer, List<Point>> mergeRowIntervalMaps(Map<Integer, List<Point>> rowIntervalsMap1,
			Map<Integer, List<Point>> rowIntervalsMap2) {
		var mergedRowIntervalsMap = new HashMap<Integer, List<Point>>(rowIntervalsMap1);
		for (int row : rowIntervalsMap2.keySet()) {
			var intervals = mergedRowIntervalsMap.getOrDefault(row, new ArrayList<Point>());
			intervals.addAll(rowIntervalsMap2.get(row));
			mergedRowIntervalsMap.put(row, intervals);
		}
		return mergedRowIntervalsMap;
	}

	public static Point findDistressBeacon(Map<Integer, List<Point>> rowIntervals) {
		for (int row : rowIntervals.keySet()) {
			var intervals = rowIntervals.get(row);
			intervals.sort((i1, i2) -> Integer.compare(i1.x, i2.x));
			intervals = removeOverlaps(intervals);
			var positionsBeforeInterval = intervals.stream().map(i -> i.x - 1).collect(Collectors.toSet());
			for (Point interval : intervals) {
				int positionAfterInterval = interval.y + 1;
				if (positionsBeforeInterval.contains(positionAfterInterval)
						&& IntegerUtils.integerIsWithinRange(row, 0, GRID_SIZE)
						&& IntegerUtils.integerIsWithinRange(positionAfterInterval, 0, GRID_SIZE)) {
					return new Point(positionAfterInterval, row);
				}
			}
		}

		return new Point();
	}

	public record Sensor(Point position, Point beaconPosition) {
		public Map<Integer, Point> getRowIntervals() {
			var rowToIntervals = new HashMap<Integer, Point>();

			for (int i = 0; i <= this.calcDistToBeacon(); i++) {
				int topRow = this.position.y - i;
				int botRow = this.position.y + i;

				rowToIntervals.put(topRow, this.getIntervalForRow(topRow));
				if (topRow != botRow)
					rowToIntervals.put(botRow, this.getIntervalForRow(botRow));
			}
			System.out.println("Generated row intervals for " + this);
			return rowToIntervals;
		}

		public Point getIntervalForRow(int row) {
			int distToRow = this.calcDistTo(new Point(this.position.x, row));
			int distToBeacon = this.calcDistToBeacon();
			int startCol = this.position.x - distToBeacon + distToRow;
			int endCol = this.position.x + distToBeacon - distToRow;
			return new Point(startCol, endCol);
		}

		public boolean isCloseToRow(int row) {
			int distToBeacon = this.calcDistToBeacon();
			return IntegerUtils.integerIsWithinRange(row, this.position.y - distToBeacon,
					this.position.y + distToBeacon);
		}

		public int calcDistToBeacon() {
			return this.calcDistTo(this.beaconPosition);
		}

		public int calcDistTo(Point otherPosition) {
			return PointUtils.calcManhattanDistance(position, otherPosition);
		}

		public static Sensor fromLine(String line) {
			Matcher matcher = SENSOR_DATA.matcher(line);
			matcher.matches();
			int sensorX = Integer.parseInt(matcher.group(1));
			int sensorY = Integer.parseInt(matcher.group(2));
			int beaconX = Integer.parseInt(matcher.group(3));
			int beaconY = Integer.parseInt(matcher.group(4));
			return new Sensor(new Point(sensorX, sensorY), new Point(beaconX, beaconY));
		}

	}

	public static List<Sensor> parseInput() {
		return AdventOfCodeUtils.readInput(Day15Main.class).stream().map(Sensor::fromLine).toList();
	}
}
