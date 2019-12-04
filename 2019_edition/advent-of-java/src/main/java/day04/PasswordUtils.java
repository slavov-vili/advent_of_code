package day04;

import java.util.List;

import utils.AdventOfCodeUtils;

public class PasswordUtils {
	private static final int PASSWORD_LENGTH = 6;
	private static final Integer RANGE_START = 246515;
	private static final Integer RANGE_END = 739105;
	private static final int CONSECUTIVE_DIGIT_COUNT = 2;
	private static final String consecutiveDigitsPattern = "(\\d)\\1+";

	
	
	// TODO: Change names to "checkForPartX"
	// TODO: Split everything into separate util classes
	public static boolean checkForDay04PartA(Integer password) {
		return checkBaseRequirementsDay04(password) && hasSequenceOfSameDigits(password)
				&& isAlwaysIncreasing(password);
	}

	public static boolean checkForDay04PartB(Integer password) {
		return checkBaseRequirementsDay04(password) && hasSequenceOfSameDigits(password, CONSECUTIVE_DIGIT_COUNT)
				&& isAlwaysIncreasing(password);
	}

	public static boolean checkBaseRequirementsDay04(Integer password) {
		return AdventOfCodeUtils.checkIntegerLength(password, PASSWORD_LENGTH)
				&& AdventOfCodeUtils.checkIfIntegerWithinRange(password, RANGE_START, RANGE_END + 1);
	}

	
	
	public static boolean hasSequenceOfSameDigits(Integer password) {
		return hasSequenceOfSameChars(String.valueOf(password), consecutiveDigitsPattern);

	}

	public static boolean hasSequenceOfSameChars(String stringToCheck, String sequenceOfSameCharsPattern) {
		return stringToCheck.matches(".*" + sequenceOfSameCharsPattern + ".*");
	}

	public static boolean hasSequenceOfSameDigits(Integer password, int sequenceLength) {
		return hasSequenceOfSameChars(String.valueOf(password), consecutiveDigitsPattern, sequenceLength);
	}

	public static boolean hasSequenceOfSameChars(String stringToCheck, String sequenceOfSameCharsPattern,
			int sequenceLength) {
		List<String> sequencesOfSameChars = AdventOfCodeUtils.getAllMatches(stringToCheck, sequenceOfSameCharsPattern);
		return sequencesOfSameChars.stream().anyMatch(seq -> seq.length() == sequenceLength);
	}

	
	
	public static boolean isAlwaysIncreasing(Integer password) {
		return isAlwaysIncreasing(String.valueOf(password));
	}

	public static boolean isAlwaysIncreasing(String stringToCheck) {
		boolean result = true;
		for (int i = 0; i < stringToCheck.length() - 1; i++)
			if (stringToCheck.charAt(i) > stringToCheck.charAt(i + 1))
				return false;
		return result;
	}
}
