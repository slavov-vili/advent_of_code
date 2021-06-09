package de.cas.adventofcode.day12;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
	N,
	E,
	S,
	W;
	
	private static final Map<Direction, Direction> rightOrder = getOrderRight();
	private static final Map<Direction, Direction> leftOrder = getOrderLeft();
	
	public static Direction getNextRight(Direction curDirection) {
		return rightOrder.get(curDirection);
	}
	
	public static Direction getNextLeft(Direction curDirection) {
		return leftOrder.get(curDirection);
	}

	private static Map<Direction, Direction> getOrderRight() {
		Map<Direction, Direction> orderRight = new HashMap<>();
		orderRight.put(N, E);
		orderRight.put(E, S);
		orderRight.put(S, W);
		orderRight.put(W, N);
		return orderRight;
	}
	
	private static Map<Direction, Direction> getOrderLeft() {
		Map<Direction, Direction> orderLeft = new HashMap<>();
		orderLeft.put(N, W);
		orderLeft.put(W, S);
		orderLeft.put(S, E);
		orderLeft.put(E, N);
		return orderLeft;
	}
}
