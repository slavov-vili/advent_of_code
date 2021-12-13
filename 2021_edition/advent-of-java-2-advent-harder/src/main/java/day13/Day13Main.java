package day13;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.AdventOfCodeUtils;

public class Day13Main {

	public static final String FOLD_X = "fold along x=";
	public static final String FOLD_Y = "fold along y=";

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day13Main.class);
		Set<Point> dots = extractDots(input);
		List<String> folds = input.subList(dots.size() + 1, input.size());

		System.out.println("Visible dots after first fold: " + solveA(dots, folds));

		System.out.println("Message after all fold:");
		System.out.println(solveB(dots, folds));
	}

	public static int solveA(Set<Point> dots, List<String> folds) {
		return foldOnce(dots, folds.get(0)).size();
	}

	public static String solveB(Set<Point> dots, List<String> folds) {
		Set<Point> finalDots = new HashSet<>(dots);
		for (String fold : folds) {
			finalDots = foldOnce(finalDots, fold);
		}
		return buildMessage(finalDots);
	}

	public static Set<Point> foldOnce(Set<Point> dots, String fold) {
		int foldIndex = Integer.parseInt(fold.substring(FOLD_X.length(), fold.length()));
		if (fold.startsWith(FOLD_X))
			return foldOnce(dots, new Point(foldIndex, Integer.MAX_VALUE));
		else
			return foldOnce(dots, new Point(Integer.MAX_VALUE, foldIndex));
	}

	public static Set<Point> foldOnce(Set<Point> dots, Point foldPoint) {
		Set<Point> newDots = new HashSet<>();
		for (Point dot : dots) {
			int x = dot.x;
			int y = dot.y;
			if (x > foldPoint.x)
				x = 2 * foldPoint.x - x;
			if (y > foldPoint.y)
				y = 2 * foldPoint.y - y;

			newDots.add(new Point(x, y));
		}
		return newDots;
	}

	public static String buildMessage(Set<Point> dots) {
		StringBuilder sb = new StringBuilder();
		int minX = dots.stream().mapToInt(dot -> dot.x).min().getAsInt();
		int maxX = dots.stream().mapToInt(dot -> dot.x).max().getAsInt();
		int minY = dots.stream().mapToInt(dot -> dot.y).min().getAsInt();
		int maxY = dots.stream().mapToInt(dot -> dot.y).max().getAsInt();

		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				if (dots.contains(new Point(x, y)))
					sb.append("#");
				else
					sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static Set<Point> extractDots(List<String> input) {
		Set<Point> dots = new HashSet<>();
		int i = 0;
		while (!input.get(i).trim().isEmpty()) {
			String[] lineSplit = input.get(i).split(",");
			int x = Integer.parseInt(lineSplit[0]);
			int y = Integer.parseInt(lineSplit[1]);
			dots.add(new Point(x, y));
			i++;
		}
		return dots;
	}
}
