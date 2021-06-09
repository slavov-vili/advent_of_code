package de.cas.adventofcode.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public abstract class DayRealTest<T> {
	
    @Test
    void solvePart1Henning() {
    	String teammate = FileReader.HENNING;
    	T input = getExpectedResults1Personal().get(teammate);
    	solveDayPersonal(getDay(), 1, teammate, input);
    }
    
    @Test
    void solvePart1Oswaldo() {
    	String teammate = FileReader.OSWALDO;
    	T input = getExpectedResults1Personal().get(teammate);
    	solveDayPersonal(getDay(), 1, teammate, input);
    }

    @Test
    void solvePart1Velislav() {
    	String teammate = FileReader.VELISLAV;
    	T input = getExpectedResults1Personal().get(teammate);
    	solveDayPersonal(getDay(), 1, teammate, input);
    }
    
    @Test
    void solvePart2Henning() {
    	String teammate = FileReader.HENNING;
    	T input = getExpectedResults2Personal().get(teammate);
    	solveDayPersonal(getDay(), 2, teammate, input);
    }
    
    @Test
    void solvePart2Oswaldo() {
    	String teammate = FileReader.OSWALDO;
    	T input = getExpectedResults2Personal().get(teammate);
    	solveDayPersonal(getDay(), 2, teammate, input);
    }
    
    @Test
    void solvePart2Velislav() {
    	String teammate = FileReader.VELISLAV;
    	T input = getExpectedResults2Personal().get(teammate);
    	solveDayPersonal(getDay(), 2, teammate, input);
    }
    
    private void solveDayPersonal(Day<T> day, int part, String name, T expectedResult) {
    	List<String> input = day.getInputPersonal(name);
    	
        final T result = (part == 1) ? day.solvePart1(input)
        		:day.solvePart2(input);
        assertEquals(expectedResult, result);
    }
    
    public abstract Day<T> getDay();
    
    public abstract Map<String, T> getExpectedResults1Personal();
    
    public abstract Map<String, T> getExpectedResults2Personal();

}