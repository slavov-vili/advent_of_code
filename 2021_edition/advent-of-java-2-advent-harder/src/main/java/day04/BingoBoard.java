package day04;

import java.awt.Point;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BingoBoard<T> {
	public static final int BOARD_SIZE = 5;
	private Map<T, SimpleEntry<Point, Boolean>> valueMap;
	private List<T> values;

	public BingoBoard(List<T> values) {
		this.values = new ArrayList<>(values);
		this.valueMap = new HashMap<>();

		for (int i = 0; i < values.size(); i++) {
			int row = i / BOARD_SIZE;
			int col = i % BOARD_SIZE;
			Point position = new Point(row, col);

			valueMap.put(values.get(i), new SimpleEntry<>(position, false));
		}
	}

	public Optional<Point> checkValue(T valueToCheck) {
		if (this.valueMap.containsKey(valueToCheck)) {
			SimpleEntry<Point, Boolean> valueProperties = this.valueMap.get(valueToCheck);
			valueProperties.setValue(true);
			return Optional.of(valueProperties.getKey());
		}
		return Optional.empty();
	}

	public boolean hasBingo(Point position) {
		return hasCoordBingo(position, Point::getX)
				|| hasCoordBingo(position, Point::getY);
	}

	public boolean hasCoordBingo(Point positionToCheck, Function<Point, Double> coordChooser) {
		Set<T> markedValuesOnSameCoord = this.getMarkedValues().stream()
				.filter(markedValue -> this.isOnSameCoord(positionToCheck, markedValue, coordChooser))
				.collect(Collectors.toSet());

		return markedValuesOnSameCoord.size() == BOARD_SIZE;
	}
	
	public Set<T> getMarkedValues() {
		return this.valueMap.keySet().stream().filter(this::isMarked).collect(Collectors.toSet());
	}
	
	public Set<T> getUnmarkedValues() {
		return this.valueMap.keySet().stream().filter(value -> !this.isMarked(value)).collect(Collectors.toSet());
	}

	public boolean isMarked(T value) {
		if (this.valueMap.containsKey(value))
			return this.valueMap.get(value).getValue();

		return false;
	}

	public boolean isOnSameCoord(Point positionToCheck, T value, Function<Point, Double> coordChooser) {
		if (this.valueMap.containsKey(value))
			return coordChooser.apply(positionToCheck).equals(coordChooser.apply(this.getPositionOf(value).get()));

		return false;
	}

	public Optional<Point> getPositionOf(T value) {
		if (this.valueMap.containsKey(value))
			return Optional.of(this.valueMap.get(value).getKey());

		return Optional.empty();
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.values.size(); i++) {
			T curValue = values.get(i);
			if (valueMap.get(curValue).getValue())
				builder.append(" *" + curValue.toString() + "*");
			else
				builder.append(" " + curValue.toString());

			// 0 causes a newline in the beginning
			if ((i + 1) % BOARD_SIZE == 0)
				builder.append("\n");
		}
		return builder.toString();
	}
}
