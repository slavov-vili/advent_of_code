package de.cas.adventofcode.shared;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public abstract class Day<T> {
    private final int day;
	private final String dayFolder;
 
    protected Day(final int day) {
        this.day = day;
    	dayFolder = String.format("day%02d", day);
    }

    protected void run() {
    	runPartInternal(1);
    	runPartInternal(2);
    }
    
    private void runPartInternal(int part) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        System.out.printf("Day %d, Part %d ----------------------------------------%n", this.day, part);
        
    	for (Map.Entry<String, List<String>> nameToInput : this.getDayInputs().entrySet()) {
    		T result;
    		if (nameToInput.getValue().isEmpty())
    			result = null;
    		else
    			result = (part == 1) ? solvePart1(nameToInput.getValue())
    					: solvePart2(nameToInput.getValue());
            printResult(nameToInput.getKey(), result);
    	}
    	
        stopwatch.stop();
        System.out.printf("Execution took %dms.%n", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println();
    }

    protected void printResult(String teammate, T result) {
        System.out.println(teammate + ": " + result);
    }

    public abstract T solvePart1(List<String> input);

    public abstract T solvePart2(List<String> input);

    public Map<String, List<String>> getDayInputs() {
    	return FileReader.getInputs(this.dayFolder);
    }

    public List<String> getInputPersonal(String teammate) {
    	return getDayInputs().get(teammate);
    }
}
