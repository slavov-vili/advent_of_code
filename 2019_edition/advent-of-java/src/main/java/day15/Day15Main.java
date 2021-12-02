package day15;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import day02.Day02Main;
import day09.Day09Main;
import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;

public class Day15Main {
	public static void main(String[] args) throws InvalidArgumentException, InvalidIntCodeException {
		System.out.printf("The droid needed %d steps to reach the oxygen system.\n", solveA());

		System.out.printf("It took %d minutes for the oxygen to fill the ship.\n", solveB());
	}

	public static int solveA() throws InvalidArgumentException, InvalidIntCodeException {
		Set<Point> stepsAlongLeftWall = findShortestPath(getDroid(), Day15Main::findOxygenSystemPosition,
				RepairDroid.NEXT_LOOKING_DIRECTION, RepairDroid.NEXT_MOVEMENT_DIRECTION);
		Set<Point> stepsAlongRightWall = findShortestPath(getDroid(), Day15Main::findOxygenSystemPosition,
				RepairDroid.NEXT_MOVEMENT_DIRECTION, RepairDroid.NEXT_LOOKING_DIRECTION);
		return Math.min(stepsAlongLeftWall.size(), stepsAlongRightWall.size());
	}

	public static int solveB() throws InvalidArgumentException, InvalidIntCodeException {
		RepairDroid droid = getDroid();
		Set<Point> stepsToWalkAroundTheShip = findShortestPath(droid, Day15Main::exploreShip,
				RepairDroid.NEXT_LOOKING_DIRECTION, RepairDroid.NEXT_MOVEMENT_DIRECTION);

		Set<Point> openPositions = droid.getOpenPositions();
		Collection<Point> nextPositions = calcAdjacentPositions(droid.getOxygenSystemPosition().get(), openPositions);
		return countMinutesUntilOxygen(nextPositions, openPositions);
	}

	public static int countMinutesUntilOxygen(Collection<Point> nextPositions, Set<Point> openPositions) {
		if (nextPositions.size() == 0)
			return 0;
		else {
			Collection<Point> nextNextPositions = nextPositions.stream()
					.flatMap(position -> calcAdjacentPositions(position, openPositions).stream())
					.collect(Collectors.toList());
			Set<Point> nextOpenPositions = new HashSet<>(openPositions);
			nextOpenPositions.removeAll(nextPositions);
			return countMinutesUntilOxygen(nextNextPositions, nextOpenPositions) + 1;
		}
	}

	public static Set<Point> findShortestPath(RepairDroid droid, Predicate<RepairDroid> endCondition,
			Map<String, String> directionMovementMap, Map<String, String> directionLookingMap)
			throws InvalidArgumentException, InvalidIntCodeException {
		Point prevPosition = droid.getPosition();
		String direction = RepairDroid.DIRECTION_NORTH;
		Set<Point> steps = new HashSet<>();

		do {
			droid.giveDirection(direction);

			if (!droid.justHitWall())
				if (steps.contains(droid.getPosition()))
					steps.remove(prevPosition);
				else
					steps.add(prevPosition);

			prevPosition = droid.getPosition();

			direction = calcNextDirection(droid, direction, directionMovementMap, directionLookingMap);

		} while (!endCondition.test(droid));

		for (Point step : steps) {
			droid.updateGrid(step, RepairDroid.DROID_CHAR);
		}

		System.out.println("Maze:");
		System.out.println(droid.getRoomLayout());
		System.out.println();

		return steps;
	}

	public static Collection<Point> calcAdjacentPositions(Point curPosition, Set<Point> openPositions) {
		return RepairDroid.NEXT_MOVEMENT_DIRECTION.keySet().stream()
				.map(direction -> RepairDroid.calcNewPosition(curPosition, direction)).filter(openPositions::contains)
				.collect(Collectors.toList());
	}

	public static String calcNextDirection(RepairDroid droid, String oldDirection,
			Map<String, String> directionMovementMap, Map<String, String> directionLookingMap) {
		String newDirection = oldDirection;
		if (droid.justHitWall()) {
			newDirection = directionMovementMap.get(oldDirection);
		} else {
			newDirection = directionLookingMap.get(oldDirection);
		}

		return newDirection;
	}

	public static boolean findOxygenSystemPosition(RepairDroid droid) {
		return droid.getOxygenSystemPosition().isPresent();
	}

	public static boolean exploreShip(RepairDroid droid) {
		return RepairDroid.ORIGIN.equals(droid.getPosition());
	}

	public static RepairDroid getDroid() throws InvalidArgumentException {
		return new RepairDroid(Day09Main.getComputer(getInput()));
	}

	protected static List<Long> getInput() {
		return Day02Main.getInput(Day15Main.class);
	}
}
