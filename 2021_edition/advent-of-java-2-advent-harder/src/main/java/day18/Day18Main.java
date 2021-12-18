package day18;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day18Main {

	public static void main(String[] args) {
		System.out.println("Sum of all numbers: " + solveA());
		System.out.println("Maximum magnitude of adding 2: " + solveB());
	}

	public static int solveA() {
		List<SnailFishNumber> numbers = parseSnailFishNumbers();
		SnailFishNumber result = numbers.stream().reduce(Day18Main::add).get();
		return result.calcMagnitude();
	}

	public static int solveB() {
		List<SnailFishNumber> numbers = parseSnailFishNumbers();
		return IntStream.range(0, numbers.size()).map(i -> calcMaxMagnitude(i, numbers)).max().getAsInt();
	}

	public static SnailFishNumber add(SnailFishNumber a, SnailFishNumber b) {
		return new SnailFishNumber(Arrays.asList(new SnailFishNumber(a), new SnailFishNumber(b))).reduce();
	}

	public static int calcMaxMagnitude(int curNumberIndex, List<SnailFishNumber> numbers) {
		int maxMagnitude = Integer.MIN_VALUE;
		for (int i = 0; i < numbers.size(); i++) {
			if (i != curNumberIndex) {
				SnailFishNumber curNumber = numbers.get(curNumberIndex);
				SnailFishNumber nextNumber = numbers.get(i);
				int curMagnitude = add(curNumber, nextNumber).calcMagnitude();
				if (curMagnitude > maxMagnitude)
					maxMagnitude = curMagnitude;
			}
		}
		return maxMagnitude;
	}

	public static List<SnailFishNumber> parseSnailFishNumbers() {
		return AdventOfCodeUtils.readInput(Day18Main.class).stream().map(SnailFishNumber::parse)
				.collect(Collectors.toList());
	}

	public static class SnailFishNumber {
		public static final int LEFT = -1;
		public static final int RIGHT = 1;

		public int value;
		public List<SnailFishNumber> children;

		public SnailFishNumber(int value) {
			this(value, new ArrayList<>());
		}

		public SnailFishNumber(List<SnailFishNumber> children) {
			this(0, children);
		}

		public SnailFishNumber(int value, List<SnailFishNumber> children) {
			this.value = value;
			this.children = new ArrayList<>(children);
		}

		public SnailFishNumber(SnailFishNumber otherNumber) {
			this.value = otherNumber.value;
			this.children = otherNumber.children.stream().map(SnailFishNumber::new).collect(Collectors.toList());
		}

		public SnailFishNumber reduce() {
			List<Integer> pathToOperand;
			do {
				pathToOperand = findNextNumberToExplode(0, new ArrayList<>());
				if (!pathToOperand.isEmpty())
					explodeNumber(pathToOperand);
				else {
					pathToOperand = findNextNumberToSplit(new ArrayList<>());
					if (!pathToOperand.isEmpty())
						splitNumber(pathToOperand);
				}
			} while (!pathToOperand.isEmpty());
			return this;
		}

		public int calcMagnitude() {
			if (this.isNormalNumber())
				return this.value;

			int leftChildMagnitude = this.children.get(0).calcMagnitude();
			int rightChildMagnitude = this.children.get(1).calcMagnitude();
			return 3 * leftChildMagnitude + 2 * rightChildMagnitude;
		}

		public List<Integer> findNextNumberToExplode(int curDepth, List<Integer> pathToCurNumber) {
			if (this.children.isEmpty())
				return new ArrayList<>();
			if (curDepth == 4)
				return pathToCurNumber;
			else {
				for (int i = 0; i < this.children.size(); i++) {
					SnailFishNumber curChild = this.children.get(i);
					List<Integer> pathToChild = new ArrayList<>(pathToCurNumber);
					pathToChild.add(i);
					List<Integer> pathToExplodingNumber = curChild.findNextNumberToExplode(curDepth + 1, pathToChild);
					if (!pathToExplodingNumber.isEmpty())
						return pathToExplodingNumber;
				}
			}

			return new ArrayList<>();
		}

		public List<Integer> findNextNumberToSplit(List<Integer> pathToCurNumber) {
			if (this.children.isEmpty() && this.value >= 10)
				return pathToCurNumber;
			else {
				for (int i = 0; i < this.children.size(); i++) {
					SnailFishNumber curChild = this.children.get(i);
					List<Integer> pathToChild = new ArrayList<>(pathToCurNumber);
					pathToChild.add(i);
					List<Integer> pathToNumberToSplit = curChild.findNextNumberToSplit(pathToChild);
					if (!pathToNumberToSplit.isEmpty())
						return pathToNumberToSplit;
				}
			}

			return new ArrayList<>();
		}

		public void explodeNumber(List<Integer> pathToNumber) {
			SnailFishNumber number = getNumber(pathToNumber);
			Optional<SnailFishNumber> leftNumber = getLeftNormalNumber(pathToNumber);
			Optional<SnailFishNumber> rightNumber = getRightNormalNumber(pathToNumber);

			if (leftNumber.isPresent())
				leftNumber.get().value += number.children.get(0).value;
			if (rightNumber.isPresent())
				rightNumber.get().value += number.children.get(1).value;
			number.children = new ArrayList<>();
			number.value = 0;
		}

		public void splitNumber(List<Integer> pathToNumber) {
			SnailFishNumber number = getNumber(pathToNumber);
			int leftValue = number.value / 2;
			int rightValue = number.value - leftValue;
			number.children.add(new SnailFishNumber(leftValue));
			number.children.add(new SnailFishNumber(rightValue));
			number.value = 0;
		}

		public Optional<SnailFishNumber> getLeftNormalNumber(List<Integer> pathToNumber) {
			return findFirstNormalNumberInDirection(pathToNumber, LEFT);
		}

		public Optional<SnailFishNumber> getRightNormalNumber(List<Integer> pathToNumber) {
			return findFirstNormalNumberInDirection(pathToNumber, RIGHT);
		}

		public Optional<SnailFishNumber> findFirstNormalNumberInDirection(List<Integer> pathToNumber, int direction) {
			Optional<SnailFishNumber> nextNumberInDirection = findNextNumberInDirection(pathToNumber, direction);
			if (nextNumberInDirection.isPresent())
				return nextNumberInDirection.get().findFirstChildWhere(-direction, SnailFishNumber::isNormalNumber);
			return Optional.empty();
		}

		public Optional<SnailFishNumber> findNextNumberInDirection(List<Integer> pathToNumber, int direction) {
			List<Integer> pathToParent = pathToNumber.subList(0, pathToNumber.size() - 1);
			SnailFishNumber parent = this.getNumber(pathToParent);
			int nextChildIndex = pathToNumber.get(pathToNumber.size() - 1) + direction;
			if (!parent.hasChildWithIndex(nextChildIndex))
				if (pathToParent.isEmpty())
					return Optional.empty();
				else
					return findNextNumberInDirection(pathToParent, direction);
			else
				return Optional.of(parent.children.get(nextChildIndex));
		}

		public Optional<SnailFishNumber> findFirstChildWhere(int childrenIndexIncrement,
				Predicate<SnailFishNumber> numberCriteriaCriteria) {
			if (numberCriteriaCriteria.test(this))
				return Optional.of(this);
			else if (this.children.isEmpty())
				return Optional.empty();

			int nextChildIndex = (1 + childrenIndexIncrement) / 2;
			SnailFishNumber nextChild = this.children.get(nextChildIndex);
			if (numberCriteriaCriteria.test(nextChild))
				return Optional.of(nextChild);
			else
				return nextChild.findFirstChildWhere(childrenIndexIncrement, numberCriteriaCriteria);
		}

		public SnailFishNumber getNumber(List<Integer> pathToNumber) {
			if (pathToNumber.isEmpty())
				return this;
			else if (pathToNumber.size() == 1)
				return this.children.get(pathToNumber.get(0));
			else {
				List<Integer> nextPath = pathToNumber.subList(1, pathToNumber.size());
				return this.children.get(pathToNumber.get(0)).getNumber(nextPath);
			}
		}

		public boolean isNormalNumber() {
			return this.children.isEmpty();
		}

		public boolean hasChildWithIndex(int childIndex) {
			return (childIndex >= 0) && (childIndex < this.children.size());
		}

		public static SnailFishNumber parse(String number) {
			int i = 0;
			Deque<List<SnailFishNumber>> childrenLists = new ArrayDeque<>();
			childrenLists.push(new ArrayList<>());

			while (i < number.length()) {
				char curChar = number.charAt(i);
				if (curChar == '[') {
					childrenLists.push(new ArrayList<>());
				} else if (Character.isDigit(curChar)) {
					int value = Character.digit(curChar, 10);
					SnailFishNumber newNumber = new SnailFishNumber(value);
					childrenLists.peek().add(newNumber);
				} else if (curChar == ']') {
					SnailFishNumber newNumber = new SnailFishNumber(childrenLists.pop());
					childrenLists.peek().add(newNumber);
				}
				i++;
			}

			return childrenLists.pop().get(0);
		}

		public String toString() {
			if (this.children.isEmpty())
				return String.valueOf(this.value);
			return this.children.toString();
		}
	}
}
