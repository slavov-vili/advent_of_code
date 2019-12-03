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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
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
     * Generates a list of all points which exist within the area of the square
     * that is formed after connecting the given arguments.
     */
    public static Set<Point> generatePointsInArea(Point a, Point b) {
    	Set<Point> pointsInArea = new HashSet();
    	List<Integer> rangeX = generateRange(a.x, b.x);
    	List<Integer> rangeY = generateRange(a.y, b.y);
    	
    	for (Integer x : rangeX)
    		for (Integer y : rangeY)
    			pointsInArea.add(new Point(x, y));
    	
    	return pointsInArea;
    }
    
    public static int calcManhattanDistance(Point a, Point b) {
    	return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    
    /*
     * Generates a range of the numbers given the beginning and end values.
     * The range is INCLUSIVE.
     */
    public static List<Integer> generateRange(int firstValue, int lastValue) {
        List<Integer> range = new ArrayList();
        int stepCount = Math.abs(firstValue - lastValue);
        int increment = -compareInts(firstValue, lastValue);
        int lastValueIncremented = lastValue+increment;
        
        int curValue = firstValue;
        do {
        	range.add(curValue);
        	curValue += increment;
        } while(curValue != lastValueIncremented);
        
        return range;
    }
    
    /*
     * Compares two integers and returns:
     *  1 - if the first integer is bigger
     * -1 - if the first integer is smaller
     *  0 - if the two integers are equal
     */
    public static int compareInts(int a, int b) {
        if (a > b)
            return 1;
        else
        	if (a < b)
        		return -1;
        	else
        		return 0;
    }
    
    
    
    public static <T> int mapAndSumList(List<T> inputList, Function<? super T, Integer> functionToApply) {
        return inputList.stream().map(functionToApply).reduce(0, (a, b) -> a + b);
    }

    public static List<Integer> parseAllStringsToInt(List<String> valuesString) {
        return valuesString.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public static <T> List<T> cloneList(List<T> listToClone) {
        return new ArrayList(listToClone);
    }

    public static <T> List<T> getElementsAt(List<T> inputList, List<Integer> indices) {
        List<T> outputList = new ArrayList();

        indices.forEach(idx -> outputList.add(inputList.get(idx)));

        return outputList;
    }
}
