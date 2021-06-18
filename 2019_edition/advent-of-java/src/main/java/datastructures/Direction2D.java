package datastructures;

import java.awt.Point;
import java.util.List;

public class Direction2D {
	public static final Point NORTH = new Point(0, 1);
	public static final Point WEST  = new Point(1, 0);
	public static final Point SOUTH = new Point(0, -1);
	public static final Point EAST  = new Point(-1, 0);
	
	public static final List<Point> order = List.of(NORTH, WEST, SOUTH, EAST);
	
	public static Point getNextRight(Point curDirection) {
		return getNext(curDirection, 1);
	}
	
	public static Point getNextLeft(Point curDirection) {
		return getNext(curDirection, -1);
	}
	
	private static Point getNext(Point curDirection, int increment) {
		int curIndex = order.indexOf(curDirection);
		int nextIndex = Math.floorMod(curIndex + increment, order.size());
		return order.get(nextIndex);
	}
}
