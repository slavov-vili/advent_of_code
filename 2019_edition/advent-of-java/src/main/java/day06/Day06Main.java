package day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import day05.Day05Main;
import utils.AdventOfCodeUtils;

public class Day06Main {

	public static void main(String[] args) {
		Map<String, Set<String>> orbiteeToOrbiters = getInput();
		int mapSize = 0;
		for (String orbitee : orbiteeToOrbiters.keySet())
			mapSize += getAllOrbitersOf(orbitee, orbiteeToOrbiters).size();
		System.out.println(mapSize);
	}
	
	protected static Set<String> getAllOrbitersOf(String orbitee, Map<String, Set<String>> orbiteeToOrbiters) {
		if (!orbiteeToOrbiters.containsKey(orbitee))
			return new HashSet<>();
		
		Set<String> directOrbiters = orbiteeToOrbiters.get(orbitee);
		Set<String> allOrbiters = new HashSet<>(directOrbiters);
		for (String orbiter : directOrbiters)
			allOrbiters.addAll(getAllOrbitersOf(orbiter, orbiteeToOrbiters));
		return allOrbiters;
	}

    protected static Map<String, Set<String>> getInput() {
    	Map<String, Set<String>> orbiteeToOrbiters = new HashMap<>();
        List<String> lines =  AdventOfCodeUtils.readClasspathFileLines(Day06Main.class, "input.txt");
        for (String line : lines) {
        	String[] parts = line.split("\\)");
        	String orbitee = parts[0];
        	String orbiter = parts[1];
        	if (orbiteeToOrbiters.containsKey(orbitee))
        		orbiteeToOrbiters.get(orbitee).add(orbiter);
        	else
        		orbiteeToOrbiters.put(orbitee, new HashSet<>(Arrays.asList(orbiter)));
        }
        return orbiteeToOrbiters;
    }
}
