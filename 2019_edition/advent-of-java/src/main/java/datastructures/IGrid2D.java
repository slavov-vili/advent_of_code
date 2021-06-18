package datastructures;

import java.awt.Point;

public interface IGrid2D<T> {
	public T get(Point position);
	
	public void set(Point position, T newValue);
	
	public int getWidth();
	
	public int getHeight();
	
	public int getSize();
}
