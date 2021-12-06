package day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day06Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day06Main.class);
		List<Integer> fishAges = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt)
				.collect(Collectors.toList());

		System.out.println("Fish after 80 days: " + solveA(fishAges));
		
		System.out.println("Fish after 256 days: " + solveB(fishAges));
	}
	
	public static int solveA(List<Integer> fishAges) {
		return simulateNDaysA(fishAges, 80).size();
	}
	
	public static long solveB(List<Integer> fishAges) {
		Map<Integer, Long> fishAgeCountMap = new HashMap<>();
		fishAges.forEach(age -> updateCountMapBy(fishAgeCountMap, age, 1L));
		
		return simulateNDaysB(fishAgeCountMap, 256).entrySet().stream()
				.mapToLong(Map.Entry::getValue).sum();
	}
	
	public static List<Integer> simulateNDaysA(List<Integer> fishAges, int n) {
		List<Integer> finalAges = new ArrayList<>(fishAges);
		for (int i=0; i<n; i++) {
			System.out.println("Day " + i);
			finalAges = simulateDayA(finalAges);
		}
		return finalAges;
	}

	public static List<Integer> simulateDayA(List<Integer> fishAges) {
		List<Integer> births = fishAges.stream().filter(age -> age == 0).map(age -> 8).collect(Collectors.toList());
		List<Integer> nextAges = fishAges.stream().map(Day06Main::calcNextAge).collect(Collectors.toList());
		nextAges.addAll(births);
		return nextAges;
	}
	
	public static Map<Integer, Long> simulateNDaysB(Map<Integer, Long> fishAgeCountMap, int n) {
		Map<Integer, Long> finalFishAgeCountMap = new HashMap<>(fishAgeCountMap);
		for (int i=0; i<n; i++) {
			System.out.println("Day " + (i+1));
			finalFishAgeCountMap = simulateDayB(finalFishAgeCountMap);
		}
		return finalFishAgeCountMap;
	}
	
	public static Map<Integer, Long> simulateDayB(Map<Integer, Long> fishAgeCountMap) {
		Map<Integer, Long> nextFishAgeCountMap = new HashMap<>(fishAgeCountMap);
		long births = fishAgeCountMap.getOrDefault(0, 0L);
		
		for(int age : fishAgeCountMap.keySet()) {	
			int newAge = calcNextAge(age);

			updateCountMapBy(nextFishAgeCountMap, age, -fishAgeCountMap.getOrDefault(age, 0L));
			updateCountMapBy(nextFishAgeCountMap, newAge, fishAgeCountMap.getOrDefault(age, 0L));
		}
		
		updateCountMapBy(nextFishAgeCountMap, 8, births);
		return nextFishAgeCountMap;
	}
	
	public static int calcNextAge(int age) {
		if (age == 0)
			return 6;
		else
			return age - 1;
	}
	
	public static void updateCountMapBy(Map<Integer, Long> countMap, Integer keyToUpdate, long offset) {
		Long newValue = countMap.getOrDefault(keyToUpdate, 0L) + offset;
		countMap.put(keyToUpdate, newValue);
	}
 }
