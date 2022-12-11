package day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Monkey<T> {
	private List<T> items;
	private UnaryOperator<T> operation;
	private UnaryOperator<T> reliefOperation;
	private int testValue;
	private BiFunction<T, Integer, Integer> monkeyDecider;
	private int inspectionCount;

	public Monkey(List<T> items, UnaryOperator<T> operation, UnaryOperator<T> reliefOperation, int testValue,
			BiFunction<T, Integer, Integer> monkeyDecider) {
		this.items = new ArrayList<>(items);
		this.operation = operation;
		this.reliefOperation = reliefOperation;
		this.testValue = testValue;
		this.monkeyDecider = monkeyDecider;
	}

	public void inspectItems() {
		var inspectedItems = new ArrayList<T>();
		this.items.forEach(x -> inspectedItems.add(this.reliefOperation.apply(this.operation.apply(x))));
		this.items = inspectedItems;
		this.inspectionCount += inspectedItems.size();
	}

	public void throwToOthers(List<Monkey<T>> monkeys) {
		for (var throwList : this.getThrowLists().entrySet()) {
			throwList.getValue().forEach(item -> monkeys.get(throwList.getKey()).addItem(item));
		}
	}

	public Map<Integer, List<T>> getThrowLists() {
		var throwLists = new HashMap<Integer, List<T>>();

		for (T nextItem : this.items) {
			var nextMonkey = this.monkeyDecider.apply(nextItem, this.getTestValue());

			var nextMonkeyList = throwLists.getOrDefault(nextMonkey, new ArrayList<>());
			nextMonkeyList.add(nextItem);

			throwLists.put(nextMonkey, nextMonkeyList);
		}

		this.items = new ArrayList<>();

		return throwLists;
	}

	public void mapItems(UnaryOperator<T> mapping) {
		this.items = this.items.stream().map(mapping).collect(Collectors.toList());
	}

	private void addItem(T newItem) {
		this.items.add(newItem);
	}

	public void setReliefOperation(UnaryOperator<T> newReliefOperation) {
		this.reliefOperation = newReliefOperation;
	}

	public int getTestValue() {
		return this.testValue;
	}

	public int getInspectionCount() {
		return this.inspectionCount;
	}
}