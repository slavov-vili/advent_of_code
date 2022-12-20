package day16;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
		var valvesToOpen = valveDistanceMap.keySet().stream().filter(v -> valveDistanceMap.get(v).rate() > 0)
				.collect(Collectors.toSet());

		System.out.println("Max pressure = " + solveA(valveDistanceMap, valvesToOpen));

		System.out.println("Max pressure with elephant = " + solveB(valveDistanceMap, valvesToOpen));
	}

	public static int solveA(Map<String, ValveWithDistances> valveDistanceMap, Set<String> valvesToOpen) {
		return findMaxPotentialPressure(START_VALVE, 0, valvesToOpen, valveDistanceMap);
	}

	public static int solveB(Map<String, ValveWithDistances> valveDistanceMap, Set<String> valvesToOpen) {
		return findMaxPotentialPressure(START_VALVE, 0, START_VALVE, 0, 0, valvesToOpen, new HashMap<>(),
				valveDistanceMap);
	}

	public static int findMaxPotentialPressure(String curValve, int curMinute, Set<String> valvesToOpen,
			Map<String, ValveWithDistances> valveDistanceMap) {
		int maxPotentialPressure = 0;
		if (valvesToOpen.isEmpty())
			return maxPotentialPressure;

		for (String nextValve : valvesToOpen) {
			int dist = valveDistanceMap.get(curValve).getDistanceTo(nextValve);
			int openMinute = curMinute + dist + OPEN_TIME;
			if (openMinute > MINUTES)
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

	// FIXME: wtf is happening here, this is SO complicated!
	public static int findMaxPotentialPressure(String myValve, int myFreeMinute, String elephantValve,
			int elephantFreeMinute, int curMinute, Set<String> valvesToOpen, Map<Integer, Integer> stateToMaxPressure,
			Map<String, ValveWithDistances> valveDistanceMap) {
		int maxPotentialPressure = 0;
		if (curMinute > (MINUTES - ELEPHANT_TRAINING_TIME) || valvesToOpen.isEmpty())
			return maxPotentialPressure;

		var curState = getState(myValve, myFreeMinute, elephantValve, elephantFreeMinute, curMinute, valvesToOpen);
		var curReverseState = getState(elephantValve, elephantFreeMinute, myValve, myFreeMinute, curMinute,
				valvesToOpen);
		if (stateToMaxPressure.containsKey(curState))
			return stateToMaxPressure.get(curState);
		else if (stateToMaxPressure.containsKey(curReverseState))
			return stateToMaxPressure.get(curReverseState);
		System.out.printf("Minute %d, myValve = %s, elephantValve = %s\n", curMinute, myValve, elephantValve);

		if (myFreeMinute <= curMinute) {
			System.out.println("I'm free!");
			for (String myNextValve : valvesToOpen) {
				int curPotentialPressure = 0;
				int myOpenMinute = curMinute + valveDistanceMap.get(myValve).getDistanceTo(myNextValve) + OPEN_TIME;

				if (myOpenMinute > MINUTES - ELEPHANT_TRAINING_TIME)
					continue;

				curPotentialPressure = (MINUTES - ELEPHANT_TRAINING_TIME - myOpenMinute)
						* valveDistanceMap.get(myNextValve).rate();
				var remainingValvesToOpen = new HashSet<>(valvesToOpen);
				remainingValvesToOpen.remove(myNextValve);
//				System.out.printf("My next valve %s is %d step(s) away => myPotentialPressure = %d\n", myNextValve,
//						valveDistanceMap.get(myValve).getDistanceTo(myNextValve), curPotentialPressure);
				int state = getState(myNextValve, myOpenMinute, elephantValve, elephantFreeMinute, curMinute,
						remainingValvesToOpen);
				int reverseState = getState(elephantValve, elephantFreeMinute, myNextValve, myOpenMinute, curMinute,
						remainingValvesToOpen);
				if (stateToMaxPressure.containsKey(state))
					return stateToMaxPressure.get(state);
				else if (stateToMaxPressure.containsKey(reverseState))
					return stateToMaxPressure.get(reverseState);

				int elephantMaxPressure = elephantForLoop(myNextValve, myOpenMinute, elephantValve, elephantFreeMinute,
						curMinute, remainingValvesToOpen, stateToMaxPressure, valveDistanceMap);
				stateToMaxPressure.put(state, elephantMaxPressure);
				stateToMaxPressure.put(reverseState, elephantMaxPressure);
				curPotentialPressure += elephantMaxPressure;

				if (curPotentialPressure > maxPotentialPressure)
					maxPotentialPressure = curPotentialPressure;
			}
		} else if (elephantFreeMinute == curMinute) {
			maxPotentialPressure = elephantForLoop(myValve, myFreeMinute, elephantValve, elephantFreeMinute, curMinute,
					valvesToOpen, stateToMaxPressure, valveDistanceMap);
		} else {
			maxPotentialPressure = findMaxPotentialPressure(myValve, myFreeMinute, elephantValve, elephantFreeMinute,
					curMinute + 1, valvesToOpen, stateToMaxPressure, valveDistanceMap);
			curState = getState(myValve, myFreeMinute, elephantValve, elephantFreeMinute, curMinute + 1, valvesToOpen);
			curReverseState = getState(elephantValve, elephantFreeMinute, myValve, myFreeMinute, curMinute + 1,
					valvesToOpen);
		}

		stateToMaxPressure.put(curState, maxPotentialPressure);
		stateToMaxPressure.put(curReverseState, maxPotentialPressure);

		return maxPotentialPressure;
	}

	public static int elephantForLoop(String myNextValve, int myOpenMinute, String elephantValve,
			int elephantFreeMinute, int curMinute, Set<String> valvesToOpen, Map<Integer, Integer> stateToMaxPressure,
			Map<String, ValveWithDistances> valveDistanceMap) {
		int maxPotentialPressure = 0;
		if (elephantFreeMinute <= curMinute) {
			System.out.println("Elephant is free!");
			for (String elephantNextValve : valvesToOpen) {
				int elephantOpenMinute = curMinute
						+ valveDistanceMap.get(elephantValve).getDistanceTo(elephantNextValve) + OPEN_TIME;

				if (elephantOpenMinute > MINUTES - ELEPHANT_TRAINING_TIME)
					continue;

				int curPotentialPressure = (MINUTES - ELEPHANT_TRAINING_TIME - elephantOpenMinute)
						* valveDistanceMap.get(elephantNextValve).rate();
				var remainingValvesToOpen = new HashSet<>(valvesToOpen);
				remainingValvesToOpen.remove(elephantNextValve);

				curPotentialPressure += findMaxPotentialPressure(myNextValve, myOpenMinute, elephantNextValve,
						elephantOpenMinute, curMinute + 1, remainingValvesToOpen, stateToMaxPressure, valveDistanceMap);

				if (curPotentialPressure > maxPotentialPressure) {
					maxPotentialPressure = curPotentialPressure;
				}
			}
		} else {
			maxPotentialPressure = findMaxPotentialPressure(myNextValve, myOpenMinute, elephantValve,
					elephantFreeMinute, curMinute + 1, valvesToOpen, stateToMaxPressure, valveDistanceMap);
		}
		return maxPotentialPressure;
	}

	public static int getState(String valve1, int freeMinute1, String valve2, int freeMinute2, int curMinute,
			Set<String> valvesToOpen) {
		return Objects.hash(valve1, freeMinute1, valve2, freeMinute2, curMinute, valvesToOpen);
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
