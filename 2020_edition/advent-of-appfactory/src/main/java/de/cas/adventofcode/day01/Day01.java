package de.cas.adventofcode.day01;

import java.util.List;

import de.cas.adventofcode.shared.ConversionUtils;
import de.cas.adventofcode.shared.Day;

public class Day01 extends Day<Integer> {

    public Day01() {
        super(1);
    }

    public static void main(final String[] args) {
        new Day01().run();
    }

    @Override
    public Integer solvePart1(List<String> input) {
    	List<Integer> parsedInput = ConversionUtils.listStringToListInt(input);
        return FindSum.findSumOfTwo(parsedInput);
    }

    @Override
    public Integer solvePart2(List<String> input) {
    	List<Integer> parsedInput = ConversionUtils.listStringToListInt(input);
        return FindSum.findSumOfThree (parsedInput);
    }
}
