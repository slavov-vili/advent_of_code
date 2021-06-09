package de.cas.adventofcode.day10;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cas.adventofcode.shared.ConversionUtils;
import de.cas.adventofcode.shared.Day;

public class Day10 extends Day<Long> {
    protected Day10() {
        super(10);
    }

    public static void main(final String[] args) {
        new Day10().run();
    }

    @Override
    public Long solvePart1(final List<String> input) {
        final List<Integer> parsedInput = ConversionUtils.listStringToListInt(input);
        parsedInput.add(0);
        Collections.sort(parsedInput);
        final Map<Integer, Integer> diffToCount = this.findDifferenceCounts(parsedInput);
        return (long) (diffToCount.get(1) * diffToCount.get(3));
    }

    private Map<Integer, Integer> findDifferenceCounts(final List<Integer> numbers) {
        final Map<Integer, Integer> diffToCount = new HashMap<>();
        diffToCount.put(1, 0);
        diffToCount.put(2, 0);
        diffToCount.put(3, 1);

        for (int i = 1; i < numbers.size(); i++) {
            final int difference = numbers.get(i) - numbers.get(i - 1);
            int curCount = diffToCount.get(difference);
            diffToCount.put(difference, ++curCount);
        }
        return diffToCount;
    }

    @Override
    public Long solvePart2(final List<String> input) {
        final List<Integer> parsedInput = ConversionUtils.listStringToListInt(input);
        parsedInput.add(0);
        Collections.sort(parsedInput);
        parsedInput.add(parsedInput.get(parsedInput.size() - 1) + 3);
        final HashMap<Integer, Long> resultsMap = new HashMap<>();
        final long l = this.find(parsedInput, 0, resultsMap);
        return l;
    }

    private long find(final List<Integer> numbers, final int i, final Map<Integer, Long> resultsMap) {

        final int number = numbers.get(i);
        if (resultsMap.containsKey(number)) {
            return resultsMap.get(number);
        }
        if (i == numbers.size() - 1) {
            return 1;
        }
        long results = 0;
        for (int difference = 1; difference <= 3; difference++) {
            final int newNumber = number + difference;
            if (numbers.contains(newNumber)) {
                long temp = this.find(numbers, numbers.indexOf(newNumber), resultsMap);
                resultsMap.put(newNumber, temp);
                results = results + temp;
            }
        }
        return results;
    }
}
