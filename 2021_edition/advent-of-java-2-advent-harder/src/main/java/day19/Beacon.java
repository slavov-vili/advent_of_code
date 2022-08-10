package day19;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Beacon {
	private int[] coords;

	public Beacon(int x, int y, int z) {
		this.setCoords(new int[] { x, y, z});
	}

	public Beacon(int[] coords) {
		this.setCoords(coords);
	}

	public static Beacon fromLine(String line) {
		List<Integer> coords = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
		return new Beacon(coords.get(0), coords.get(1), coords.get(2));
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}

	public int[] getCoords() {
		return this.coords;
	}

	public int getX() {
		return this.getCoords()[0];
	}

	public int getY() {
		return this.getCoords()[1];
	}

	public int getZ() {
		return this.getCoords()[2];
	}

	@Override
	public String toString() {
		return String.format("[x=%d, y=%d, z=%d]", this.getX(), this.getY(), this.getZ());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Beacon))
			return false;

		Beacon otherBeacon = (Beacon) other;
		return this.getX() == otherBeacon.getX()
				&& this.getY() == otherBeacon.getY()
				&& this.getZ() == otherBeacon.getZ();
	}
}
