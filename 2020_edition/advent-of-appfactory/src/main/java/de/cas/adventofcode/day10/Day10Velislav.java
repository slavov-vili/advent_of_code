package de.cas.adventofcode.day10;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cas.adventofcode.shared.ConversionUtils;
import de.cas.adventofcode.shared.Day;

public class Day10Velislav extends Day<Long> {
    protected Day10Velislav() {
        super(10);
    }

    public static void main(final String[] args) {
        new Day10Velislav().run();
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
        final long combinationCount = this.countPossibleCombinations(parsedInput, 0, new HashMap<>());
        return combinationCount;
    }

    private long countPossibleCombinations(final List<Integer> sortedNumbers, int curIndex, final Map<Integer, Long> resultsMap) {
    	if (curIndex == sortedNumbers.size()-1)
    		return 1;
    	
    	int curNumber = sortedNumbers.get(curIndex);
    	long combinations = 0L;
    	for (int dist=1; dist<=3; dist++) {
    		int nextIndex = curIndex+dist;
    		
    		if (nextIndex < sortedNumbers.size()) {
	    		int nextNumber = sortedNumbers.get(nextIndex);
	    		
	    		if (nextNumber-curNumber <= 3) {
	    			if (resultsMap.containsKey(nextNumber))
	    				combinations += resultsMap.get(nextNumber);
	    			else
	    				combinations += countPossibleCombinations(sortedNumbers, nextIndex, resultsMap);
	    		}
    		}
    	}
    	resultsMap.put(curNumber, combinations);
    	return combinations;
    }
}
