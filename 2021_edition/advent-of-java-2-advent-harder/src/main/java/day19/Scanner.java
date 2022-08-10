package day19;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Scanner {
	private Set<Beacon> beacons;

	public static final Function<Beacon, Beacon> FRONT = (beacon -> new Beacon(beacon.getX(), beacon.getY(),
			beacon.getZ()));
	public static final Function<Beacon, Beacon> BACK = (beacon -> new Beacon(-beacon.getX(), beacon.getY(),
			-beacon.getZ()));
	public static final Function<Beacon, Beacon> RIGHT = (beacon -> new Beacon(-beacon.getZ(), beacon.getY(),
			beacon.getX()));
	public static final Function<Beacon, Beacon> LEFT = (beacon -> new Beacon(beacon.getZ(), beacon.getY(),
			-beacon.getX()));
	public static final Function<Beacon, Beacon> UP = (beacon -> new Beacon(beacon.getX(), -beacon.getZ(),
			-beacon.getY()));
	public static final Function<Beacon, Beacon> DOWN = (beacon -> new Beacon(beacon.getX(), beacon.getZ(),
			-beacon.getY()));

	public static final List<Function<Beacon, Beacon>> DIRECTIONS = Arrays.asList(FRONT, BACK, RIGHT, LEFT, UP, DOWN);

	public static final Function<Beacon, Beacon> ROTATE_0 = (beacon -> new Beacon(beacon.getX(), beacon.getY(),
			beacon.getZ()));
	public static final Function<Beacon, Beacon> ROTATE_90 = (beacon -> new Beacon(-beacon.getY(), beacon.getX(),
			beacon.getZ()));
	public static final Function<Beacon, Beacon> ROTATE_180 = (beacon -> new Beacon(-beacon.getX(), -beacon.getY(),
			beacon.getZ()));
	public static final Function<Beacon, Beacon> ROTATE_270 = (beacon -> new Beacon(beacon.getY(), -beacon.getX(),
			beacon.getZ()));

	public static final List<Function<Beacon, Beacon>> ROTATIONS = Arrays.asList(ROTATE_0, ROTATE_90, ROTATE_180,
			ROTATE_270);

	public static final List<Function<Beacon, Beacon>> ORIENTATIONS = getAllOrientations();
	
	public Scanner() {
		this.beacons = new HashSet<>();
	}

	public int countIntersects(Scanner other) {
		return (int) other.getBeacons().stream().filter(this::isFromOtherOrientation).count();
	}

	public boolean isFromOtherOrientation(Beacon beacon) {
		return ORIENTATIONS.stream()
				.anyMatch(orientation -> this.seesBeacon(orientation.apply(beacon)));
	}
	
	public boolean seesBeacon(Beacon beacon) {
		return this.getBeacons().contains(beacon);
	}

	public void addBeacon(Beacon beacon) {
		this.beacons.add(beacon);
	}

	public Set<Beacon> getBeacons() {
		return this.beacons;
	}

	public static List<Function<Beacon, Beacon>> getAllOrientations() {
		return DIRECTIONS.stream().flatMap(direction -> ROTATIONS.stream().map(rotation -> direction.andThen(rotation)))
				.collect(Collectors.toList());
	}
}
