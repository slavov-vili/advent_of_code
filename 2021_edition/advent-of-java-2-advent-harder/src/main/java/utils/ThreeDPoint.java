package utils;

import java.util.Objects;

public class ThreeDPoint {

	public static ThreeDPoint ORIGIN = new ThreeDPoint(0, 0, 0);

	public int x;
	public int y;
	public int z;

	public ThreeDPoint(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public ThreeDPoint(ThreeDPoint otherPoint) {
		this.x = otherPoint.x;
		this.y = otherPoint.y;
		this.z = otherPoint.z;
	}

	public ThreeDPoint translate(ThreeDPoint otherPoint) {
		return new ThreeDPoint(this.x + otherPoint.x, this.y + otherPoint.y, this.z + otherPoint.z);
	}

	public int calcManhattanDistanceFrom(ThreeDPoint otherPoint) {
		ThreeDPoint threeDDistance = this.calc3DDistanceFrom(otherPoint);
		return threeDDistance.x + threeDDistance.y + threeDDistance.z;
	}

	public ThreeDPoint calc3DDistanceFrom(ThreeDPoint otherPoint) {
		int distX = Math.abs(this.x - otherPoint.x);
		int distY = Math.abs(this.y - otherPoint.y);
		int distZ = Math.abs(this.z - otherPoint.z);
		return new ThreeDPoint(distX, distY, distZ);
	}

	public ThreeDPoint calcRelativePositionOf(ThreeDPoint otherPoint) {
		ThreeDPoint distance = calc3DDistanceFrom(otherPoint);
		int directionX = IntegerUtils.compareInts(this.x, otherPoint.x);
		int directionY = IntegerUtils.compareInts(this.y, otherPoint.y);
		int directionZ = IntegerUtils.compareInts(this.z, otherPoint.z);
		return new ThreeDPoint(directionX * distance.x, directionY * distance.y, directionZ * distance.z);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ThreeDPoint))
			return false;
		ThreeDPoint objPoint = (ThreeDPoint) obj;
		return (this.x == objPoint.x) && (this.y == objPoint.y) && (this.z == objPoint.z);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y, this.z);
	}

	@Override
	public String toString() {
		return "ThreeDPoint(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
}
