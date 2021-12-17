package day17;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.AdventOfCodeUtils;
import utils.IntegerUtils;

public class Day17Main {

	public static Pattern INPUT_PATTERN = Pattern.compile("target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)");
	public static Point targetStart;
	public static Point targetEnd;
	public static final Point START_POINT = new Point(0, 0);

	public static void main(String[] args) {
		String input = AdventOfCodeUtils.readInput(Day17Main.class).get(0);
		targetStart = extractTargetStart(input);
		targetEnd = extractTargetEnd(input);
		
		Map<Point, Integer> validVelocitiesMap = findValidInitialVelocities();
		int maxY = validVelocitiesMap.keySet().stream().mapToInt(validVelocitiesMap::get).max().getAsInt();
		System.out.println("Maximum Y with optimal initial velocity: " + maxY);
		System.out.println("Valid initial velocity count: " + validVelocitiesMap.size());
	}

	public static Map<Point, Integer> findValidInitialVelocities() {
		Map<Point, Integer> validVelocitiesMap = new HashMap<>();
		for (int x = 0; x <= targetEnd.x; x++) {
			for (int y = targetEnd.y; y <= Math.abs(targetEnd.y); y++) {
				Point curPosition = new Point(START_POINT);
				Point initialVelocity = new Point(x, y);
				Point curVelocity = new Point(initialVelocity);
				int curMaxY = Integer.MIN_VALUE;
				while (!isPastTarget(curPosition)) {
					curPosition.translate(curVelocity.x, curVelocity.y);
					curMaxY = Math.max(curMaxY, curPosition.y);
					
					if (isWithinTarget(curPosition)) {
						validVelocitiesMap.put(initialVelocity, curMaxY);
					}
					
					curVelocity = new Point(curVelocity.x - IntegerUtils.compareInts(curVelocity.x, 0),
							curVelocity.y - 1);
				}
			}
		}

		return validVelocitiesMap;
	}

	public static boolean isWithinTarget(Point position) {
		boolean isWithinX = IntegerUtils.integerIsWithinRange(position.x, targetStart.x, targetEnd.x);
		boolean isWithinY = IntegerUtils.integerIsWithinRange(position.y, targetEnd.y, targetStart.y);
		return isWithinX && isWithinY;
	}

	public static boolean isPastTarget(Point position) {
		boolean isPastX = position.x > targetEnd.x;
		boolean isPastY = position.y < targetEnd.y;
		return isPastX || isPastY;
	}

	public static final Point extractTargetStart(String input) {
		Matcher matcher = INPUT_PATTERN.matcher(input);
		matcher.find();
		int x = Integer.parseInt(matcher.group(1));
		int y = Integer.parseInt(matcher.group(4));

		return new Point(x, y);
	}

	public static final Point extractTargetEnd(String input) {
		Matcher matcher = INPUT_PATTERN.matcher(input);
		matcher.find();
		int x = Integer.parseInt(matcher.group(2));
		int y = Integer.parseInt(matcher.group(3));

		return new Point(x, y);
	}
}
