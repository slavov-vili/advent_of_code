package day09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day09Main {

	public static void main(String[] args) {
		System.out.println("Sum of next values: " + solveA(parseHistories()));

		System.out.println("Sum of prev values: " + solveB(parseHistories()));
	}

	public static long solveA(List<History> histories) {
		return histories.stream().mapToLong(History::findNextValue).sum();
	}

	public static long solveB(List<History> histories) {
		return histories.stream().mapToLong(History::findPrevValue).sum();
	}

	record History(List<Long> numbers) {
		public long findNextValue() {
			return this.findValue(seq -> seq.get(seq.size() - 1), List::add, (prev, diff) -> prev + diff);
		}

		public long findPrevValue() {
			return this.findValue(seq -> seq.get(0), (seq, el) -> seq.add(0, el), (prev, diff) -> prev - diff);
		}

		public long findValue(Function<List<Long>, Long> elementGetter, BiConsumer<List<Long>, Long> elementAdder,
				BiFunction<Long, Long, Long> valueCombiner) {
			var history = this.extrapolate(elementGetter, elementAdder, valueCombiner).get(0);
			return elementGetter.apply(history);
		}

		public List<List<Long>> extrapolate(Function<List<Long>, Long> elementGetter,
				BiConsumer<List<Long>, Long> elementAdder, BiFunction<Long, Long, Long> valueCombiner) {
			var sequences = this.findSequences();

			long value = 0;
			for (int i = sequences.size() - 1; i > 0; i--) {
				var curSequence = sequences.get(i);
				elementAdder.accept(curSequence, value);
				var prevValue = elementGetter.apply(sequences.get(i - 1));
				value = valueCombiner.apply(prevValue, value);
			}
			elementAdder.accept(sequences.get(0), value);

			return sequences;
		}

		public List<List<Long>> findSequences() {
			List<List<Long>> sequences = new ArrayList<>();
			var curSequence = this.numbers;
			while (!curSequence.stream().allMatch(n -> n == 0)) {
				sequences.add(curSequence);
				var streamSequence = new ArrayList<>(curSequence);
				curSequence = IntStream.range(0, streamSequence.size() - 1)
						.mapToObj(i -> streamSequence.get(i + 1) - streamSequence.get(i)).collect(Collectors.toList());
			}
			sequences.add(curSequence);
			return sequences;
		}
	}

	public static List<History> parseHistories() {
		List<String> input = AdventOfCodeUtils.readInput(Day09Main.class);
		return input.stream().map(
				line -> new History(Arrays.stream(line.split(" ")).map(Long::parseLong).collect(Collectors.toList())))
				.toList();
	}
}
