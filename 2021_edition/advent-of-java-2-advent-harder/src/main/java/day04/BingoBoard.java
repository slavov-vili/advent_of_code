package day04;

import java.awt.Point;
import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BingoBoard<T> {
	public static final int BOARD_SIZE = 5;
	private Map<T, SimpleEntry<Point, Boolean>> valueMap;
	private Set<T> valueMarkOrder;

	public BingoBoard(List<T> values) {
		this.valueMarkOrder = new LinkedHashSet<>();
		this.valueMap = new LinkedHashMap<>();

		for (int i = 0; i < values.size(); i++) {
			int row = i / BOARD_SIZE;
			int col = i % BOARD_SIZE;
			Point position = new Point(row, col);

			valueMap.put(values.get(i), new SimpleEntry<>(position, false));
		}
	}

	public boolean markValue(T valueToCheck) {
		if (this.valueMap.containsKey(valueToCheck)) {
			SimpleEntry<Point, Boolean> valueProperties = this.valueMap.get(valueToCheck);
			valueProperties.setValue(true);
			valueMarkOrder.add(valueToCheck);
			return this.hasBingo(valueProperties.getKey());
		}
		return false;
	}

	public boolean hasBingo(Point position) {
		return hasCoordBingo(position, Point::getX) || hasCoordBingo(position, Point::getY);
	}

	public boolean hasCoordBingo(Point positionToCheck, Function<Point, Double> coordChooser) {
		Set<T> markedValuesOnSameCoord = this.getMarkedValues().stream()
				.filter(markedValue -> this.isOnSameCoord(positionToCheck, markedValue, coordChooser))
				.collect(Collectors.toSet());

		return markedValuesOnSameCoord.size() == BOARD_SIZE;
	}
	
	public T getValueByMarkIndex(int valueMarkIndex) {
		Iterator<T> valueIter = this.valueMarkOrder.iterator();
		T value = valueIter.next();
		for (int i=0; i<valueMarkIndex; i++)
			value = valueIter.next();
		return value;
	}
	
	public Set<T> getValueMarkOrder() {
		return this.valueMarkOrder;
	}
	
	public List<T> getValuesInOrder() {
		return this.valueMap.keySet().stream()
				.collect(Collectors.toList());
	}
	
	public Set<T> getValues() {
		return this.valueMap.keySet();
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
	
	public boolean equals(BingoBoard<T> otherBoard) {
		return this.getValuesInOrder().equals(otherBoard.getValuesInOrder());
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		List<T> valuesInOrder = this.getValuesInOrder();
		for (int i = 0; i < valuesInOrder.size(); i++) {
			T curValue = valuesInOrder.get(i);
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
