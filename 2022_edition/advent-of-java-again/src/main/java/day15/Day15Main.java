package day15;

import java.awt.Point;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.AdventOfCodeUtils;
import utils.IntegerUtils;
import utils.PointUtils;

public class Day15Main {
	public static final Pattern SENSOR_DATA = Pattern
			.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
	public static final int RELEVANT_ROW = 2000000;

	public static void main(String[] args) {
		var sensors = parseInput();

		System.out.printf("Positions of non-beacons at row %d: %d\n", RELEVANT_ROW, solveA(sensors, RELEVANT_ROW));
	}

	public static int solveA(List<Sensor> sensors, int relevantRow) {
		var sensorsCloseToRelevantRow = sensors.stream().filter(s -> s.isCloseToRow(relevantRow)).toList();
		var minX = sensorsCloseToRelevantRow.stream().min((s1, s2) -> Integer.compare(s1.position().x, s2.position().x))
				.map(s -> s.position().x - s.calcDistToBeacon()).get();
		var maxX = sensorsCloseToRelevantRow.stream().max((s1, s2) -> Integer.compare(s1.position().x, s2.position().x))
				.map(s -> s.position().x + s.calcDistToBeacon()).get();
		var notABeaconCount = 0;
		for (int x = minX; x <= maxX; x++) {
			var curPosition = new Point(x, relevantRow);
			var allWhoSeeSayYes = sensorsCloseToRelevantRow.stream().map(s -> s.canBeUnknownBeacon(curPosition))
					.filter(result -> result.isPresent() && result.get()).count() != 0;
			if (allWhoSeeSayYes)
				notABeaconCount++;
		}
		return notABeaconCount;
	}

	public static void solveB() {
	}

	public record Sensor(Point position, Point beaconPosition) {
		public boolean isCloseToRow(int row) {
			var distToBeacon = this.calcDistToBeacon();
			return IntegerUtils.integerIsWithinRange(row, this.position.y - distToBeacon,
					this.position.y + distToBeacon);
		}

		public Optional<Boolean> canBeUnknownBeacon(Point positionToTest) {
			if (this.isOutOfRange(positionToTest))
				return Optional.empty();

			return Optional.of(!(positionToTest.equals(this.position) || positionToTest.equals(this.beaconPosition)));
		}

		public boolean isOutOfRange(Point positionToTest) {
			return this.calcDistTo(positionToTest) > this.calcDistToBeacon();
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
