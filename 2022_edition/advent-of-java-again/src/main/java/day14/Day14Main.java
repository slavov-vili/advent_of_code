package day14;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import utils.AdventOfCodeUtils;
import utils.PointUtils;

public class Day14Main {
	public static final Point NEW_SAND = new Point(500, 0);

	public static void main(String[] args) {
		var blockedTiles = parseInput();

		System.out.println("Sands at rest before all in Abyss: " + solveA(blockedTiles));

		System.out.println("Sands at rest if floor: " + solveB(blockedTiles));
	}

	public static int solveA(Set<Point> blockedPoints) {
		var curBlockedPoints = new HashSet<>(blockedPoints);
		var xToLowestBlocked = mapXToLowestBlocked(blockedPoints);
		var curPosition = NEW_SAND;
		int sandsAtRest = 0;

		while (!isGoingToAbyss(curPosition, xToLowestBlocked)) {
			var nextPosition = new Point(curPosition.x, curPosition.y + 1);
			if (curBlockedPoints.contains(nextPosition)) {
				nextPosition = new Point(curPosition.x - 1, curPosition.y + 1);

				if (curBlockedPoints.contains(nextPosition)) {
					nextPosition = new Point(curPosition.x + 1, curPosition.y + 1);

					if (curBlockedPoints.contains(nextPosition)) {
						curBlockedPoints.add(curPosition);
						sandsAtRest++;
						nextPosition = NEW_SAND;
					}
				}
			}
			curPosition = nextPosition;
		}

		return sandsAtRest;
	}

	public static int solveB(Set<Point> blockedPoints) {
		var curBlockedPoints = new HashSet<>(blockedPoints);
		var floorLevel = mapXToLowestBlocked(blockedPoints).values().stream().mapToInt(x -> x).max().getAsInt() + 2;
		var curPosition = NEW_SAND;
		int sandsAtRest = 0;

		while (!curBlockedPoints.contains(NEW_SAND)) {
			var nextPosition = new Point(curPosition.x, curPosition.y + 1);

			if (curBlockedPoints.contains(nextPosition) || nextPosition.y == floorLevel) {
				nextPosition = new Point(curPosition.x - 1, curPosition.y + 1);

				if (curBlockedPoints.contains(nextPosition) || nextPosition.y == floorLevel) {
					nextPosition = new Point(curPosition.x + 1, curPosition.y + 1);

					if (curBlockedPoints.contains(nextPosition) || nextPosition.y == floorLevel) {
						curBlockedPoints.add(curPosition);
						sandsAtRest++;
						nextPosition = NEW_SAND;
					}
				}
			}
			curPosition = nextPosition;
		}

		return sandsAtRest;
	}

	public static Map<Integer, Integer> mapXToLowestBlocked(Set<Point> blockedPoints) {
		var lowestBlocked = new HashMap<Integer, Integer>();
		for (Point curPoint : blockedPoints) {
			if (curPoint.y > lowestBlocked.getOrDefault(curPoint.x, -1))
				lowestBlocked.put(curPoint.x, curPoint.y);
		}
		return lowestBlocked;
	}

	public static boolean isGoingToAbyss(Point curPosition, Map<Integer, Integer> lowestBlocked) {
		return curPosition.y >= lowestBlocked.getOrDefault(curPosition.x, -1);
	}

	public static Set<Point> parseInput() {
		List<String> input = AdventOfCodeUtils.readInput(Day14Main.class);
		return input.stream().flatMap(Day14Main::parseLine).collect(Collectors.toSet());
	}

	public static Stream<Point> parseLine(String line) {
		var blockedTiles = new HashSet<Point>();
		var parts = line.split(" -> ");
		for (int i = 0; i < parts.length - 1; i++) {
			var a = parts[i].split(",");
			var b = parts[i + 1].split(",");
			blockedTiles.addAll(
					PointUtils.generatePointsInAreaInclusive(new Point(Integer.parseInt(a[0]), Integer.parseInt(a[1])),
							new Point(Integer.parseInt(b[0]), Integer.parseInt(b[1]))));
		}
		return blockedTiles.stream();
	}
}
