package day12;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day12Main {

	public static final String START = "start";
	public static final String END = "end";

	public static Map<String, Set<String>> caveMap;

	public static void main(String[] args) {
		caveMap = getInput();

		System.out.println("Paths without revisiting small caves: " + solveA());
		System.out.println("Paths with revisiting a single small cave: " + solveB());
	}

	public static int solveA() {
		return findAllPaths(Day12Main::isBigOrNotVisited).size();
	}

	public static int solveB() {
		return caveMap.keySet().stream().filter(cave -> isSmallCave(cave))
				.filter(smallCave -> !START.equals(smallCave) && !END.equals(smallCave))
				.flatMap(smallCave -> findAllPaths(getCanVisitTwicePredicate(smallCave)).stream())
				.collect(Collectors.toSet()).size();
	}

	public static Set<List<String>> findAllPaths(BiPredicate<String, Collection<String>> canBeVisited) {
		return findPaths(START, new ArrayList<>(), canBeVisited);
	}

	public static Set<List<String>> findPaths(String curCave, List<String> visitedCaves,
			BiPredicate<String, Collection<String>> canBeVisited) {
		Set<List<String>> paths = new HashSet<>();
		List<String> updatedPath = new ArrayList<>(visitedCaves);
		updatedPath.add(curCave);

		if (END.equals(curCave)) {
			paths.add(updatedPath);
			return paths;
		} else {
			Set<String> nextCaves = caveMap.get(curCave).stream()
					.filter(nextCave -> canBeVisited.test(nextCave, visitedCaves)).collect(Collectors.toSet());
			nextCaves.forEach(nextCave -> paths.addAll(findPaths(nextCave, updatedPath, canBeVisited)));
			return paths;
		}
	}

	public static boolean isBigOrNotVisited(String cave, Collection<String> visitedCaves) {
		return !isSmallCave(cave) || !visitedCaves.contains(cave);
	}

	public static BiPredicate<String, Collection<String>> getCanVisitTwicePredicate(String canBeVisitedTwice) {
		BiPredicate<String, Collection<String>> initCondition = Day12Main::isBigOrNotVisited;
		return initCondition.or((cave,
				visitedCaves) -> (canBeVisitedTwice.equals(cave) && Collections.frequency(visitedCaves, cave) < 2));
	}

	public static boolean isSmallCave(String cave) {
		return cave.chars().mapToObj(code -> new Character((char) code)).allMatch(Character::isLowerCase);
	}

	public static Map<String, Set<String>> getInput() {
		Map<String, Set<String>> caveMap = new HashMap<>();
		List<String> input = AdventOfCodeUtils.readInput(Day12Main.class);
		for (String line : input) {
			String[] lineSplit = line.split("-");
			String caveA = lineSplit[0];
			String caveB = lineSplit[1];

			putPathInMap(caveA, caveB, caveMap);
			putPathInMap(caveB, caveA, caveMap);
		}
		return caveMap;
	}

	public static void putPathInMap(String caveA, String caveB, Map<String, Set<String>> caveMap) {
		Set<String> curPaths = caveMap.getOrDefault(caveA, new HashSet<>());
		curPaths.add(caveB);
		caveMap.put(caveA, curPaths);
	}
}
