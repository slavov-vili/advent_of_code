package day15;

import java.awt.Point;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import datastructures.Grid2DInfinite;
import datastructures.IGrid2D;
import day09.IntCodeComputer9;
import exceptions.InvalidIntCodeException;

public class RepairDroid {
	public static final int WALL_DIGIT = 0;
	public static final int TRAVERSEBLE_DIGIT = 1;
	public static final int OXYGEN_SYSTEM_DIGIT = 2;

	public static final char EMPTY_CHAR = ' ';
	public static final char WALL_CHAR = '#';
	public static final char TRAVERSEBLE_CHAR = '.';
	public static final char DROID_CHAR = 'D';
	public static final char OXYGEN_SYSTEM_CHAR = 'X';
	public static final char ORIGIN_CHAR = 'S';

	public static final Point ORIGIN = new Point();

	public static final String DIRECTION_NORTH = "1";
	public static final String DIRECTION_SOUTH = "2";
	public static final String DIRECTION_WEST = "3";
	public static final String DIRECTION_EAST = "4";

	public static final Map<String, String> NEXT_MOVEMENT_DIRECTION = generateDirectionMovementOrder();
	public static final Map<String, String> NEXT_LOOKING_DIRECTION = generateDirectionLookingOrder();

	private IntCodeComputer9 computer;
	private IGrid2D<Character> grid;
	private Point position;
	private Optional<Point> oxygenSystemPosition;
	private int lastResponse;

	public RepairDroid(IntCodeComputer9 computer) {
		this.computer = computer;
		this.grid = new Grid2DInfinite<>(EMPTY_CHAR);
		position = ORIGIN;
		updateGrid(ORIGIN, 'S');
		oxygenSystemPosition = Optional.empty();
		lastResponse = 0;
	}

	public String giveDirection(String direction) throws InvalidIntCodeException {
		Writer outputWriter = new StringWriter();
		Reader inputReader = new StringReader(direction);

		computer.run(inputReader, outputWriter);

		Point newPosition = calcNewPosition(direction);
		lastResponse = Integer.parseInt(outputWriter.toString().replaceAll("\n", ""));

		this.updateState(newPosition);
		return this.getRoomLayout();
	}

	public Point calcNewPosition(String direction) {
		return calcNewPosition(this.position, direction);
	}
	
	public static Point calcNewPosition(Point curPosition, String direction) {
		int newX = curPosition.x;
		int newY = curPosition.y;

		if (DIRECTION_NORTH.equals(direction)) {
			newY -= 1;
		} else if (DIRECTION_SOUTH.equals(direction)) {
			newY += 1;
		} else if (DIRECTION_WEST.equals(direction)) {
			newX -= 1;
		} else {
			newX += 1;
		}

		return new Point(newX, newY);
	}

	public void updateState(Point positionToUpdate) {
		if (WALL_DIGIT == lastResponse) {
			updateGrid(positionToUpdate, WALL_CHAR);
		} else if (TRAVERSEBLE_DIGIT == lastResponse) {
			updateGrid(this.position, TRAVERSEBLE_CHAR);
			updateGrid(positionToUpdate, DROID_CHAR);
			this.position = positionToUpdate;
		} else {
			this.oxygenSystemPosition = Optional.of(positionToUpdate);
			updateGrid(positionToUpdate, OXYGEN_SYSTEM_CHAR);
			this.position = positionToUpdate;
		}
	}

	public void updateGrid(Point positionToUpdate, char newChar) {
		if (ORIGIN.equals(positionToUpdate))
			newChar = ORIGIN_CHAR;
		else if (this.oxygenSystemPosition.isPresent() && this.oxygenSystemPosition.get().equals(positionToUpdate))
			newChar = OXYGEN_SYSTEM_CHAR;

		this.grid.set(positionToUpdate, newChar);
	}

	public Set<Point> getOpenPositions() {
		return new HashSet<>(this.grid.getPositionsWhere(character -> WALL_CHAR != character));
	}

	private static Map<String, String> generateDirectionMovementOrder() {
		Map<String, String> directionOrder = new HashMap<>();
		directionOrder.put(DIRECTION_NORTH, DIRECTION_WEST);
		directionOrder.put(DIRECTION_WEST, DIRECTION_SOUTH);
		directionOrder.put(DIRECTION_SOUTH, DIRECTION_EAST);
		directionOrder.put(DIRECTION_EAST, DIRECTION_NORTH);
		return directionOrder;
	}

	private static Map<String, String> generateDirectionLookingOrder() {
		Map<String, String> directionOrder = new HashMap<>();
		directionOrder.put(DIRECTION_NORTH, DIRECTION_EAST);
		directionOrder.put(DIRECTION_EAST, DIRECTION_SOUTH);
		directionOrder.put(DIRECTION_SOUTH, DIRECTION_WEST);
		directionOrder.put(DIRECTION_WEST, DIRECTION_NORTH);
		return directionOrder;
	}

	public Optional<Point> getOxygenSystemPosition() {
		return this.oxygenSystemPosition;
	}

	public String getRoomLayout() {
		return this.grid.toString();
	}

	public Point getPosition() {
		return this.position;
	}

	public boolean justHitWall() {
		return WALL_DIGIT == this.lastResponse;
	}

	public boolean computerIsHalted() {
		return this.computer.isHalted();
	}

	public boolean computerIsWaitingForInput() {
		return this.computer.isWaitingForInput();
	}
}
