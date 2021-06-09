package de.cas.adventofcode.day19;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.cas.adventofcode.shared.Day;

public class Day19Velislav extends Day<Integer> {
	private static final Pattern RULE_PATTERN = Pattern.compile("(\\d+): ([\"ab\\d| ]+)");
	private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");
	
	protected Day19Velislav() {
		super(19);
	}

	public static void main(final String[] args) {
		new Day19Velislav().run();
	}

	@Override
	public Integer solvePart1(List<String> input) {
		if (input.isEmpty())
			return null;
		Map<String, String> idToPattern = compileRules(extractRules(input));
		List<String> lines = extractLines(input, idToPattern.size());
		List<String> matchingLines = findMatchingLines(lines, idToPattern.get("0"));
		return matchingLines.size();
	}

	@Override
	public Integer solvePart2(List<String> input) {
		if (input.isEmpty())
			return null;
		Map<String, String> idToPattern = compileRules(extractRules(input));
		List<String> lines = extractLines(input, idToPattern.size());
		
		return (int)lines.stream()
				.filter(line -> matchesRule0Part2(line, idToPattern))
				.count();
	}
	
	private List<String> findMatchingLines(List<String> lines, String pattern) {
		return lines.stream()
				.filter(line -> line.matches(pattern))
				.collect(Collectors.toList());
	}
	
	private boolean matchesRule0Part2(String stringToCheck, Map<String, String> idToPattern) {
		String pattern42 = idToPattern.get("42");
		String pattern31 = idToPattern.get("31");
		
		int count31 = countMatchesAtEnd(stringToCheck, pattern31);
		if (count31 == 0)
			return false;
		
		String pattern0 = String.format("%s+%s{%d}%s{%d}", pattern42, pattern42, count31, pattern31, count31);
		return stringToCheck.matches(pattern0);
	}
	
	private int countMatchesAtEnd(String stringToCheck, String pattern) {
		int count = 0;
		String sequenceAtEnd = findSequenceAtEnd(stringToCheck, pattern);
		Matcher matcher = Pattern.compile(pattern).matcher(sequenceAtEnd);
		
		while(matcher.find())
			count++;
		return count;
	}
	
	private String findSequenceAtEnd(String stringToCheck, String pattern) {
		Matcher matcher = Pattern.compile("(("+pattern+")+)$").matcher(stringToCheck);
		if(!matcher.find())
			return "";
		
		return matcher.group(1);
	}

	private Map<String, String> compileRules(Map<String, String> idToRule) {
		Map<String, String> idToPattern = new HashMap<>(idToRule);
		Set<String> fullyCompiledIDs = new HashSet<>();
		
		while (fullyCompiledIDs.size() != idToPattern.size()) {
			for (String id : idToPattern.keySet()) {
				if (!fullyCompiledIDs.contains(id)) {
					
					String compiledPattern = resolveReferences(idToPattern.get(id),
							fullyCompiledIDs, idToPattern);
					
					if (!NUMBER_PATTERN.matcher(compiledPattern).find()) {
						compiledPattern = compiledPattern.replace(" ", "");
						compiledPattern = wrapInGroup(compiledPattern);
						fullyCompiledIDs.add(id);	
					}
					
					idToPattern.replace(id, compiledPattern);
				}
			}
		}
		
		return idToPattern;
	}

	private String resolveReferences(String value, Set<String> fullyCompiledIDs,
			Map<String, String> idToCompiledPattern) {
		String newValue = value;
		Matcher numberMatcher = NUMBER_PATTERN.matcher(value);
		
		while (numberMatcher.find()) {
			String reference = numberMatcher.group(1);
			if (fullyCompiledIDs.contains(reference))
				newValue = newValue.replaceFirst(matchWord(reference), idToCompiledPattern.get(reference));
		}
		
		return newValue;
	}

	private Map<String, String> extractRules(List<String> input) {
		Map<String, String> idToRule = new HashMap<>();
		
		for (String line : input) {
			if (line.isBlank())
				break;
			Matcher ruleMatcher = RULE_PATTERN.matcher(line);
			ruleMatcher.find();
			
			idToRule.put(ruleMatcher.group(1), ruleMatcher.group(2).replaceAll("\"", ""));
		}
		
		return idToRule;
	}
	
	private List<String> extractLines(List<String> input, int ruleCount) {
		return input.stream().skip(ruleCount).collect(Collectors.toList());
	}
	
	private String matchWord(String regex) {
		return String.format("\\b%s\\b", regex);
	}
	
	private String wrapInGroup(String regex) {
		return String.format("(%s)", regex);
	}
	
}
