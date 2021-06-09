package de.cas.adventofcode.day14;

import de.cas.adventofcode.shared.Day;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 extends Day<Long> {
	protected Day14() {
		super(14);
	}

	public static void main(final String[] args) {
		new Day14().run();
	}

	@Override
	public Long solvePart1(final List<String> input) {
		return new SeaPortComputerPart1().run(input);
	}

	@Override
	public Long solvePart2(final List<String> input) {
		return new SeaPortComputerPart2().run(input);
	}
}
