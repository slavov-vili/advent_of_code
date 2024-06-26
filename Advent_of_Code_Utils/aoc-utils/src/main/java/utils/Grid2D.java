package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

// Point.x = row
// Point.y = column
public class Grid2D<T> {

	private Object[][] grid;

	public Grid2D(int rowCount, int columnCount) {
		this.grid = new Object[rowCount][columnCount];
	}

	public T get(Point position) {
		checkPosition(position);
		return (T) this.grid[position.x][position.y];
	}

	public T set(Point position, T newValue) {
		checkPosition(position);
		T oldValue = this.get(position);
		this.grid[position.x][position.y] = newValue;
		return oldValue;
	}

	public List<Point> getAllNeighborPositions(Point position) {
		return PointUtils.getAllNeighborPositions(position, this::hasPosition);
	}

	public int getColumnCount() {
		return this.grid[0].length;
	}

	public int getRowCount() {
		return this.grid.length;
	}

	public int getSize() {
		return this.getColumnCount() * this.getRowCount();
	}

	public boolean isLastPosition(Point positionToCheck) {
		return positionToCheck.equals(new Point(this.getRowCount() - 1, this.getColumnCount() - 1));
	}

	public boolean hasPosition(Point positionToCheck) {
		return (positionToCheck.x >= 0) && (positionToCheck.x < this.getRowCount()) && (positionToCheck.y >= 0)
				&& (positionToCheck.y < this.getColumnCount());
	}

	protected void checkPosition(Point positionToCheck) {
		if (!hasPosition(positionToCheck)) {
			throw new IndexOutOfBoundsException("Position not in grid: " + positionToCheck);
		}
	}

	public List<Point> getPositionsWhere(Predicate<T> predicate) {
		var positions = new ArrayList<Point>();
		var positionIter = this.positionIterator();
		while (positionIter.hasNext()) {
			var nextPosition = positionIter.next();
			if (predicate.test(this.get(nextPosition)))
				positions.add(nextPosition);
		}
		return positions;
	}

	public Iterator<Point> positionIterator() {
		return new PositionIterator(this.getRowCount(), this.getColumnCount(), this::isLastPosition);
	}

	@Override
	public String toString() {
		return this.grid.toString();
	}

	public static class PositionIterator implements Iterator<Point> {
		public static final Point INITIAL_POSITION = new Point(0, -1);

		public int rowCount, columnCount;
		public Predicate<Point> isLastPosition;
		public Point curPosition;

		public PositionIterator(int rowCount, int columnCount, Predicate<Point> isLastPosition) {
			this.rowCount = rowCount;
			this.columnCount = columnCount;
			this.isLastPosition = isLastPosition;
			this.curPosition = INITIAL_POSITION;
		}

		@Override
		public boolean hasNext() {
			return INITIAL_POSITION.equals(this.curPosition) || !isLastPosition.test(this.curPosition);
		}

		@Override
		public Point next() {
			int newColumn = this.curPosition.y + 1;
			int newRow = (newColumn == this.columnCount) ? this.curPosition.x + 1 : this.curPosition.x;
			this.curPosition = new Point(newRow, newColumn % this.columnCount);
			return this.curPosition;
		}

	}
}
