package de.cas.adventofcode.day23;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import de.cas.adventofcode.shared.Day;

public class Day23 extends Day<String> {

	protected Day23() {
		super(23);
	}

	public static void main(final String[] args) {
		new Day23().run();
	}

	@Override
	public String solvePart1(List<String> input) {
		LinkedList<Long> cupLabels = new LinkedList<>();
		for (int i = 0; i < input.get(0).length(); i++) {
			cupLabels.add(Long.parseLong("" + input.get(0).charAt(i)));
		}
		long max = cupLabels.stream().reduce(Long::max).get();
		long min = cupLabels.stream().reduce(Long::min).get();

		int currentIndex = 0;
		for (int i = 0; i < 100; i++) {
			long currentLabel = cupLabels.get(currentIndex);
			rotateList(cupLabels, -currentIndex);
			currentIndex = 0;
			int removeIndex = currentIndex+1;
			List<Long> tempList = new ArrayList<>(); 
			for (int j=0; j<3; j++) {
				tempList.add(cupLabels.remove(removeIndex));
			}
			
			long destinationLabel = calcDestinationLabel(currentLabel, min, max, tempList);
			
			int destinationIndex = cupLabels.indexOf(destinationLabel) + 1;
			cupLabels.addAll(destinationIndex, tempList);
			currentIndex += 1;
		}
		
		int indexOf1 = cupLabels.indexOf(1);
		rotateList(cupLabels, -indexOf1);
		return String.join("", cupLabels.stream()
				.map(String::valueOf)
				.collect(Collectors.toList()));
	}

	@Override
	public String solvePart2(List<String> input) {
		// TODO: represent the cups as an array where the index is the cup label
		// and the value is the next cup in the sequence
		// for the actual implementation see Inusette's github account
		return null;
	}
	
	private <T> void rotateList(LinkedList<T> toRotate, int rotation) {
		for (int i=0; i<Math.abs(rotation); i++) {
			if (rotation < 0)
				toRotate.addLast(toRotate.removeFirst());
			else
				toRotate.addFirst(toRotate.removeLast());
		}
	}

	private long calcDestinationLabel(long curLabel, long min, long max, List<Long> excludedLabels) {
		long destinationLabel = curLabel;
		do {
			destinationLabel = calcNextLabel(destinationLabel, min, max);
		} while (excludedLabels.contains(destinationLabel));
		return destinationLabel;
	}

	private long calcNextLabel(long curLabel, long min, long max) {
		return (curLabel == min) ? max : curLabel-1;
	}

}
