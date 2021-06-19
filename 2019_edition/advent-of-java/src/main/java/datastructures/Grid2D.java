package datastructures;

import java.awt.Point;
import java.util.function.Predicate;

public class Grid2D<T> implements IGrid2D<T> {
	private Object[][] grid;

	public Grid2D(Point dimensions) {
		this.grid = new Object[dimensions.y][dimensions.x];
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T get(Point position) {
		checkPosition(position);
		return (T) this.grid[position.y][position.x];
	}

	@Override
	public void set(Point position, T newValue) {
		checkPosition(position);
		this.grid[position.y][position.x] = newValue;
	}
	
	@Override
	public int getWidth() {
		return this.grid.length;
	}
	
	@Override
	public int getHeight() {
		return this.grid[0].length;
	}
	
	@Override
	public int getSize() {
		return this.getWidth() * this.getHeight();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public long count(Predicate<T> condition) {
		long result = 0;
		for (int x=0; x<this.getWidth(); x++)
			for (int y=0; y<this.getHeight(); y++)
				if (condition.test((T) this.grid[x][y]))
					result++;
		
		return result;
	}
	
	private void checkPosition(Point positionToCheck) {
		if ((positionToCheck.x < 0) || (positionToCheck.x >= this.getWidth())) {
			throw new IndexOutOfBoundsException("Width: " + positionToCheck.x);
		} else if ((positionToCheck.y < 0) || (positionToCheck.y >= this.getHeight())) {
			throw new IndexOutOfBoundsException("Height: " + positionToCheck.y);
		}
	}
	
	@Override
	public String toString() {
		return this.grid.toString();
	}
}
