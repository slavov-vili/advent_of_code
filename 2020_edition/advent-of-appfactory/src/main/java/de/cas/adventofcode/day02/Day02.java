package de.cas.adventofcode.day02;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import de.cas.adventofcode.shared.Day;

public class Day02 extends Day<Integer> {
	Pattern pattern = Pattern.compile("([0-9]+)\\-([0-9]+) ([a-z])\\: ([a-z]+)");

	protected Day02() {
		super(2);
	}

	public static void main(final String[] args) {
		new Day02().run();
	}

	@Override
	public Integer solvePart1(List<String> input) {
		int validPasswordsCount = 0;
		for (String line: input) {
			Matcher matcher = pattern.matcher(line);
			if (!matcher.find()) {
				continue;
			}
			int min = Integer.parseInt(matcher.group(1));
			int max = Integer.parseInt(matcher.group(2));
			char wantedLetter = matcher.group(3).charAt(0);
			String password = matcher.group(4);

			int countOfLetters = StringUtils.countMatches(password, wantedLetter);
			if (countOfLetters >= min && countOfLetters <= max){
				validPasswordsCount ++;
			}
		}
		return validPasswordsCount;
	}

	@Override
	public Integer solvePart2(List<String> input) {
		int validPasswordsCount = 0;
		for (String line: input) {
			Matcher matcher = pattern.matcher(line);
			if (!matcher.find()) {
				continue;
			}
			int index1 = Integer.parseInt(matcher.group(1));
			int index2 = Integer.parseInt(matcher.group(2));
			char wantedLetter = matcher.group(3).charAt(0);
			String password = matcher.group(4);

			if (password.charAt(index1 - 1) == wantedLetter ^ password.charAt(index2 - 1) == wantedLetter){
				validPasswordsCount ++;
			}
		}
		return validPasswordsCount;
	}
}
