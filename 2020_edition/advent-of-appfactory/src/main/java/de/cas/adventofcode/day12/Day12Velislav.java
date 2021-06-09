package de.cas.adventofcode.day12;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import de.cas.adventofcode.shared.Day;

public class Day12Velislav extends Day<Integer> {
	private static final char MOVE_N = 'N';
	private static final char MOVE_E = 'E';
	private static final char MOVE_S = 'S';
	private static final char MOVE_W = 'W';
	private static final char ROTATE_L = 'L';
	private static final char ROTATE_R = 'R';
	private static final char MOVE_F = 'F';
	private static final String PATTERN_VALUE = "([A-Z])([0-9]+)";
	private Map<Character, BiConsumer<MovingObject, Integer>> directionMovements;
	private Map<Character, Consumer<MovingObject>> directionRotations;
	private Map<Character, Consumer<MovingObject>> positionRotations;
	
	protected Day12Velislav() {
		super(12);
		this.directionMovements = getDirectionMovements();
		this.directionRotations = getDirectionRotations();
		this.positionRotations = getPositionRotations();
	}

	public static void main(final String[] args) {
		new Day12Velislav().run();
	}

	@Override
	public Integer solvePart1(List<String> input) {
		MovingObject ship = new MovingObject(new Point(0,0), Direction.E);
		
		return solvePart(input, this::handleAction1, ship);
	}
	
	private void handleAction1(Instruction instruction, MovingObject... objectsToMove) {
		MovingObject ship = objectsToMove[0];
		char action = instruction.getAction();
		int value = instruction.getValue();
		
		if (this.directionMovements.containsKey(action))
			this.directionMovements.get(action).accept(ship, value);
		
		else if (this.directionRotations.containsKey(action))
			for (int i=0; i<(value/90); i++)
				this.directionRotations.get(action).accept(ship);
		
		else if (action == MOVE_F)
			ship.moveForward(value);
	}

	@Override
	public Integer solvePart2(List<String> input) {
		MovingObject ship = new MovingObject(new Point(0, 0), Direction.E);
		MovingObject waypoint = new MovingObject(new Point(10, 1), Direction.E);
		
		return solvePart(input, this::handleAction2, ship, waypoint);
	}
	
	private void handleAction2(Instruction instruction, MovingObject... objectsToMove) {
		char action = instruction.getAction();
		int value = instruction.getValue();
		MovingObject ship = objectsToMove[0];
		MovingObject waypoint = objectsToMove[1];
		
		if (this.directionMovements.containsKey(action))
			this.directionMovements.get(action).accept(waypoint, value);
		
		else if (this.positionRotations.containsKey(action))
			for (int i=0; i<(value/90); i++)
				this.positionRotations.get(action).accept(waypoint);
		
		else if (action == MOVE_F)
			ship.moveN(value*waypoint.getPosition().x,
					   value*waypoint.getPosition().y);
	}
	
	private Integer solvePart(List<String> input, BiConsumer<Instruction, MovingObject[]> actionHandler,
			MovingObject... objectsToMove) {
		for (String line : input) {
			actionHandler.accept(new Instruction(line), objectsToMove);
		}
		
		MovingObject ship = objectsToMove[0];
		return Math.abs(ship.getPosition().x) + Math.abs(ship.getPosition().y);
	}
	
	private static Map<Character, BiConsumer<MovingObject, Integer>> getDirectionMovements() {
		Map<Character, BiConsumer<MovingObject, Integer>> directionMovements = new HashMap<>();
		directionMovements.put(MOVE_N, MovingObject::moveNorth);
		directionMovements.put(MOVE_E, MovingObject::moveEast);
		directionMovements.put(MOVE_S, MovingObject::moveSouth);
		directionMovements.put(MOVE_W, MovingObject::moveWest);
		return directionMovements;
	}
	
	private static Map<Character, Consumer<MovingObject>> getDirectionRotations() {
		Map<Character, Consumer<MovingObject>> directionRotations = new HashMap<>();
		directionRotations.put(ROTATE_L, MovingObject::rotateDirectionLeft);
		directionRotations.put(ROTATE_R, MovingObject::rotateDirectionRight);
		return directionRotations;
	}
	
	private static Map<Character, Consumer<MovingObject>> getPositionRotations() {
		Map<Character, Consumer<MovingObject>> positionRotations = new HashMap<>();
		positionRotations.put(ROTATE_L, MovingObject::rotatePositionLeft);
		positionRotations.put(ROTATE_R, MovingObject::rotatePositionRight);
		return positionRotations;
	}
	
	private class Instruction {
		private char action;
		private int value;

		Instruction(String strForm) {
			this.action = strForm.replaceAll(PATTERN_VALUE, "$1").charAt(0);
			this.value = Integer.parseInt(strForm.replaceAll(PATTERN_VALUE, "$2"));
		}
		
		public char getAction() {
			return this.action;
		}
		
		public int getValue() {
			return this.value;
		}
	}

}
