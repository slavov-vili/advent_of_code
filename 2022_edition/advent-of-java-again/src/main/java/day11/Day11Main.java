package day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import utils.AdventOfCodeUtils;

public class Day11Main {
	public static final String MONKEY = "Monkey ";
	public static final String STARTING_ITEMS = "  Starting items: ";
	public static final String OPERATION = "  Operation: new = ";
	public static final String TEST = "  Test: divisible by ";
	public static final String OLD = "old";
	public static final String IF_TRUE = "    If true: throw to monkey ";
	public static final String IF_FALSE = "    If false: throw to monkey ";

	public static final UnaryOperator<Long> REDUCE_WORRY = (x -> x / 3);
	public static final UnaryOperator<Long> DO_NOTHING = UnaryOperator.identity();

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day11Main.class);

		System.out.println("Level of monkey business after 20 rounds: " + solveA(input));

		System.out.println("Level of monkey business after 10K rounds: " + solveB(input));
	}

	public static long solveA(List<String> input) {
		var monkeys = getMonkeys(input, REDUCE_WORRY);

		for (int i = 0; i < 20; i++) {
			for (var monkey : monkeys) {
				monkey.inspectItems();
				monkey.throwToOthers(monkeys);
			}
		}
		return monkeys.stream().mapToLong(Monkey::getInspectionCount).sorted().skip(monkeys.size() - 2)
				.reduce((x, y) -> x * y).getAsLong();
	}

	public static long solveB(List<String> input) {
		var monkeys = getMonkeys(input, DO_NOTHING);
		long reductionFactor = monkeys.stream().map(m -> (long) m.getTestValue()).reduce((x, y) -> x * y).get();
		UnaryOperator<Long> betterWorryReducer = (x -> x % reductionFactor);
		monkeys.forEach(m -> m.setReliefOperation(betterWorryReducer));

		for (int i = 0; i < 10000; i++) {
			for (var monkey : monkeys) {
				monkey.inspectItems();
				monkey.throwToOthers(monkeys);
			}
		}

		return monkeys.stream().mapToLong(Monkey::getInspectionCount).sorted().skip(monkeys.size() - 2)
				.reduce((x, y) -> x * y).getAsLong();
	}

	public static List<Monkey<Long>> getMonkeys(List<String> input, UnaryOperator<Long> reliefOperation) {
		var monkeys = new ArrayList<Monkey<Long>>();
		var curItems = new ArrayList<Long>();
		UnaryOperator<Long> curOperation = DO_NOTHING;
		int curTestValue = 1;
		int curTrueValue = -1;
		int curFalseValue = -2;

		for (String line : input) {
			if (line.startsWith(MONKEY))
				continue;

			else if (line.startsWith(STARTING_ITEMS)) {
				curItems = new ArrayList<>();
				Arrays.stream(line.substring(STARTING_ITEMS.length(), line.length()).split(", ")).map(Long::parseLong)
						.forEach(curItems::add);
			}

			else if (line.startsWith(OPERATION))
				curOperation = parseOperation(line.substring(OPERATION.length(), line.length()));

			else if (line.startsWith(TEST))
				curTestValue = Integer.parseInt(line.substring(TEST.length(), line.length()));

			else if (line.startsWith(IF_TRUE))
				curTrueValue = Integer.parseInt(line.substring(IF_TRUE.length(), line.length()));

			else if (line.startsWith(IF_FALSE))
				curFalseValue = Integer.parseInt(line.substring(IF_FALSE.length(), line.length()));

			else
				monkeys.add(new Monkey<Long>(curItems, curOperation, reliefOperation, curTestValue,
						getTest(curTrueValue, curFalseValue)));

		}
		if (!input.get(input.size() - 1).isEmpty())
			monkeys.add(new Monkey<Long>(curItems, curOperation, reliefOperation, curTestValue,
					getTest(curTrueValue, curFalseValue)));
		return monkeys;
	}

	public static UnaryOperator<Long> parseOperation(String operation) {
		var parts = operation.split(" ");
		Optional<Long> a = (OLD.equals(parts[0])) ? Optional.empty() : Optional.of(Long.parseLong(parts[0]));
		Optional<Long> b = (OLD.equals(parts[2])) ? Optional.empty() : Optional.of(Long.parseLong(parts[2]));
		BinaryOperator<Long> operator = parseOperator(parts[1]);
		return (x -> operator.apply(a.orElse(x), b.orElse(x)));
	}

	public static BinaryOperator<Long> parseOperator(String operator) {
		return switch (operator) {
		case "*" -> ((x, y) -> x * y);
		case "+" -> ((x, y) -> x + y);
		default -> null;
		};
	}

	public static BiFunction<Long, Integer, Integer> getTest(int trueValue, int falseValue) {
		return ((x, t) -> (x % t == 0) ? trueValue : falseValue);
	}
}
