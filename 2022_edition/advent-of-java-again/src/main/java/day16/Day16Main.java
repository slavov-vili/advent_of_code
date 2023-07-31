package day16;

import utils.AdventOfCodeUtils;
import utils.DijkstraShortestPathFinder;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16Main {
	public static final Pattern VALVE_PATTERN = Pattern
			.compile("Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)");
	public static final int MINUTES = 30;
	public static final int OPEN_TIME = 1;
	public static final String START_VALVE = "AA";

	public static final int ELEPHANT_TRAINING_TIME = 4;
	public static final int MINUTES_WITH_ELEPHANT = MINUTES - ELEPHANT_TRAINING_TIME;

	public static void main(String[] args) {
		var valveDistanceMap = findValveShortestPaths(parseInput());

		System.out.println("Max pressure = " + solveA(valveDistanceMap));

		System.out.println("Max pressure with elephant = " + solveB(valveDistanceMap));
	}

	public static int solveA(Map<String, ValveWithDistances> valveDistanceMap) {
		var valvesToOpen = valveDistanceMap.keySet().stream().filter(v -> valveDistanceMap.get(v).rate() > 0 ).collect(Collectors.toSet());
		return findMaxPotentialPressure(START_VALVE, 0, valvesToOpen, valveDistanceMap);
	}

	public static int solveB(Map<String, ValveWithDistances> valveDistanceMap) {
		var cleanValveDistanceMap = new HashMap<>(valveDistanceMap);
		valveDistanceMap.keySet().stream().filter(v -> valveDistanceMap.get(v).rate() == 0 && !START_VALVE.equals(v)).forEach(cleanValveDistanceMap::remove);
		var pathPressures = mapPathsToPressure(List.of(START_VALVE), 0, 0, cleanValveDistanceMap);
		pathPressures.remove(List.of(START_VALVE));
		var cleanPathPressures = pathPressures.keySet().stream()
				.filter(p -> p.size() >= 7) // otherwise it will never finish...
				.collect(Collectors.toMap(p -> p.subList(1, p.size()), pathPressures::get));

		return findMaxIndependentPairs(cleanPathPressures);
	}

	public static int findMaxPotentialPressure(String curValve, int curMinute, Set<String> valvesToOpen,
			Map<String, ValveWithDistances> valveDistanceMap) {
		int maxPotentialPressure = 0;
		if (valvesToOpen.isEmpty())
			return maxPotentialPressure;

		for (String nextValve : valvesToOpen) {
			int dist = valveDistanceMap.get(curValve).getDistanceTo(nextValve);
			int openMinute = curMinute + dist + OPEN_TIME;
			if (openMinute >= MINUTES)
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

	public static Map<List<String>, Integer> mapPathsToPressure(List<String> curPath, int curPressure, int curMinute,
																Map<String, ValveWithDistances> valveDistanceMap) {
		var nextValves = new HashSet<>(valveDistanceMap.keySet());
		curPath.forEach(nextValves::remove);

		var openMinutesMap = nextValves.stream().collect(Collectors.toMap(v -> v, v ->
				curMinute + valveDistanceMap.get(curPath.get(curPath.size()-1)).getDistanceTo(v) + OPEN_TIME));
		openMinutesMap.keySet().stream().filter(v -> openMinutesMap.get(v) >= MINUTES_WITH_ELEPHANT).forEach(nextValves::remove);

		if (nextValves.isEmpty())
			return Map.of(curPath, curPressure);

		var pathsToPressure = new HashMap<List<String>, Integer>();
		pathsToPressure.put(curPath, curPressure);
		for (String nextValve : nextValves) {
			int openMinute = openMinutesMap.get(nextValve);
			int nextPressure = curPressure + (MINUTES_WITH_ELEPHANT - openMinute) * valveDistanceMap.get(nextValve).rate();
			var nextPath = new ArrayList<>(curPath);
			nextPath.add(nextValve);
			var newPathsToPressure = mapPathsToPressure(nextPath, nextPressure, openMinute, valveDistanceMap);
			for (var path : newPathsToPressure.keySet()) {
				int prevPressure = pathsToPressure.getOrDefault(path, 0);
				pathsToPressure.put(path, Integer.max(prevPressure, newPathsToPressure.get(path)));
			}
		}

		return pathsToPressure;
	}

	public static int findMaxIndependentPairs(Map<List<String>, Integer> pathPressures) {
		int count = 0;
		// TODO: try using indices instead, maybe takes less heap space?
		var pairPathPressures = new HashMap<Set<List<String>>, Integer>();
		for (var path : pathPressures.keySet()) {
			count++;
			System.out.printf("Finding pairs %d / %d\n", count, pathPressures.size());
			var nextPaths = pathPressures.keySet().stream()
					.filter(p -> p.stream().noneMatch(path::contains)) // are independent
					.filter(p -> !pairPathPressures.containsKey(Set.of(path, p))).toList(); // pair hasn't been seen
			var newPairPathPressures = nextPaths.stream()
					.collect(Collectors.toMap(p -> Set.of(path, p), p -> pathPressures.get(path) + pathPressures.get(p)));
			pairPathPressures.putAll(newPairPathPressures);
		}

		return pairPathPressures.values().stream().max(Integer::compare).orElseGet(() -> 0);
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

	public static Map<String, Valve> parseInput() {
		return AdventOfCodeUtils.readInput(Day16Main.class).stream().map(Valve::fromLine)
				.collect(Collectors.toMap(Valve::name, v -> v));
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
}
