package de.cas.adventofcode.shared;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListUtils {
	public static List<String> fromString(String strToParse) {
		return Arrays.asList(strToParse.split("(\r)?\n"));
	}
	
	public static <T> List<List<T>> getColumns(List<List<T>> rows) {
		return IntStream.range(0, rows.get(0).size())
		.mapToObj(i -> rows.stream().map(row -> row.get(i))
				.collect(Collectors.toList()))
		.collect(Collectors.toList());
	}
}
