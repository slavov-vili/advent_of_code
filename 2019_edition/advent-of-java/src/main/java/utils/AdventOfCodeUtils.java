package utils;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AdventOfCodeUtils {

	/*
	 * Returns an immutable list of the lines of a given file. If the file does not
	 * exists, it returns an empty list and prints an exception.
	 */
	public static List<String> readClasspathFileLines(Class sourceClass, String fileName) {
		List<String> fileLines = Collections.emptyList();

		try {
			fileLines = getLinesFromClasspathFile(sourceClass, fileName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return fileLines;
	}

	private static List<String> getLinesFromClasspathFile(Class sourceClass, String fileName)
			throws URISyntaxException, IOException {
		URL fileUrl = sourceClass.getResource(fileName);

		if (fileUrl == null) {
			throw new FileNotFoundException();
		}

		File fileToRead = new File(fileUrl.toURI().getPath());
		return Files.readAllLines(fileToRead.toPath());
	}

	
	
	/*
	 * Generates a list of all points which exist within the area of the square that
	 * is formed after connecting the given arguments.
	 * 
	 * The generation first goes horizontally and then vertically!
	 */
	public static List<Point> generatePointsInArea(Point a, Point b) {
		List<Point> pointsInArea = new ArrayList();
		List<Integer> rangeX = generateRange(a.x, b.x+1);
		List<Integer> rangeY = generateRange(a.y, b.y+1);

		for (Integer x : rangeX)
			for (Integer y : rangeY)
				pointsInArea.add(new Point(x, y));

		return pointsInArea;
	}

	public static Point getOriginPoint() {
		return new Point(0, 0);
	}

	public static int calcManhattanDistance(Point a, Point b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	
	// TODO: change everything where there is logic to streams
	// and everything where data is stored - to collections
	public static List<Integer> generateRange(int startInclusive, int endExclusive) {
		List<Integer> range = new ArrayList();
		int increment = -compareInts(startInclusive, endExclusive);
		int lastValueIncremented = endExclusive + increment;

		int curValue = startInclusive;
		do {
			range.add(curValue);
			curValue += increment;
		} while (curValue != lastValueIncremented);

		return range;
	}

	/*
	 * Compares two integers and returns: 1 if the first integer is bigger; -1 if
	 * the first integer is smaller; 0 if the two integers are equal
	 */
	public static int compareInts(int a, int b) {
		if (a > b)
			return 1;
		else if (a < b)
			return -1;
		else
			return 0;
	}

	public static boolean checkIntegerLength(Integer integerToCheck, int lengthToCheck) {
		return String.valueOf(integerToCheck).length() == lengthToCheck;
	}

	public static boolean checkIfIntegerWithinRange(Integer integerToCheck, Integer rangeStartInclusive, Integer rangeEndExclusive) {
		return (rangeStartInclusive <= integerToCheck) && (integerToCheck < rangeEndExclusive);
	}

	
	
	public static List<String> getAllMatches(String inputString, String regex) {
		List<String> matches = new ArrayList();
		Matcher matcher = Pattern.compile(regex).matcher(inputString);
		while (matcher.find())
			matches.add(matcher.group());
		return matches;
	}

	
	
	/*
	 * Applies the given function to each element of the list and sums the results.
	 * 
	 * Since the end result is a sum, the function HAS TO return an Integer!
	 */
	public static <T> int mapAndSumList(List<T> inputList, Function<? super T, Integer> functionToApply) {
		return inputList.stream().map(functionToApply).reduce(0, (a, b) -> a + b);
	}

	public static List<Integer> parseStringsToIntegers(List<String> valuesString) {
		return valuesString.stream().map(Integer::parseInt).collect(Collectors.toList());
	}

	public static <T> List<T> cloneList(List<T> listToClone) {
		return new ArrayList(listToClone);
	}

	public static <T> List<T> getListElementsAt(List<T> inputList, List<Integer> indices) {
		return indices.stream()
				.map(idx -> inputList.get(idx))
				.collect(Collectors.toList());
	}
}
