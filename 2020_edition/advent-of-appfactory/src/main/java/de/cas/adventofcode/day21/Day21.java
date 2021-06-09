package de.cas.adventofcode.day21;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.cas.adventofcode.shared.Day;

public class Day21 extends Day<String> {
	private static final String NO_ALLERGEN = "";
	private static final Pattern PATTERN = Pattern.compile("([a-zA-Z ]*) \\(contains ([a-zA-Z ,]*)\\)");

	protected Day21() {
		super(21);
	}

	public static void main(final String[] args) {
		new Day21().run();
	}

	@Override
	public String solvePart1(List<String> input) {
		Map<String, Set<String>> allergenIngredientsMap = createAllergenIngredientsMap(input);
		Set<String> safeIngredients = allergenIngredientsMap.get(NO_ALLERGEN);
		allergenIngredientsMap.remove(NO_ALLERGEN);
		
		return String.valueOf(safeIngredients.stream()
				.mapToLong(ingredient -> input.stream()
						.filter(line -> (" " + line).contains(" " + ingredient + " "))
						.count()).sum());
	}

	@Override
	public String solvePart2(List<String> input) {
		Map<String, Set<String>> allergenIngredientsMap = createAllergenIngredientsMap(input);
		allergenIngredientsMap.remove(NO_ALLERGEN);
		
		Map<String, String> ingredientAllergenMap = resolveAllergens(allergenIngredientsMap);
		List<String> orderedIngredients = ingredientAllergenMap.keySet().stream()
				.sorted(Comparator.comparing(ingredient -> ingredientAllergenMap.get(ingredient)))
				.collect(Collectors.toList());
		
		return String.join(",", orderedIngredients);
	}
	
	private Map<String, String> resolveAllergens(Map<String, Set<String>> allergenIngredientsMap) {
		Map<String, String> ingredientAllergenMap = new HashMap<>();
		Set<String> unresolvedAllergens = new HashSet<>(allergenIngredientsMap.keySet());
		
		while (ingredientAllergenMap.size() != allergenIngredientsMap.size()) {
			for (String allergen : unresolvedAllergens) {
				Set<String> potentialIngredients = new HashSet<>(allergenIngredientsMap.get(allergen));
				
				potentialIngredients.removeAll(ingredientAllergenMap.keySet());
				if (potentialIngredients.size() == 1)
					potentialIngredients.forEach(ingredient -> ingredientAllergenMap.put(ingredient, allergen));
			}
			
			unresolvedAllergens.removeAll(ingredientAllergenMap.values());
		}
		return ingredientAllergenMap;
	}
	
	private Map<String, Set<String>> createAllergenIngredientsMap(List<String> input) {
		Map<String, Set<String>> allergenIngredientsMap = new HashMap<>();
		Set<String> safeIngredients = new HashSet<>();
		for(String line: input) {
			Matcher matcher = PATTERN.matcher(line);
			matcher.find();
			
			List<String> ingredients = Arrays.asList(matcher.group(1).split(" "));
			List<String> allergens = Arrays.asList(matcher.group(2).split(", "));
			
			safeIngredients.addAll(ingredients);
			for (String allergen : allergens) {
				allergenIngredientsMap.putIfAbsent(allergen, new HashSet<>(ingredients));
				allergenIngredientsMap.get(allergen).retainAll(ingredients);
			}
		}
		
		allergenIngredientsMap.values().forEach(value -> safeIngredients.removeAll(value));
		allergenIngredientsMap.put(NO_ALLERGEN, safeIngredients);
		return allergenIngredientsMap;
	}

}
