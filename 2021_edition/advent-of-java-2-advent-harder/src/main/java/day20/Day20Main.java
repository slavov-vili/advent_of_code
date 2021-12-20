package day20;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day20Main {
	public static final char DARK_CHAR = '.';
	public static final char LIGHT_CHAR = '#';
	public static final String DARK_BIT = "0";
	public static final String LIGHT_BIT = "1";

	public static Set<Integer> algorithm;

	public static void main(String[] args) {
		algorithm = parseAlgorithm();

		Map<Point, Boolean> image = enhanceImageNTimes(parseImage(), 2);
		System.out.println("Lit pixels after 2: " + getLitPixels(image).size());

		image = enhanceImageNTimes(parseImage(), 50);
		System.out.println("Lit pixels after 50: " + getLitPixels(image).size());
	}

	public static Map<Point, Boolean> enhanceImageNTimes(Map<Point, Boolean> originalImage, int inhanceCount) {
		Map<Point, Boolean> enhancedImage = originalImage;
		boolean infinityLit = false;
		for (int i = 0; i < inhanceCount; i++) {
			enhancedImage = enhanceImage(enhancedImage, infinityLit);
			infinityLit = !infinityLit;
		}
		return enhancedImage;
	}

	public static Map<Point, Boolean> enhanceImage(Map<Point, Boolean> oldImage, boolean infinityLit) {
		Map<Point, Boolean> enhancedImage = new HashMap<>();
		int minX = oldImage.keySet().stream().mapToInt(point -> point.x).min().getAsInt();
		int maxX = oldImage.keySet().stream().mapToInt(point -> point.x).max().getAsInt();
		int minY = oldImage.keySet().stream().mapToInt(point -> point.y).min().getAsInt();
		int maxY = oldImage.keySet().stream().mapToInt(point -> point.y).max().getAsInt();

		for (int y = minY - 1; y <= maxY + 1; y++) {
			for (int x = minX - 1; x <= maxX + 1; x++) {
				Point curPoint = new Point(x, y);
				enhancedImage.put(curPoint, isLight(curPoint, oldImage, infinityLit));
			}
		}
		return enhancedImage;
	}

	public static boolean isLight(Point curPixel, Map<Point, Boolean> oldImage, boolean infinityLit) {
		int indexInAlgorithm = Integer
				.parseInt(getNeighbours(curPixel).stream().map(pixel -> oldImage.getOrDefault(pixel, infinityLit))
						.map(Day20Main::boolToBit).reduce(String::concat).get(), 2);
		return algorithm.contains(indexInAlgorithm);
	}

	public static Set<Point> getLitPixels(Map<Point, Boolean> image) {
		return image.keySet().stream().filter(image::get).collect(Collectors.toSet());
	}

	public static List<Point> getNeighbours(Point curPoint) {
		List<Point> neighbours = new ArrayList<>();

		for (int y = curPoint.y - 1; y <= curPoint.y + 1; y++)
			for (int x = curPoint.x - 1; x <= curPoint.x + 1; x++) {
				neighbours.add(new Point(x, y));
			}
		return neighbours;
	}

	public static boolean isLight(char pixel) {
		return pixel == LIGHT_CHAR;
	}

	public static String boolToBit(boolean isLight) {
		if (isLight)
			return LIGHT_BIT;
		return DARK_BIT;
	}

	public static Set<Integer> parseAlgorithm() {
		Set<Integer> lightPixelIndices = new HashSet<>();
		List<String> input = AdventOfCodeUtils.readInput(Day20Main.class);
		int curPixelIndex = 0;
		for (String line : input) {
			if (line.trim().isEmpty())
				break;

			for (char pixel : line.toCharArray()) {
				if (isLight(pixel))
					lightPixelIndices.add(curPixelIndex);
				curPixelIndex++;
			}
		}

		return lightPixelIndices;
	}

	public static Map<Point, Boolean> parseImage() {
		Map<Point, Boolean> image = new HashMap<>();
		List<String> input = AdventOfCodeUtils.readInput(Day20Main.class);
		int emptyLineIndex = -1;
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).trim().isEmpty()) {
				emptyLineIndex = i;
				break;
			}
		}

		for (int y = 0; y < input.size() - emptyLineIndex - 1; y++) {
			String line = input.get(y + emptyLineIndex + 1);
			char[] pixels = line.toCharArray();
			for (int x = 0; x < pixels.length; x++) {
				char pixel = pixels[x];
				image.put(new Point(x, y), isLight(pixel));
			}
		}

		return image;
	}
}
