package day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import utils.AdventOfCodeUtils;

public class Day19Main {

	// Orientations: assume that the scanner is in a specific orientation and
	// convert the coordinates
	// into the standard orientation (x -> right, y -> up, z -> closed to the
	// person)
	public static final Function<int[], int[]> RIGHT = (coords -> new int[] { getX(coords), getY(coords),
			getZ(coords) });
	public static final Function<int[], int[]> LEFT = (coords -> new int[] { -getX(coords), getY(coords),
			getZ(coords) });
	public static final List<Function<int[], int[]>> HORIZONTAL = Arrays.asList(RIGHT, LEFT);

	public static final Function<int[], int[]> FRONT = (coords -> new int[] { getX(coords), getY(coords),
			-getZ(coords) });
	public static final Function<int[], int[]> BACK = (coords -> new int[] { getX(coords), getY(coords),
			getZ(coords) });
	public static final List<Function<int[], int[]>> DEPTH = Arrays.asList(FRONT, BACK);

	public static final Function<int[], int[]> UP = (coords -> new int[] { getX(coords), getY(coords), getZ(coords) });
	public static final Function<int[], int[]> DOWN = (coords -> new int[] { getX(coords), -getY(coords),
			getZ(coords) });
	public static final List<Function<int[], int[]>> VERTICAL = Arrays.asList(UP, DOWN);

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day19Main.class);
		int[] coords = new int[] { -1, -1, 1 };
		getAllOrientations().forEach(orientation -> System.out.println(Arrays.toString(orientation.apply(coords))));
	}

	// TODO: double check originals, add all and then rotate each
	public static Set<Function<int[], int[]>> getAllOrientations() {
		Set<Function<int[], int[]>> allOrientations = new HashSet<>();

		for (Function<int[], int[]> horiz : HORIZONTAL) {
			for (Function<int[], int[]> vert : VERTICAL) {
				for (Function<int[], int[]> depth : DEPTH) {
					allOrientations.add(horiz.andThen(vert).andThen(depth));
				}
			}
		}
		return allOrientations;
	}

	public static int getX(int[] coords) {
		return coords[0];
	}

	public static int getY(int[] coords) {
		return coords[1];
	}

	public static int getZ(int[] coords) {
		return coords[2];
	}
}
