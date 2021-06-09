package de.cas.adventofcode.day12;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MovingObject {

	private Point position;
	private Direction direction;
	private Map<Direction, Function<Integer, Point>> directionToMoveFunction;

	public MovingObject(Point initialPosition, Direction initialDirection) {
		this.position = new Point(initialPosition);
		this.direction = initialDirection;
		this.directionToMoveFunction = mapDirectionsToMoveFunctions();
	}

	public Point moveForward(int distance) {
		return this.directionToMoveFunction.get(this.direction).apply(distance);
	}
	
	public Point moveNorth(int distance) {
		return this.moveN(0, distance);
	}
	
	public Point moveEast(int distance) {
		return this.moveN(distance, 0);
	}
	
	public Point moveSouth(int distance) {
		return this.moveN(0, -distance);
	}
	
	public Point moveWest(int distance) {
		return this.moveN(-distance, 0);
	}
	
	public Point moveN(int dx, int dy) {
		this.position.translate(dx, dy);
		return this.position;
	}
	
	public void rotateDirectionRight() {
		this.direction = Direction.getNextRight(this.direction);
	}

	public void rotateDirectionLeft() {
		this.direction = Direction.getNextLeft(this.direction);
	}
	
	public void rotatePositionRight() {
		int curX = this.position.x;
		int curY = this.position.y;
		this.position.setLocation(new Point(curY, -curX));
	}
	
	public void rotatePositionLeft() {
		int curX = this.position.x;
		int curY = this.position.y;
		this.position.setLocation(new Point(-curY, curX));
	}
	
	public Point getPosition() {
		return this.position;
	}
	
	private Map<Direction, Function<Integer, Point>> mapDirectionsToMoveFunctions() {
		Map<Direction, Function<Integer, Point>> dirToMoveFunc = new HashMap<>();
		dirToMoveFunc.put(Direction.N, this::moveNorth);
		dirToMoveFunc.put(Direction.E, this::moveEast);
		dirToMoveFunc.put(Direction.S, this::moveSouth);
		dirToMoveFunc.put(Direction.W, this::moveWest);
		return dirToMoveFunc;
	}
	
}
