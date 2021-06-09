package de.cas.adventofcode.day13;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.MathUtils;
import de.cas.adventofcode.shared.MathUtils.EEData;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day13Velislav extends Day<Long> {
    protected Day13Velislav() {
        super(13);
    }

    public static void main(final String[] args) {
        new Day13Velislav().run();
    }

    @Override
    public Long solvePart1(final List<String> input) {
    	if (input.isEmpty())
    		return null;
    	long departureTime = Long.parseLong(input.get(0));
    	List<Integer> busses = Arrays.stream(input.get(1).split(","))
    			.filter(bus -> !bus.equals("x"))
    			.map(Integer::parseInt)
    			.collect(Collectors.toList());
    	
    	Map<Integer, Long> nextAvailable = busses.stream()
    			.collect(Collectors.toMap(Function.identity(), id -> (countTripsTilNextAvailable(departureTime, id)*id)));
    	
    	Integer nextBus = nextAvailable.keySet().stream()
    			.min(Comparator.comparingLong(id -> nextAvailable.get(id)))
    			.get();
        return nextBus * (nextAvailable.get(nextBus) - departureTime);
    }
    
    private long countTripsTilNextAvailable(double departureTime, double busID) {
    	return (long)Math.ceil(departureTime / busID);
    }

	@Override
	public Long solvePart2(final List<String> input) {
		if (input.isEmpty())
    		return null;
		Map<Long, Long> busToExpectedRemainer = new HashMap<>();
		String[] busses = input.get(1).split(",");
		
		for (int i=0; i<busses.length; i++)
			if(!busses[i].equals("x")) {
				long curBus = Long.parseLong(busses[i]);
				busToExpectedRemainer.put(curBus, curBus-i);
			}
    	
		return MathUtils.solveCongruenceEquations(busToExpectedRemainer);
    }
}
