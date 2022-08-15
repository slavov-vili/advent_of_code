package day19;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;
import utils.ListUtils;
import utils.ThreeDPoint;

public class Day19Main {
	public static String BEGIN_SCANNER = "--- scanner \\d+ ---";

	public static void main(String[] args) {
		List<Scanner> scanners = parseInput();

		System.out.println("Beacon count: " + solveA(scanners));

		System.out.println("Max Scanner distance: " + solveB(scanners));
	}

	public static int solveA(List<Scanner> scanners) {
		Set<ThreeDPoint> allBeacons = new HashSet<>();
		List<Function<ThreeDPoint, ThreeDPoint>> positionTransformers = findPositionTransformers(scanners);
		for (int i = 0; i < scanners.size(); i++) {
			List<ThreeDPoint> beacons = scanners.get(i).getBeacons();
			for (ThreeDPoint beacon : beacons)
				allBeacons.add(positionTransformers.get(i).apply(beacon));
		}
		return allBeacons.size();
	}

	public static int solveB(List<Scanner> scanners) {
		List<ThreeDPoint> scannerPositions = findPositionTransformers(scanners).stream()
				.map(positionTransformer -> positionTransformer.apply(ThreeDPoint.ORIGIN)).collect(Collectors.toList());
		int maxDistance = 0;
		for (int i = 0; i < scannerPositions.size() - 1; i++) {
			ThreeDPoint curScannerPosition = scannerPositions.get(i);

			for (int j = i + 1; j < scannerPositions.size(); j++) {
				ThreeDPoint otherScannerPosition = scannerPositions.get(j);
				int distance = curScannerPosition.calcManhattanDistanceFrom(otherScannerPosition);

				if (distance > maxDistance)
					maxDistance = distance;
			}
		}

		return maxDistance;
	}

	public static List<Function<ThreeDPoint, ThreeDPoint>> findPositionTransformers(List<Scanner> scanners) {
		List<Function<ThreeDPoint, ThreeDPoint>> positionTransformers = ListUtils.generateAndInitialize(scanners.size(),
				null);
		positionTransformers.set(0, Scanner.ORIENTATIONS.get(0));

		List<Integer> unprocessedScannerIndices = IntStream.range(1, scanners.size()).mapToObj(index -> index)
				.collect(Collectors.toList());
		Queue<Integer> nextIndices = new ArrayDeque<>();
		nextIndices.add(0);

		while (!nextIndices.isEmpty()) {
			int curScannerIndex = nextIndices.poll();
			List<Integer> overlappingIndices = new ArrayList<>();
			for (int nextScannerIndex : unprocessedScannerIndices) {
				Optional<Function<ThreeDPoint, ThreeDPoint>> positionTransformer = scanners.get(curScannerIndex)
						.findPositionTransformerFor(scanners.get(nextScannerIndex));

				if (positionTransformer.isPresent()) {
					positionTransformers.set(nextScannerIndex,
							positionTransformer.get().andThen(positionTransformers.get(curScannerIndex)));
					overlappingIndices.add(nextScannerIndex);
				}
			}
			nextIndices.addAll(overlappingIndices);
			unprocessedScannerIndices.removeAll(overlappingIndices);
		}

		return positionTransformers;
	}

	public static List<Scanner> parseInput() {
		List<Scanner> scanners = new ArrayList<>();
		List<String> input = AdventOfCodeUtils.readInput(Day19Main.class);
		Scanner curScanner = new Scanner();
		for (String line : input) {
			if (line.matches(BEGIN_SCANNER))
				curScanner = new Scanner();
			else if (line.isBlank())
				scanners.add(curScanner);
			else
				curScanner.addBeacon(parseLine(line));
		}

		return scanners;
	}

	public static ThreeDPoint parseLine(String line) {
		List<Integer> coords = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
		return new ThreeDPoint(coords.get(0), coords.get(1), coords.get(2));
	}
}
