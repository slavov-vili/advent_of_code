package utils;

import java.awt.Point;

public class Grid2D<T> {
	private Object[][] grid;

	public Grid2D(Point dimensions) {
		this.grid = new Object[dimensions.y][dimensions.x];
	}
	
	@SuppressWarnings("unchecked")
	public T get(Point position) {
		checkPosition(position);
		return (T) this.grid[position.y][position.x];
	}
	
	public T set(Point position, T newValue) {
		checkPosition(position);
		T oldValue = this.get(position);
		this.grid[position.y][position.x] = newValue;
		return oldValue;
	}
	
	public int getWidth() {
		return this.grid.length;
	}
	
	public int getHeight() {
		return this.grid[0].length;
	}
	
	public int getSize() {
		return this.getWidth() * this.getHeight();
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
