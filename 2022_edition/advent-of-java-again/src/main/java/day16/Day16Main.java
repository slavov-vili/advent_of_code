package day16;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.DijkstraShortestPathFinder;

public class Day16Main {
	public static final Pattern VALVE_PATTERN = Pattern
			.compile("Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)");
	public static final int MINUTES = 30;
	public static final int OPEN_TIME = 1;
	public static final String START_VALVE = "AA";
	public static final int ELEPHANT_TRAINING_TIME = 4;

	public static void main(String[] args) {
		var valveDistanceMap = findValveShortestPaths(parseInput());
		System.out.println("Max pressure = " + solveA(valveDistanceMap));
	}

	public static int solveA(Map<String, ValveWithDistances> valveDistanceMap) {
		var valvesToOpen = valveDistanceMap.keySet().stream().filter(v -> valveDistanceMap.get(v).rate() > 0)
				.collect(Collectors.toSet());
		System.out.println(valvesToOpen);
		return findMaxPotentialPressure(START_VALVE, 0, valvesToOpen, valveDistanceMap);
	}

	public static void solveB() {
	}

	public static int findMaxPotentialPressure(String curValve, int curMinute, Set<String> valvesToOpen,
			Map<String, ValveWithDistances> valveDistanceMap) {
		int maxPotentialPressure = 0;
		if (valvesToOpen.isEmpty())
			return maxPotentialPressure;

		for (String nextValve : valvesToOpen) {
			int dist = valveDistanceMap.get(curValve).getDistanceTo(nextValve);
			int openMinute = curMinute + dist + OPEN_TIME;
			if (openMinute > 30)
				continue;
			int curPotentialPressure = (MINUTES - openMinute) * valveDistanceMap.get(nextValve).rate();
			var newValvesToOpen = new HashSet<>(valvesToOpen);
			newValvesToOpen.remove(nextValve);
			curPotentialPressure += findMaxPotentialPressure(nextValve, openMinute, newValvesToOpen, valveDistanceMap);
			if (curPotentialPressure > maxPotentialPressure) {
				maxPotentialPressure = curPotentialPressure;
			}
		}

		return maxPotentialPressure;
	}

	public static Map<String, ValveWithDistances> findValveShortestPaths(Map<String, Valve> valveMap) {
		var valveDistanceMap = new HashMap<String, ValveWithDistances>();
		for (String valve : valveMap.keySet()) {
			var shortestPathFinder = new DijkstraShortestPathFinder<String>(valveMap.keySet(),
					v -> valveMap.get(v).neighbors());
			valveDistanceMap.put(valve,
					new ValveWithDistances(valve, valveMap.get(valve).rate(), shortestPathFinder.findAll(valve)));
		}
		return valveDistanceMap;
	}

	public record Valve(String name, int rate, List<String> neighbors) {
		public static Valve fromLine(String line) {
			var matcher = VALVE_PATTERN.matcher(line);
			matcher.matches();
			return new Valve(matcher.group(1), Integer.parseInt(matcher.group(2)),
					Arrays.asList(matcher.group(3).split(", ")));
		}
	}

	public record ValveWithDistances(String name, int rate, Map<String, Integer> neighborDistances) {
		public int getDistanceTo(String otherValve) {
			return this.neighborDistances.getOrDefault(otherValve, Integer.MAX_VALUE);
		}
	}

	public static Map<String, Valve> parseInput() {
		return AdventOfCodeUtils.readInput(Day16Main.class).stream().map(Valve::fromLine)
				.collect(Collectors.toMap(v -> v.name(), v -> v));
	}
}
