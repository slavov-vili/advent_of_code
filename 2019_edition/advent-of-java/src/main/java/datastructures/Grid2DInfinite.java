package datastructures;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Grid2DInfinite<T> implements IGrid2D<T>{
	
	public static final String LINE_SEPARATOR = "\n";

	private Map<Point, T> grid;
	private T defaultValue;

	public Grid2DInfinite(T defaultValue) {
		this.grid = new HashMap<>();
		this.defaultValue = defaultValue;
	}
	
	@Override
	public T get(Point position) {
		return this.grid.getOrDefault(position, defaultValue);
	}

	@Override
	public void set(Point position, T newValue) {
		this.grid.put(position, newValue);
	}

	@Override
	public int getWidth() {
		return this.getMaxX() - this.getMinX();
	}

	@Override
	public int getHeight() {
		return this.getMaxY() - this.getMinY();
	}

	@Override
	public int getSize() {
		return this.getWidth() * this.getHeight();
	}
	
	@Override
	public String toString() {
		StringBuilder gridBuilder = new StringBuilder();

		// TOP LEFT = 0,0
		for (int y=this.getMinY(); y<=this.getMaxY(); y++) {
			for (int x=this.getMinX(); x<=this.getMaxX(); x++) {
				gridBuilder.append(this.get(new Point(x, y)).toString());
			}
			gridBuilder.append(LINE_SEPARATOR);
		}
		
		return gridBuilder.toString();
	}
	
	@Override
	public long count(Predicate<T> condition) {
		return this.grid.values().stream()
				.filter(condition)
				.count();
	}
	
	public int getMaxX() {
		return getMax(Point::getX);
	}
	
	public int getMinX() {
		return getMin(Point::getX);
	}
	
	public int getMaxY() {
		return getMax(Point::getY);
	}
	
	public int getMinY() {
		return getMin(Point::getY);
	}

	private int getMax(Function<Point, Double> coordGetter) {
		return this.grid.keySet().stream()
				.mapToInt(curPoint -> coordGetter.apply(curPoint).intValue())
				.max().getAsInt();
	}
	
	private int getMin(Function<Point, Double> coordGetter) {
		return this.grid.keySet().stream()
				.mapToInt(curPoint -> coordGetter.apply(curPoint).intValue())
				.min().getAsInt();
	}

}
