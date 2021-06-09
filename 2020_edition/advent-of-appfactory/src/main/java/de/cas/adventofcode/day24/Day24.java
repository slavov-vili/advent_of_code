package de.cas.adventofcode.day24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.cas.adventofcode.shared.Day;

public class Day24 extends Day<Integer> {
	private static final List<String> directions = List.of("ne", "nw", "se", "sw", "e", "w");
	private static final Pattern PATTERN = Pattern.compile(String.join("|", directions));
	
	protected Day24() {
		super(24);
	}

	public static void main(final String[] args) {
		new Day24().run();
	}

	@Override
	public Integer solvePart1(List<String> input) {
		List<List<String>> tileDirections = parseTileDirections(input);
		tileDirections.forEach(this::simplifyDirections);
		tileDirections.forEach(this::simplifyDirections);
		tileDirections.forEach(Collections::sort);
		
		Map<List<String>, Integer> tileFlipCountMap = mapToFlipCount(tileDirections);
		
		return (int)tileFlipCountMap.keySet().stream()
				.filter(direction -> !isWhite(direction, tileFlipCountMap))
				.count();
	}

	@Override
	public Integer solvePart2(List<String> input) {
		List<List<String>> tileDirections = parseTileDirections(input);
		tileDirections.forEach(this::simplifyDirections);
		tileDirections.forEach(this::simplifyDirections);
		tileDirections.forEach(Collections::sort);
		
		Map<List<String>, Integer> tileFlipCountMap = mapToFlipCount(tileDirections);
		
		flipForDays(tileFlipCountMap, 100);
		
		return (int)tileFlipCountMap.keySet().stream()
				.filter(direction -> !isWhite(direction, tileFlipCountMap))
				.count();
	}
	
	private void flipForDays(Map<List<String>, Integer> tileFlipCountMap, int days) {
		for (int i=0; i<days; i++) {
			System.out.println("Day " + i);
			Set<List<String>> toFlip = new HashSet<>();
			for (List<String> tile : tileFlipCountMap.keySet()) {
				Set<List<String>> neighboursToFlip = getNeighboursToFlip(tile, tileFlipCountMap);
				
				toFlip.addAll(neighboursToFlip);
			}
			toFlip.forEach(tile -> flipTile(tileFlipCountMap, tile));
		}
	}
	
	private Set<List<String>> getNeighboursToFlip(List<String> curTile,
			Map<List<String>, Integer> tileFlipCountMap) {
		List<List<String>> neighbours = getNeighbours(curTile);
		neighbours.add(curTile);
		return neighbours.stream()
				.filter(neighbour -> shouldFlip(neighbour, tileFlipCountMap))
				.collect(Collectors.toSet());
	}
	
	private boolean shouldFlip(List<String> curTile, Map<List<String>, Integer> tileFlipCountMap) {
		int blackNeighbours = (int)getNeighbours(curTile).stream()
				.filter(neighbour -> !isWhite(neighbour, tileFlipCountMap))
				.count();
		boolean result = false;
		
		if (isWhite(curTile, tileFlipCountMap)) {
			if (blackNeighbours == 2)
				result = true;
		} else {
			if (blackNeighbours == 0 || blackNeighbours > 2)
				result = true;
		}
		
		return result;
	}
	
	private List<List<String>> getNeighbours(List<String> curTile) {
		List<List<String>> neighbourDirections = new ArrayList<>();
		for (String dir : directions) {
			List<String> curNeighbour = new ArrayList<>(curTile);
			curNeighbour.add(dir);
			neighbourDirections.add(curNeighbour);
		}

		neighbourDirections.forEach(this::simplifyDirections);
		neighbourDirections.forEach(this::simplifyDirections);
		neighbourDirections.forEach(Collections::sort);
		return neighbourDirections;
	}
	
	private <T> boolean isWhite(T tile, Map<T, Integer> tileCountMap) {
		return tileCountMap.getOrDefault(tile, 2) % 2 == 0;
	}
	
	private <T> Map<T, Integer> mapToFlipCount(List<T> toTraverse) {
		Map<T, Integer> tileFlipCountMap = new HashMap<>();
		for (T element : toTraverse) {
			flipTile(tileFlipCountMap, element);
		}
		return tileFlipCountMap;
	}

	private <T> void flipTile(Map<T, Integer> directionCountMap, T element) {
		directionCountMap.putIfAbsent(element, 0);
		int curCount = directionCountMap.get(element);
		directionCountMap.put(element, curCount+1);
	}
	
	private void simplifyDirections(List<String> tileDirections) {
		// Remove cancellations
		while (tileDirections.contains("nw") && tileDirections.contains("se")) {
			tileDirections.remove("nw");
			tileDirections.remove("se");
		}
		while (tileDirections.contains("ne") && tileDirections.contains("sw")) {
			tileDirections.remove("ne");
			tileDirections.remove("sw");
		}
		while (tileDirections.contains("e") && tileDirections.contains("w")) {
			tileDirections.remove("e");
			tileDirections.remove("w");
		}
		
		// Simplify East/West
		while (tileDirections.contains("ne") && tileDirections.contains("se")) {
			tileDirections.remove("ne");
			tileDirections.remove("se");
			tileDirections.add("e");
		}
		while (tileDirections.contains("nw") && tileDirections.contains("sw")) {
			tileDirections.remove("nw");
			tileDirections.remove("sw");
			tileDirections.add("w");
		}
		
		// Simplify North
		while (tileDirections.contains("ne") && tileDirections.contains("w")) {
			tileDirections.remove("ne");
			tileDirections.remove("w");
			tileDirections.add("nw");
		}
		while (tileDirections.contains("nw") && tileDirections.contains("e")) {
			tileDirections.remove("nw");
			tileDirections.remove("e");
			tileDirections.add("ne");
		}
		
		// Simplify South
		while (tileDirections.contains("se") && tileDirections.contains("w")) {
			tileDirections.remove("se");
			tileDirections.remove("w");
			tileDirections.add("sw");
		}
		while (tileDirections.contains("sw") && tileDirections.contains("e")) {
			tileDirections.remove("sw");
			tileDirections.remove("e");
			tileDirections.add("se");
		}
	}
	
	private List<List<String>> parseTileDirections(List<String> input) {
		List<List<String>> tileDirections = new ArrayList<>();
		
		for (String line : input) {
			List<String> curDirections = new ArrayList<>();
			Matcher dirMatcher = PATTERN.matcher(line);
			while (dirMatcher.find())
				curDirections.add(dirMatcher.group());
			tileDirections.add(curDirections);
		}
		
		return tileDirections;
	}
}
