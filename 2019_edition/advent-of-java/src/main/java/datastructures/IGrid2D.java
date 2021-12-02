package datastructures;

import java.awt.Point;
import java.util.Collection;
import java.util.function.Predicate;

public interface IGrid2D<T> {
	public Collection<Point> getPositionsWhere(Predicate<T> condition);

	public T get(Point position);
	
	public void set(Point position, T newValue);
	
	public int getWidth();
	
	public int getHeight();
	
	public int getSize();
	
	public long count(Predicate<T> condition);
}
