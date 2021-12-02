package day15;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import day02.Day02Main;
import day09.Day09Main;
import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;

public class Day15Main {
	public static void main(String[] args) throws InvalidArgumentException, InvalidIntCodeException {
		System.out.println(solveA());
	}

	public static int solveA() throws InvalidArgumentException, InvalidIntCodeException {
		Set<Point> stepsAlongLeftWall = findStepsToOxygenSystem(RepairDroid.NEXT_LOOKING_DIRECTION,
				RepairDroid.NEXT_MOVEMENT_DIRECTION);
		Set<Point> stepsAlongRightWall = findStepsToOxygenSystem(RepairDroid.NEXT_MOVEMENT_DIRECTION,
				RepairDroid.NEXT_LOOKING_DIRECTION);
		return Math.min(stepsAlongLeftWall.size(), stepsAlongRightWall.size());
	}

	public static Set<Point> findStepsToOxygenSystem(Map<String, String> directionMovementMap,
			Map<String, String> directionLookingMap) throws InvalidArgumentException, InvalidIntCodeException {
		RepairDroid droid = getDroid();
		Point prevPosition = droid.getPosition();
		String direction = RepairDroid.DIRECTION_NORTH;
		Set<Point> steps = new HashSet<>();

		do {
			droid.giveDirection(direction);
//			System.out.println("Maze:");
//			System.out.println(droid.getRoomLayout());
//			System.out.println();

			if (!droid.justHitWall())
				if (steps.contains(droid.getPosition()))
					steps.remove(prevPosition);
				else
					steps.add(prevPosition);

			prevPosition = droid.getPosition();
			
			direction = calcNextDirection(droid, direction, directionMovementMap, directionLookingMap);

//		} while (!RepairDroid.ORIGIN.equals(droid.getPosition()));
		} while (droid.getOxygenSystemPosition().isEmpty());

		for (Point step : steps) {
			droid.updateGrid(step, RepairDroid.DROID_CHAR);
		}
		
		return steps;
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

	public static RepairDroid getDroid() throws InvalidArgumentException {
		return new RepairDroid(Day09Main.getComputer(getInput()));
	}

	protected static List<Long> getInput() {
		return Day02Main.getInput(Day15Main.class);
	}
}
