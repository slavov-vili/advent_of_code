package day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import day21.Pair;
import utils.ThreeDPoint;

public class Scanner {
	private List<ThreeDPoint> beacons;

	public static final Function<ThreeDPoint, ThreeDPoint> NEUTRAL = beacon -> new ThreeDPoint(beacon.x, beacon.y,
			beacon.z);

	public static final Function<ThreeDPoint, ThreeDPoint> TURN_UP = beacon -> new ThreeDPoint(beacon.x, -beacon.z,
			beacon.y);
	public static final Function<ThreeDPoint, ThreeDPoint> TURN_DOWN = beacon -> new ThreeDPoint(beacon.x, beacon.z,
			-beacon.y);
	public static final List<Function<ThreeDPoint, ThreeDPoint>> ROTATE_X = Arrays.asList(TURN_UP, TURN_DOWN);

	public static final Function<ThreeDPoint, ThreeDPoint> TURN_RIGHT = beacon -> new ThreeDPoint(-beacon.z, beacon.y,
			beacon.x);
	public static final Function<ThreeDPoint, ThreeDPoint> TURN_BACK = TURN_RIGHT.andThen(TURN_RIGHT);
	public static final Function<ThreeDPoint, ThreeDPoint> TURN_LEFT = TURN_BACK.andThen(TURN_RIGHT);
	public static final List<Function<ThreeDPoint, ThreeDPoint>> ROTATE_Y = Arrays.asList(NEUTRAL, TURN_RIGHT,
			TURN_BACK, TURN_LEFT);

	public static final Function<ThreeDPoint, ThreeDPoint> LIE_RIGHT = beacon -> new ThreeDPoint(-beacon.y, beacon.x,
			beacon.z);
	public static final Function<ThreeDPoint, ThreeDPoint> LIE_FACE_DOWN = LIE_RIGHT.andThen(LIE_RIGHT);
	public static final Function<ThreeDPoint, ThreeDPoint> LIE_LEFT = LIE_FACE_DOWN.andThen(LIE_RIGHT);
	public static final List<Function<ThreeDPoint, ThreeDPoint>> ROTATE_Z = Arrays.asList(NEUTRAL, LIE_RIGHT,
			LIE_FACE_DOWN, LIE_LEFT);

	public static final List<Function<ThreeDPoint, ThreeDPoint>> ORIENTATIONS = getAllOrientations();

	public Scanner() {
		this.beacons = new ArrayList<>();
	}

	public Optional<Function<ThreeDPoint, ThreeDPoint>> findPositionTransformerFor(Scanner otherScanner) {
		List<Pair<ThreeDPoint, ThreeDPoint>> overlaps = new ArrayList<>();
		Function<ThreeDPoint, ThreeDPoint> orientation = ORIENTATIONS.get(0);

		for (Function<ThreeDPoint, ThreeDPoint> curOrientation : ORIENTATIONS) {
			Scanner orientedScanner = new Scanner();
			otherScanner.beacons.stream().map(curOrientation).forEach(orientedScanner::addBeacon);

			List<Pair<Integer, Integer>> curOverlaps = this.findIntersects(orientedScanner);
			if (curOverlaps.size() > overlaps.size()) {
				overlaps = curOverlaps.stream().map(pair -> new Pair<>(this.getBeacon(pair.getValue1()),
						orientedScanner.getBeacon(pair.getValue2()))).collect(Collectors.toList());
				orientation = curOrientation;
			}
		}

		if (overlaps.size() < 12)
			return Optional.empty();
		else {
			Pair<ThreeDPoint, ThreeDPoint> anyOverlap = overlaps.get(0);
			ThreeDPoint position = anyOverlap.getValue1().calcRelativePositionOf(anyOverlap.getValue2());
			final Function<ThreeDPoint, ThreeDPoint> rightOrientation = orientation;
			return Optional.of(point -> position.translate(rightOrientation.apply(point)));
		}
	}

	public List<Pair<Integer, Integer>> findIntersects(Scanner otherScanner) {
		List<ThreeDPoint> otherBeacons = otherScanner.getBeacons();
		Map<ThreeDPoint, List<Pair<Integer, Integer>>> distToIndexMap = new HashMap<>();

		for (int myBeaconIndex = 0; myBeaconIndex < this.beacons.size(); myBeaconIndex++) {
			List<ThreeDPoint> distances = otherScanner.calcDistancesFrom(this.getBeacon(myBeaconIndex));

			for (int otherBeaconIndex = 0; otherBeaconIndex < otherBeacons.size(); otherBeaconIndex++) {
				ThreeDPoint distance = distances.get(otherBeaconIndex);

				List<Pair<Integer, Integer>> indexPairs = distToIndexMap.getOrDefault(distance, new ArrayList<>());
				indexPairs.add(new Pair<>(myBeaconIndex, otherBeaconIndex));

				distToIndexMap.put(distance, indexPairs);
			}
		}

		return distToIndexMap.values().stream().max((map1, map2) -> Integer.compare(map1.size(), map2.size())).get();
	}

	private List<ThreeDPoint> calcDistancesFrom(ThreeDPoint otherBeacon) {
		return this.getBeacons().stream().map(beacon -> beacon.calc3DDistanceFrom(otherBeacon))
				.collect(Collectors.toList());
	}

	public void addBeacon(ThreeDPoint beacon) {
		this.beacons.add(beacon);
	}

	public List<ThreeDPoint> getBeacons() {
		return this.beacons;
	}

	public ThreeDPoint getBeacon(int i) {
		return this.beacons.get(i);
	}

	public static List<Function<ThreeDPoint, ThreeDPoint>> getAllOrientations() {
		List<Function<ThreeDPoint, ThreeDPoint>> allOrientations = new ArrayList<>();
		ROTATE_X.stream().flatMap(rotation -> ROTATE_Y.stream().map(rotation::andThen)).forEach(allOrientations::add);
		ROTATE_Z.stream().flatMap(rotation -> ROTATE_Y.stream().map(rotation::andThen)).forEach(allOrientations::add);
		return allOrientations;
	}
}
